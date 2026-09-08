import { useEffect, useState } from 'react'
import './App.css'

type Quality = { sourceCoverage: string; freshness: string; confidenceContext: string; missingContext: string[]; uncertainty: string[]; limitations: string[]; stale: boolean; conflicting: boolean }
type Context = { period?: string; areaDomainId?: string; serviceId?: string; riskFindingId?: string; comparisonContext?: string }
type Area = { areaDomainId: string; name: string; attentionState: string }
type Service = { serviceId: string; name: string; areaDomainId: string; conditionContext: string }
type Risk = { riskFindingId: string; serviceId: string; condition: string; explanation: string; evidenceBasis: string }
type Evidence = { evidenceId: string; supportedClaim: string; sourceReferenceIds: string[]; limitations: string; observedAt: string }
type Overview = { context: Context; areas: Area[]; services: Service[]; attentionFindings: Risk[]; quality: Quality }
type ServiceView = { context: Context; service: Service; riskFindings: Risk[]; evidence: Evidence[]; quality: Quality }
type RiskView = { context: Context; riskFinding: Risk; evidence: Evidence[]; quality: Quality }

async function read<T>(path: string): Promise<T> {
  const response = await fetch(path)
  if (!response.ok) throw new Error(`Experience request failed (${response.status})`)
  return response.json() as Promise<T>
}

function QualityPanel({ quality }: { quality: Quality }) {
  return <aside className="quality" aria-label="Data quality and limits"><strong>Evidence and limits</strong><span>Coverage: {quality.sourceCoverage}</span><span>Freshness: {quality.freshness}</span><span>Confidence: {quality.confidenceContext}</span>{quality.uncertainty.map((item) => <span key={item}>Uncertainty: {item}</span>)}{quality.missingContext.map((item) => <span key={item}>Missing context: {item}</span>)}{quality.limitations.map((item) => <span key={item}>Limit: {item}</span>)}{quality.stale && <span>State: stale</span>}{quality.conflicting && <span>State: conflicting claims</span>}</aside>
}

function App() {
  const [overview, setOverview] = useState<Overview>()
  const [serviceView, setServiceView] = useState<ServiceView>()
  const [riskView, setRiskView] = useState<RiskView>()
  const [error, setError] = useState<string>()
  useEffect(() => { void read<Overview>('/api/experience/overview?period=local-dataset-v1').then(setOverview).catch((reason: Error) => setError(reason.message)) }, [])
  const selectService = (serviceId: string) => { setError(undefined); setRiskView(undefined); void read<ServiceView>(`/api/experience/services/${serviceId}?period=local-dataset-v1`).then(setServiceView).catch((reason: Error) => setError(reason.message)) }
  const selectRisk = (riskId: string) => { setError(undefined); void read<RiskView>(`/api/experience/risks/${riskId}?period=local-dataset-v1&serviceId=${serviceView?.service.serviceId ?? ''}`).then(setRiskView).catch((reason: Error) => setError(reason.message)) }
  if (error) return <main className="shell"><p className="eyebrow">VECTOR · investigation slice</p><h1>Experience data is unavailable.</h1><p>{error}</p></main>
  if (!overview) return <main className="shell"><p className="eyebrow">VECTOR · investigation slice</p><h1>Loading operational context…</h1></main>
  return <main className="shell"><header><p className="eyebrow">VECTOR · evidence-first investigation</p><h1>Technology overview</h1><p className="lede">Attention → context → explanation → evidence</p></header><section className="attention"><h2>Attention</h2><div className="cards">{overview.areas.map((area) => <article key={area.areaDomainId}><span className="state">{area.attentionState}</span><h3>{area.name}</h3><p>Area / Domain requiring attention</p></article>)}</div></section><section><h2>Context</h2><div className="cards">{overview.services.map((service) => <button className="card-button" key={service.serviceId} onClick={() => selectService(service.serviceId)}><span>Service</span><h3>{service.name}</h3><p>{service.conditionContext}</p></button>)}</div></section>{serviceView && <section><h2>Explanation</h2><article className="panel"><h3>{serviceView.service.name}</h3>{serviceView.riskFindings.map((risk) => <button className="risk" key={risk.riskFindingId} onClick={() => selectRisk(risk.riskFindingId)}><strong>{risk.condition}</strong><span>{risk.explanation}</span></button>)}<QualityPanel quality={serviceView.quality} /></article></section>}{riskView && <section><h2>Evidence</h2><article className="panel"><h3>{riskView.riskFinding.condition}</h3><p>{riskView.riskFinding.explanation}</p><div className="evidence-list">{riskView.evidence.map((item) => <div key={item.evidenceId}><strong>{item.supportedClaim}</strong><span>Source: {item.sourceReferenceIds.join(', ')}</span><small>Observed: {item.observedAt}</small></div>)}</div><QualityPanel quality={riskView.quality} /></article></section>}{!serviceView && <QualityPanel quality={overview.quality} />}</main>
}

export default App
