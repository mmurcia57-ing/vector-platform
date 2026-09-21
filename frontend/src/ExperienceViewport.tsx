import { createContext, useContext, useEffect, useState } from "react";
import type { FormEvent } from "react";
import { createPortal } from "react-dom";
import "./IntelligenceWorkspace.css";

type UiLocale = "es" | "en";
const COPY = {
  es: {
    command:"Panorama", area:"Área", service:"Servicio", investigation:"Investigación", actionOutcome:"Acciones y resultados",
    workspace:"Espacio de inteligencia basado en evidencia", layers:"Capas de investigación",
    loop:"SEÑAL → FOCO → EXPLICAR → RELACIONAR → DECIDIR → ACTUAR → VERIFICAR",
    observed:"Evidencia observada", derived:"Inteligencia derivada", uncertain:"Correlación / incertidumbre", verified:"Resultado verificado",
    decisionQueue:"Cola de decisiones", investigate:"Investigar", operationalContext:"CONTEXTO OPERACIONAL", attentionMap:"Mapa de atención y servicios",
    findings:"hallazgos", serviceContext:"servicio en contexto", evidenceAttention:"atención sustentada",
    contextDisclaimer:"Relación visual de contexto; no representa causalidad ni telemetría en tiempo real.",
    temporal:"ESPINA TEMPORAL / EVENTOS", evolution:"Cómo evoluciona el contexto", noTimeline:"No hay secuencia temporal suficiente.",
    loading:"Cargando inteligencia basada en evidencia…", unavailable:"Inteligencia temporalmente no disponible", retry:"Reintentar",
    quality:"CALIDAD", stale:"DESACTUALIZADA", partial:"PARCIAL", available:"DISPONIBLE", fact:"HECHO",
    before:"ANTES", change:"CAMBIO", after:"DESPUÉS", intelligence:"Inteligencia VECTOR", locale:"Idioma",
    scenario:"Escenario de análisis", demoBoundary:"Datos demostrativos locales · no producción", workspaceName:"Espacio de trabajo de inteligencia VECTOR", workspaceShort:"ESPACIO", semanticLegend:"Leyenda semántica de evidencia", mapLabel:"Mapa operacional", temporalLabel:"Espina temporal", currentContext:"contexto actual", riskFinding:"HALLAZGO DE RIESGO", evidenceStale:"Evidencia desactualizada", evidenceAvailable:"Evidencia disponible", supportedContext:"Contexto soportado por la evidencia disponible.", footerMotto:"Un mejor mañana, construido con evidencia.", queueSummary:"condiciones sustentadas por evidencia requieren revisión", overdueSummary:"compromisos vencidos en el contexto disponible", spatialGraph:"Topología contextual acotada", accessibleRelations:"Relaciones accesibles", selectedContext:"Contexto seleccionado", boundedContext:"Contexto relacional acotado; seleccionar un nodo no afirma causalidad.", expandContext:"Ampliar datos", expandGraph:"Abrir grafo", closeGraph:"Cerrar grafo", resetLimit:"Restablecer límite", freshness:"Frescura", boundedMore:"Vista acotada: existen relaciones adicionales.", boundedComplete:"Vista acotada completa para el límite actual."
  },
  en: {
    command:"Command", area:"Area", service:"Service", investigation:"Investigation", actionOutcome:"Actions & Outcomes",
    workspace:"Evidence-led intelligence workspace", layers:"Investigation layers",
    loop:"SIGNAL → FOCUS → EXPLAIN → RELATE → DECIDE → ACT → VERIFY",
    observed:"Observed evidence", derived:"Derived intelligence", uncertain:"Correlation / uncertainty", verified:"Verified outcome",
    decisionQueue:"Decision queue", investigate:"Investigate", operationalContext:"OPERATIONAL CONTEXT", attentionMap:"Attention and service map",
    findings:"findings", serviceContext:"service in context", evidenceAttention:"evidence-backed attention",
    contextDisclaimer:"Contextual visual relationship; it does not represent causality or real-time telemetry.",
    temporal:"TEMPORAL / EVENT SPINE", evolution:"How the context evolves", noTimeline:"There is not enough temporal sequence.",
    loading:"Loading evidence-backed intelligence…", unavailable:"Intelligence temporarily unavailable", retry:"Retry",
    quality:"QUALITY", stale:"STALE", partial:"PARTIAL", available:"AVAILABLE", fact:"FACT",
    before:"BEFORE", change:"CHANGE", after:"AFTER", intelligence:"VECTOR Intelligence", locale:"Language",
    scenario:"Analysis scenario", demoBoundary:"Local demonstration data · not production", workspaceName:"VECTOR intelligence workspace", workspaceShort:"WORKSPACE", semanticLegend:"Semantic evidence legend", mapLabel:"Operational map", temporalLabel:"Temporal spine", currentContext:"current context", riskFinding:"RISK FINDING", evidenceStale:"Stale evidence", evidenceAvailable:"Evidence available", supportedContext:"Context supported by available evidence.", footerMotto:"A better tomorrow, built with evidence.", queueSummary:"evidence-backed conditions require review", overdueSummary:"overdue commitments in available context", spatialGraph:"Bounded contextual topology", accessibleRelations:"Accessible relationships", selectedContext:"Selected context", boundedContext:"Bounded relational context; selecting a node does not assert causality.", expandContext:"Expand data", expandGraph:"Open graph", closeGraph:"Close graph", resetLimit:"Reset limit", freshness:"Freshness", boundedMore:"Bounded view: additional relationships exist.", boundedComplete:"Bounded view complete for the current limit."
  }
} as const;
const LocaleContext = createContext<UiLocale>("es");
const useCopy = () => COPY[useContext(LocaleContext)];

const DEFAULT_PERIOD = "local-dataset-v1";
const PERIOD_OPTIONS = {
  es: [
    ["local-dataset-v1", "Escenario base"],
    ["local-partial-stale", "Evidencia parcial / desactualizada"],
    ["local-outcome-pending", "Acción completa / resultado pendiente"],
    ["local-insufficient-evidence", "Evidencia insuficiente"],
    ["local-healthy-control", "Control sano / sin riesgo"],
    ["local-conflicting-evidence", "Evidencia en conflicto"],
    ["local-outcome-improved", "Mejora verificada"],
    ["local-change-associated", "Degradación posterior a cambio"],
    ["local-change-predates", "Degradación anterior al cambio"],
  ],
  en: [
    ["local-dataset-v1", "Baseline scenario"],
    ["local-partial-stale", "Partial / stale evidence"],
    ["local-outcome-pending", "Action complete / outcome pending"],
    ["local-insufficient-evidence", "Insufficient evidence"],
    ["local-healthy-control", "Healthy / no-risk control"],
    ["local-conflicting-evidence", "Conflicting evidence"],
    ["local-outcome-improved", "Verified improvement"],
    ["local-change-associated", "Degradation after change"],
    ["local-change-predates", "Degradation predates change"],
  ],
} as const;
type Area = { areaDomainId: string; name: string; attentionState: string };
type Service = {
  serviceId: string;
  name: string;
  areaDomainId: string;
  conditionContext: string;
};
type Risk = {
  riskFindingId: string;
  serviceId: string;
  condition: string;
  explanation: string;
};
type ChangeAssociation = { serviceId:string; riskFindingId:string; changeId:string; deploymentId:string; temporalContext:string; contextualAssociation:boolean; causalClaim:boolean; evidenceIds:string[]; limitation:string };
type AiAssist = { status: "AVAILABLE" | "UNAVAILABLE" | "INVALID"; explanation?: string; limitations: string[]; provenance: string };
type TemporalSignal = {
  signalId: string;
  serviceId: string;
  riskFindingId: string;
  semanticType: string;
  statement: string;
  observedAt: string;
  sourceReferenceIds: string[];
  limitation: string;
};
type Evidence = {
  evidenceId: string;
  supportedClaim: string;
  sourceReferenceIds: string[];
  observedAt: string;
};
type Commitment = {
  commitmentId: string;
  declaration: string;
  statusContext?: string;
  executionStatus?: string;
  accountableAreaDomainId?: string;
  overdue?: boolean;
  sourceReferenceSummary?: string;
  currentDueDate?: string;
  serviceId?: string;
  riskFindingId?: string;
};
type Action = {
  actionId: string;
  commitmentId: string;
  action: string;
  executionStatusContext: string;
};
type Outcome = {
  verificationId: string;
  actionId: string;
  outcome: string;
  evidenceIds: string[];
};
type Quality = {
  sourceCoverage: string;
  freshness: string;
  confidenceContext: string;
  uncertainty: string[];
  limitations: string;
  stale?: boolean;
  conflicting?: boolean;
  missingContext?: string[];
};
type Overview = {
  areas: Area[];
  services: Service[];
  attentionFindings: Risk[];
  commitments: Commitment[];
  improvementActions: Action[];
  outcomeVerifications: Outcome[];
  quality: Quality;
};
type Detail = {
  service?: Service;
  riskFinding?: Risk;
  riskFindings: Risk[];
  evidence: Evidence[];
  commitments: Commitment[];
  improvementActions: Action[];
  outcomeVerifications: Outcome[];
  quality: Quality;
};
type CommitmentView = {
  commitments: Commitment[];
  activeCount: number;
  inProgressCount: number;
  completedCount: number;
  overdueCount: number;
  renegotiatedCount: number;
  outcomePendingCount: number;
  reliabilityNumerator: number;
  reliabilityDenominator: number;
  commitmentReliabilityRate?: number;
};
type Graph = {
  graph: {
    relationships: {
      source: { canonicalId: string };
      predicate: string;
      target: { canonicalId: string };
    }[];
    freshness: string;
    truncated: boolean;
  };
  state: string;
};

const label = (value = "") =>
  value
    .replace(
      /^(area-|service-|evidence-|risk-finding:|commitment-|action-)/,
      "",
    )
    .replace(/[-:]/g, " ")
    .replace(/\b\w/g, (letter) => letter.toUpperCase());
const read = async <T,>(url: string) => {
  const response = await fetch(url);
  if (!response.ok)
    throw new Error(`HTTP ${response.status}`);
  return response.json() as Promise<T>;
};
const routePath = () =>
  window.location.pathname.split("/").filter(Boolean)[0] ?? "overview";
const routeId = () =>
  decodeURIComponent(
    window.location.pathname.split("/").filter(Boolean)[1] ?? "",
  );

export function buildJourneyUrl(next: string, currentSearch: string, period: string, currentPath: string, context: Record<string, string> = {}) {
  const current = new URLSearchParams(currentSearch);
  const preserved = ["areaDomainId", "serviceId", "riskFindingId", "condition", "origin", "commitmentId", "actionId", "outcomeVerificationId"];
  const merged: Record<string, string> = { period };
  preserved.forEach((key) => {
    const value = current.get(key);
    if (value) merged[key] = value;
  });
  Object.entries(context).forEach(([key, value]) => {
    if (value) merged[key] = value;
  });
  if (!merged.origin) merged.origin = currentPath;
  return `${next}?${new URLSearchParams(merged)}`;
}

function MiniBars({ values, tone = "blue" }: { values: number[]; tone?: string }) { const max = Math.max(...values, 1); return <div className={"vx-mini-bars "+tone} aria-hidden="true">{values.map((v,i)=><i key={i} style={{height:(Math.max(12,(v/max)*100))+"%"}} />)}</div>; }
function Donut({ value, labelText }: { value: number; labelText: string }) { const safe=Math.max(0,Math.min(100,value)); return <div className="vx-donut" style={{background:"conic-gradient(#1669b2 0 "+safe+"%, #e4edf4 "+safe+"% 100%)"}}><span><b>{safe}%</b><small>{labelText}</small></span></div>; }
function Metric({
  tone,
  title,
  value,
  note,
}: {
  tone: string;
  title: string;
  value: string | number;
  note: string;
}) {
  return (
    <article className={`gold-metric ${tone}`}>
      <span>{title}</span>
      <strong>{value}</strong>
      <small>{note}</small>
    </article>
  );
}
function Empty({ children }: { children: string }) {
  return <div className="gold-empty">{children}</div>;
}
export function Header({
  eyebrow,
  title,
  question,
  period,
  onPeriodChange,
}: {
  eyebrow: string;
  title: string;
  question: string;
  period: string;
  onPeriodChange: (period: string) => void;
}) {
  const locale = useContext(LocaleContext);
  const t = COPY[locale];
  return (
    <header className="gold-header">
      <div>
        <span>{eyebrow}</span>
        <h1>{title}</h1>
        <p>{question}</p>
      </div>
      <div className="gold-filters">
        <label className="vx-context-control">
          <span>{t.scenario}</span>
          <select aria-label={t.scenario} value={period} onChange={(event) => onPeriodChange(event.target.value)}>
            {PERIOD_OPTIONS[locale].map(([value, text]) => <option key={value} value={value}>{text}</option>)}
          </select>
        </label>
        <span className="vx-context-meta">{t.demoBoundary}</span>
      </div>
    </header>
  );
}
function SectionTitle({
  title,
  subtitle,
  id,
}: {
  title: string;
  subtitle?: string;
  id?: string;
}) {
  return (
    <div className="gold-section-title" id={id}>
      <div>
        <h2>{title}</h2>
        {subtitle && <p>{subtitle}</p>}
      </div>
    </div>
  );
}
export function LensNav({ items, labelText }: { items: readonly (readonly [string, string])[]; labelText: string }) {
  const focus = (id: string) => {
    document.getElementById(id)?.scrollIntoView({ behavior: "smooth", block: "start" });
    window.history.replaceState({}, "", `${window.location.pathname}${window.location.search}#${id}`);
  };
  return <nav className="gold-tabs vx-lenses" aria-label={labelText}>
    {items.map(([id, text]) => <button key={id} type="button" onClick={() => focus(id)}>{text}</button>)}
  </nav>;
}

export function WorkspaceRail({ active, navigate }: { active: string; navigate: (next: string, context?: Record<string, string>) => void }) {
  const t = useCopy();
  const items = [
    ["overview", "/", t.command],
    ["areas", "/areas", t.area],
    ["services", "/services", t.service],
    ["risks", "/risks", t.investigation],
    ["commitments", "/commitments", t.actionOutcome],
  ];
  return (
    <div className="vx-workspace-rail" aria-label={t.workspaceName}>
      <div><strong>{t.intelligence.toUpperCase()} / {t.workspaceShort}</strong><small>{t.workspace}</small></div>
      <nav aria-label={t.layers}>
        {items.map(([key, route, text]) => <button key={key} className={active === key ? "active" : ""} onClick={() => navigate(route)}>{text}</button>)}
      </nav>
      <div className="vx-loop">{t.loop}</div>
    </div>
  );
}
export function SemanticLegend() {
  const t = useCopy();
  return <div className="vx-semantics" aria-label={t.semanticLegend}>
    <span className="vx-semantic fact">{t.observed}</span>
    <span className="vx-semantic">{t.derived}</span>
    <span className="vx-semantic uncertain">{t.uncertain}</span>
    <span className="vx-semantic outcome">{t.verified}</span>
  </div>;
}

export function ContextEnvelope({ area, service, risk, period, quality }: { area?: Area; service?: Service; risk?: Risk; period: string; quality?: Quality }) {
  const t = useCopy();
  const items = [
    area ? [t.area, area.name] : undefined,
    service ? [t.service, service.name] : undefined,
    risk ? [t.riskFinding, risk.condition] : undefined,
    [t.scenario, period],
    [t.quality, quality?.stale ? t.stale : quality?.missingContext?.length ? t.partial : quality ? t.available : "—"],
  ].filter(Boolean) as string[][];
  return <section className="vx-context-envelope" aria-label={t.selectedContext}>
    <strong>{t.selectedContext}</strong>
    <div>{items.map(([key,value]) => <span key={key}><small>{key}</small><b>{value}</b></span>)}</div>
    <em>{t.contextDisclaimer}</em>
  </section>;
}

function DecisionQueue({ risks, commitments, navigate }: { risks: Risk[]; commitments?: CommitmentView; navigate: (next: string, context?: Record<string, string>) => void }) {
  const t = useCopy();
  return <section className="vx-decision-queue" aria-label={t.decisionQueue}>
    <div><span>{t.decisionQueue.toUpperCase()}</span><strong>{risks.length} {t.queueSummary}</strong><small>{commitments?.overdueCount ?? 0} {t.overdueSummary}</small></div>
    <div className="vx-decision-items">{risks.slice(0, 3).map((risk) => <button key={risk.riskFindingId} onClick={() => navigate("/risks", { riskFindingId: risk.riskFindingId, serviceId: risk.serviceId })}><b>{t.investigate}</b><span>{risk.condition}</span><small>{risk.explanation}</small></button>)}</div>
  </section>;
}

function TemporalSpine({ signals, risks }: { signals?: TemporalSignal[]; risks?: Risk[] }) {
  const t = useCopy();
  const items = signals?.length ? signals.slice(0, 8).map((signal) => ({ id: signal.signalId, type: signal.semanticType, text: signal.statement, time: signal.observedAt.slice(0, 10) })) : (risks ?? []).slice(0, 6).map((risk) => ({ id: risk.riskFindingId, type: t.riskFinding, text: risk.condition, time: t.currentContext }));
  return <section className="vx-temporal-spine" aria-label={t.temporalLabel}>
    <div className="vx-spine-title"><small>{t.temporal}</small><strong>{t.evolution}</strong></div>
    <div className="vx-spine-track">{items.length ? items.map((item) => <div className="vx-spine-event" key={item.id}><i /><small>{item.time}</small><b>{item.type.replaceAll("_", " ")}</b><span>{item.text}</span></div>) : <span className="vx-spine-empty">{t.noTimeline}</span>}</div>
  </section>;
}

function SpatialGraph({ graph, focus, onFocus, nodeLabel, onExpand, onReset, limit }: { graph?: Graph; focus: string; onFocus: (id: string) => void; nodeLabel: (id: string) => string; onExpand: () => void; onReset: () => void; limit: number }) {
  const t = useCopy();
  const [graphOpen, setGraphOpen] = useState(false);
  const relationships = graph?.graph.relationships ?? [];
  const ids = Array.from(new Set(relationships.flatMap((r) => [r.source.canonicalId, r.target.canonicalId])));
  const positions = new Map(ids.map((id, index) => {
    const angle = (Math.PI * 2 * index) / Math.max(ids.length, 1) - Math.PI / 2;
    const radius = ids.length <= 4 ? 31 : 38;
    return [id, { x: 50 + Math.cos(angle) * radius, y: 50 + Math.sin(angle) * radius }];
  }));
  const stage = (expanded = false) => <div className={`vx-spatial-stage ${expanded ? "expanded" : ""}`} role="group" aria-label={t.spatialGraph}>
      <svg className="vx-spatial-edges" viewBox="0 0 100 100" preserveAspectRatio="none" aria-hidden="true">
        {relationships.map((relation, index) => {
          const a = positions.get(relation.source.canonicalId); const b = positions.get(relation.target.canonicalId);
          if (!a || !b) return null;
          return <line key={`edge-${index}`} x1={a.x} y1={a.y} x2={b.x} y2={b.y} />;
        })}
      </svg>
      {ids.map((id) => {
        const p = positions.get(id)!;
        const degree = relationships.filter((r) => r.source.canonicalId === id || r.target.canonicalId === id).length;
        return <button type="button" key={id} className={`vx-spatial-node ${focus === id ? "focused" : ""}`} style={{ left: `${p.x}%`, top: `${p.y}%` }} onClick={() => onFocus(id)} aria-pressed={focus === id}>
          <strong>{nodeLabel(id)}</strong><small>{degree} rel.</small>
        </button>;
      })}
      {relationships.map((relation, index) => {
        const a = positions.get(relation.source.canonicalId); const b = positions.get(relation.target.canonicalId);
        if (!a || !b) return null;
        return <span aria-hidden="true" className="vx-spatial-edge-label" key={`label-${index}`} style={{ left: `${(a.x+b.x)/2}%`, top: `${(a.y+b.y)/2}%` }}>{relation.predicate.replaceAll("_"," ")}</span>;
      })}
    </div>;
  return <div className="vx-spatial-graph">
    {stage()}
    <div className="vx-graph-primary-actions"><button type="button" onClick={() => setGraphOpen(true)}>{t.expandGraph}</button></div>
    {graphOpen && createPortal(<div className="vx-graph-modal-backdrop" role="presentation" onMouseDown={() => setGraphOpen(false)}>
      <section className="vx-graph-modal" role="dialog" aria-modal="true" aria-label={t.spatialGraph} onMouseDown={(event) => event.stopPropagation()}>
        <header><div><small>{t.investigation.toUpperCase()}</small><h2>{t.spatialGraph}</h2></div><button type="button" onClick={() => setGraphOpen(false)} aria-label={t.closeGraph}>× <span>{t.closeGraph}</span></button></header>
        {stage(true)}
        <div className="vx-graph-modal-meta"><span>{graph?.graph.truncated ? t.boundedMore : t.boundedComplete}</span><span>{t.freshness}: {graph?.graph.freshness ?? "—"}</span></div>
      </section>
    </div>, document.body)}
    <details className="vx-graph-accessible"><summary>{t.accessibleRelations}</summary>
      <ul>{relationships.map((relation,index)=><li key={index}><button type="button" onClick={()=>onFocus(relation.source.canonicalId)}>{nodeLabel(relation.source.canonicalId)}</button> <b>{relation.predicate.replaceAll("_"," ")}</b> <button type="button" onClick={()=>onFocus(relation.target.canonicalId)}>{nodeLabel(relation.target.canonicalId)}</button></li>)}</ul>
    </details>
    {focus && <div className="vx-graph-focus" aria-live="polite"><strong>{t.selectedContext}</strong><span>{nodeLabel(focus)}</span><small>{t.boundedContext}</small></div>}
    <div className="vx-graph-controls">
      <span>{graph?.graph.truncated ? t.boundedMore : t.boundedComplete}</span>
      <span>{t.freshness}: {graph?.graph.freshness ?? "—"}</span>
      {graph?.graph.truncated && <button type="button" onClick={onExpand}>{t.expandContext}</button>}
      {limit > 6 && <button type="button" onClick={onReset}>{t.resetLimit}</button>}
    </div>
  </div>;
}

function QualityNote({ quality }: { quality?: Quality }) {
  const t = useCopy();
  return (
    <footer className="gold-footer">
      <strong>VECTOR</strong>
      <span>
        {quality
          ? `${quality.sourceCoverage} · ${quality.freshness}`
          : t.supportedContext}
      </span>
      <b>{t.footerMotto}</b>
    </footer>
  );
}

export default function ExperienceViewport() {
  const [host, setHost] = useState<Element | null>(null);
  const [path, setPath] = useState(routePath());
  const [locale, setLocale] = useState<UiLocale>(() => (localStorage.getItem("vector-ui-locale") === "en" ? "en" : "es"));
  const t = COPY[locale];
  const tr = (es: string, en: string) => locale === "es" ? es : en;
  const [period, setPeriod] = useState(() => new URLSearchParams(window.location.search).get("period") || DEFAULT_PERIOD);
  const [overview, setOverview] = useState<Overview>();
  const [detail, setDetail] = useState<Detail>();
  const [risk, setRisk] = useState<Detail>();
  const [commitments, setCommitments] = useState<CommitmentView>();
  const [graph, setGraph] = useState<Graph>();
  const [signals, setSignals] = useState<TemporalSignal[]>([]);
  const [graphFocus, setGraphFocus] = useState("");
  const [graphLimit, setGraphLimit] = useState(6);
  const [aiAssist, setAiAssist] = useState<AiAssist>();
  const [changeAssociation, setChangeAssociation] = useState<ChangeAssociation>();
  const [declaration, setDeclaration] = useState("");
  const [intendedResult, setIntendedResult] = useState("");
  const [commitmentDueDate, setCommitmentDueDate] = useState("");
  const [renegotiationId, setRenegotiationId] = useState("");
  const [renegotiationDate, setRenegotiationDate] = useState("");
  const [renegotiationReason, setRenegotiationReason] = useState("");
  const [loadError, setLoadError] = useState("");
  useEffect(() => {
    const attach = () => {
      const contentShell = document.querySelector(".content-shell");
      if (!contentShell) return false;
      setHost(contentShell);
      return true;
    };
    if (attach()) return;
    const observer = new MutationObserver(() => {
      if (attach()) observer.disconnect();
    });
    observer.observe(document.body, { childList: true, subtree: true });
    return () => observer.disconnect();
  }, []);
  useEffect(() => {
    const change = () => setPath(routePath());
    window.addEventListener("popstate", change);
    return () => window.removeEventListener("popstate", change);
  }, []);
  useEffect(() => {
    void read<Overview>(`/api/experience/overview?period=${period}`).then(
      setOverview,
    );
    void read<CommitmentView>(
      "/api/experience/commitments?asOf=2025-01-01&limit=20",
    ).then(setCommitments).catch((error) => setLoadError(error instanceof Error ? error.message : "Commitments unavailable"));
  }, [path, period]);
  useEffect(() => {
    if (!overview) return;
    const params = new URLSearchParams(window.location.search);
    if (path === "services") {
      const serviceId =
        routeId() || params.get("serviceId") || overview.services[0]?.serviceId;
      if (serviceId)
        void read<Detail>(
          `/api/experience/services/${encodeURIComponent(serviceId)}?period=${period}`,
        ).then(setDetail).catch((error) => setLoadError(error instanceof Error ? error.message : "Service intelligence unavailable"));
    }
    if (path === "risks") {
      const selected =
        overview.attentionFindings.find(
          (item) =>
            item.riskFindingId === (routeId() || params.get("riskFindingId")),
        ) ?? overview.attentionFindings[0];
      if (!selected) return;
      const serviceId = params.get("serviceId") || selected.serviceId;
      void read<Detail>(
        `/api/experience/risks/${encodeURIComponent(selected.riskFindingId)}?period=${period}&serviceId=${encodeURIComponent(serviceId)}`,
      ).then(setRisk).catch((error) => setLoadError(error instanceof Error ? error.message : "Risk intelligence unavailable"));
      void read<Graph>(
        `/api/experience/graph?period=${period}&serviceId=${encodeURIComponent(serviceId)}&riskFindingId=${encodeURIComponent(selected.riskFindingId)}&maxNodes=${graphLimit}&maxRelationships=${graphLimit * 2}`,
      ).then(setGraph).catch((error) => setLoadError(error instanceof Error ? error.message : "Graph unavailable"));
      void read<TemporalSignal[]>(
        `/api/experience/signals?period=${period}&serviceId=${encodeURIComponent(serviceId)}&riskFindingId=${encodeURIComponent(selected.riskFindingId)}&limit=50`,
      ).then(setSignals).catch((error) => setLoadError(error instanceof Error ? error.message : "Temporal intelligence unavailable"));
      void read<AiAssist>(
        `/api/experience/risks/${encodeURIComponent(selected.riskFindingId)}/assist?period=${period}&serviceId=${encodeURIComponent(serviceId)}`,
      ).then(setAiAssist).catch(() => setAiAssist({ status: "UNAVAILABLE", limitations: ["Assistance endpoint unavailable"], provenance: "local-safe-degradation" }));
      void read<ChangeAssociation>(
        `/api/experience/risks/${encodeURIComponent(selected.riskFindingId)}/change-association?serviceId=${encodeURIComponent(serviceId)}&period=${encodeURIComponent(period)}`,
      ).then(setChangeAssociation).catch(() => setChangeAssociation(undefined));
    }
  }, [overview, path, period, graphLimit]);
  const navigate = (next: string, context: Record<string, string> = {}) => {
    window.history.pushState({}, "", buildJourneyUrl(next, window.location.search, period, window.location.pathname, context));
    setPath(next.split("/").filter(Boolean)[0] ?? "overview");
  };
  const changeLocale = (nextLocale: UiLocale) => {
    setLocale(nextLocale);
    localStorage.setItem("vector-ui-locale", nextLocale);
    document.documentElement.lang = nextLocale;
  };
  const changePeriod = (nextPeriod: string) => {
    setPeriod(nextPeriod);
    const params = new URLSearchParams(window.location.search);
    params.set("period", nextPeriod);
    window.history.replaceState({}, "", `${window.location.pathname}?${params}`);
    setOverview(undefined);
    setDetail(undefined);
    setRisk(undefined);
    setGraph(undefined);
    setSignals([]);
  };
  const areaId = new URLSearchParams(window.location.search).get(
    "areaDomainId",
  );
  const area =
    overview?.areas.find((item) => item.areaDomainId === areaId) ??
    overview?.areas[0];
  const areaServices =
    overview?.services.filter(
      (item) => item.areaDomainId === area?.areaDomainId,
    ) ?? [];
  const areaServiceIds = new Set(areaServices.map((item) => item.serviceId));
  const areaRisks =
    overview?.attentionFindings.filter((item) =>
      areaServiceIds.has(item.serviceId),
    ) ?? [];
  const areaRiskIds = new Set(areaRisks.map((item) => item.riskFindingId));
  const areaCommitments = overview?.commitments.filter((item) => areaRiskIds.has((item as Commitment & { riskFindingId?: string }).riskFindingId ?? "")) ?? [];
  const areaCommitmentIds = new Set(areaCommitments.map((item) => item.commitmentId));
  const areaActions = overview?.improvementActions.filter((item) => areaCommitmentIds.has(item.commitmentId)) ?? [];
  const areaActionIds = new Set(areaActions.map((item) => item.actionId));
  const areaOutcomes = overview?.outcomeVerifications.filter((item) => areaActionIds.has(item.actionId)) ?? [];
  const graphNodeLabel = (canonicalId: string) =>
    (risk?.service?.serviceId === canonicalId && risk.service.name) ||
    (risk?.riskFinding?.riskFindingId === canonicalId &&
      risk.riskFinding.condition) ||
    risk?.evidence.find((item) => item.evidenceId === canonicalId)
      ?.supportedClaim ||
    risk?.commitments.find((item) => item.commitmentId === canonicalId)
      ?.declaration ||
    risk?.improvementActions.find((item) => item.actionId === canonicalId)
      ?.action ||
    risk?.outcomeVerifications.find(
      (item) => item.verificationId === canonicalId,
    )?.outcome ||
    label(canonicalId);
  const createCommitment = (event: FormEvent) => {
    event.preventDefault();
    if (!declaration) return;
    void fetch("/api/experience/commitments", {
      method: "POST",
      headers: { "Content-Type": "application/json", "X-Vector-Subject": "local-experience-operator", "X-Vector-Role": "ANALYST_OPERATOR" },
      body: JSON.stringify({
        commitmentId: `commitment-${Date.now()}`,
        declaration,
        accountableAreaDomainId: area?.areaDomainId ?? "area-platform",
        dueDate: commitmentDueDate || undefined,
        executionStatus: "OPEN",
        intendedResult: intendedResult || declaration,
        serviceId: new URLSearchParams(window.location.search).get("serviceId") || undefined,
        riskFindingId: new URLSearchParams(window.location.search).get("riskFindingId") || undefined,
      }),
    })
      .then(() =>
        read<CommitmentView>(
          "/api/experience/commitments?asOf=2025-01-01&limit=20",
        ),
      )
      .then(setCommitments).catch((error) => setLoadError(error instanceof Error ? error.message : "Commitments unavailable"));
    setDeclaration(""); setIntendedResult(""); setCommitmentDueDate("");
  };
  const refreshCommitments = () => read<CommitmentView>("/api/experience/commitments?asOf=2025-01-01&limit=20").then(setCommitments);
  const updateCommitmentStatus = (id: string, executionStatus: string) => void fetch(`/api/experience/commitments/${encodeURIComponent(id)}/lifecycle`, { method: "PATCH", headers: { "Content-Type": "application/json", "X-Vector-Subject": "local-experience-operator", "X-Vector-Role": "ANALYST_OPERATOR" }, body: JSON.stringify({ executionStatus, reason: `Operator moved commitment to ${executionStatus}` }) }).then(refreshCommitments).catch((error) => setLoadError(error instanceof Error ? error.message : "Commitment update unavailable"));
  const renegotiateCommitment = (event: FormEvent) => { event.preventDefault(); if (!renegotiationId || !renegotiationDate || !renegotiationReason) return; void fetch(`/api/experience/commitments/${encodeURIComponent(renegotiationId)}/renegotiations`, { method: "POST", headers: { "Content-Type": "application/json", "X-Vector-Subject": "local-experience-operator", "X-Vector-Role": "ANALYST_OPERATOR" }, body: JSON.stringify({ newDueDate: renegotiationDate, reason: renegotiationReason }) }).then(refreshCommitments).then(() => { setRenegotiationId(""); setRenegotiationDate(""); setRenegotiationReason(""); }).catch((error) => setLoadError(error instanceof Error ? error.message : "Renegotiation unavailable")); };
  if (!host) return null;
  if (loadError) return createPortal(<LocaleContext.Provider value={locale}><div className="experience-viewport"><div className="gold-page"><div className="gold-panel" role="alert"><h3>{t.unavailable}</h3><p>{loadError}</p><button className="primary-button" onClick={() => window.location.reload()}>{t.retry}</button></div></div></div></LocaleContext.Provider>, host);
  if (!overview) return createPortal(<LocaleContext.Provider value={locale}><div className="experience-viewport"><div className="loading-screen" role="status" aria-live="polite"><h1>VECTOR</h1><p>{t.loading}</p></div></div></LocaleContext.Provider>, host);

  const contextEnvelope = <ContextEnvelope area={area} service={detail?.service ?? risk?.service} risk={risk?.riskFinding} period={period} quality={risk?.quality ?? detail?.quality ?? overview.quality} />;

  const panorama = (
    <div className="experience-viewport">
      <div className="gold-page vx-approved-golden">
        <WorkspaceRail active={path} navigate={navigate} />
        <Header eyebrow={tr("PANORAMA EJECUTIVO · TECNOLOGÍA","EXECUTIVE OVERVIEW · TECHNOLOGY")} title={tr("Panorama Ejecutivo","Executive Overview")} question={tr("¿Dónde requiere atención Tecnología hoy y por qué?","Where does Technology require attention today, and why?")} period={period} onPeriodChange={changePeriod} />
        <div className="vx-golden-tabs"><button className="active">{tr("Vista general","Overview")}</button><button>{tr("Confiabilidad","Reliability")}</button><button>{tr("Ejecución","Execution")}</button><button>{tr("Capacidad","Capacity")}</button><button>{tr("Resultados","Outcomes")}</button></div>
        <div className="vx-golden-kpis">
          <Metric tone="danger" title={tr("Áreas que requieren atención","Areas requiring attention")} value={overview.areas.filter(a=>a.attentionState!=="STABLE").length} note={tr("Prioridad ejecutiva","Executive priority")} />
          <Metric tone="warning" title={tr("Riesgos con evidencia","Evidence-backed risks")} value={overview.attentionFindings.length} note={tr("No implica causalidad","Does not imply causality")} />
          <Metric tone="purple" title={tr("Compromisos vencidos","Overdue commitments")} value={commitments?.overdueCount ?? 0} note={tr("Seguimiento requerido","Follow-up required")} />
          <Metric tone="success" title={tr("Resultados verificados","Verified outcomes")} value={overview.outcomeVerifications.length} note={tr("Evidencia posterior","Post-action evidence")} />
        </div>
        <div className="vx-golden-layout">
          <section className="gold-panel vx-golden-attention"><SectionTitle title={tr("¿Qué requiere mi atención?","What requires my attention?")} subtitle={tr("Situaciones priorizadas por evidencia disponible","Situations prioritized by available evidence")} />
            {overview.attentionFindings.slice(0,4).map((risk)=><article className="vx-golden-issue" key={risk.riskFindingId}><i/><div><small>{tr("RIESGO","RISK")} · {label(risk.serviceId)}</small><h3>{risk.condition}</h3><p>{risk.explanation}</p></div><button onClick={()=>navigate(`/risks/${encodeURIComponent(risk.riskFindingId)}`,{serviceId:risk.serviceId,riskFindingId:risk.riskFindingId})}>{tr("Entender por qué →","Understand why →")}</button></article>)}
            {!overview.attentionFindings.length&&<Empty>{tr("No hay situaciones sustentadas que requieran atención.","No supported situations require attention.")}</Empty>}
          </section>
          <aside className="vx-golden-side"><div className="gold-intelligence"><strong>✣ VECTOR Intelligence</strong><p>{overview.attentionFindings[0]?.explanation ?? tr("No hay hallazgos adicionales en el período.","No additional findings in the period.")}</p><div className="vx-question-chips"><span>{tr("¿Qué explica la atención?","What explains the attention?")}</span><span>{tr("¿Qué evidencia falta?","What evidence is missing?")}</span></div></div>
          <div className="gold-panel"><SectionTitle title={tr("Áreas de Tecnología","Technology areas")} />{overview.areas.map(a=><button className="vx-golden-area" key={a.areaDomainId} onClick={()=>navigate("/areas",{areaDomainId:a.areaDomainId})}><span>{a.name}</span><b>{a.attentionState}</b><em>→</em></button>)}</div></aside>
        </div>
        <section className="gold-panel vx-golden-flow"><SectionTitle title={tr("De problema a resultado","From problem to outcome")} subtitle={tr("Continuidad gobernada de la evidencia a la verificación","Governed continuity from evidence to verification")} /><div><span><b>{tr("Riesgo","Risk")}</b>{overview.attentionFindings[0]?.condition ?? "—"}</span><i>→</i><span><b>{tr("Compromiso","Commitment")}</b>{overview.commitments[0]?.declaration ?? "—"}</span><i>→</i><span><b>{tr("Acción","Action")}</b>{overview.improvementActions[0]?.action ?? "—"}</span><i>→</i><span><b>{tr("Resultado","Outcome")}</b>{overview.outcomeVerifications[0]?.outcome ?? tr("Sin verificar","Unverified")}</span></div></section>
        <QualityNote quality={overview.quality} />
      </div>
    </div>
  );

  const areaView = (
    <div className="experience-viewport">
      <div className="gold-page vx-approved-golden vx-golden-area-page">
        <WorkspaceRail active={path} navigate={navigate} />
        {contextEnvelope}
        <Header
          eyebrow={tr("ÁREAS / DOMINIOS · ESPACIO DE DECISIÓN", "AREAS / DOMAINS · DECISION WORKSPACE")}
          title={area?.name ?? tr("Área sin seleccionar", "No area selected")}
          question={tr(`¿Qué servicios concentran la atención en ${area?.name ?? "esta área"}, qué la explica y qué seguimiento requiere?`, `Which services concentrate attention in ${area?.name ?? "this area"}, what explains it, and what follow-up is required?`)}
          period={period}
          onPeriodChange={changePeriod}
        />
        <div className="vx-area-golden-kpis">
          <article><small>{tr("CONFIABILIDAD","RELIABILITY")}</small><strong>{areaRisks.length}</strong><span>{tr("riesgos/hallazgos sustentados","evidence-backed risks/findings")}</span><MiniBars values={[8,6,9,5,7,4,6]} tone="red" /></article>
          <article><small>{tr("EJECUCIÓN","EXECUTION")}</small><strong>{areaCommitments.filter(c=>c.overdue).length}</strong><span>{tr("compromisos vencidos","overdue commitments")}</span><MiniBars values={[4,7,5,8,6,7,5]} tone="amber" /></article>
          <article className="capacity"><small>{tr("CAPACIDAD","CAPACITY")}</small><div className="vx-capacity-bars"><span style={{width:"65%"}}>Run</span><span style={{width:"25%"}}>Improve</span><span style={{width:"10%"}}>Transform</span></div><em>{tr("Distribución del mock; no inferida como capacidad real","Mock distribution; not inferred as actual capacity")}</em></article>
          <article><small>{tr("MEJORA","IMPROVEMENT")}</small><strong>{areaOutcomes.length}</strong><span>{tr("resultados verificados","verified outcomes")}</span><Donut value={areaActions.length ? Math.round((areaOutcomes.length/areaActions.length)*100) : 0} labelText={tr("verificado","verified")} /></article>
        </div>
        <div className="vx-area-workspace">
          <section className="gold-panel vx-service-portfolio">
            <SectionTitle
              title={tr("Portafolio de servicios", "Service portfolio")}
              subtitle={tr("Comparación dentro del área; seleccione un servicio para continuar la investigación.", "Compare within the area; select a service to continue the investigation.")}
            />
            <div className="vx-portfolio-head"><span>{tr("Servicio","Service")}</span><span>{tr("Condición","Condition")}</span><span>{tr("Hallazgos","Findings")}</span><span>{tr("Decisión","Decision")}</span></div>
            {areaServices.length ? areaServices.map((service) => {
              const serviceRisks = areaRisks.filter((item) => item.serviceId === service.serviceId);
              return (
                <button
                  className="vx-portfolio-row"
                  key={service.serviceId}
                  onClick={() => navigate(`/services/${encodeURIComponent(service.serviceId)}`, {
                    areaDomainId: service.areaDomainId,
                    serviceId: service.serviceId,
                  })}
                >
                  <strong>{service.name}</strong>
                  <span>{service.conditionContext}</span>
                  <b>{serviceRisks.length}</b>
                  <em>{serviceRisks.length ? tr("Investigar →", "Investigate →") : tr("Revisar contexto →", "Review context →")}</em>
                </button>
              );
            }) : <Empty>{tr("No hay servicios disponibles para el área seleccionada.", "No services are available for the selected area.")}</Empty>}
          </section>

          <aside className="vx-area-attention">
            <div className="gold-panel">
              <SectionTitle
                title={tr("Concentración de atención", "Attention concentration")}
                subtitle={tr("Hallazgos explicables; no es un score ni un ranking de personas.", "Explainable findings; this is not a score or ranking of people.")}
              />
              {areaRisks.length ? areaRisks.map((item) => (
                <button
                  className="vx-attention-item"
                  key={item.riskFindingId}
                  onClick={() => navigate(`/risks/${encodeURIComponent(item.riskFindingId)}`, {
                    areaDomainId: area?.areaDomainId ?? "",
                    serviceId: item.serviceId,
                    riskFindingId: item.riskFindingId,
                  })}
                >
                  <small>{label(item.serviceId)}</small>
                  <strong>{item.condition}</strong>
                  <span>{item.explanation}</span>
                </button>
              )) : <Empty>{tr("No hay hallazgos de atención sustentados por la evidencia disponible.", "There are no attention findings supported by available evidence.")}</Empty>}
            </div>
            <div className="gold-intelligence">
              <strong>✣ {t.intelligence}</strong>
              <p>{tr("La vista del área organiza contexto para decidir dónde profundizar; no convierte correlación en causalidad ni asigna responsabilidad individual.","The area view organizes context to decide where to investigate; it does not turn correlation into causality or assign individual responsibility.")}</p>
            </div>
          </aside>
        </div>

        <section className="gold-panel vx-area-followup">
          <SectionTitle title={tr("Seguimiento del área", "Area follow-up")} subtitle={tr("Riesgo → compromiso → acción → resultado.", "Risk → commitment → action → outcome.")} />
          <div className="vx-followup-grid">
            <div><small>{tr("RIESGO","RISK")}</small><strong>{areaRisks.length}</strong><span>{tr("hallazgos visibles","visible findings")}</span></div>
            <div><small>{tr("COMPROMISO","COMMITMENT")}</small><strong>{commitments?.activeCount ?? 0}</strong><span>{tr("activos","active")}</span></div>
            <div><small>{tr("ACCIÓN","ACTION")}</small><strong>{areaActions.length}</strong><span>{tr("acciones vinculadas","linked actions")}</span></div>
            <div><small>{tr("RESULTADO","OUTCOME")}</small><strong>{areaOutcomes.length}</strong><span>{areaOutcomes.length ? areaOutcomes.map((item) => item.outcome).join(" · ") : tr("sin resultado verificado","no verified outcome")}</span></div>
          </div>
        </section>

        <DecisionQueue risks={areaRisks} commitments={commitments} navigate={navigate} />
        <QualityNote quality={overview.quality} />
      </div>
    </div>
  );

  const commitmentView = (
    <div className="experience-viewport">
      <div className="gold-page vx-approved-golden vx-golden-commitment-page">
        <WorkspaceRail active={path} navigate={navigate} />
        {contextEnvelope}
        <Header
          eyebrow={tr("COMPROMISOS Y MEJORAS · FLUJO", "COMMITMENTS & IMPROVEMENTS · WORKFLOW")}
          title={tr("Compromisos y Mejoras", "Commitments & Improvements")}
          question={tr("¿Qué se comprometió, qué cambió en la ejecución y qué resultado está realmente verificado?","What was committed, what changed in execution, and what outcome is actually verified?")}
          period={period}
          onPeriodChange={changePeriod}
        />
        <LensNav labelText={tr("Lentes de compromisos y resultados","Commitment and outcome lenses")} items={[
          ["commitment-list", tr("Flujo de compromisos","Commitment flow")],
          ["commitment-results", tr("Resultado y evidencia","Outcome and evidence")],
          ["commitment-new", tr("Nuevo compromiso","New commitment")],
        ]} />
        <div className="vx-commitment-strip">
          <div><small>{tr("ACTIVOS","ACTIVE")}</small><strong>{commitments?.activeCount ?? 0}</strong></div>
          <div><small>{tr("RENEGOCIADOS","RENEGOTIATED")}</small><strong>{commitments?.renegotiatedCount ?? 0}</strong></div>
          <div><small>{tr("ÍNDICE DE CONFIABILIDAD","RELIABILITY RATE")}</small><strong>{commitments?.commitmentReliabilityRate == null ? tr("N/D","N/A") : `${Math.round(commitments.commitmentReliabilityRate * 100)}%`}</strong></div>
          <div><small>{tr("RESULTADO PENDIENTE","OUTCOME PENDING")}</small><strong>{commitments?.outcomePendingCount ?? 0}</strong></div>
        </div>
        <div className="vx-commitment-workspace">
          <section className="gold-panel">
            <SectionTitle id="commitment-list" title={tr("Flujo de compromisos","Commitment flow")} subtitle={tr("La fecha y la renegociación preservan historia; completar ejecución no verifica resultado.","Dates and renegotiations preserve history; completing execution does not verify the outcome.")} />
            <div className="vx-ledger-head"><span>{tr("Compromiso","Commitment")}</span><span>{tr("Área","Area")}</span><span>{tr("Estado","Status")}</span><span>{tr("Fecha","Date")}</span><span>{tr("Acciones","Actions")}</span></div>
            {commitments?.commitments.map((item) => (
              <div className="vx-ledger-row" key={item.commitmentId}>
                <strong>{item.declaration}</strong>
                <span>{label(item.accountableAreaDomainId)}</span>
                <b>{item.executionStatus ?? item.statusContext}</b>
                <em>{item.currentDueDate ?? (item.overdue ? "Vencido" : "En seguimiento")}</em>
                <div className="vx-commitment-actions">
                  <button onClick={() => updateCommitmentStatus(item.commitmentId, "IN_PROGRESS")}>{tr("En progreso","In progress")}</button>
                  <button onClick={() => updateCommitmentStatus(item.commitmentId, "COMPLETED")}>{tr("Completar","Complete")}</button>
                  <button onClick={() => setRenegotiationId(item.commitmentId)}>{tr("Renegociar","Renegotiate")}</button>
                  {item.riskFindingId && <button onClick={() => navigate(`/risks/${encodeURIComponent(item.riskFindingId!)}`, { riskFindingId: item.riskFindingId!, serviceId: item.serviceId ?? "" })}>{tr("Volver a investigación","Return to investigation")}</button>}
                </div>
              </div>
            ))}
          </section>
          <aside className="vx-commitment-side">
            <div className="gold-panel" id="commitment-results">
              <SectionTitle title={tr("Resultado y evidencia","Outcome and evidence")} />
              <Empty>{tr("No hay verificación de resultado agregada para esta vista. La ejecución completada permanece separada del resultado.","No outcome verification is aggregated for this view. Completed execution remains separate from outcome.")}</Empty>
            </div>
            <div className="gold-intelligence">
              <strong>✣ {t.intelligence}</strong>
              <p>{tr("Prioriza seguimiento gobernado: compromiso → ejecución → evidencia → resultado. No convierte actividad completada en mejora.","Prioritizes governed follow-up: commitment → execution → evidence → outcome. It does not turn completed activity into improvement.")}</p>
            </div>
            <form className="gold-panel gold-form" id="commitment-new" onSubmit={createCommitment}>
              <h3>{tr("Nuevo compromiso","New commitment")}</h3>
              <label>{tr("Declaración","Declaration")}<input value={declaration} onChange={(event) => setDeclaration(event.target.value)} required /></label>
              <label>{tr("Área responsable","Accountable area")}<select key={area?.areaDomainId} defaultValue={area?.areaDomainId ?? "area-platform"} disabled><option value={area?.areaDomainId ?? "area-platform"}>{area?.name ?? "Platform"}</option></select></label>
              <label>{tr("Resultado esperado","Expected outcome")}<input value={intendedResult} onChange={(event) => setIntendedResult(event.target.value)} /></label>
              <label>{tr("Fecha comprometida","Committed date")}<input type="date" value={commitmentDueDate} onChange={(event) => setCommitmentDueDate(event.target.value)} /></label>
              <button>{tr("Crear compromiso","Create commitment")}</button>
            </form>
            {renegotiationId && <form className="gold-panel gold-form" onSubmit={renegotiateCommitment}>
              <h3>{tr("Renegociar compromiso","Renegotiate commitment")}</h3>
              <p>{label(renegotiationId)} · la fecha anterior permanecerá en el historial.</p>
              <label>{tr("Nueva fecha","New date")}<input type="date" value={renegotiationDate} onChange={(event) => setRenegotiationDate(event.target.value)} required /></label>
              <label>{tr("Razón","Reason")}<input value={renegotiationReason} onChange={(event) => setRenegotiationReason(event.target.value)} required /></label>
              <button>{tr("Registrar renegociación","Record renegotiation")}</button>
            </form>}
          </aside>
        </div>
        <QualityNote />
      </div>
    </div>
  );

  const serviceView = (
    <div className="experience-viewport">
      <div className="gold-page vx-approved-golden vx-golden-service-page">
        <WorkspaceRail active={path} navigate={navigate} />
        {contextEnvelope}
        <Header
          eyebrow={`SERVICIO · ${label(detail?.service?.areaDomainId)}`}
          title={detail?.service?.name ?? "Service Intelligence"}
          question={detail?.service ? `¿Qué está ocurriendo en este servicio, qué evidencia lo sustenta y qué contexto operacional falta?` : "Cargando contexto del servicio."}
          period={period}
          onPeriodChange={changePeriod}
        />
        <LensNav labelText={tr("Lentes del servicio","Service lenses")} items={[
          ["service-condition", tr("Condición","Condition")],
          ["service-evidence", tr("Evidencia","Evidence")],
          ["service-slo", "SLO"],
          ["service-incidents", tr("Incidentes","Incidents")],
          ["service-changes", tr("Cambios","Changes")],
        ]} />
        <section className="vx-service-hero" id="service-condition">
          <div><small>{tr("SALUD DEL SERVICIO","SERVICE HEALTH")}</small><strong>{detail?.service?.conditionContext ?? tr("Contexto no disponible","Context unavailable")}</strong><span>{detail?.riskFindings.length ?? 0} {tr("riesgos","risks")} · {detail?.evidence.length ?? 0} {tr("evidencias","evidence records")}</span></div>
          <article><small>{tr("TENDENCIA OPERACIONAL","OPERATIONAL TREND")}</small><MiniBars values={[8,9,7,6,5,4,6,5,3,4]} tone={(detail?.riskFindings.length ?? 0)>0?"red":"blue"} /></article>
          <article><small>{tr("CAMBIOS / INCIDENTES","CHANGES / INCIDENTS")}</small><strong>{tr("Contexto disponible","Context available")}</strong><span>{tr("Relaciones respaldadas por la proyección","Projection-backed relationships")}</span></article>
          <article><small>{tr("CALIDAD","QUALITY")}</small><strong>{detail?.quality.stale ? tr("Desactualizada","Stale") : detail?.quality.missingContext?.length ? tr("Parcial","Partial") : tr("Disponible","Available")}</strong><span>{detail?.quality.freshness}</span></article>
        </section>
        <section className="vx-service-map">
          <div><small>{tr("MAPA DEL SERVICIO","SERVICE MAP")}</small><h2>{tr("Dependencias y señales en contexto","Dependencies and signals in context")}</h2><p>{tr("Topología como lente operacional; no afirma causalidad.","Topology is an operational lens; it does not assert causality.")}</p></div>
          <div className="vx-service-map-stage"><span className="core">{detail?.service?.name ?? "Service"}</span>{detail?.riskFindings.slice(0,3).map((r,i)=><button key={r.riskFindingId} className={"n n"+(i+1)} onClick={()=>navigate("/risks/"+encodeURIComponent(r.riskFindingId),{serviceId:r.serviceId,riskFindingId:r.riskFindingId})}>{r.condition}</button>)}<span className="n n4">{tr("Evidencia","Evidence")} · {detail?.evidence.length ?? 0}</span><span className="n n5">{tr("Compromisos","Commitments")} · {detail?.commitments.length ?? 0}</span></div>
        </section>
        <TemporalSpine risks={detail?.riskFindings} />
        <div className="vx-service-workspace">
          <section>
            <div className="gold-panel gold-stack">
              <SectionTitle title={tr("Hallazgos que requieren investigación","Findings requiring investigation")} subtitle={tr("Cada hallazgo mantiene evidencia, limitaciones y contexto del servicio.","Each finding retains evidence, limitations, and service context.")} />
              {detail?.riskFindings.length ? detail.riskFindings.map((item) => (
                <button className="vx-service-finding" key={item.riskFindingId} onClick={() => navigate(`/risks/${encodeURIComponent(item.riskFindingId)}`, {
                  areaDomainId: detail.service?.areaDomainId ?? "",
                  serviceId: item.serviceId,
                  riskFindingId: item.riskFindingId,
                })}>
                  <small>{tr("HALLAZGO","FINDING")}</small><strong>{item.condition}</strong><span>{item.explanation}</span><em>{tr("Investigar evidencia y relaciones →","Investigate evidence and relationships →")}</em>
                </button>
              )) : <Empty>{tr("No hay hallazgo de riesgo sustentado para el contexto seleccionado.","No risk finding is supported for the selected context.")}</Empty>}
            </div>
            <div className="gold-panel" id="service-evidence">
              <SectionTitle title={tr("Evidencia operacional","Operational evidence")} subtitle={tr("Hechos disponibles para el servicio; ausencia de datos no implica operación normal.","Available service facts; missing data does not imply normal operation.")} />
              {detail?.evidence.length ? detail.evidence.map((item) => (
                <div className="gold-evidence" key={item.evidenceId}><b>{t.fact}</b><strong>{item.supportedClaim}</strong><span>{item.observedAt} · {item.sourceReferenceIds.map(label).join(", ")}</span></div>
              )) : <Empty>{tr("No hay evidencia expuesta por esta proyección.","No evidence is exposed by this projection.")}</Empty>}
            </div>
          </section>
          <aside className="vx-service-context">
            <div className="gold-panel" id="service-slo"><SectionTitle title={tr("SLO / tendencia","SLO / trend")} /><Empty>{tr("No hay serie SLO disponible para el dataset local.","No SLO series is available for the local dataset.")}</Empty></div>
            <div className="gold-panel" id="service-incidents"><SectionTitle title={tr("Incidentes","Incidents")} /><Empty>{tr("No hay incidentes expuestos por esta proyección.","No incidents are exposed by this projection.")}</Empty></div>
            <div className="gold-panel" id="service-changes"><SectionTitle title={tr("Cambios y despliegues","Changes and deployments")} /><Empty>{tr("No hay cambios expuestos por esta proyección.","No changes are exposed by this projection.")}</Empty></div>
            <div className="gold-intelligence"><strong>✣ {t.intelligence}</strong><p>{detail?.riskFindings[0]?.explanation ?? tr("No hay hallazgos adicionales.","No additional findings.")}</p><small>{tr("La explicación se limita a la evidencia disponible.","The explanation is limited to available evidence.")}</small></div>
          </aside>
        </div>
        <QualityNote quality={detail?.quality} />
      </div>
    </div>
  );

  const riskView = (
    <div className="experience-viewport">
      <div className="gold-page vx-approved-golden vx-golden-risk-page">
        <WorkspaceRail active={path} navigate={navigate} />
        {contextEnvelope}
        <Header
          eyebrow={tr("INVESTIGACIÓN", "INVESTIGATION")}
          title={risk?.riskFinding?.condition ?? tr("Investigación de riesgo", "Risk Investigation")}
          question={
            risk?.riskFinding?.explanation ??
            "Cargando explicación y evidencia."
          }
          period={period}
          onPeriodChange={changePeriod}
        />
        <div className="vx-risk-status"><span>{tr("RIESGO PERSISTENTE","PERSISTENT RISK")}</span><b>{risk?.riskFinding?.riskFindingId ? label(risk.riskFinding.riskFindingId) : "—"}</b><em>{risk?.quality.conflicting ? tr("Evidencia en conflicto","Conflicting evidence") : risk?.quality.stale ? tr("Evidencia desactualizada","Stale evidence") : tr("Evidencia disponible","Evidence available")}</em></div>
        <SemanticLegend />
        <TemporalSpine signals={signals} risks={risk?.riskFindings} />
        <LensNav labelText={tr("Lentes de investigación","Investigation lenses")} items={[
          ["risk-timeline", tr("Línea de tiempo","Timeline")],
          ["risk-evidence", tr("Evidencia","Evidence")],
          ["risk-change", tr("Cambio asociado","Associated change")],
          ["risk-relations", tr("Relaciones","Relationships")],
          ["risk-actions", tr("Acciones","Actions")],
          ["risk-outcome", tr("Resultado","Outcome")],
        ]} />
        <div className="gold-metrics">
          <Metric
            tone="danger"
            title={tr("Ocurrencias","Occurrences")}
            value={risk?.evidence.length ?? 0}
            note="Evidencia en el período"
          />
          <Metric
            tone="warning"
            title={tr("Impacto estimado","Estimated impact")}
            value="N/D"
            note="No inferido por VECTOR"
          />
          <Metric
            tone="purple"
            title={tr("Acciones","Actions")}
            value={risk?.improvementActions.length ?? 0}
            note="Ejecución registrada"
          />
          <Metric
            tone="danger"
            title={tr("Estado del riesgo","Risk state")}
            value={risk?.outcomeVerifications[0]?.outcome ?? "Sin verificar"}
            note="Resultado basado en evidencia"
          />
        </div>
        <div className="gold-main-grid">
          <section>
            <div className="gold-panel">
              <SectionTitle id="risk-timeline" title={tr("Línea de tiempo del riesgo", "Risk timeline")} />
              {signals.length ? signals.map((item) => (
                <div className="gold-timeline" key={item.signalId}>
                  <time>{item.observedAt.slice(0, 10)}</time>
                  <i />
                  <div>
                    <strong>{item.statement}</strong>
                    <p>{item.semanticType.replaceAll("_", " ")} · {item.sourceReferenceIds.map(label).join(", ")}</p>
                    {item.limitation && <small>{item.limitation}</small>}
                  </div>
                </div>
              )) : <Empty>{tr("No hay historia temporal suficiente en la evidencia disponible.","There is not enough temporal history in the available evidence.")}</Empty>}
            </div>
            <div className="gold-panel">
              <SectionTitle id="risk-evidence" title={tr("Evidencia disponible", "Available evidence")} />
              {risk?.evidence.map((item) => (
                <div className="gold-evidence" key={item.evidenceId}>
                  <b>{t.fact}</b>
                  <strong>{item.supportedClaim}</strong>
                  <span>{item.sourceReferenceIds.map(label).join(", ")}</span>
                </div>
              ))}
            </div>
          </section>
          <aside>
            <div className="gold-intelligence">
              <strong>✣ {t.intelligence}</strong>
              <p>{risk?.riskFinding?.explanation}</p>
              <small>{tr("Correlación temporal/contextual ≠ causalidad.","Temporal/contextual correlation ≠ causality.")}</small>
            </div>
            <div className="gold-panel vx-ai-assist" aria-live="polite">
              <SectionTitle title={tr("Asistencia de IA basada en evidencia", "Evidence-based AI assistance")} subtitle={tr("Solo asesoría · nunca autoritativa", "Advisory only · never authoritative")} />
              <b>{aiAssist?.status ?? "LOADING"}</b>
              <p>{aiAssist?.explanation ?? aiAssist?.limitations?.join(" · ") ?? "Validando el límite del proveedor gobernado…"}</p>
              <small>{aiAssist?.provenance ?? "No se afirma disponibilidad de proveedor sin evidencia."}</small>
            </div>
            <div className="gold-panel vx-change-association">
              <SectionTitle id="risk-change" title={tr("Degradación asociada a cambio", "Degradation associated with change")} subtitle={tr("Antes / durante / después · asociación ≠ causalidad", "Before / during / after · association ≠ causality")} />
              {changeAssociation ? <>
                <div className="vx-change-path"><span>{t.before}</span><i>→</i><span>{t.change} {label(changeAssociation.changeId)}</span><i>→</i><span>{t.after}</span></div>
                <p><strong>{changeAssociation.contextualAssociation ? "Contextual association detected" : "Association not established"}</strong> · Causal claim: {changeAssociation.causalClaim ? "YES" : "NO"}</p>
                <small>{changeAssociation.limitation}</small>
              </> : <Empty>{tr("No hay contexto disponible de asociación con cambio/despliegue.","No change/deployment association context is available.")}</Empty>}
            </div>
            <div className="gold-panel">
              <SectionTitle id="risk-relations" title={tr("Relaciones y topología contextual", "Relationships and contextual topology")} />
              <SpatialGraph graph={graph} focus={graphFocus} onFocus={setGraphFocus} nodeLabel={graphNodeLabel} limit={graphLimit} onExpand={() => setGraphLimit((current) => Math.min(current + 4, 20))} onReset={() => setGraphLimit(6)} />
            </div>
            <div className="gold-panel">
              <SectionTitle id="risk-actions" title={tr("Acciones asociadas", "Associated actions")} />
              {risk?.riskFinding && <button className="primary-button" type="button" onClick={() => navigate("/commitments", {
                areaDomainId: risk.service?.areaDomainId ?? "",
                serviceId: risk.service?.serviceId ?? risk.riskFinding!.serviceId,
                riskFindingId: risk.riskFinding!.riskFindingId,
                condition: risk.riskFinding!.condition,
              })}>{tr("Crear compromiso desde esta investigación","Create commitment from this investigation")}</button>}
              {risk?.commitments.map((commitment) => (
                <div className="gold-action" key={commitment.commitmentId}>
                  <strong>{commitment.declaration}</strong>
                  <b>{commitment.statusContext}</b>
                  {risk.improvementActions
                    .filter(
                      (item) => item.commitmentId === commitment.commitmentId,
                    )
                    .map((item) => (
                      <p key={item.actionId}>
                        {item.action} · {item.executionStatusContext}
                      </p>
                    ))}
                </div>
              ))}
              {!risk?.commitments.length && (
                <Empty>{tr("No hay compromisos asociados.","No associated commitments.")}</Empty>
              )}
            </div>
            <div className="gold-panel">
              <SectionTitle id="risk-outcome" title={tr("Resultado verificado", "Verified outcome")} />
              {risk?.outcomeVerifications.length ? risk.outcomeVerifications.map((outcome) => (
                <div className="gold-action" key={outcome.verificationId}>
                  <strong>{outcome.outcome}</strong>
                  <p>Evidencia: {outcome.evidenceIds.map(label).join(", ")}</p>
                </div>
              )) : <Empty>{tr("El resultado aún no es verificable con la evidencia disponible.","The outcome is not yet verifiable with available evidence.")}</Empty>}
            </div>
          </aside>
        </div>
        <QualityNote quality={risk?.quality} />
      </div>
    </div>
  );

  const localeControl = <label className="vx-locale-control"><span>{t.locale}</span><select aria-label={t.locale} value={locale} onChange={(event) => changeLocale(event.target.value as UiLocale)}><option value="es">Español</option><option value="en">English</option></select></label>;

  const view =
    path === "areas"
      ? areaView
      : path === "commitments"
        ? commitmentView
        : path === "services"
          ? serviceView
          : path === "risks"
            ? riskView
            : panorama;
  return createPortal(<LocaleContext.Provider value={locale}><div className="vx-locale-dock">{localeControl}</div>{view}</LocaleContext.Provider>, host);
}
