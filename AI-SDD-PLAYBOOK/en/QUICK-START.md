# AI-SDD-PLAYBOOK Quick Start

Version: v0.1
Status: BOOTSTRAP / PARTIALLY VALIDATED

## 1. Start safely

1. Read governance and current Project State.
2. Identify approved evidence, specifications, and CLOSED decisions as Source of Truth.
3. State the current step, scope, and unresolved questions.
4. Do not implement while the applicable SDD status is not READY FOR IMPLEMENTATION.

## 2. Run validated Steps 0–2

### Step 0 — Initialization

Create governance, registers, Discovery Baseline, evidence inventory, and initial Quality Gate. Separate raw evidence from normative specification.

### Step 1 — Product Definition

Materialize approved Product Problem, Mission, Outcomes, Users, Value Proposition, Non-Goals, and Principles. Treat stakeholder input as Evidence until approved.

### Step 2 — Scope & Capabilities

Create the Master Capability Map using Capability ID, Name, Purpose, Core Question, Expected Outcome, and Depends On. Classify each capability as MUST, SHOULD, or POST-V1. Define Vertical Journeys and prove every MUST capability has Journey coverage.

### Step 3 — Product Boundary / Domain Model

Resolve Product Boundary and Bounded Context semantic ownership before detailed Functional Specification. Define the minimum vendor-independent Canonical Domain Model, preserve Source Authority and identity uncertainty, and confirm correlation does not imply causation.

### Step 4 — Functional Specification

Materialize approved Journey behavior, Cross-Journey Functional Rules, observable outcomes, insufficient-data behavior, and Functional Coverage Matrix. Defer formulas and architecture decisions. Step 4 remains PRE-AUDIT until external Quality Gate review.

## 3. Audit before closure

- Apply the anti-loop rule: ask whether the absence of the information would force an implementer to invent a relevant decision. If YES, raise SPEC-BLOCKER. If NO, record Backlog / Future Improvement / documented debt. A missing decision is not automatically a SPEC-BLOCKER.
- Check CLOSED decision protection and semantic fidelity.
- Reconcile inventories, classifications, counts, IDs, references, and coverage.
- Preserve Source Authority, Evidence, TBDs, and target-environment uncertainty.
- Use Mermaid-first diagrams for normative repository visuals.

## 4. Close and hand off

Pass the applicable Quality Gate, inspect git diff and staged content, then commit the CLOSED block. Push after accepted closure. GitHub Projects handoff is ROADMAP / PARTIALLY DEFINED and never replaces specification.

## 5. ROADMAP / NOT YET VALIDATED

Step 4 is MATERIALIZED / PRE-AUDIT / NOT YET VALIDATED. Steps 5–16 are not yet validated. Apply only governing principles until those stages are exercised and validated.
