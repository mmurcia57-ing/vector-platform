# AI-SDD-PLAYBOOK Quality Gates

Version: v0.1
Status: BOOTSTRAP / PARTIALLY VALIDATED

## 1. Common audit logic

Every Quality Gate checks Source of Truth, scope fidelity, explicit TBDs, CLOSED decision protection, semantic fidelity, and mathematical/referential consistency. Valid syntax or counts alone are insufficient.

Use the anti-loop rule: ask whether the absence of the information would force an implementer to invent a relevant decision. If YES, raise SPEC-BLOCKER. If NO, record Backlog / Future Improvement / documented debt. A missing decision is not automatically a SPEC-BLOCKER.

Before trusting a validation result, verify that the artifacts intended for audit are actually included in the validation scope. A successful command is not evidence of quality when intended artifacts were outside that scope.

## 2. Step 0 — Initialization Quality Gate

Confirm repository governance, AGENTS.md, Project State, Decision Register, Open Questions Register, Evidence Register, Source Inventory, Discovery Baseline, raw-evidence classification, and blocker policy exist and are coherent.

## 3. Step 1 — Product Definition Quality Gate

Confirm Product Problem, Mission, Outcomes, Target Users, Value Proposition, Non-Goals, and Principles reflect approved decisions. Confirm stakeholder input has not been promoted to specification without approval.

## 4. Step 2 — Scope Quality Gate

Confirm every capability has the validated definition contract; classifications are mutually exclusive; inventory counts reconcile; Vertical Journeys are defined; every MUST maps to at least one Journey; orphan MUST count is zero; and the matrix remains semantically faithful to approved journeys.

## 5. Environment Portability Gate

Status: ROADMAP / NOT YET VALIDATED. The validated portability principle is Canonical Model → Integration Contract → Adapter; target-environment details remain TBD when unknown.

## 6. Step 3 — Product Boundary / Domain Model Quality Gate

Confirm Product Boundary and Bounded Context responsibilities are explicit; boundaries remain semantic rather than deployment decisions; the minimum Canonical Domain Model is vendor-independent and justified by required capabilities and journeys; Source Authority remains distinct from Canonical Representation; identity states preserve uncertainty and provenance; correlation is Evidence-backed and is not promoted to causation; J01–J04 are representable; and all MUST capabilities remain supported.

## 7. Step 4 — Functional Specification Quality Gate

Status: PRE-AUDIT / NOT YET VALIDATED. Audit Journey behavior, Cross-Journey Functional Rules, Evidence/provenance, Explainability, partial/insufficient-data behavior, Correlation != Causation, Execution != Outcome, Source Authority preservation, Functional Coverage Matrix, orphan MUST count, canonical-entity preservation, and deferral of formulas and architecture decisions.

## 8. ROADMAP / NOT YET VALIDATED

Quality Gates for Steps 5–16 are not yet validated. They may use common audit logic but have no detailed gate definition in v0.1.
