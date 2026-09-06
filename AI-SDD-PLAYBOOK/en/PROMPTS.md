# AI-SDD-PLAYBOOK Prompts

Version: v0.1
Status: BOOTSTRAP / PARTIALLY VALIDATED

## 1. Initialization

Materialize Step 0 — Initialization. Create only governance, registers, evidence inventory, Discovery Baseline, and an initial Quality Gate. Separate raw evidence from normative specification. Raise a SPEC-BLOCKER for missing decisions that would force invention.

## 2. Product Definition materialization

Materialize approved Product Definition decisions only. Do not redesign, add capabilities, choose technology, or promote stakeholder Evidence into specification. Preserve CLOSED decisions and TBDs.

## 3. Capability materialization

Materialize the approved Master Capability Map. For every capability use Capability ID, Name, Purpose, Core Question, Expected Outcome, and Depends On. Keep MUST, SHOULD, and POST-V1 classifications exact. Prove Journey coverage and orphan MUST count.

## 4. Audit

Audit this artifact against Source of Truth. Check that intended artifacts are inside the validation scope, then check semantic fidelity, counts, IDs, references, classifications, coverage, TBDs, and CLOSED decision protection. Report SPEC-BLOCKER rather than silently resolving a contradiction.

## 5. Product Boundary / Domain Model materialization

Materialize approved Product Boundary, Bounded Context responsibilities, the minimum Canonical Domain Model, Source Authority, and identity/correlation semantics only. Do not infer deployment architecture, vendor schemas, matching algorithms, thresholds, or causation. Preserve TBDs and prove approved journeys and MUST capabilities are representable.

## 6. Semantic drift correction

Correct only the materialization defect. Restore approved semantics exactly. Do not redesign scope, introduce decisions, or change statuses. Re-run structural and semantic checks.

## 7. Blocker reporting

Stop implementation or closure. Record a SPEC-BLOCKER with conflicting artifacts, authority order, missing decision, impact, and required resolution. Do not invent a resolution.

## 8. Model escalation check

Before escalating models, assess prompt ambiguity, context size, task breadth, and scope reduction. Use the lowest-capability model that can reliably complete the task. Treat model names as NON-NORMATIVE tooling examples.
