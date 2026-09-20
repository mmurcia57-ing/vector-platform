import { useEffect, useState } from "react";
import type { FormEvent } from "react";
import { createPortal } from "react-dom";
import "./IntelligenceWorkspace.css";

const PERIOD = "local-dataset-v1";
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
function Header({
  eyebrow,
  title,
  question,
}: {
  eyebrow: string;
  title: string;
  question: string;
}) {
  return (
    <header className="gold-header">
      <div>
        <span>{eyebrow}</span>
        <h1>{title}</h1>
        <p>{question}</p>
      </div>
      <div className="gold-filters">
        <span>Últimos 30 días</span>
        <span>Contexto local</span>
      </div>
    </header>
  );
}
function SectionTitle({
  title,
  subtitle,
}: {
  title: string;
  subtitle?: string;
}) {
  return (
    <div className="gold-section-title">
      <div>
        <h2>{title}</h2>
        {subtitle && <p>{subtitle}</p>}
      </div>
    </div>
  );
}
export function WorkspaceRail({ active, navigate }: { active: string; navigate: (next: string, context?: Record<string, string>) => void }) {
  const items = [
    ["overview", "/", "Command"],
    ["areas", "/areas", "Area"],
    ["services", "/services", "Service"],
    ["risks", "/risks", "Investigation"],
    ["commitments", "/commitments", "Action & Outcome"],
  ];
  return (
    <div className="vx-workspace-rail" aria-label="VECTOR intelligence workspace">
      <div><strong>VECTOR / INTELLIGENCE WORKSPACE</strong><small>Evidence-led technology control plane</small></div>
      <nav aria-label="Investigation layers">
        {items.map(([key, route, text]) => <button key={key} className={active === key ? "active" : ""} onClick={() => navigate(route)}>{text}</button>)}
      </nav>
      <div className="vx-loop"><b>SIGNAL</b> → FOCUS → EXPLAIN → RELATE → DECIDE → ACT → VERIFY</div>
    </div>
  );
}
export function SemanticLegend() {
  return <div className="vx-semantics" aria-label="Semantic evidence legend">
    <span className="vx-semantic fact">Observed evidence</span>
    <span className="vx-semantic">Derived intelligence</span>
    <span className="vx-semantic uncertain">Correlation / uncertainty</span>
    <span className="vx-semantic outcome">Verified outcome</span>
  </div>;
}

function DecisionQueue({ risks, commitments, navigate }: { risks: Risk[]; commitments?: CommitmentView; navigate: (next: string, context?: Record<string, string>) => void }) {
  return <section className="vx-decision-queue" aria-label="Decision queue">
    <div><span>DECISION QUEUE</span><strong>{risks.length} evidence-backed conditions require review</strong><small>{commitments?.overdueCount ?? 0} overdue commitments in available context</small></div>
    <div className="vx-decision-items">{risks.slice(0, 3).map((risk) => <button key={risk.riskFindingId} onClick={() => navigate("/risks", { riskFindingId: risk.riskFindingId, serviceId: risk.serviceId })}><b>Investigate</b><span>{risk.condition}</span><small>{risk.explanation}</small></button>)}</div>
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
  const [overview, setOverview] = useState<Overview>();
  const [detail, setDetail] = useState<Detail>();
  const [risk, setRisk] = useState<Detail>();
  const [commitments, setCommitments] = useState<CommitmentView>();
  const [graph, setGraph] = useState<Graph>();
  const [signals, setSignals] = useState<TemporalSignal[]>([]);
  const [graphFocus, setGraphFocus] = useState("");
  const [declaration, setDeclaration] = useState("");
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
    void read<Overview>(`/api/experience/overview?period=${PERIOD}`).then(
      setOverview,
    );
    void read<CommitmentView>(
      "/api/experience/commitments?asOf=2025-01-01&limit=20",
    ).then(setCommitments).catch((error) => setLoadError(error instanceof Error ? error.message : "Commitments unavailable"));
  }, [path]);
  useEffect(() => {
    if (!overview) return;
    const params = new URLSearchParams(window.location.search);
    if (path === "services") {
      const serviceId =
        routeId() || params.get("serviceId") || overview.services[0]?.serviceId;
      if (serviceId)
        void read<Detail>(
          `/api/experience/services/${encodeURIComponent(serviceId)}?period=${PERIOD}`,
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
        `/api/experience/risks/${encodeURIComponent(selected.riskFindingId)}?period=${PERIOD}&serviceId=${encodeURIComponent(serviceId)}`,
      ).then(setRisk).catch((error) => setLoadError(error instanceof Error ? error.message : "Risk intelligence unavailable"));
      void read<Graph>(
        `/api/experience/graph?period=${PERIOD}&serviceId=${encodeURIComponent(serviceId)}&riskFindingId=${encodeURIComponent(selected.riskFindingId)}&maxNodes=12&maxRelationships=16`,
      ).then(setGraph).catch((error) => setLoadError(error instanceof Error ? error.message : "Graph unavailable"));
      void read<TemporalSignal[]>(
        `/api/experience/signals?period=${PERIOD}&serviceId=${encodeURIComponent(serviceId)}&riskFindingId=${encodeURIComponent(selected.riskFindingId)}&limit=50`,
      ).then(setSignals).catch((error) => setLoadError(error instanceof Error ? error.message : "Temporal intelligence unavailable"));
    }
  }, [overview, path]);
  const navigate = (next: string, context: Record<string, string> = {}) => {
    const params = new URLSearchParams({ period: PERIOD, ...context });
    window.history.pushState({}, "", `${next}?${params}`);
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
        executionStatus: "OPEN",
        intendedResult: declaration,
      }),
    })
      .then(() =>
        read<CommitmentView>(
          "/api/experience/commitments?asOf=2025-01-01&limit=20",
        ),
      )
      .then(setCommitments).catch((error) => setLoadError(error instanceof Error ? error.message : "Commitments unavailable"));
    setDeclaration("");
  };
  if (!host) return null;
  if (loadError) return createPortal(<div className="experience-viewport"><div className="gold-page"><div className="gold-panel" role="alert"><h3>Intelligence temporarily unavailable</h3><p>{loadError}</p><button className="primary-button" onClick={() => window.location.reload()}>Retry</button></div></div></div>, host);
  if (!overview) return createPortal(<div className="experience-viewport"><div className="loading-screen" role="status" aria-live="polite"><h1>VECTOR</h1><p>Loading evidence-backed intelligence…</p></div></div>, host);

  const panorama = (
    <div className="experience-viewport">
      <div className="gold-page">
        <WorkspaceRail active={path} navigate={navigate} />
        <Header
          eyebrow="DE LA EVIDENCIA A UNA TECNOLOGÍA MÁS CONFIABLE"
          title="Panorama Ejecutivo"
          question="¿Dónde requiere atención Tecnología hoy y por qué?"
        />
        <div className="gold-metrics">
          <Metric
            tone="danger"
            title="Áreas con atención"
            value={
              overview.areas.filter((item) => item.attentionState !== "STABLE")
                .length
            }
            note="Requieren revisión o intervención"
          />
          <Metric
            tone="purple"
            title="Compromisos vencidos"
            value={commitments?.overdueCount ?? 0}
            note="Según contexto disponible"
          />
          <Metric
            tone="warning"
            title="Riesgos persistentes"
            value={overview.attentionFindings.length}
            note="Con evidencia disponible"
          />
          <Metric
            tone="info"
            title="Acciones sin efecto"
            value="N/D"
            note="No disponible en esta proyección"
          />
        </div>
        <div className="gold-main-grid">
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
          eyebrow="ÁREAS / DOMINIOS"
          title={`Area Intelligence — ${area?.name ?? "Sin área"}`}
          question={`¿Qué está ocurriendo en ${area?.name ?? "esta área"} y qué requiere atención?`}
        />
        <div className="gold-metrics">
          <Metric
            tone="danger"
            title="Servicios con atención"
            value={areaServices.length}
            note={`Dentro de ${area?.name ?? "esta área"}`}
          />
          <Metric
            tone="purple"
            title="Compromisos"
            value={commitments?.activeCount ?? 0}
            note="Contexto actualmente disponible"
          />
          <Metric
            tone="warning"
            title="Riesgos persistentes"
            value={areaRisks.length}
            note="Con evidencia disponible"
          />
          <Metric
            tone="info"
            title="Resultados"
            value="N/D"
            note="Sin verificación agregada"
          />
        </div>
        <div className="gold-main-grid">
          <section className="gold-panel">
            <SectionTitle
              title="Servicios que requieren atención"
              subtitle="Servicios del área con señales relevantes"
            />
            {areaServices.length ? (
              areaServices.map((service) => (
                <button
                  className="gold-row"
                  key={service.serviceId}
                  onClick={() =>
                    navigate(
                      `/services/${encodeURIComponent(service.serviceId)}`,
                      {
                        areaDomainId: service.areaDomainId,
                        serviceId: service.serviceId,
                      },
                    )
                  }
                >
                  <i className="alert" />
                  <strong>{service.name}</strong>
                  <span>{service.conditionContext}</span>
                  <b>
                    {
                      areaRisks.filter(
                        (item) => item.serviceId === service.serviceId,
                      ).length
                    }{" "}
                    riesgos
                  </b>
                  <em>Ver detalle →</em>
                </button>
              ))
            ) : (
              <Empty>
                No hay servicios adicionales que requieran atención.
              </Empty>
            )}
          </section>
          <aside className="gold-panel">
            <SectionTitle
              title="¿Qué explica la situación del área?"
              subtitle="Señales consolidadas desde evidencia SRE"
            />
            {areaRisks.map((item) => (
              <div className="gold-signal" key={item.riskFindingId}>
                <i>↗</i>
                <div>
                  <strong>Riesgo</strong>
                  <p>{item.explanation}</p>
                </div>
              </div>
            ))}
            <div className="gold-intelligence">
              <strong>✣ VECTOR Intelligence</strong>
              <p>
                La atención del área se explica únicamente con condiciones y
                evidencia disponibles; no se infiere causalidad.
              </p>
            </div>
          </aside>
        </div>
        <div className="gold-bottom">
          <section className="gold-panel">
            <h3>Compromisos del área</h3>
            <p>
              {commitments?.activeCount ?? 0} compromisos activos en el contexto
              disponible.
            </p>
          </section>
          <section className="gold-panel">
            <h3>Resultado de mejoras</h3>
            <Empty>No hay verificación agregada disponible.</Empty>
          </section>
          <section className="gold-panel">
            <h3>Contexto SRE del área</h3>
            <p>
              {areaServices.length} servicios · {areaRisks.length} condiciones
              de riesgo.
            </p>
          </section>
        </div>
        <DecisionQueue risks={path === "areas" ? areaRisks : overview.attentionFindings} commitments={commitments} navigate={navigate} />
        <QualityNote quality={overview.quality} />
      </div>
    </div>
  );

  const commitmentView = (
    <div className="experience-viewport">
      <div className="gold-page">
        <WorkspaceRail active={path} navigate={navigate} />
        <Header
          eyebrow="COMPROMISOS & MEJORAS / VISTA GENERAL"
          title="Compromisos & Mejoras"
          question="Seguimiento de compromisos, acciones de mejora y verificación de resultados."
        />
        <nav className="gold-tabs">
          <b>Vista general</b>
          <span>Compromisos</span>
          <span>Acciones</span>
          <span>Resultados</span>
          <span>Evidencia</span>
        </nav>
        <div className="gold-metrics">
          <Metric
            tone="info"
            title="Compromisos activos"
            value={commitments?.activeCount ?? 0}
            note="En el contexto disponible"
          />
          <Metric
            tone="purple"
            title="Acciones ejecutadas"
            value={commitments?.completedCount ?? 0}
            note="Ejecución no implica resultado"
          />
          <Metric
            tone="success"
            title="Mejora verificada"
            value="N/D"
            note="Sin agregado disponible"
          />
          <Metric
            tone="danger"
            title="Sin mejora"
            value="N/D"
            note="Sin agregado disponible"
          />
        </div>
        <div className="gold-main-grid">
          <section>
            <div className="gold-panel">
              <h3>Estado de compromisos</h3>
              <div className="gold-progress">
                <i />
                <i />
                <i />
                <i />
              </div>
            </div>
            <div className="gold-panel gold-stack">
              <SectionTitle title="Compromisos recientes" />
              {commitments?.commitments.map((item) => (
                <div className="gold-table-row" key={item.commitmentId}>
                  <strong>{item.declaration}</strong>
                  <span>{label(item.accountableAreaDomainId)}</span>
                  <b>{item.executionStatus ?? item.statusContext}</b>
                  <em>{item.overdue ? "Vencido" : "En seguimiento"}</em>
                </div>
              ))}
            </div>
          </section>
          <aside>
            <div className="gold-intelligence">
              <strong>✣ VECTOR Intelligence</strong>
              <p>
                La ejecución se presenta separada de la verificación del
                resultado estructural.
              </p>
            </div>
            <div className="gold-panel">
              <h3>Verificación de resultados & Evidencia</h3>
              <Empty>
                No hay OutcomeVerification agregado para esta vista.
              </Empty>
            </div>
            <form className="gold-panel gold-form" onSubmit={createCommitment}>
              <h3>Nuevo compromiso</h3>
              <label>
                Declaración
                <input
                  value={declaration}
                  onChange={(event) => setDeclaration(event.target.value)}
                  required
                />
              </label>
              <label>
                Área responsable
                <select
                  key={area?.areaDomainId}
                  defaultValue={area?.areaDomainId ?? "area-platform"}
                  disabled
                >
                  <option value={area?.areaDomainId ?? "area-platform"}>
                    {area?.name ?? "Platform"}
                  </option>
                </select>
              </label>
              <button>Crear compromiso</button>
            </form>
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
          eyebrow={`SERVICIOS › ${label(detail?.service?.areaDomainId)}`}
          title={detail?.service?.name ?? "Service Intelligence"}
          question={
            detail?.service
              ? `${detail.service.conditionContext}. Evidencia operacional disponible.`
              : "Cargando contexto del servicio."
          }
        />
        <nav className="gold-tabs">
          <b>Vista general</b>
          <span>SLO</span>
          <span>Incidentes</span>
          <span>Cambios</span>
          <span>Riesgos</span>
          <span>Compromisos</span>
        </nav>
        <div className="gold-metrics">
          <Metric
            tone="danger"
            title="SLO cumplimiento"
            value="N/D"
            note="No disponible en la proyección"
          />
          <Metric
            tone="warning"
            title="Incidentes activos"
            value="N/D"
            note="No disponible en la proyección"
          />
          <Metric
            tone="info"
            title="Cambios recientes"
            value="N/D"
            note="No disponible en la proyección"
          />
          <Metric
            tone="purple"
            title="Compromisos"
            value={detail?.commitments.length ?? 0}
            note="En contexto del servicio"
          />
        </div>
        <div className="gold-main-grid">
          <section>
            <div className="gold-panel">
              <SectionTitle
                title="Tendencia SLO"
                subtitle="Histórico del período seleccionado"
              />
              <Empty>No hay serie SLO disponible para el dataset local.</Empty>
            </div>
            <div className="gold-panel gold-stack">
              <SectionTitle title="Hallazgos y riesgos" />
              {detail?.riskFindings.map((item) => (
                <button
                  className="gold-table-row"
                  key={item.riskFindingId}
                  onClick={() =>
                    navigate(
                      `/risks/${encodeURIComponent(item.riskFindingId)}`,
                      {
                        areaDomainId: detail.service?.areaDomainId ?? "",
                        serviceId: item.serviceId,
                        riskFindingId: item.riskFindingId,
                      },
                    )
                  }
                >
                  <strong>{item.condition}</strong>
                  <span>{item.explanation}</span>
                  <b>Atención</b>
                  <em>Investigar →</em>
                </button>
              ))}
            </div>
            <div className="gold-panel">
              <h3>Incidentes recientes</h3>
              <Empty>No hay incidentes expuestos por esta proyección.</Empty>
            </div>
          </section>
          <aside>
            <div className="gold-intelligence">
              <strong>✣ VECTOR Intelligence</strong>
              <p>
                {detail?.riskFindings[0]?.explanation ??
                  "No hay hallazgos adicionales."}
              </p>
            </div>
            <div className="gold-panel">
              <h3>Cambios recientes</h3>
              <Empty>No hay cambios expuestos por esta proyección.</Empty>
            </div>
            <div className="gold-panel">
              <h3>Evidencia disponible</h3>
              {detail?.evidence.map((item) => (
                <div className="gold-signal" key={item.evidenceId}>
                  <i>•</i>
                  <div>
                    <strong>{item.supportedClaim}</strong>
                    <p>
                      {item.observedAt} ·{" "}
                      {item.sourceReferenceIds.map(label).join(", ")}
                    </p>
                  </div>
                </div>
              ))}
            </div>
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
        />
        <SemanticLegend />
        <nav className="gold-tabs">
          <b>Hallazgo</b>
          <span>Evidencia</span>
          <span>Línea de tiempo</span>
          <span>Relaciones</span>
          <span>Correlaciones</span>
          <span>Acciones</span>
          <span>Resultado</span>
        </nav>
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
              <SectionTitle title="Línea de Tiempo del Riesgo" />
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
              <SectionTitle title="Evidencia Disponible" />
              {risk?.evidence.map((item) => (
                <div className="gold-evidence" key={item.evidenceId}>
                  <b>FACT</b>
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
            <div className="gold-panel">
              <SectionTitle title="Grafo de Relaciones" />
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
              {graphFocus && <div className="vx-graph-focus" aria-live="polite"><strong>Focused context</strong><span>{graphNodeLabel(graphFocus)}</span><small>Bounded relationship context; selecting a node does not assert causality.</small></div>}
            </div>
            <div className="gold-panel">
              <SectionTitle title="Acciones Asociadas" />
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
          </aside>
        </div>
        <QualityNote quality={risk?.quality} />
      </div>
    </div>
  );

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
  return createPortal(view, host);
}
