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

## 6. ROADMAP / NOT YET VALIDATED

Quality Gates for Steps 3–16 are not yet validated. They may use common audit logic but have no detailed gate definition in v0.1.
