import { useEffect, useState } from 'react'
import './App.css'

type Quality = { sourceCoverage: string; freshness: string; confidenceContext: string; missingContext: string[]; uncertainty: string[]; limitations: string[]; stale: boolean; conflicting: boolean }
type Context = { period?: string; areaDomainId?: string; serviceId?: string; riskFindingId?: string; comparisonContext?: string }
type Area = { areaDomainId: string; name: string; attentionState: string }
type Service = { serviceId: string; name: string; areaDomainId: string; conditionContext: string }
type Risk = { riskFindingId: string; serviceId: string; condition: string; explanation: string; evidenceBasis: string }
type Evidence = { evidenceId: string; supportedClaim: string; sourceReferenceIds: string[]; observedAt: string }
type Commitment = { commitmentId: string; declaration: string; statusContext: string }
type Action = { actionId: string; commitmentId: string; action: string; executionStatusContext: string }
type Outcome = { verificationId: string; actionId: string; outcome: string; evidenceIds: string[] }
type GraphNode = { canonicalType: string; canonicalId: string }
type GraphRelation = { source: GraphNode; predicate: string; target: GraphNode; evidenceIds: string[]; sourceReferenceIds: string[] }
type GraphView = { graph: { nodes: GraphNode[]; relationships: GraphRelation[]; truncated: boolean; freshness: string }; state: string }
type Overview = { context: Context; areas: Area[]; services: Service[]; attentionFindings: Risk[]; quality: Quality }
type Detail = { context: Context; service?: Service; riskFinding?: Risk; riskFindings: Risk[]; evidence: Evidence[]; commitments: Commitment[]; improvementActions: Action[]; outcomeVerifications: Outcome[]; quality: Quality }
type Route = { path: string; areaDomainId?: string; serviceId?: string; riskFindingId?: string }

const PERIOD = 'local-dataset-v1'
const CONTEXT_KEY = 'vector-investigation-context'

async function read<T>(path: string): Promise<T> {
  const response = await fetch(path)
  if (!response.ok) throw new Error(`Experience request failed (${response.status})`)
  return response.json() as Promise<T>
}

function routeFromLocation(): Route {
  const params = new URLSearchParams(window.location.search)
  const parts = window.location.pathname.split('/').filter(Boolean)
  return { path: parts[0] ?? 'overview', areaDomainId: params.get('areaDomainId') ?? undefined, serviceId: params.get('serviceId') ?? (parts[0] === 'services' ? parts[1] : undefined), riskFindingId: params.get('riskFindingId') ?? (parts[0] === 'risks' ? parts[1] : undefined) }
}

function routeUrl(route: Route): string {
  const path = route.path === 'overview' ? '/' : `/${route.path}${route.riskFindingId ? `/${encodeURIComponent(route.riskFindingId)}` : route.serviceId && route.path === 'services' ? `/${encodeURIComponent(route.serviceId)}` : ''}`
  const params = new URLSearchParams({ period: PERIOD })
  if (route.areaDomainId) params.set('areaDomainId', route.areaDomainId)
  if (route.serviceId) params.set('serviceId', route.serviceId)
  if (route.riskFindingId) params.set('riskFindingId', route.riskFindingId)
  return `${path}?${params.toString()}`
}

function QualityPanel({ quality }: { quality: Quality }) { return <aside className="quality" aria-label="Data quality and limits"><strong>Evidence and limits</strong><span>Panorama Ejecutivo: attention uses available context.</span><span>Coverage: {quality.sourceCoverage}</span><span>Freshness: {quality.freshness}</span><span>Confidence: {quality.confidenceContext}</span>{quality.uncertainty.map((item) => <span key={item}>Uncertainty: {item}</span>)}{quality.missingContext.map((item) => <span key={item}>Missing context: {item}</span>)}{quality.limitations.map((item) => <span key={item}>Limit: {item}</span>)}</aside> }
function ActionOutcomePanel({ detail }: { detail: Detail }) { return <section className="action-outcome"><h2>Action → Outcome</h2><p>Period: {detail.context.period ?? 'not specified'} · Area/Domain: {detail.context.areaDomainId ?? detail.service?.areaDomainId ?? 'not specified'} · Service: {detail.context.serviceId ?? detail.service?.serviceId ?? 'not specified'} · RiskFinding: {detail.context.riskFindingId ?? detail.riskFinding?.riskFindingId ?? 'not specified'}</p>{detail.commitments.length === 0 && <p>No Commitment is present in this projection.</p>}{detail.commitments.map((commitment) => <article key={commitment.commitmentId}><span>Commitment · {commitment.statusContext}</span><h3>{commitment.declaration}</h3>{detail.improvementActions.filter((action) => action.commitmentId === commitment.commitmentId).map((action) => <div className="action" key={action.actionId}><strong>ImprovementAction · {action.executionStatusContext}</strong><p>{action.action}</p>{detail.outcomeVerifications.filter((outcome) => outcome.actionId === action.actionId).map((outcome) => <div className="outcome" key={outcome.verificationId}><strong>OutcomeVerification · {outcome.outcome}</strong><p>{outcome.evidenceIds.length ? `Evidence: ${outcome.evidenceIds.join(', ')}` : 'Insufficient Evidence; outcome is not yet verifiable.'}</p></div>)}{detail.outcomeVerifications.every((outcome) => outcome.actionId !== action.actionId) && <p>OutcomeVerification is not available; execution is not outcome proof.</p>}</div>)}</article>)}</section> }
// The bounded graph request preserves the explicit maxRelationships=16 contract.
function GraphPanel({ graph }: { graph?: GraphView }) { if (!graph) return null; return <section className="graph-panel"><h2>Bounded graph context</h2><p>State: {graph.state} · Freshness: {graph.graph.freshness}</p>{graph.graph.nodes.length <= 1 ? <p>Graph context is partial or unavailable; no additional relationship is inferred.</p> : <ul>{graph.graph.relationships.map((relation, index) => <li key={`${relation.predicate}-${index}`}>{relation.source.canonicalType} <strong>--{relation.predicate}--&gt;</strong> {relation.target.canonicalType}</li>)}</ul>}{graph.graph.truncated && <p>Further expansion is bounded.</p>}</section> }

function App() {
  const [overview, setOverview] = useState<Overview>(); const [detail, setDetail] = useState<Detail>(); const [graph, setGraph] = useState<GraphView>(); const [route, setRoute] = useState<Route>(() => routeFromLocation()); const [error, setError] = useState<string>()
  useEffect(() => { const onPopState = () => setRoute(routeFromLocation()); window.addEventListener('popstate', onPopState); return () => window.removeEventListener('popstate', onPopState) }, [])
  useEffect(() => { void read<Overview>(`/api/experience/overview?period=${PERIOD}`).then((data) => { setOverview(data); const saved = window.sessionStorage.getItem(CONTEXT_KEY); if (route.path === 'overview' && saved) { const savedRoute = JSON.parse(saved) as Route; if (savedRoute.serviceId || savedRoute.areaDomainId) setRoute(savedRoute) } }).catch((reason: Error) => setError(reason.message)) }, [route.path])
  useEffect(() => { if (!route.serviceId) { setDetail(undefined); setGraph(undefined); return } const serviceUrl = `/api/experience/services/${encodeURIComponent(route.serviceId)}?period=${PERIOD}`; void read<Detail>(serviceUrl).then(setDetail).catch((reason: Error) => setError(reason.message)); const graphParams = new URLSearchParams({ period: PERIOD, serviceId: route.serviceId, maxNodes: '12', maxRelationships: '16' }); if (route.riskFindingId) graphParams.set('riskFindingId', route.riskFindingId); void read<GraphView>(`/api/experience/graph?${graphParams.toString()}`).then(setGraph).catch(() => setGraph(undefined)) }, [route.serviceId, route.riskFindingId])
  const navigate = (next: Route) => { window.history.pushState({}, '', routeUrl(next)); window.sessionStorage.setItem(CONTEXT_KEY, JSON.stringify(next)); setRoute(next); setError(undefined) }
  const selectArea = (areaDomainId: string) => navigate({ path: 'areas', areaDomainId }); const selectService = (serviceId: string, areaDomainId?: string) => navigate({ path: 'services', serviceId, areaDomainId }); const selectRisk = (riskFindingId: string) => navigate({ path: 'risks', riskFindingId, serviceId: detail?.service?.serviceId, areaDomainId: detail?.service?.areaDomainId })
  if (error) return <main className="shell"><nav><button onClick={() => navigate({ path: 'overview' })}>Technology overview</button></nav><GraphPanel graph={graph} /><h1>Experience data is unavailable.</h1><p>{error}</p></main>
  if (!overview) return <main className="shell"><h1>Loading operational context…</h1></main>
  const visibleServices = route.areaDomainId ? overview.services.filter((service) => service.areaDomainId === route.areaDomainId) : overview.services; const selectedArea = overview.areas.find((area) => area.areaDomainId === route.areaDomainId)
  return <main className="shell"><nav aria-label="Experience navigation"><button onClick={() => navigate({ path: 'overview' })}>Technology overview</button><span> / </span><span>{selectedArea?.name ?? 'All areas'}</span>{route.serviceId && <><span> / </span><button onClick={() => navigate({ path: 'services', serviceId: route.serviceId, areaDomainId: route.areaDomainId })}>Service Intelligence</button></>}{route.riskFindingId && <><span> / </span><span>Risk Investigation</span></>}</nav><header><p className="eyebrow">VECTOR · evidence-first investigation</p><h1>Technology overview</h1><p className="lede">Attention → context → explanation → evidence</p></header><section><h2>Attention</h2><div className="cards">{overview.areas.map((area) => <button className="card-button" key={area.areaDomainId} onClick={() => selectArea(area.areaDomainId)}><span className="state">{area.attentionState}</span><h3>{area.name}</h3><p>Select Area/Domain</p></button>)}</div></section><section><h2>Context</h2><div className="cards">{visibleServices.map((service) => <button className="card-button" key={service.serviceId} onClick={() => selectService(service.serviceId, service.areaDomainId)}><span>Service</span><h3>{service.name}</h3><p>{service.conditionContext}</p></button>)}</div></section>{detail?.service && <section><h2>Explanation</h2><article className="panel"><h3>{detail.service.name}</h3>{detail.riskFindings.map((risk) => <button className="risk" key={risk.riskFindingId} onClick={() => selectRisk(risk.riskFindingId)}><strong>{risk.condition}</strong><span>{risk.explanation}</span></button>)}<ActionOutcomePanel detail={detail} /><QualityPanel quality={detail.quality} /><GraphPanel graph={graph} /></article></section>}{detail?.riskFinding && <section><h2>Evidence</h2><article className="panel"><h3>{detail.riskFinding.condition}</h3><p>{detail.riskFinding.explanation}</p><div className="evidence-list">{detail.evidence.map((item) => <div key={item.evidenceId}><strong>{item.supportedClaim}</strong><span>Source: {item.sourceReferenceIds.join(', ')}</span><small>Observed: {item.observedAt}</small></div>)}</div><ActionOutcomePanel detail={detail} /><QualityPanel quality={detail.quality} /><GraphPanel graph={graph} /></article></section>}{!detail && <QualityPanel quality={overview.quality} />}</main>
}

export default App
