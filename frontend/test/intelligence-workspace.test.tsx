// @vitest-environment jsdom
import { render, screen, fireEvent, cleanup } from "@testing-library/react";
import { afterEach, describe, expect, it, vi } from "vitest";
afterEach(cleanup);
import { SemanticLegend, WorkspaceRail } from "../src/ExperienceViewport";

describe("EXT-003 intelligence workspace", () => {
  it("communicates the full decision loop and layers without relying on color", () => {
    render(<><WorkspaceRail active="overview" navigate={() => undefined} /><SemanticLegend /></>);
    expect(screen.getByText(/SIGNAL/)).toBeTruthy();
    expect(screen.getByText("Command")).toBeTruthy();
    expect(screen.getByText("Investigation")).toBeTruthy();
    expect(screen.getByText("Action & Outcome")).toBeTruthy();
    expect(screen.getByText("Observed evidence")).toBeTruthy();
    expect(screen.getByText("Correlation / uncertainty")).toBeTruthy();
    expect(screen.getByText("Verified outcome")).toBeTruthy();
  });

  it("preserves navigable investigation layers", () => {
    const navigate = vi.fn();
    render(<WorkspaceRail active="overview" navigate={navigate} />);
    fireEvent.click(screen.getByRole("button", { name: "Investigation" }));
    expect(navigate).toHaveBeenCalledWith("/risks");
  });

  it("marks the active workspace layer for visual state", () => {
    render(<WorkspaceRail active="commitments" navigate={() => undefined} />);
    expect(screen.getByRole("button", { name: "Action & Outcome" }).className).toContain("active");
  });
});


it("keeps workspace navigation behavior explicit rather than decorative", () => {
  const calls: string[] = [];
  render(<WorkspaceRail active="services" navigate={(next) => calls.push(next)} />);
  fireEvent.click(screen.getByRole("button", { name: "Investigation" }));
  expect(calls).toEqual(["/risks"]);
  expect(screen.getByRole("button", { name: "Service" }).className).toContain("active");
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
