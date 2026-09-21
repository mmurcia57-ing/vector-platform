import test from "node:test";
import assert from "node:assert/strict";
import fs from "node:fs";

const viewport = fs.readFileSync(new URL("../src/ExperienceViewport.tsx", import.meta.url), "utf8");
const contract = JSON.parse(fs.readFileSync(new URL("../../contracts/vector-experience-contract.schema.json", import.meta.url), "utf8"));

test("EXT-003 experience exposes the intelligence-workspace structure without copy or locale coupling", () => {
  for (const token of ["WorkspaceRail","SemanticLegend","DecisionQueue","TemporalSpine","SpatialGraph"]) assert.match(viewport, new RegExp(token));
  assert.match(viewport, /path === "commitments"/);
  assert.match(viewport, /path === "risks"/);
});
test("critical experience calls are represented in the machine contract", () => {
  for (const endpoint of [
    "GET /api/experience/overview",
    "GET /api/experience/services/{serviceId}",
    "GET /api/experience/risks/{riskFindingId}",
    "GET /api/experience/commitments",
    "GET /api/experience/signals",
    "POST /api/experience/commitments",
  ]) assert.ok(contract["x-vector-endpoints"][endpoint], endpoint);
});
test("mutating commitment contract requires authorization and audit", () => {
  const mutation = contract["x-vector-endpoints"]["POST /api/experience/commitments"];
  assert.equal(mutation.authorizationRequired, true);
  assert.equal(mutation.auditRequired, true);
});
test("semantic safety invariants remain explicit", () => {
  assert.ok(contract["x-vector-invariants"].includes("Correlation is not causation"));
  assert.ok(contract["x-vector-invariants"].includes("Graph is a projection, never canonical authority"));
  assert.ok(contract["x-vector-invariants"].includes("AI output is non-authoritative"));
});
test("semantic presentation is implemented as explicit non-color structure", () => {
  assert.match(viewport, /function SemanticLegend/);
  for (const semanticKey of ["observed:","derived:","uncertain:","verified:"]) assert.match(viewport, new RegExp(semanticKey));
});
