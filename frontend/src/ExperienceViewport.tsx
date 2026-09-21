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
    before:"ANTES", change:"CAMBIO", after:"DESPUÉS", intelligence:"Inteligencia VECTOR", locale:"Idioma"
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
    before:"BEFORE", change:"CHANGE", after:"AFTER", intelligence:"VECTOR Intelligence", locale:"Language"
  }
} as const;
const LocaleContext = createContext<UiLocale>("es");
const useCopy = () => COPY[useContext(LocaleContext)];

const DEFAULT_PERIOD = "local-dataset-v1";
const PERIOD_OPTIONS = [
  ["local-dataset-v1", "Escenario base"],
  ["local-partial-stale", "Evidencia parcial / desactualizada"],
  ["local-outcome-pending", "Acción completa / resultado pendiente"],
  ["local-insufficient-evidence", "Evidencia insuficiente"],
] as const;
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
  confidence: string;
  uncertainty: string;
  limitations: string;
};
type Overview = {
  areas: Area[];
  services: Service[];
  attentionFindings: Risk[];
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
    throw new Error(`Experience unavailable (${response.status})`);
  return response.json() as Promise<T>;
};
const routePath = () =>
  window.location.pathname.split("/").filter(Boolean)[0] ?? "overview";
const routeId = () =>
  decodeURIComponent(
    window.location.pathname.split("/").filter(Boolean)[1] ?? "",
  );

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
  return (
    <header className="gold-header">
      <div>
        <span>{eyebrow}</span>
        <h1>{title}</h1>
        <p>{question}</p>
      </div>
      <div className="gold-filters">
        <label className="vx-context-control">
          <span>Escenario de análisis</span>
          <select aria-label="Escenario de análisis" value={period} onChange={(event) => onPeriodChange(event.target.value)}>
            {PERIOD_OPTIONS.map(([value, text]) => <option key={value} value={value}>{text}</option>)}
          </select>
        </label>
        <span className="vx-context-meta">Datos demostrativos locales · no producción</span>
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
    <div className="vx-workspace-rail" aria-label="VECTOR intelligence workspace">
      <div><strong>VECTOR / INTELLIGENCE WORKSPACE</strong><small>{t.workspace}</small></div>
      <nav aria-label={t.layers}>
        {items.map(([key, route, text]) => <button key={key} className={active === key ? "active" : ""} onClick={() => navigate(route)}>{text}</button>)}
      </nav>
      <div className="vx-loop">{t.loop}</div>
    </div>
  );
}
export function SemanticLegend() {
  const t = useCopy();
  return <div className="vx-semantics" aria-label="Semantic evidence legend">
    <span className="vx-semantic fact">{t.observed}</span>
    <span className="vx-semantic">{t.derived}</span>
    <span className="vx-semantic uncertain">{t.uncertain}</span>
    <span className="vx-semantic outcome">{t.verified}</span>
  </div>;
}

function DecisionQueue({ risks, commitments, navigate }: { risks: Risk[]; commitments?: CommitmentView; navigate: (next: string, context?: Record<string, string>) => void }) {
  return <section className="vx-decision-queue" aria-label="Decision queue">
    <div><span>DECISION QUEUE</span><strong>{risks.length} evidence-backed conditions require review</strong><small>{commitments?.overdueCount ?? 0} overdue commitments in available context</small></div>
    <div className="vx-decision-items">{risks.slice(0, 3).map((risk) => <button key={risk.riskFindingId} onClick={() => navigate("/risks", { riskFindingId: risk.riskFindingId, serviceId: risk.serviceId })}><b>Investigate</b><span>{risk.condition}</span><small>{risk.explanation}</small></button>)}</div>
  </section>;
}

function OperationalCanvas({ overview, selectedServiceId, navigate }: { overview: Overview; selectedServiceId?: string; navigate: (next: string, context?: Record<string, string>) => void }) {
  const t = useCopy();
  const services = overview.services.slice(0, 8);
  return <section className="vx-ops-canvas" aria-label="Mapa operacional">
    <div className="vx-canvas-head"><div><small>{t.operationalContext}</small><strong>{t.attentionMap}</strong></div><span>{overview.quality.stale ? "Evidencia desactualizada" : "Evidencia disponible"}</span></div>
    <div className="vx-canvas-stage">
      <div className="vx-orbit orbit-a" /><div className="vx-orbit orbit-b" />
      <div className="vx-core"><span>VECTOR</span><b>{overview.attentionFindings.length}</b><small>{t.findings}</small></div>
      {services.map((service, index) => {
        const riskCount = overview.attentionFindings.filter((risk) => risk.serviceId === service.serviceId).length;
        const angle = (Math.PI * 2 * index) / Math.max(services.length, 1) - Math.PI / 2;
        const radius = index % 2 ? 39 : 31;
        const left = 50 + Math.cos(angle) * radius;
        const top = 50 + Math.sin(angle) * radius;
        return <button key={service.serviceId} className={`vx-canvas-node ${riskCount ? "attention" : "stable"} ${selectedServiceId === service.serviceId ? "selected" : ""}`} style={{ left: `${left}%`, top: `${top}%` }} onClick={() => navigate(`/services/${encodeURIComponent(service.serviceId)}`, { areaDomainId: service.areaDomainId, serviceId: service.serviceId })}>
          <span>{service.name}</span><small>{service.conditionContext}</small>{riskCount > 0 && <b>{riskCount}</b>}
        </button>;
      })}
      {overview.attentionFindings.slice(0, 5).map((risk, index) => <button key={risk.riskFindingId} className="vx-risk-beacon" style={{ left: `${18 + index * 15}%` }} onClick={() => navigate(`/risks/${encodeURIComponent(risk.riskFindingId)}`, { serviceId: risk.serviceId, riskFindingId: risk.riskFindingId })}><i /><span>{risk.condition}</span></button>)}
    </div>
    <div className="vx-canvas-legend"><span><i className="stable" /> {t.serviceContext}</span><span><i className="attention" /> {t.evidenceAttention}</span><small>{t.contextDisclaimer}</small></div>
  </section>;
}

function TemporalSpine({ signals, risks }: { signals?: TemporalSignal[]; risks?: Risk[] }) {
  const t = useCopy();
  const items = signals?.length ? signals.slice(0, 8).map((signal) => ({ id: signal.signalId, type: signal.semanticType, text: signal.statement, time: signal.observedAt.slice(0, 10) })) : (risks ?? []).slice(0, 6).map((risk) => ({ id: risk.riskFindingId, type: "RISK FINDING", text: risk.condition, time: "contexto actual" }));
  return <section className="vx-temporal-spine" aria-label="Espina temporal">
    <div className="vx-spine-title"><small>{t.temporal}</small><strong>{t.evolution}</strong></div>
    <div className="vx-spine-track">{items.length ? items.map((item) => <div className="vx-spine-event" key={item.id}><i /><small>{item.time}</small><b>{item.type.replaceAll("_", " ")}</b><span>{item.text}</span></div>) : <span className="vx-spine-empty">{t.noTimeline}</span>}</div>
  </section>;
}

function QualityNote({ quality }: { quality?: Quality }) {
  return (
    <footer className="gold-footer">
      <strong>VECTOR</strong>
      <span>
        {quality
          ? `${quality.sourceCoverage} · ${quality.freshness}`
          : "Contexto soportado por la evidencia disponible."}
      </span>
      <b>Un mejor mañana, construido con evidencia.</b>
    </footer>
  );
}

export default function ExperienceViewport() {
  const [host, setHost] = useState<Element | null>(null);
  const [path, setPath] = useState(routePath());
  const [locale, setLocale] = useState<UiLocale>(() => (localStorage.getItem("vector-ui-locale") === "en" ? "en" : "es"));
  const t = COPY[locale];
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
        `/api/experience/risks/${encodeURIComponent(selected.riskFindingId)}/change-association?serviceId=${encodeURIComponent(serviceId)}`,
      ).then(setChangeAssociation).catch(() => setChangeAssociation(undefined));
    }
  }, [overview, path, period, graphLimit]);
  const navigate = (next: string, context: Record<string, string> = {}) => {
    const params = new URLSearchParams({ period, ...context });
    window.history.pushState({}, "", `${next}?${params}`);
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

  const panorama = (
    <div className="experience-viewport">
      <div className="gold-page">
        <WorkspaceRail active={path} navigate={navigate} />
        <Header
          eyebrow="DE LA EVIDENCIA A UNA TECNOLOGÍA MÁS CONFIABLE"
          title="Panorama Ejecutivo"
          question="¿Dónde requiere atención Tecnología hoy y por qué?"
          period={period}
          onPeriodChange={changePeriod}
        />
        <div className="vx-command-status">
          <div><small>ÁREAS CON ATENCIÓN</small><strong>{overview.areas.filter((item) => item.attentionState !== "STABLE").length}</strong></div>
          <div><small>RIESGOS CON EVIDENCIA</small><strong>{overview.attentionFindings.length}</strong></div>
          <div><small>COMPROMISOS VENCIDOS</small><strong>{commitments?.overdueCount ?? 0}</strong></div>
          <div><small>{t.quality}</small><strong>{overview.quality.stale ? t.stale : overview.quality.partial ? t.partial : t.available}</strong></div>
        </div>
        <OperationalCanvas overview={overview} navigate={navigate} />
        <TemporalSpine risks={overview.attentionFindings} />
        <div className="gold-main-grid vx-command-support">
          <section className="gold-panel">
            <SectionTitle
              title="Áreas que requieren atención"
              subtitle="Áreas con señales que requieren revisión o intervención"
            />
            {overview.areas.map((item) => (
              <button
                className="gold-row"
                key={item.areaDomainId}
                onClick={() =>
                  navigate("/areas", { areaDomainId: item.areaDomainId })
                }
              >
                <i
                  className={item.attentionState === "STABLE" ? "ok" : "alert"}
                />{" "}
                <strong>{item.name}</strong>
                <span>
                  {
                    overview.services.filter(
                      (service) => service.areaDomainId === item.areaDomainId,
                    ).length
                  }{" "}
                  servicios
                </span>
                <b>{item.attentionState}</b>
                <em>Ver detalle →</em>
              </button>
            ))}
          </section>
          <aside className="gold-panel">
            <SectionTitle
              title="¿Qué está moviendo la atención?"
              subtitle="Principales señales en el período"
            />
            {overview.attentionFindings.map((item) => (
              <div className="gold-signal" key={item.riskFindingId}>
                <i>↗</i>
                <div>
                  <strong>Riesgo</strong>
                  <p>{item.condition}</p>
                </div>
              </div>
            ))}
            <div className="gold-intelligence">
              <strong>✣ VECTOR Intelligence</strong>
              <p>
                {overview.attentionFindings[0]?.explanation ??
                  "No hay hallazgos adicionales en el período."}
              </p>
            </div>
          </aside>
        </div>
        <div className="gold-bottom">
          <section className="gold-panel">
            <h3>Estado de compromisos</h3>
            <Empty>Detalle agregado no disponible para el dataset local.</Empty>
          </section>
          <section className="gold-panel">
            <h3>Resultado de acciones</h3>
            <Empty>No hay verificación agregada disponible.</Empty>
          </section>
            <section className="gold-panel">
              <h3>Contexto adicional</h3>
              <Empty>No hay contexto adicional disponible.</Empty>
            </section>
        </div>
        <DecisionQueue risks={path === "areas" ? areaRisks : overview.attentionFindings} commitments={commitments} navigate={navigate} />
        <QualityNote quality={overview.quality} />
      </div>
    </div>
  );

  const areaView = (
    <div className="experience-viewport">
      <div className="gold-page">
        <WorkspaceRail active={path} navigate={navigate} />
        <Header
          eyebrow="ÁREAS / DOMINIOS · WORKSPACE DE DECISIÓN"
          title={area?.name ?? "Área sin seleccionar"}
          question={`¿Qué servicios concentran la atención en ${area?.name ?? "esta área"}, qué la explica y qué seguimiento requiere?`}
          period={period}
          onPeriodChange={changePeriod}
        />
        <div className="vx-area-summary">
          <div>
            <small>CONTEXTO DEL ÁREA</small>
            <strong>{areaServices.length} servicios en contexto</strong>
            <span>{areaRisks.length} hallazgos con evidencia disponible</span>
          </div>
          <div>
            <small>SEGUIMIENTO</small>
            <strong>{commitments?.activeCount ?? 0} compromisos activos</strong>
            <span>La ejecución no implica resultado verificado.</span>
          </div>
          <div>
            <small>CALIDAD DE DECISIÓN</small>
            <strong>{overview.quality.stale ? "Contexto desactualizado" : "Contexto disponible"}</strong>
            <span>{overview.quality.missingContext.length ? `${overview.quality.missingContext.length} vacíos de contexto` : "Sin vacíos declarados"}</span>
          </div>
        </div>

        <div className="vx-area-workspace">
          <section className="gold-panel vx-service-portfolio">
            <SectionTitle
              title="Portafolio de servicios"
              subtitle="Comparación dentro del área; seleccione un servicio para continuar la investigación."
            />
            <div className="vx-portfolio-head"><span>Servicio</span><span>Condición</span><span>Hallazgos</span><span>Decisión</span></div>
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
                  <em>{serviceRisks.length ? "Investigar →" : "Revisar contexto →"}</em>
                </button>
              );
            }) : <Empty>No hay servicios disponibles para el área seleccionada.</Empty>}
          </section>

          <aside className="vx-area-attention">
            <div className="gold-panel">
              <SectionTitle
                title="Concentración de atención"
                subtitle="Hallazgos explicables; no es un score ni un ranking de personas."
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
              )) : <Empty>No hay hallazgos de atención sustentados por la evidencia disponible.</Empty>}
            </div>
            <div className="gold-intelligence">
              <strong>✣ VECTOR Intelligence</strong>
              <p>La vista del área organiza contexto para decidir dónde profundizar; no convierte correlación en causalidad ni asigna responsabilidad individual.</p>
            </div>
          </aside>
        </div>

        <section className="gold-panel vx-area-followup">
          <SectionTitle title="Seguimiento del área" subtitle="Riesgo → compromiso → acción → resultado." />
          <div className="vx-followup-grid">
            <div><small>RIESGO</small><strong>{areaRisks.length}</strong><span>hallazgos visibles</span></div>
            <div><small>COMPROMISO</small><strong>{commitments?.activeCount ?? 0}</strong><span>activos</span></div>
            <div><small>ACCIÓN</small><strong>N/D</strong><span>sin agregado de área</span></div>
            <div><small>RESULTADO</small><strong>N/D</strong><span>sin verificación agregada</span></div>
          </div>
        </section>

        <DecisionQueue risks={areaRisks} commitments={commitments} navigate={navigate} />
        <QualityNote quality={overview.quality} />
      </div>
    </div>
  );

  const commitmentView = (
    <div className="experience-viewport">
      <div className="gold-page">
        <WorkspaceRail active={path} navigate={navigate} />
        <Header
          eyebrow="COMPROMISOS & MEJORAS · WORKFLOW"
          title="Compromisos & Mejoras"
          question="¿Qué se comprometió, qué cambió en la ejecución y qué resultado está realmente verificado?"
          period={period}
          onPeriodChange={changePeriod}
        />
        <LensNav labelText="Lentes de compromisos y resultados" items={[
          ["commitment-list", "Flujo de compromisos"],
          ["commitment-results", "Resultado y evidencia"],
          ["commitment-new", "Nuevo compromiso"],
        ]} />
        <div className="vx-commitment-strip">
          <div><small>ACTIVOS</small><strong>{commitments?.activeCount ?? 0}</strong></div>
          <div><small>RENEGOCIADOS</small><strong>{commitments?.renegotiatedCount ?? 0}</strong></div>
          <div><small>RELIABILITY RATE</small><strong>{commitments?.commitmentReliabilityRate == null ? "N/D" : `${Math.round(commitments.commitmentReliabilityRate * 100)}%`}</strong></div>
          <div><small>OUTCOME PENDIENTE</small><strong>{commitments?.outcomePendingCount ?? 0}</strong></div>
        </div>
        <div className="vx-commitment-workspace">
          <section className="gold-panel">
            <SectionTitle id="commitment-list" title="Flujo de compromisos" subtitle="La fecha y la renegociación preservan historia; completar ejecución no verifica resultado." />
            <div className="vx-ledger-head"><span>Compromiso</span><span>Área</span><span>Estado</span><span>Fecha</span><span>Acciones</span></div>
            {commitments?.commitments.map((item) => (
              <div className="vx-ledger-row" key={item.commitmentId}>
                <strong>{item.declaration}</strong>
                <span>{label(item.accountableAreaDomainId)}</span>
                <b>{item.executionStatus ?? item.statusContext}</b>
                <em>{item.currentDueDate ?? (item.overdue ? "Vencido" : "En seguimiento")}</em>
                <div className="vx-commitment-actions">
                  <button onClick={() => updateCommitmentStatus(item.commitmentId, "IN_PROGRESS")}>En progreso</button>
                  <button onClick={() => updateCommitmentStatus(item.commitmentId, "COMPLETED")}>Completar</button>
                  <button onClick={() => setRenegotiationId(item.commitmentId)}>Renegociar</button>
                </div>
              </div>
            ))}
          </section>
          <aside className="vx-commitment-side">
            <div className="gold-panel" id="commitment-results">
              <SectionTitle title="Resultado y evidencia" />
              <Empty>No hay OutcomeVerification agregado para esta vista. La ejecución completada permanece separada del resultado.</Empty>
            </div>
            <div className="gold-intelligence">
              <strong>✣ VECTOR Intelligence</strong>
              <p>Prioriza seguimiento gobernado: compromiso → ejecución → evidencia → resultado. No convierte actividad completada en mejora.</p>
            </div>
            <form className="gold-panel gold-form" id="commitment-new" onSubmit={createCommitment}>
              <h3>Nuevo compromiso</h3>
              <label>Declaración<input value={declaration} onChange={(event) => setDeclaration(event.target.value)} required /></label>
              <label>Área responsable<select key={area?.areaDomainId} defaultValue={area?.areaDomainId ?? "area-platform"} disabled><option value={area?.areaDomainId ?? "area-platform"}>{area?.name ?? "Platform"}</option></select></label>
              <label>Resultado esperado<input value={intendedResult} onChange={(event) => setIntendedResult(event.target.value)} /></label>
              <label>Fecha comprometida<input type="date" value={commitmentDueDate} onChange={(event) => setCommitmentDueDate(event.target.value)} /></label>
              <button>Crear compromiso</button>
            </form>
            {renegotiationId && <form className="gold-panel gold-form" onSubmit={renegotiateCommitment}>
              <h3>Renegociar compromiso</h3>
              <p>{label(renegotiationId)} · la fecha anterior permanecerá en el historial.</p>
              <label>Nueva fecha<input type="date" value={renegotiationDate} onChange={(event) => setRenegotiationDate(event.target.value)} required /></label>
              <label>Razón<input value={renegotiationReason} onChange={(event) => setRenegotiationReason(event.target.value)} required /></label>
              <button>Registrar renegociación</button>
            </form>}
          </aside>
        </div>
        <QualityNote />
      </div>
    </div>
  );

  const serviceView = (
    <div className="experience-viewport">
      <div className="gold-page">
        <WorkspaceRail active={path} navigate={navigate} />
        <Header
          eyebrow={`SERVICIO · ${label(detail?.service?.areaDomainId)}`}
          title={detail?.service?.name ?? "Service Intelligence"}
          question={detail?.service ? `¿Qué está ocurriendo en este servicio, qué evidencia lo sustenta y qué contexto operacional falta?` : "Cargando contexto del servicio."}
          period={period}
          onPeriodChange={changePeriod}
        />
        <LensNav labelText="Lentes del servicio" items={[
          ["service-condition", "Condición"],
          ["service-evidence", "Evidencia"],
          ["service-slo", "SLO"],
          ["service-incidents", "Incidentes"],
          ["service-changes", "Cambios"],
        ]} />
        <section className="vx-service-condition" id="service-condition">
          <div>
            <small>CONDICIÓN OPERACIONAL</small>
            <strong>{detail?.service?.conditionContext ?? "Contexto no disponible"}</strong>
            <span>{detail?.riskFindings.length ?? 0} hallazgos · {detail?.evidence.length ?? 0} evidencias · {detail?.commitments.length ?? 0} compromisos</span>
          </div>
          <div>
            <small>CALIDAD DEL CONTEXTO</small>
            <strong>{detail?.quality.stale ? "Desactualizado" : detail?.quality.partial ? "Parcial" : "Disponible"}</strong>
            <span>{detail?.quality.missingContext.length ? detail.quality.missingContext.join(" · ") : "Sin contexto faltante declarado"}</span>
          </div>
        </section>
        <OperationalCanvas overview={overview} selectedServiceId={detail?.service?.serviceId} navigate={navigate} />
        <TemporalSpine risks={detail?.riskFindings} />
        <div className="vx-service-workspace">
          <section>
            <div className="gold-panel gold-stack">
              <SectionTitle title="Hallazgos que requieren investigación" subtitle="Cada hallazgo mantiene evidencia, limitaciones y contexto del servicio." />
              {detail?.riskFindings.length ? detail.riskFindings.map((item) => (
                <button className="vx-service-finding" key={item.riskFindingId} onClick={() => navigate(`/risks/${encodeURIComponent(item.riskFindingId)}`, {
                  areaDomainId: detail.service?.areaDomainId ?? "",
                  serviceId: item.serviceId,
                  riskFindingId: item.riskFindingId,
                })}>
                  <small>HALLAZGO</small><strong>{item.condition}</strong><span>{item.explanation}</span><em>Investigar evidencia y relaciones →</em>
                </button>
              )) : <Empty>No hay RiskFinding sustentado para el contexto seleccionado.</Empty>}
            </div>
            <div className="gold-panel" id="service-evidence">
              <SectionTitle title="Evidencia operacional" subtitle="Hechos disponibles para el servicio; ausencia de datos no implica operación normal." />
              {detail?.evidence.length ? detail.evidence.map((item) => (
                <div className="gold-evidence" key={item.evidenceId}><b>{t.fact}</b><strong>{item.supportedClaim}</strong><span>{item.observedAt} · {item.sourceReferenceIds.map(label).join(", ")}</span></div>
              )) : <Empty>No hay evidencia expuesta por esta proyección.</Empty>}
            </div>
          </section>
          <aside className="vx-service-context">
            <div className="gold-panel" id="service-slo"><SectionTitle title="SLO / tendencia" /><Empty>No hay serie SLO disponible para el dataset local.</Empty></div>
            <div className="gold-panel" id="service-incidents"><SectionTitle title="Incidentes" /><Empty>No hay incidentes expuestos por esta proyección.</Empty></div>
            <div className="gold-panel" id="service-changes"><SectionTitle title="Cambios y despliegues" /><Empty>No hay cambios expuestos por esta proyección.</Empty></div>
            <div className="gold-intelligence"><strong>✣ VECTOR Intelligence</strong><p>{detail?.riskFindings[0]?.explanation ?? "No hay hallazgos adicionales."}</p><small>La explicación se limita a la evidencia disponible.</small></div>
          </aside>
        </div>
        <QualityNote quality={detail?.quality} />
      </div>
    </div>
  );

  const riskView = (
    <div className="experience-viewport">
      <div className="gold-page">
        <WorkspaceRail active={path} navigate={navigate} />
        <Header
          eyebrow="INVESTIGACIÓN"
          title={risk?.riskFinding?.condition ?? "Risk Investigation"}
          question={
            risk?.riskFinding?.explanation ??
            "Cargando explicación y evidencia."
          }
          period={period}
          onPeriodChange={changePeriod}
        />
        <SemanticLegend />
        <TemporalSpine signals={signals} risks={risk?.riskFindings} />
        <LensNav labelText="Lentes de investigación" items={[
          ["risk-timeline", "Línea de tiempo"],
          ["risk-evidence", "Evidencia"],
          ["risk-change", "Cambio asociado"],
          ["risk-relations", "Relaciones"],
          ["risk-actions", "Acciones"],
          ["risk-outcome", "Resultado"],
        ]} />
        <div className="gold-metrics">
          <Metric
            tone="danger"
            title="Ocurrencias"
            value={risk?.evidence.length ?? 0}
            note="Evidencia en el período"
          />
          <Metric
            tone="warning"
            title="Impacto estimado"
            value="N/D"
            note="No inferido por VECTOR"
          />
          <Metric
            tone="purple"
            title="Acciones"
            value={risk?.improvementActions.length ?? 0}
            note="Ejecución registrada"
          />
          <Metric
            tone="danger"
            title="Estado del riesgo"
            value={risk?.outcomeVerifications[0]?.outcome ?? "Sin verificar"}
            note="Resultado basado en evidencia"
          />
        </div>
        <div className="gold-main-grid">
          <section>
            <div className="gold-panel">
              <SectionTitle id="risk-timeline" title="Línea de Tiempo del Riesgo" />
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
              )) : <Empty>No hay historia temporal suficiente en la evidencia disponible.</Empty>}
            </div>
            <div className="gold-panel">
              <SectionTitle id="risk-evidence" title="Evidencia Disponible" />
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
              <strong>✣ VECTOR Intelligence</strong>
              <p>{risk?.riskFinding?.explanation}</p>
              <small>Correlación temporal/contextual ≠ causalidad.</small>
            </div>
            <div className="gold-panel vx-ai-assist" aria-live="polite">
              <SectionTitle title="Asistencia de IA basada en evidencia" subtitle="Asesoría únicamente · nunca autoritativa" />
              <b>{aiAssist?.status ?? "LOADING"}</b>
              <p>{aiAssist?.explanation ?? aiAssist?.limitations?.join(" · ") ?? "Validando el límite del proveedor gobernado…"}</p>
              <small>{aiAssist?.provenance ?? "No se afirma disponibilidad de proveedor sin evidencia."}</small>
            </div>
            <div className="gold-panel vx-change-association">
              <SectionTitle id="risk-change" title="Degradación asociada a cambio" subtitle="Antes / durante / después · asociación ≠ causalidad" />
              {changeAssociation ? <>
                <div className="vx-change-path"><span>{t.before}</span><i>→</i><span>{t.change} {label(changeAssociation.changeId)}</span><i>→</i><span>{t.after}</span></div>
                <p><strong>{changeAssociation.contextualAssociation ? "Contextual association detected" : "Association not established"}</strong> · Causal claim: {changeAssociation.causalClaim ? "YES" : "NO"}</p>
                <small>{changeAssociation.limitation}</small>
              </> : <Empty>No change/deployment association context is available.</Empty>}
            </div>
            <div className="gold-panel">
              <SectionTitle id="risk-relations" title="Relaciones y topología contextual" />
              <div className="semantic-graph">
                {graph?.graph.relationships.map((relation, index) => (
                  <div
                    className="semantic-edge"
                    key={`${relation.predicate}-${index}`}
                  >
                      <button className={graphFocus === relation.source.canonicalId ? "focused" : ""} onClick={() => setGraphFocus(relation.source.canonicalId)}>{graphNodeLabel(relation.source.canonicalId)}</button>
                    <b>{relation.predicate.replaceAll("_", " ")}</b>
                    <button className={graphFocus === relation.target.canonicalId ? "focused" : ""} onClick={() => setGraphFocus(relation.target.canonicalId)}>{graphNodeLabel(relation.target.canonicalId)}</button>
                  </div>
                ))}
              </div>
              {graphFocus && <div className="vx-graph-focus" aria-live="polite"><strong>Contexto seleccionado</strong><span>{graphNodeLabel(graphFocus)}</span><small>Contexto relacional acotado; seleccionar un nodo no afirma causalidad.</small></div>}
              <div className="vx-graph-controls">
                <span>{graph?.graph.truncated ? "Vista acotada: existen relaciones adicionales." : "Vista acotada completa para el límite actual."}</span>
                <span>Frescura: {graph?.graph.freshness ?? "desconocida"}</span>
                {graph?.graph.truncated && <button type="button" onClick={() => setGraphLimit((current) => Math.min(current + 4, 20))}>Expandir contexto</button>}
                {graphLimit > 6 && <button type="button" onClick={() => setGraphLimit(6)}>Restablecer límite</button>}
              </div>
            </div>
            <div className="gold-panel">
              <SectionTitle id="risk-actions" title="Acciones Asociadas" />
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
                <Empty>No hay compromisos asociados.</Empty>
              )}
            </div>
            <div className="gold-panel">
              <SectionTitle id="risk-outcome" title="Resultado verificado" />
              {risk?.outcomeVerifications.length ? risk.outcomeVerifications.map((outcome) => (
                <div className="gold-action" key={outcome.verificationId}>
                  <strong>{outcome.outcome}</strong>
                  <p>Evidencia: {outcome.evidenceIds.map(label).join(", ")}</p>
                </div>
              )) : <Empty>El resultado aún no es verificable con la evidencia disponible.</Empty>}
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
