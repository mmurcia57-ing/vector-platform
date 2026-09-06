# AI-SDD-PLAYBOOK Checklist

Version: v0.1
Status: BOOTSTRAP / PARTIALLY VALIDATED

## 1. Step 0 — Initialization

- [ ] Existen governance y AGENTS.md.
- [ ] Existen Project State, Decision Register y Open Questions Register.
- [ ] Existen Evidence Register, Source Inventory y Discovery Baseline.
- [ ] Raw evidence y normative specification estan diferenciados.
- [ ] Se aplican initial Quality Gate y SPEC-BLOCKER policy.

## 2. Step 1 — Product Definition

- [ ] Product Problem, Mission, Outcomes, Users, Value Proposition, Non-Goals y Principles estan materializados.
- [ ] Stakeholder input permanece Evidence hasta aprobarse.
- [ ] Product Definition Quality Gate pasa.

## 3. Step 2 — Scope & Capabilities

- [ ] Master Capability Map usa el validated definition contract.
- [ ] MUST, SHOULD y POST-V1 classifications reconcilian.
- [ ] Existen Vertical Journeys y Coverage Matrix.
- [ ] Orphan MUST count es cero.
- [ ] Scope Quality Gate pasa checks semanticos y estructurales.

## 4. Step 3 — Product Boundary / Domain Model

- [ ] Product Boundary y Bounded Context semantic ownership son explicitos.
- [ ] No se infiere deployment architecture.
- [ ] El minimo vendor-independent Canonical Domain Model se justifica por capabilities y journeys.
- [ ] Source Authority, Canonical Representation y Derived Intelligence permanecen distintos.
- [ ] Identity uncertainty, provenance y Evidence se preservan.
- [ ] Correlation no se promueve a causation.
- [ ] J01–J04 son representables y todas las MUST capabilities permanecen soportadas.

## 5. Step 4 — Functional Specification

- [ ] J01–J04 functional behavior y Cross-Journey Functional Rules estan materializados.
- [ ] Toda MUST capability tiene representacion en Functional Coverage Matrix.
- [ ] Partial/insufficient-data behavior y Evidence/Explainability son explicitos.
- [ ] Algorithms, formulas, thresholds y architecture decisions permanecen deferred.
- [ ] Step 4 permanece PRE-AUDIT hasta external Quality Gate review.

## 6. Cross-cutting checks

- [ ] Anti-loop rule aplicada.
- [ ] Los artifacts previstos para el audit estan dentro del validation scope.
- [ ] CLOSED decision protection aplicada.
- [ ] Semantic drift audit realizado.
- [ ] Mathematical/referential consistency verificada.
- [ ] Target-environment unknowns permanecen TBD.
- [ ] Mermaid-first rule aplicada.
- [ ] git diff y staged content inspeccionados antes de commit.

## 7. ROADMAP / NOT YET VALIDATED

- [ ] Steps 5–16: aplicar solo validated governing principles; no se reclama metodologia detallada.
