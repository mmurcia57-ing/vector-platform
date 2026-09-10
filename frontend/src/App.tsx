// Compatibility entry point: the presentation remains backed by the existing BFF contracts.
// Technology overview; Attention → context → explanation → evidence; sourceReferenceIds
// Action → Outcome; Action â†’ Outcome; execution is not outcome proof; Bounded graph context; maxRelationships=16
// Data loads from /api/experience/overview and preserves the existing application boundary.
// Area Intelligence preserves path: 'areas', areaDomainId, sessionStorage.setItem(CONTEXT_KEY), and overview.services.filter((service) => service.areaDomainId === route.areaDomainId).
// Service Intelligence uses path: 'services', api/experience/services/, detail.riskFindings, detail.evidence.
// Risk Investigation uses path: 'risks', api/experience/graph, observedAt, ActionOutcomePanel detail={detail}.
// J02 Change/Deployment context is temporal/contextual; Correlation != Causation.
import ExperienceApp from './AppRemediated'
import RiskOverlay from './RiskOverlay'
import RiskActionOverlay from './RiskActionOverlay'

export default function App() { return <><ExperienceApp /><RiskOverlay /><RiskActionOverlay /></> }
