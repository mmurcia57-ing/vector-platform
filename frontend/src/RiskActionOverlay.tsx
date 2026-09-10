import { useEffect, useState } from 'react'

type Commitment = { commitmentId: string; declaration: string; statusContext: string }
type Action = { actionId: string; commitmentId: string; action: string; executionStatusContext: string }
type Outcome = { verificationId: string; actionId: string; outcome: string; evidenceIds: string[] }
type Data = { commitments: Commitment[]; improvementActions: Action[]; outcomeVerifications: Outcome[] }

export default function RiskActionOverlay() {
  const [data, setData] = useState<Data>(); const [path, setPath] = useState(window.location.pathname)
  useEffect(() => { const onPopState = () => setPath(window.location.pathname); window.addEventListener('popstate', onPopState); return () => window.removeEventListener('popstate', onPopState) }, [])
  useEffect(() => { if (!path.startsWith('/risks/')) return; const riskId = decodeURIComponent(path.split('/')[2] ?? ''); void fetch(`/api/experience/risks/${encodeURIComponent(riskId)}?period=local-dataset-v1`).then((response) => response.json()).then(setData) }, [path])
  if (!path.startsWith('/risks/') || !data) return null
  return <section className="risk-action-overlay panel"><div className="section-kicker">Decision continuity</div><h2>Action <span>→</span> Outcome</h2><p className="muted">Execution is tracked separately from structural outcome verification.</p>{data.commitments.map((commitment) => <article className="commitment-row" key={commitment.commitmentId}><div className="row-top"><span className="label">Commitment</span><span className="status status-success">{commitment.statusContext}</span></div><h3>{commitment.declaration}</h3>{data.improvementActions.filter((action) => action.commitmentId === commitment.commitmentId).map((action) => <div className="action-row" key={action.actionId}><div className="row-top"><span className="label">ImprovementAction</span><span className="status status-success">{action.executionStatusContext}</span></div><p>{action.action}</p>{data.outcomeVerifications.filter((outcome) => outcome.actionId === action.actionId).map((outcome) => <div className="outcome-row" key={outcome.verificationId}><div className="row-top"><span className="label">OutcomeVerification</span><span className="status status-warning">{outcome.outcome}</span></div><p>{outcome.evidenceIds.length ? `Evidence: ${outcome.evidenceIds.join(', ')}` : 'Insufficient Evidence; outcome is not yet verifiable.'}</p></div>)}</div>)}</article>)}</section>
}
