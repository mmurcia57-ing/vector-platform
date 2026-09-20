# VECTOR — Framework Pilot Brownfield Inspection Baseline

## Status
- Pilot: Engineering Intelligence & Execution Framework full-lifecycle pilot
- Branch: `framework/vector-full-lifecycle-pilot`
- Baseline main commit: `13c8996424af569444efab559fb740e4e18fb345`
- Inspection mode: NO-REDO / brownfield
- Implementation changes during this inspection: NONE
- Inspection result: **CONTEXT READY WITH RECONCILIATION GAPS**
- Global SPEC-BLOCKER discovered by inspection: **NONE at this stage**

## 1. Executive finding

VECTOR is not a five-screen prototype and is not an early specification project. The repository already contains a broad closed SDD baseline, an implementation plan/task DAG, traceability, a runnable React frontend, a Spring Boot BFF/backend, canonical/persistence/graph/intelligence/security/observability code, J01–J03 implementation, experience projections, controlled extensions, and automated tests.

The framework therefore MUST NOT regenerate VECTOR from zero.

The correct pilot mode is:

`Inspect → Reconcile → Preserve → Complete missing product/UX/engineering behavior → Assure → Converge → Release evidence`.

## 2. Project classification

Provisional framework profile: **CRITICAL brownfield**, with residual uncertainty.

Evidence/rationale:
- technology reliability/execution decision-support product;
- operational/service evidence and integrations are core concerns;
- security, provenance, Source Authority, auditability and AI governance are explicit;
- architecture includes canonical persistence plus graph projection;
- multiple external/corporate integration decisions remain intentionally TBD;
- the repository contains implementation and controlled extensions that must be reconciled rather than replaced.

This classification is about engineering rigor, not a statement that the local deterministic build is production-critical.

## 3. Existing assets — preserve under NO-REDO

### Product/specification
Preserve and validate:
- product specification;
- 31-capability master map;
- V1 MUST/SHOULD/POST-V1 classification;
- J01–J04 vertical journeys;
- requirements;
- domain/data/graph/integration specifications;
- architecture specification;
- security/NFR/observability specification;
- AI behavior specification;
- acceptance strategy;
- implementation plan/task inventory;
- traceability matrix;
- decision/evidence/source registers.

### Implementation
Existing implementation includes:
- React/Vite frontend;
- Spring Boot BFF/backend;
- 17 canonical entity implementation;
- SQLite canonical repository;
- Evidence/SourceReference path;
- deterministic intelligence;
- graph projection/outbox/query components;
- integration abstractions and sandbox fixture adapter;
- authorization/audit abstractions;
- AI provider/governance/validation/telemetry abstractions;
- experience projections/controllers;
- J01, J02 and J03 services;
- observability and performance harnesses.

### Tests/evidence
Existing tests cover canonical, evidence, graph, integrations, deterministic intelligence, journeys, security, AI, observability, performance and release/semantic acceptance paths.

`docs/implementation/RELEASE-READINESS.md` records local evidence from 2026-09-09: 70 backend tests / 24 suites, frontend tests/lint/build/runtime smoke and local deterministic experience. This is historical evidence and will be revalidated before a new release claim.

## 4. Reconciliation findings

### RCN-01 — README implementation-state drift
**Status: CONFLICT / stale documentation**

Root README states that the repository contains only the technical foundation through `TASK-FND-002` and that canonical entities, persistence, journeys and final UX are deferred.

The current tree contains canonical entities, SQLite, graph, journeys, experience, security, AI and extensive tests. The README is therefore not an accurate current implementation inventory.

Action: do not rewrite immediately; reconcile during Convergence/Governance after authoritative implementation/task state is established.

### RCN-02 — Original task inventory versus implemented repository
**Status: EVIDENCE-GAP**

The normative V1 task inventory defines 29 tasks, but the inspection has not yet proven task-by-task completion against current code/tests/evidence.

Action: build a deterministic Task → Implementation → Test → Evidence reconciliation before declaring remaining work.

### RCN-03 — UX baseline versus current experience extensions
**Status: REVALIDATION REQUIRED**

The closed UX/interaction baseline predates/has been extended by EXT-001 and EXT-002 and current frontend implementation. Current UI artifacts must be evaluated using the new Product/UX/Functional Engineering method rather than accepted solely because Step 8/9.5 historically passed.

Action: preserve closed semantic decisions, but re-evaluate journey/task coverage, IA, interaction states, accessibility, visual hierarchy, contract alignment and usability for the current full product scope.

### RCN-04 — Five-screen framing is obsolete
**Status: CLOSED finding**

The product scope is defined by capabilities and journeys, not screen count. Current experience work includes Panorama/Area/Commitments/Service/Risk/J02/navigation/graph concerns. Existing screens are implementation evidence, not the product boundary.

### RCN-05 — Corporate production boundary remains intentionally unresolved
**Status: READY / explicit TBD, not local blocker**

Local release readiness explicitly does not establish corporate capacity, availability, source mappings, credentials, Source Authority, AI provider/model or production deployment readiness.

Action: preserve these as TBD/external dependency until authoritative organizational inputs exist. Do not fabricate them.

### RCN-06 — Execution tracking exists but needs framework reconciliation
**Status: READY WITH GAP**

GitHub Issues exist for the original V1 tasks, EXT-001 and EXT-002/evolution work. The specifications also define a GitHub Project population model.

Gap: framework execution semantics (minimal fields, validation/evidence state, forecast distinctions, current completion evidence) have not yet been reconciled with the actual tracker.

## 5. Framework status map

| Domain | Inspection status | Treatment |
|---|---|---|
| Product problem/outcomes | CLOSED / preserve | Validate only if new evidence contradicts |
| Product boundary | CLOSED / preserve | No redesign without trigger |
| Capability map | CLOSED / preserve | Use as complete-product backbone |
| V1 scope | CLOSED / preserve | Keep MUST/SHOULD/POST-V1 separation |
| Journeys J01–J04 | CLOSED semantics / implementation revalidation | Trace to current experience/code |
| Functional requirements | CLOSED baseline | Gap-check against extensions/current code |
| UX / interaction | REVALIDATION REQUIRED | New framework method applies |
| Architecture | CLOSED baseline / implementation reconciliation | Fitness/conformance check |
| Canonical/data model | CLOSED baseline / implementation present | Conformance check |
| Graph model | CLOSED baseline / implementation present | Conformance + UX usefulness check |
| Integrations | CLOSED contracts / corporate TBDs | Preserve boundaries |
| AuthN/AuthZ/security | CLOSED baseline / implementation evidence present | Threat/negative/conformance recheck |
| NFR | CLOSED local baseline / corporate TBD | Re-run applicable evidence |
| Observability | CLOSED baseline / implementation evidence present | End-to-end evidence check |
| AI | CLOSED baseline / implementation evidence present | Governed behavior/evidence recheck |
| Acceptance | CLOSED baseline / executable tests present | Re-run and map |
| Planning/tasks | CLOSED historical inventory | Reconcile remaining work |
| GitHub tracking | PARTIAL | Align issues/project with framework |
| Release readiness | HISTORICAL LOCAL READY | Revalidate after pilot changes |
| Production readiness | TBD | Requires corporate inputs |

## 6. Adaptive artifact selector result

Do NOT create another complete SDD set.

Artifacts/work justified by current evidence:
1. Implementation reconciliation matrix — REQUIRED.
2. Current-state UX/functional coverage map — REQUIRED.
3. Experience State Matrix for critical interactive journeys — REQUIRED where state complexity exists.
4. Architecture fitness/conformance checks — REQUIRED, preferably executable where possible.
5. Security/AuthN/AuthZ conformance and negative-test review — REQUIRED.
6. Current API/frontend-backend contract map — REQUIRED where implicit contracts exist.
7. Updated work breakdown / remaining-work inventory — REQUIRED after reconciliation.
8. GitHub execution-control mapping — REQUIRED after remaining work is known.
9. New architecture diagrams — ONLY if current diagrams are missing/stale for a decision/risk.
10. New ADR — ONLY if the pilot must change a CLOSED/significant architectural decision.
11. PoC/experiment — ONLY for unresolved technical/UX uncertainty.

## 7. Traceability pilot

Target minimum:
`Outcome/Requirement → Journey/Task → UI State → Frontend Action → API Contract → AuthN/AuthZ → Backend Use Case → Domain/Data → Test/Check → Evidence`.

The existing traceability matrix is strong at Capability/Journey/Task level, but the pilot must extend validation through actual implementation and current experience states.

## 8. Context Readiness Gate

PASS for continued pilot work because:
- source repository and branch are accessible;
- closed decisions/specs are materialized;
- historical evidence is present;
- explicit TBDs/open questions exist;
- implementation/tests are discoverable;
- product scope/capabilities/journeys are defined;
- no global blocker currently forces invention.

Condition: all work remains on the pilot branch and CLOSED decisions are protected unless contradiction, invalidated assumption, authoritative evidence or security/safety issue justifies change.

## 9. Next execution sequence

1. Reconcile all V1 + EXT task state against code/tests/evidence.
2. Run Product/UX/Functional revalidation against complete capabilities/journeys.
3. Map current frontend ↔ BFF contracts ↔ backend behavior.
4. Run architecture/security/NFR/observability conformance.
5. Produce remaining-work WBS and dependencies only from proven gaps.
6. Align GitHub Issues/Project execution tracking.
7. Reach renewed READY FOR IMPLEMENTATION for missing slices.
8. Implement missing work on this branch.
9. Run implementation assurance.
10. Converge spec ↔ UX ↔ architecture ↔ code ↔ tests ↔ evidence.
11. Produce pilot release-readiness evidence and framework lessons.

## 10. Pilot success condition

The pilot succeeds only if it can take the existing brownfield VECTOR product to a coherent, usable, tested and evidence-backed implementation while preserving valid work and exposing genuine missing work. Producing more documents without reducing uncertainty, defects, drift or missing implementation is not success.
