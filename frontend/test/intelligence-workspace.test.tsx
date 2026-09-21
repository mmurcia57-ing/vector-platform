// @vitest-environment jsdom
import { render, screen, fireEvent, cleanup } from "@testing-library/react";
import { afterEach, describe, expect, it, vi } from "vitest";
afterEach(cleanup);
import { Header, LensNav, LocaleBoundary, SemanticLegend, WorkspaceRail } from "../src/ExperienceViewport";

describe("EXT-003 intelligence workspace", () => {
  it("communicates the full decision loop and layers without relying on color", () => {
    render(<LocaleBoundary locale="en"><WorkspaceRail active="overview" navigate={() => undefined} /><SemanticLegend /></LocaleBoundary>);
    expect(screen.getByText(/SIGNAL/)).toBeTruthy();
    expect(screen.getByText("Command")).toBeTruthy();
    expect(screen.getByText("Investigation")).toBeTruthy();
    expect(screen.getByText("Actions & Outcomes")).toBeTruthy();
    expect(screen.getByText("Observed evidence")).toBeTruthy();
    expect(screen.getByText("Correlation / uncertainty")).toBeTruthy();
    expect(screen.getByText("Verified outcome")).toBeTruthy();
  });

  it("preserves navigable investigation layers", () => {
    const navigate = vi.fn();
    render(<LocaleBoundary locale="en"><WorkspaceRail active="overview" navigate={navigate} /></LocaleBoundary>);
    fireEvent.click(screen.getByRole("button", { name: "Investigation" }));
    expect(navigate).toHaveBeenCalledWith("/risks");
  });

  it("marks the active workspace layer for visual state", () => {
    render(<LocaleBoundary locale="en"><WorkspaceRail active="commitments" navigate={() => undefined} /></LocaleBoundary>);
    expect(screen.getByRole("button", { name: "Actions & Outcomes" }).className).toContain("active");
  });
});


it("keeps workspace navigation behavior explicit rather than decorative", () => {
  const calls: string[] = [];
  render(<LocaleBoundary locale="en"><WorkspaceRail active="services" navigate={(next) => calls.push(next)} /></LocaleBoundary>);
  fireEvent.click(screen.getByRole("button", { name: "Investigation" }));
  expect(calls).toEqual(["/risks"]);
  expect(screen.getByRole("button", { name: "Service" }).className).toContain("active");
});


it("changes the deterministic analysis scenario through a real control", () => {
  const periods: string[] = [];
  render(<LocaleBoundary locale="es"><Header eyebrow="Contexto" title="VECTOR" question="Pregunta" period="local-dataset-v1" onPeriodChange={(period) => periods.push(period)} /></LocaleBoundary>);
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


it("area workspace remains structurally distinct from executive triage", () => {
  const source = require("node:fs").readFileSync(new URL("../src/ExperienceViewport.tsx", import.meta.url), "utf8");
  expect(source).toContain("vx-service-portfolio");
  expect(source).toContain('tr("Portafolio de servicios", "Service portfolio")');
  expect(source).toContain('tr("Concentración de atención", "Attention concentration")');
  expect(source).toContain('tr("Seguimiento del área", "Area follow-up")');
});


it("renders the workspace coherently in Spanish through an explicit locale boundary", () => {
  render(<LocaleBoundary locale="es"><WorkspaceRail active="overview" navigate={() => undefined} /><SemanticLegend /></LocaleBoundary>);
  expect(screen.getByText("Panorama")).toBeTruthy();
  expect(screen.getByText("Investigación")).toBeTruthy();
  expect(screen.getByText("Acciones y resultados")).toBeTruthy();
  expect(screen.getByText("Evidencia observada")).toBeTruthy();
  expect(screen.getByText("Correlación / incertidumbre")).toBeTruthy();
});
