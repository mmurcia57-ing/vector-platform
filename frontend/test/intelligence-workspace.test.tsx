// @vitest-environment jsdom
import { render, screen, fireEvent } from "@testing-library/react";
import { describe, expect, it, vi } from "vitest";
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
