import test from "node:test";
import assert from "node:assert/strict";
import fs from "node:fs";

const viewport = fs.readFileSync(new URL("../src/ExperienceViewport.tsx", import.meta.url), "utf8");
const contract = JSON.parse(fs.readFileSync(new URL("../../contracts/vector-experience-contract.schema.json", import.meta.url), "utf8"));

test("EXT-003 experience exposes the intelligence-workspace grammar", () => {
  for (const token of ["NorthStarUniverse","Operational Universe","Intelligence Inspector","Signal","Verify"]) assert.match(viewport, new RegExp(token,"i"));
  assert.match(viewport,/contextDisclaimer/);
  assert.match(viewport,/vx-ns-flow/);
});
test("critical experience calls are represented in the machine contract", () => {
  for (const endpoint of ["GET /api/experience/overview","GET /api/experience/services/{serviceId}","GET /api/experience/risks/{riskFindingId}","GET /api/experience/commitments","GET /api/experience/signals","POST /api/experience/commitments"]) assert.ok(contract["x-vector-endpoints"][endpoint], endpoint);
});
test("mutating commitment contract requires authorization and audit", () => {
  const mutation = contract["x-vector-endpoints"]["POST /api/experience/commitments"];
  assert.equal(mutation.authorizationRequired, true); assert.equal(mutation.auditRequired, true);
});
test("semantic safety invariants remain explicit", () => {
  assert.ok(contract["x-vector-invariants"].includes("Correlation is not causation"));
  assert.ok(contract["x-vector-invariants"].includes("Graph is a projection, never canonical authority"));
  assert.ok(contract["x-vector-invariants"].includes("AI output is non-authoritative"));
});
test("experience includes semantic distinction beyond color-only presentation", () => {
  for (const key of ["observed:","derived:","uncertain:","verified:"]) assert.match(viewport,new RegExp(key));
  for (const className of ["vx-ns-fact","vx-ns-derived","vx-ns-limit"]) assert.match(viewport,new RegExp(className));
});
