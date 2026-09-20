import test from "node:test";
import assert from "node:assert/strict";
import fs from "node:fs";

const viewport=fs.readFileSync(new URL("../src/ExperienceViewport.tsx",import.meta.url),"utf8");
const contract=JSON.parse(fs.readFileSync(new URL("../../contracts/vector-experience-contract.schema.json",import.meta.url),"utf8"));

test("commitment capability closes lifecycle beyond create/list representation",()=>{
  for(const token of ["Reliability Rate","Resultado pendiente","Renegotiate","Registrar renegociación","Complete"]) assert.match(viewport,new RegExp(token));
});
test("commitment mutation contracts require authorization and audit",()=>{
  for(const key of ["PATCH /api/experience/commitments/{commitmentId}/lifecycle","POST /api/experience/commitments/{commitmentId}/renegotiations"]){
    assert.equal(contract["x-vector-endpoints"][key].authorizationRequired,true);
    assert.equal(contract["x-vector-endpoints"][key].auditRequired,true);
  }
});
test("commitment invariants prohibit false completion semantics",()=>{
  assert.ok(contract["x-vector-invariants"].includes("Commitment execution completion is not outcome verification"));
  assert.ok(contract["x-vector-invariants"].includes("Commitment renegotiation preserves prior due date and reason"));
  assert.ok(contract["x-vector-invariants"].includes("No individual commitment scoring or ranking"));
});
