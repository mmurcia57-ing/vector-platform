// @vitest-environment jsdom
import { render, screen, fireEvent, cleanup } from "@testing-library/react";
import { afterEach, describe, expect, it, vi } from "vitest";
afterEach(cleanup);
import { buildJourneyUrl, ContextEnvelope, Header, LensNav, SemanticLegend, WorkspaceRail } from "../src/ExperienceViewport";

describe("EXT-003 intelligence workspace", () => {
  it("communicates the full decision loop and layers without relying on color", () => {
    render(<><WorkspaceRail active="overview" navigate={() => undefined} /><SemanticLegend /></>);
    expect(screen.getByText(/SEÑAL/)).toBeTruthy();
    expect(screen.getByText("Panorama")).toBeTruthy();
    expect(screen.getByText("Investigación")).toBeTruthy();
    expect(screen.getByText("Acciones y resultados")).toBeTruthy();
    expect(screen.getByText("Evidencia observada")).toBeTruthy();
    expect(screen.getByText("Correlación / incertidumbre")).toBeTruthy();
    expect(screen.getByText("Resultado verificado")).toBeTruthy();
  });

  it("preserves navigable investigation layers", () => {
    const navigate = vi.fn();
    render(<WorkspaceRail active="overview" navigate={navigate} />);
    fireEvent.click(screen.getByRole("button", { name: "Investigación" }));
    expect(navigate).toHaveBeenCalledWith("/risks");
  });

  it("marks the active workspace layer for visual state", () => {
    render(<WorkspaceRail active="commitments" navigate={() => undefined} />);
    expect(screen.getByRole("button", { name: "Acciones y resultados" }).className).toContain("active");
  });
});


it("keeps workspace navigation behavior explicit rather than decorative", () => {
  const calls: string[] = [];
  render(<WorkspaceRail active="services" navigate={(next) => calls.push(next)} />);
  fireEvent.click(screen.getByRole("button", { name: "Investigación" }));
  expect(calls).toEqual(["/risks"]);
  expect(screen.getByRole("button", { name: "Servicio" }).className).toContain("active");
});


it("changes the deterministic analysis scenario through a real control", () => {
  const periods: string[] = [];
  render(<Header eyebrow="Contexto" title="VECTOR" question="Pregunta" period="local-dataset-v1" onPeriodChange={(period) => periods.push(period)} />);
  fireEvent.change(screen.getByRole("combobox", { name: "Escenario de análisis" }), { target: { value: "local-partial-stale" } });
  expect(periods).toEqual(["local-partial-stale"]);
  expect(screen.getByText(/Datos demostrativos locales/)).toBeTruthy();
});

it("investigation lenses execute navigation to an explicit section", () => {
  const target = document.createElement("div");
  target.id = "risk-evidence";
  const scrollIntoView = vi.fn();
  target.scrollIntoView = scrollIntoView;
  document.body.appendChild(target);
  render(<LensNav labelText="Lentes de investigación" items={[["risk-evidence", "Evidencia"]]} />);
  fireEvent.click(screen.getByRole("button", { name: "Evidencia" }));
  expect(scrollIntoView).toHaveBeenCalledOnce();
  target.remove();
});



describe("Experience Contract V3 context continuity", () => {
  it("renders the selected cross-surface context without implying causality", () => {
    render(<ContextEnvelope
      area={{ areaDomainId: "area-a", name: "Área A", attentionState: "ATTENTION" }}
      service={{ serviceId: "service-a", name: "Servicio A", areaDomainId: "area-a", conditionContext: "Condición observada" }}
      risk={{ riskFindingId: "risk-a", serviceId: "service-a", condition: "Persistencia", explanation: "Evidencia disponible" }}
      period="local-dataset-v1"
      quality={{ sourceCoverage: "PARTIAL", freshness: "CURRENT", confidenceContext: "BOUNDED", uncertainty: ["VISIBLE"], limitations: ["Demo"], missingContext: ["partial fixture"] }}
    />);
    expect(screen.getByText("Área A")).toBeTruthy();
    expect(screen.getByText("Servicio A")).toBeTruthy();
    expect(screen.getByText("Persistencia")).toBeTruthy();
    expect(screen.getByText(/no representa causalidad/i)).toBeTruthy();
    expect(screen.getByText("PARCIAL")).toBeTruthy();
  });
});


describe("Experience Contract V3 executable journey continuity", () => {
  it("preserves Area → Service → Risk → Commitment context and supports return without causal mutation", () => {
    const s1s2 = buildJourneyUrl("/areas", "?period=local-dataset-v1", "local-dataset-v1", "/", { areaDomainId: "area-a", condition: "attention-a" });
    expect(s1s2).toContain("areaDomainId=area-a");
    expect(s1s2).toContain("origin=%2F");

    const q2 = s1s2.slice(s1s2.indexOf("?"));
    const s2s3 = buildJourneyUrl("/services/service-a", q2, "local-dataset-v1", "/areas", { serviceId: "service-a" });
    expect(s2s3).toContain("areaDomainId=area-a");
    expect(s2s3).toContain("serviceId=service-a");

    const q3 = s2s3.slice(s2s3.indexOf("?"));
    const s3s4 = buildJourneyUrl("/risks/risk-a", q3, "local-dataset-v1", "/services/service-a", { riskFindingId: "risk-a" });
    expect(s3s4).toContain("riskFindingId=risk-a");
    expect(s3s4).toContain("condition=attention-a");

    const q4 = s3s4.slice(s3s4.indexOf("?"));
    const s4s5 = buildJourneyUrl("/commitments", q4, "local-dataset-v1", "/risks/risk-a", { commitmentId: "commitment-a" });
    expect(s4s5).toContain("serviceId=service-a");
    expect(s4s5).toContain("riskFindingId=risk-a");
    expect(s4s5).toContain("commitmentId=commitment-a");

    const q5 = s4s5.slice(s4s5.indexOf("?"));
    const returned = buildJourneyUrl("/risks/risk-a", q5, "local-dataset-v1", "/commitments", { outcomeVerificationId: "outcome-a" });
    expect(returned).toContain("areaDomainId=area-a");
    expect(returned).toContain("serviceId=service-a");
    expect(returned).toContain("riskFindingId=risk-a");
    expect(returned).toContain("commitmentId=commitment-a");
    expect(returned).toContain("outcomeVerificationId=outcome-a");
    expect(returned).not.toMatch(/causal/i);
  });
});
