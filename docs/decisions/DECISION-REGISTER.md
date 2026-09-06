# VECTOR — Decision Register

> Artifact type: Decision Ledger  
> Status: ACTIVE  
> Purpose: Preserve explicit project decisions and prevent silent reinterpretation.

---

## Decision States

- PROPOSED
- ACCEPTED_FOR_DISCOVERY
- CLOSED
- SUPERSEDED

A CLOSED decision may only be reopened due to:

- a real implementation blocker;
- a contradiction;
- invalidated assumptions;
- authoritative new evidence;
- security or safety concerns.

A better idea alone is not sufficient reason to reopen a CLOSED decision.

---

## DEC-001 — Product Name

Status: CLOSED

Decision:

The official current product name is VECTOR.

Previous codename:

CRIO

Rule:

The rename from CRIO to VECTOR does not automatically change product scope, architecture or capabilities.

---

## DEC-002 — Product Continuity

Status: CLOSED

Decision:

VECTOR preserves the conceptual work previously developed under CRIO unless a later formal decision explicitly supersedes it.

---

## DEC-003 — Product Boundary Hypothesis

Status: ACCEPTED_FOR_DISCOVERY

Current hypothesis:

VECTOR is one product composed of three logical areas:

- Reliability Intelligence
- Execution Intelligence
- Intelligence Core

Rule:

This is not yet a CLOSED Product Boundary.

It must be validated during the Product Boundary / Domain Modeling Quality Gate.

---

## DEC-004 — No Simplistic Individual Productivity Scoring

Status: CLOSED

Decision:

VECTOR must not become a simplistic individual employee productivity ranking system.

Primary analysis should focus on:

- services;
- domains;
- processes;
- technology systems;
- operational patterns;
- team-level or organizational operating structures.

Raw activity metrics must not be converted into individual productivity judgments without an explicitly approved future specification.

Examples include:

- ticket counts;
- commits;
- pull request counts;
- meeting counts;
- hours;
- lines of code;
- raw incident volume.

---

## DEC-005 — Evidence-First Principle

Status: CLOSED

Decision:

Important VECTOR conclusions must be explainable through underlying evidence.

Aggregated indicators should support drill-down toward the evidence that produced them.

---

## DEC-006 — Source Authority

Status: CLOSED

Decision:

VECTOR may correlate information from multiple systems but must preserve which source is authoritative for each fact or measurement.

VECTOR must not silently replace the authority of an originating system with its own interpretation.

---

## DEC-007 — Provenance Preservation

Status: CLOSED

Decision:

Normalized data must preserve sufficient provenance to identify its original source and relevant evidence.

---

## DEC-008 — Lab to Corporate Portability

Status: CLOSED

Decision:

VECTOR must use a vendor-independent domain model and isolate external dependencies through:

Canonical Model
→ Integration Contracts
→ Adapters

Local implementations may use mocks, fixtures and sandboxes.

Corporate implementations may replace adapters without redefining the product domain.

---

## DEC-009 — Unknown Corporate Sources

Status: CLOSED

Decision:

When a corporate source, field, API, mapping, permission or implementation detail is unknown, it must remain TBD.

AI agents must not invent corporate implementation details.

---

## DEC-010 — Corporate Discovery Delta

Status: CLOSED

Decision:

Moving VECTOR from the Lab environment to the corporate environment must not require redefining the product from zero.

The transition must identify differences between:

Lab Specification
and
Corporate Reality

and convert those differences into a corporate integration backlog.

---

## DEC-011 — SRE Skill Incremental Authority

Status: CLOSED

Decision:

For SRE Skill v2.1:

incremental
→ authoritative = false

full_rescan_periodico
→ authoritative = true

full_rescan_cold_start
→ authoritative = true

VECTOR must not interpret an incremental PR evaluation as an authoritative repository-wide or service-wide SRE score.

---

## DEC-012 — SRE Skill Source Authority

Status: CLOSED

Decision:

The SRE Skill is authoritative only for the technical controls it evaluates.

Known evaluated pillars:

- observability
- resilience
- automation
- security

Incident Management is intentionally not evaluated by the Skill.

For Incident Management:

evaluated_by = servicenow

VECTOR must preserve this separation of authority.

---

## DEC-013 — SRE Skill as Evidence, Not Productivity Metric

Status: CLOSED

Decision:

SRE Skill findings may be used as engineering reliability evidence.

They must not be converted into individual productivity scoring.

---

## DEC-014 — Graph Technology Is Not Preselected

Status: CLOSED

Decision:

VECTOR may require graph capabilities because of cross-source relationships.

However, no graph technology is selected yet.

Graph technology must be justified by required relationships, traversal patterns and queries.

---

## DEC-015 — Synthetic Data Must Be Coherent

Status: CLOSED

Decision:

Synthetic Lab data must reproduce meaningful relationships rather than random disconnected records.

Synthetic scenarios should allow end-to-end validation of VECTOR intelligence.

---

## DEC-016 — Stakeholder Inputs Are Evidence

Status: CLOSED

Decision:

Stakeholder proposals, dashboard concepts and suggested metrics are Discovery Evidence.

They do not automatically become:

- requirements;
- final UX;
- architecture;
- scope;
- implementation decisions.

They must pass the appropriate specification process first.

---

## DEC-017 — Personal / Interpersonal Stakeholder Information Exclusion

Status: CLOSED

Decision:

Personal, interpersonal or private management information about stakeholders is outside the VECTOR product scope.

It must not be incorporated into:

- Discovery Baseline;
- specifications;
- evidence models;
- requirements;
- metrics;
- UX;
- architecture;
- AI behavior;
- implementation decisions.

Only professional, technical, operational and product-relevant information may be used.

---

## DEC-018 — Progressive Specification

Status: CLOSED

Decision:

VECTOR may use Progressive Specification.

A vertical may become READY FOR BUILD before the entire product reaches READY FOR IMPLEMENTATION only when:

- all specifications required by that vertical are CLOSED;
- no unresolved transversal blocker affects it;
- required Acceptance Criteria are defined.

This must not be used to bypass specification.

---

## DEC-019 — Anti-Loop Rule

Status: CLOSED

Decision:

Before requiring more specification detail, ask:

"Would the absence of this information force an implementer to invent a relevant decision?"

If YES:

the gap is a Specification Blocker.

If NO:

the item belongs to Backlog, Future Improvement or documented debt.

---

## DEC-020 — CLOSED Decision Protection

Status: CLOSED

Decision:

A CLOSED decision must not be reopened because an AI agent proposes a potentially better alternative.

Reopening requires a genuine blocker, contradiction, invalidated assumption, authoritative evidence or security/safety concern.

---

## DEC-021 — SDD Before Implementation

Status: CLOSED

Decision:

Implementation is forbidden until the relevant specification reaches the required readiness state.

The global target state is:

READY FOR IMPLEMENTATION

Vertical execution may use READY FOR BUILD according to DEC-018.

---

## DEC-022 — AI Execution Roles

Status: CLOSED

Decision:

ChatGPT primarily acts as:

- SDD mentor;
- discovery and reasoning partner;
- architecture/product decision support;
- Quality Gate auditor;
- adversarial reviewer.

Codex primarily acts as:

- specification materialization agent;
- scoped implementation agent;
- test executor;
- blocker reporter.

Codex must not independently redefine product scope or CLOSED decisions.

---

## DEC-023 — AI Model Escalation

Status: CLOSED

Decision:

Use the lowest-capability model that can reliably complete the task.

Escalate only when task complexity, cross-artifact reasoning or repeated failure justifies it.

Prompt ambiguity or oversized scope must be corrected before escalating model capability.

---

## DEC-024 — SDD Playbook Language Convention

Status: CLOSED

Decision:

Reusable SDD documentation must preserve standard technical terminology in English.

Examples:

- Quality Gate
- SPEC-BLOCKER
- Source of Truth
- Canonical Model
- Adapter
- Evidence
- Source Authority
- READY FOR IMPLEMENTATION
- Definition of Ready

For the Spanish learning version:

- technical terminology remains in English;
- explanations, execution steps and learning guidance are written in Spanish.

A separate fully-English Playbook version may also be maintained.

---

## DEC-025 — Dual Product Problem

Status: CLOSED

Decision:

VECTOR must address both Reliability Intelligence and Execution Intelligence, correlated through the Intelligence Core.

Reliability Intelligence explains technology reliability, risk, recurrence, debt and impact.

Execution Intelligence explains how effectively technology areas and domains manage risk and convert identified problems and commitments into structural, sustainable and evidence-verifiable improvements.

Area/domain evaluation is within the product vision.

Automated individual performance scoring/ranking is outside V1 and is tracked separately as POST-V1.

---

---

## DEC-026 — VECTOR Master Capability Map

Status: CLOSED

Decision:

VECTOR Master Capability Map v1 contains 31 capabilities:

- 10 Reliability Intelligence capabilities;
- 9 Execution Intelligence capabilities;
- 12 Intelligence Core capabilities.

---

## DEC-027 — VECTOR V1 Scope Classification

Status: CLOSED

Decision:

V1 MUST = 18.

V1 SHOULD = 7.

POST-V1 = 6.

Total = 31.

POST-V1 capabilities remain part of the Master Capability Map and are candidates for progressive specification.

---

## DEC-028 — VECTOR V1 Vertical Journeys

Status: CLOSED

Decision:

VECTOR V1 is demonstrated through:

- J01 Persistent Reliability Risk;
- J02 Change-Associated Degradation;
- J03 Structural Improvement Verification;
- J04 Area / Domain Decision View.

---

## DEC-029 — Minimum End-to-End Slice Strategy

Status: CLOSED

Decision:

V1 MUST capabilities are implemented through minimum useful end-to-end slices sufficient to execute the approved Vertical Journeys. Exhaustive implementation of each capability is not required for V1.

---

## Change Control

When a decision changes:

1. Do not silently edit its historical meaning.
2. Mark the previous decision as SUPERSEDED when appropriate.
3. Create a new decision or ADR.
4. Record why the previous decision became invalid.
5. Update dependent specifications explicitly.
