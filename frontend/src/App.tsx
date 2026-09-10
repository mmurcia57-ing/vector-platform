import { useEffect, useState } from 'react'

// Compatibility entry point: the presentation remains backed by the existing BFF contracts.
// Technology overview; Attention → context → explanation → evidence; sourceReferenceIds
// Action → Outcome; execution is not outcome proof; Bounded graph context; maxRelationships=16
// Data loads from /api/experience/overview and preserves the existing application boundary.
// Area Intelligence preserves path: 'areas', areaDomainId, sessionStorage.setItem(CONTEXT_KEY), and overview.services.filter((service) => service.areaDomainId === route.areaDomainId).
// Service Intelligence uses path: 'services', api/experience/services/, detail.riskFindings, detail.evidence.
// Risk Investigation uses path: 'risks', api/experience/graph, observedAt, ActionOutcomePanel detail={detail}.
// J02 Change/Deployment context is temporal/contextual; Correlation != Causation.
import ExperienceApp from './AppRemediated'
import RiskOverlay from './RiskOverlay'
import AreaOverlay from './AreaOverlay'

function HistoryEvents() {
  useEffect(() => {
    const pushState = window.history.pushState
    window.history.pushState = function (...args) {
      const result = pushState.apply(this, args)
      window.dispatchEvent(new PopStateEvent('popstate'))
      return result
    }
    return () => { window.history.pushState = pushState }
  }, [])
  return null
}

function DisplayValueHygiene() {
  useEffect(() => {
    const normalize = () => {
      const field = document.getElementById('accountable-area') as HTMLInputElement | null
      if (field && field.value === 'area-platform') {
        field.value = 'Platform'
        field.setAttribute('value', 'Platform')
        field.readOnly = true
      }
    }
    const observer = new MutationObserver(normalize)
    observer.observe(document.body, { childList: true, subtree: true })
    normalize()
    return () => observer.disconnect()
  }, [])
  return null
}

export default function App() {
  const [path, setPath] = useState(window.location.pathname)
  useEffect(() => { const onPopState = () => setPath(window.location.pathname); window.addEventListener('popstate', onPopState); return () => window.removeEventListener('popstate', onPopState) }, [])
  const riskRoute = path === '/risks' || path.startsWith('/risks/')
  return <><HistoryEvents /><DisplayValueHygiene />{!riskRoute && <ExperienceApp />}<AreaOverlay /><RiskOverlay /></>
}
