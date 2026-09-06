# AI-SDD-PLAYBOOK

Version: v0.1
Status: BOOTSTRAP / PARTIALLY VALIDATED

## 1. Purpose and validation boundary

This reusable playbook records SDD methodology exercised through Integration Specification. Steps 0–6 are VALIDATED. Step 6 is CLOSED / VALIDATED. Step 7 is CLOSED / EXTERNAL QUALITY GATE PASSED. Step 8 is NEXT; Steps 9–16 remain ROADMAP / NOT YET VALIDATED and no step authorizes implementation by itself.

## 2. SDD operating model

Understand → Decide → Materialize → Audit → Quality Gate → Commit → Next Step

ChatGPT acts as mentor, reasoning/discovery partner, product/architecture decision support, Quality Gate auditor, and adversarial reviewer. Codex acts as specification materialization agent, scoped execution agent, test executor, and blocker reporter.

## 3. Validated lifecycle

### Step 0 — Initialization

Set up repository governance: AGENTS.md, Project State, Decision Register, Open Questions Register, Evidence Register, Source Inventory, Discovery Baseline, SPEC-BLOCKER policy, and initial Quality Gate. Raw evidence is historical input; normative specification is an approved specification or CLOSED decision.

### Step 1 — Product Definition

Materialize Product Problem, Product Mission, Product Outcomes, Target Users, Value Proposition, Product Non-Goals, and Product Principles. Stakeholder input is Evidence, not approved specification. Close the Product Definition Quality Gate only without expanding scope.

### Step 2 — Scope & Capabilities

Create the Master Capability Map. Every capability uses Capability ID, Name, Purpose, Core Question, Expected Outcome, and Depends On. Classify each once as MUST, SHOULD, or POST-V1.

V1 uses minimum useful end-to-end slices: the smallest useful portion required for approved Vertical Journeys, not exhaustive implementation. Define Vertical Journeys, a Capability-to-Journey Coverage Matrix, and orphan MUST detection. Close the Scope Quality Gate only when counts, classifications, IDs, and coverage reconcile.

### Step 3 — Product Boundary / Domain Model

Resolve Product Boundary before detailed Functional Specification when domains could otherwise be conflated. Define Bounded Context semantic ownership without inferring deployment architecture. Define a minimum vendor-independent Canonical Domain Model justified by required capabilities and journeys, preserve Source Authority separately from Canonical Representation, and make identity/correlation uncertainty explicit.

### Step 4 — Functional Specification

Status: CLOSED / VALIDATED. Use Journeys as the functional-specification backbone; define observable behavior, Cross-Journey Functional Rules, insufficient-data behavior, Evidence/Explainability requirements, and a Functional Coverage Matrix. Separate requirements from algorithms, formulas, and architecture decisions. Avoid orphan MUST capabilities and preserve Progressive Specification compatibility.

### Step 5 — Data Specification

Status: CLOSED / VALIDATED. Define Minimum Data Contracts from functional behavior, preserve canonical/source identity, provenance, Source Authority, temporal semantics when relevant, and contextual Data Confidence before scoring formulas. Cover every canonical entity, Journey, and MUST capability without selecting physical persistence.

### Step 6 — Graph / Evidence Specification

Status: CLOSED / VALIDATED. Define directional, typed semantic relationships and journey traversability; preserve Evidence/provenance and temporal context without selecting graph technology or promoting correlation to causation.

### Step 7 — Integration Specification

Status: CLOSED / EXTERNAL QUALITY GATE PASSED. Define vendor-independent integration contracts behind an Adapter / Anti-Corruption Layer; distinguish integration from Source Authority; use contract-compatible mocks or sandboxes when corporate access is unavailable; preserve provenance, identity/correlation uncertainty, and graceful partial intelligence without inventing corporate details.

## 4. Governing rules

### Closed Baseline vs Evolution Backlog

Steps 0–6 are a closed semantic baseline. Evolution Backlog items record bounded future evolution and must not reopen, reinterpret, or introduce semantic drift into the closed baseline.

### Anti-loop rule

Question: “Would the absence of this information force an implementer to invent a relevant decision?”

YES → SPEC-BLOCKER
NO → Backlog / Future Improvement / documented debt

A better idea alone does not reopen a CLOSED decision.

### CLOSED decision protection

A CLOSED decision may reopen only because of a real implementation blocker, contradiction, invalidated assumption, authoritative new evidence, or security/safety concern. Do not silently reinterpret a CLOSED decision.

### Semantic Drift Rule

An artifact can pass counts, syntax, and structural checks while drifting from an approved CLOSED decision. Quality Gates validate semantic fidelity as well as structural correctness.

### Mathematical and referential consistency

Quality Gates validate consistency between inventories, classifications, counts, coverage matrices, IDs, references, and Traceability structures. A mismatch is a specification defect.

### Progressive Specification / Vertical Readiness

Global specification may continue while independent verticals advance. A vertical reaches READY FOR BUILD only when all required specifications are CLOSED, no unresolved transversal blocker affects it, and Acceptance Criteria are defined. Progressive Specification never bypasses specification.

### Model Escalation Policy

Use the lowest-capability model that can reliably complete the task. Before escalating, inspect prompt ambiguity, context size, task breadth, and whether scope can be reduced.

#### NON-NORMATIVE / CURRENT TOOLING EXAMPLE

- Level 1 execution: GPT-5.6 Luna
- Level 2 multi-file synthesis: GPT-5.6 Terra
- Level 3 deep reasoning: GPT-5.6 Sol

Model names may change and are not part of the SDD methodology.

### Target-Environment-Aware Specification

When the real target environment is unavailable: model known target sources, define vendor-independent contracts, use contract-compatible mocks/sandboxes, leave unknown corporate details as TBD, and do not invent target-environment details.

Portability pattern: Canonical Model → Integration Contract → Adapter.

### Mermaid-first rule

Normative repository diagrams are Mermaid-first, text-based, and versionable. They cannot introduce unapproved semantics. ASCII is conversational support only and is not normative repository format.

## 5. Delivery discipline

Every CLOSED SDD block should be committed after its Quality Gate passes; push after accepted closure. Do not commit known blockers as CLOSED. Use git diff before commit and inspect staged content, not only summaries.

GitHub Projects handoff is ROADMAP / PARTIALLY DEFINED: specification blockers remain in SDD; known executable work moves to Project backlog; future improvements move to backlog; Projects does not replace specification.

## 6. Lessons

- SDD-001 — Separate requirement from solution proposal.
- SDD-002 — Stakeholder input is Evidence, not specification.
- SDD-003 — Potential improvement does not keep specification open.
- SDD-004 — CLOSED reopens only for a blocker or equivalent approved condition.
- SDD-005 — Validate Product Boundary before functional specification.
- SDD-006 — READY FOR IMPLEMENTATION is a Quality Gate, not a feeling.
- SDD-007 — Quality Gates validate mathematical and referential consistency between inventories, classifications, and Traceability structures.
- SDD-008 — Repository diagrams are Mermaid-first.
- SDD-009 — Structural correctness does not guarantee semantic fidelity; audit semantic drift against CLOSED decisions.
- SDD-010 — Audit Scope Verification: a Quality Gate must verify that the artifacts intended for audit are actually included in the validation scope. A successful validation command is not evidence of quality when the intended artifacts were outside that scope.
- SDD-011 — Resolve Product Boundary before detailed Functional Specification when multiple domains could otherwise be conflated.
- SDD-012 — Bounded Context is a semantic ownership boundary, not physical deployment architecture.
- SDD-013 — Canonical Model is vendor-independent and is justified by required capabilities and journeys, not every source object.
- SDD-014 — Canonical Representation does not imply Source Authority.
- SDD-015 — Identity and correlation are separate concepts.
- SDD-016 — Inferred identity/correlation preserves uncertainty, provenance, and Evidence.
- SDD-017 — Correlation must not be promoted to causation without sufficient Evidence or approved semantics.
- SDD-018 — A minimum Canonical Domain Model excludes unrequired source-system objects and individual-performance entities.
- SDD-019 — Minimum Data Contracts are justified by functional behavior, not source schemas.
- SDD-020 — Preserve provenance, temporal semantics, and contextual confidence before physical persistence or scoring formulas.
- SDD-021 — Conflicting claims remain explicit when no applicable authority rule exists.
- SDD-022 — Predicate semantic fidelity requires a true SOURCE + PREDICATE + TARGET statement.
- SDD-023 — Traversal direction does not change normative predicate direction.
- SDD-024 — Pairwise Capability × Journey coverage must reconcile with detailed relationship Supports declarations.
- SDD-025 — Connected semantic subgraphs must not be reduced to linear narrative edges.
- SDD-026 — Persist external Quality Gate closure in repository Source of Truth artifacts.
- SDD-027 — Integration does not establish Source Authority; authority must remain explicit per supplied claim or `TBD`.
- SDD-028 — An Adapter / Anti-Corruption Layer protects Canonical Model semantics from vendor-specific source models.
- SDD-029 — A contract-compatible mock or sandbox can validate a local path without asserting corporate connectivity, mapping, or authority.
- SDD-030 — Graceful partial integration exposes missing, stale, partial, and unresolved context rather than fabricating certainty.
- SDD-031 — An internal recommendation to proceed to an external Quality Gate is not closure; CLOSED status requires the formal external result to be persisted in repository Source of Truth artifacts.

## 7. ROADMAP / NOT YET VALIDATED

- Step 7 Integrations — CLOSED / EXTERNAL QUALITY GATE PASSED
- Step 8 UX — NEXT
- Step 9 Architecture
- Step 10 Security + NFR + Observability
- Step 11 AI Behavior
- Step 12 Acceptance / Test Strategy
- Step 13 Implementation Plan
- Step 14 Task Decomposition
- Step 15 Traceability
- Step 16 Final Adversarial Review

No detailed methodology rules are established for these steps beyond the validated principles in this playbook.
