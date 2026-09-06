# VECTOR — UX Specification

## 1. Status

- Status: CLOSED / EXTERNAL QUALITY GATE PASSED
- Step: Step 8 — UX Specification
- External Quality Gate: PASS
- SDD status: NOT_READY_FOR_IMPLEMENTATION
- SPEC-BLOCKERS: 0

This specification defines conceptual V1 information architecture, navigable decision behavior, and semantic UX states. It does not select a frontend framework, visual library, design system, CSS, access-control implementation, data schema, API, corporate source, or implementation approach.

## 2. UX Scope and Governing Semantics

VECTOR begins with a Technology Overview for attention, risk, and outcome—not a CMDB, technical inventory, ITSM replacement, observability replacement, or project/portfolio-management replacement.

The primary drill-down is:

`Technology Overview → Area/Domain → Service → RiskFinding → Evidence → Commitment → ImprovementAction → OutcomeVerification`

Incident, Problem, Change, Deployment, SLO/SLOObservation, MonitoringEvent, MetricObservation, ConfigurationItem, and SourceReference may supply relevant navigable context. Their availability neither changes the canonical semantics nor blocks the maximum justifiable partial journey.

The UX preserves these approved distinctions:

- source fact / observed fact;
- derived intelligence / RiskFinding;
- Evidence-backed correlation;
- hypothesis or uncertainty;
- OutcomeVerification.

Identity is distinct from correlation; correlation is not causation. The UX must not label a temporal or contextual association as root cause, causal attribution, certainty, or confirmed identity unless that status already exists in the supplied canonical context.

Service remains the primary technology correlation anchor. AreaDomain provides contextual grouping. The UX does not introduce BusinessOutcome, BusinessProcess, CustomerJourney, Person, Employee, ProductivityScore, individual ranking, public team ranking, leaderboard, or individual-productivity metrics.

## 3. Users and Decision Depth

| User context | UX emphasis | Boundary |
|---|---|---|
| Technology Leadership | Summarized decision view: attention, risk concentration, persistence, action and outcome context. | Supports human-accountable prioritization; does not automate organizational or employment decisions. |
| Domain / Area Leadership | Area/Domain and Service decision context with explainable drill-down. | Uses the same canonical model and views. |
| SRE / Reliability Engineering | Operational and Evidence detail, correlation limitations, freshness, and history. | Does not create a separate product or data model. |
| Technology Operations | Operational context, incidents, problems, changes, deployments, and source limitations. | Does not create a separate product or data model. |

## 4. Information Architecture

### 4.1 Technology Overview

The entry view presents two conceptual layers.

| Layer | Required decision information | Guardrail |
|---|---|---|
| Outcome / Attention | Concentration of attention/risk, persistent reliability condition, change-associated degradation, improvement status/outcome. | Attention is explainable context, not an opaque score or causal claim. |
| Operational Evidence | Available events, incidents, problems, SLO observations, changes/deployments, commitments/actions, Evidence, and source limitations. | Source context remains distinguishable from derived intelligence. |

The view enables selection of an AreaDomain, Service, RiskFinding, or available decision context. It must expose why attention is present, supporting Evidence, applicable confidence/uncertainty, Source Coverage, Freshness, and Missing Context.

### 4.2 Area/Domain View

The Area/Domain View contextualizes Services requiring attention and their available risk, Evidence, action, and outcome context. It supports the J04 decision path from AreaDomain to Service without implying that AreaDomain is a source authority or a causal owner.

### 4.3 Service View

The Service View is the primary operational correlation anchor. It presents available operational context: ConfigurationItem, MonitoringEvent, Incident, Problem, Change, Deployment, SLO, SLOObservation, MetricObservation, Evidence, RiskFinding, SourceReference, and relevant action/outcome context. Missing optional context is visibly absent or limited, not fabricated.

### 4.4 RiskFinding Detail

A decision-relevant RiskFinding must expose:

1. What: the condition or finding.
2. Why: explainable condition, association, and applicable limitations.
3. Supporting Evidence and provenance.
4. Confidence or uncertainty when applicable.
5. Source Coverage and Freshness.
6. Missing Context.
7. Related Commitment and ImprovementAction when available.
8. OutcomeVerification when available.

RiskFinding Detail distinguishes derived intelligence from supplied source facts. It provides a path to Service context and supporting Evidence; it never turns correlation into causation or invents root cause.

### 4.5 Evidence Detail / Evidence Panel

Evidence Detail presents the applicable claim/context, source identity and provenance through SourceReference where available, temporal context, freshness, limitations, identity-resolution state, and its relationship to the displayed finding, correlation, or outcome verification. It makes conflicts and limitations inspectable without silently selecting a winner.

### 4.6 Commitment & ImprovementAction Context

This context shows the approved chain from RiskFinding to Commitment and ImprovementAction, including available status/due or execution context and provenance. It explicitly distinguishes absent Commitment, absent action, and completed action. Completion does not imply improvement.

### 4.7 Outcome Verification Context

Outcome Verification Context presents the relationship between ImprovementAction, prior/new operational Evidence, limitations, and the available V1 semantic: `IMPROVED`, `PERSISTENT`, or insufficient evidence / not yet verifiable. OutcomeVerification is derived/native VECTOR context; it does not make VECTOR authoritative for supporting external source facts.

### 4.8 Integration / Data Confidence Context

Any view with sourced or partial context can expose an Integration / Data Confidence context. It presents available Source Coverage, Freshness, Data Confidence, Missing Context, Uncertainty, source availability, and conflict context without defining physical widgets, formulas, numeric thresholds, or technical enums.

## 5. Cross-View UX Rules

| ID | UX rule |
|---|---|
| UX-R01 | Every material finding, attention context, correlation, and outcome view is traceable to available Evidence and provenance. |
| UX-R02 | Derived intelligence is visibly distinct from source/observed fact. |
| UX-R03 | Correlation, identity state, uncertainty, and causation are not conflated. |
| UX-R04 | Unavailable, stale, partial, missing, conflicting, or unresolved context is visible and allows unaffected partial navigation. |
| UX-R05 | Action completion is visibly distinct from demonstrated outcome. |
| UX-R06 | Decision views preserve human accountability and exclude individual productivity/ranking behavior. |
| UX-R07 | Corporate source identity, authority, precedence, access, and mappings remain `TBD` unless already confirmed in supplied context. |

## 6. Required UX States

These are semantic presentation states, not physical enums or implementation requirements.

| State | Required UX behavior |
|---|---|
| Loading | Identify requested context as loading without implying availability, health, or completion. |
| No data | State that no applicable context is available; do not infer normal operation or absence of risk. |
| Partial data | Show available context and Missing Context; allow supported partial navigation. |
| Stale data | Retain last known context only with visible stale/freshness limitation and provenance. |
| Conflicting claims | Preserve competing claims, Evidence, provenance, and unresolved status; do not silently select a winner. |
| Unresolved identity | Display `UNRESOLVED` identity without merging records or blocking unaffected context. |
| Inferred identity | Display `INFERRED` identity with applicable method/context, confidence, Evidence, and provenance; do not present it as confirmed. |
| Insufficient evidence | Limit finding, correlation, or outcome conclusion to what Evidence supports; do not fabricate certainty. |
| No RiskFinding | Show available operational context without inventing a finding or attention conclusion. |
| RiskFinding without Commitment | Show the finding and explicit absence of Commitment context; do not fabricate ownership or action. |
| Commitment without completed action | Show available Commitment/action context and lack of completed action; do not assert execution. |
| Action completed but outcome not yet verifiable | Preserve completed-action context and state that structural improvement has not yet been demonstrated. |
| IMPROVED | Present Evidence-backed OutcomeVerification with comparable context and limitations. |
| PERSISTENT | Present Evidence-backed OutcomeVerification showing continued condition or insufficient structural improvement; do not blame an individual or assign causal certainty. |

## 7. Journey UX Specifications

### J01 — Persistent Reliability Risk

| Aspect | UX specification |
|---|---|
| Entry point | Technology Overview attention context, Area/Domain View, Service View, or a selected RiskFinding. |
| User intent | Understand a persistent reliability condition and why it requires attention. |
| Primary navigation | Technology Overview / AreaDomain → Service → RiskFinding → Evidence Detail, with available MonitoringEvent, Incident, Problem, SLO/SLOObservation, MetricObservation, and ConfigurationItem context. |
| Required information | Service operational context, available risk/degradation/recurrence context, Evidence, provenance, explanation, and relevant Source Coverage/Freshness/Missing Context. |
| Decisions supported | Human-accountable attention, investigation, and follow-up for a persistent reliability condition. |
| Uncertainty/conflict behavior | Identity/correlation uncertainty and conflicting claims remain explicit; recurrence is not root cause. |
| Incomplete-data behavior | Missing Problem or ConfigurationItem does not block the journey; no RiskFinding is invented where Evidence is insufficient. |
| Exit/outcome | Explainable attention context or explicit limited/no-finding state with a path to Evidence and Service context. |

### J02 — Change-Associated Degradation

| Aspect | UX specification |
|---|---|
| Entry point | Service View, Change/Deployment context, Technology Overview attention context, or selected RiskFinding. |
| User intent | Inspect whether available evidence supports a temporal/contextual association between a Change/Deployment and degradation. |
| Primary navigation | Service → Change/Deployment → Before/During/After operational context → Evidence / correlation explanation → RiskFinding when justified. |
| Required information | Change, Deployment, Service, available MonitoringEvent, SLOObservation, Incident, Evidence, SourceReference, identity state, and association limitations. |
| Decisions supported | Human review of change-associated risk, investigation, and follow-up without causal attribution. |
| Uncertainty/conflict behavior | `CONFIRMED`, `INFERRED`, and `UNRESOLVED` identity states remain separate from Evidence-backed association uncertainty; conflicting claims remain visible. |
| Incomplete-data behavior | Missing signal/Evidence produces limited or inconclusive context, not positive or negative causality. |
| Exit/outcome | Inspectable association explanation and limitations, or an explicit insufficient-evidence state. |

### J03 — Structural Improvement Verification

| Aspect | UX specification |
|---|---|
| Entry point | RiskFinding Detail, Commitment & ImprovementAction Context, Outcome Verification Context, or J04 drill-down. |
| User intent | Determine whether declared action produced demonstrated structural improvement. |
| Primary navigation | RiskFinding → Commitment → ImprovementAction → new operational Evidence → OutcomeVerification. |
| Required information | RiskFinding, available Commitment/action context, completion/execution context, relevant prior/new operational Evidence, comparable context, and limitations. |
| Decisions supported | Human follow-up on commitments/actions and assessment of improvement evidence. |
| Uncertainty/conflict behavior | External Commitment authority remains `TBD` where applicable (OQ-009); outcome remains limited when Evidence or comparable context is insufficient. |
| Incomplete-data behavior | Missing Commitment/action is explicit. Completed action without sufficient Evidence is not yet verifiable. |
| Exit/outcome | `IMPROVED`, `PERSISTENT`, or insufficient evidence / not yet verifiable, with traceable Evidence. |

### J04 — Area / Domain Decision View

| Aspect | UX specification |
|---|---|
| Entry point | Technology Overview or Area/Domain View. |
| User intent | Identify Areas/Domains and Services requiring explainable attention, then understand risk, action, and outcome context. |
| Primary navigation | Technology Overview → AreaDomain → Service → RiskFinding → Evidence → Commitment → ImprovementAction → OutcomeVerification. |
| Required information | AreaDomain, Service, explainable conditions/risks, Evidence/provenance, available operational context, action/outcome context, and visible limitations. |
| Decisions supported | Human-accountable prioritization, attention allocation, and follow-up—not organizational or employment decisions. |
| Uncertainty/conflict behavior | Incomplete, conflicting, inferred, unresolved, stale, and missing context remains visible; no opaque priority or silent conflict resolution. |
| Incomplete-data behavior | Available partial drill-down remains navigable; unavailable links are explicit. |
| Exit/outcome | A traceable decision context or an explicit limited-information state. |

## 8. Traceability Matrix

| UX view / context | Journey(s) | Supported functional requirements | Canonical entities used | Decisions supported |
|---|---|---|---|---|
| Technology Overview | J01, J02, J03, J04 | FR-J01-003; FR-J02-002/003; FR-J03-002/003; FR-J04-001/003 | AreaDomain, Service, RiskFinding, Evidence, OutcomeVerification, SourceReference | Attention, investigation, outcome-aware leadership context. |
| Area/Domain View | J01, J04 | FR-J01-003; FR-J04-001/002/003 | AreaDomain, Service, RiskFinding, Evidence, Commitment, ImprovementAction, OutcomeVerification | Area/domain attention and accountable follow-up. |
| Service View | J01, J02, J03, J04 | FR-J01-001/002; FR-J02-001/002/003; FR-J03-002/003; FR-J04-002 | Service, ConfigurationItem, MonitoringEvent, Incident, Problem, Change, Deployment, SLO, SLOObservation, MetricObservation, Evidence, RiskFinding, SourceReference | Operational-context inspection and explainable association. |
| RiskFinding Detail | J01, J02, J03, J04 | FR-J01-002/003; FR-J02-002/003; FR-J03-001; FR-J04-001/002 | RiskFinding, Service, Evidence, SourceReference, Incident, Problem, Change, Deployment | Explainable attention, risk review, and follow-up. |
| Evidence Detail / Panel | J01, J02, J03, J04 | FR-J01-001/002; FR-J02-001/002/003; FR-J03-002/003; FR-J04-002 | Evidence, SourceReference, applicable canonical context | Evidence/provenance inspection and uncertainty review. |
| Commitment & ImprovementAction Context | J03, J04 | FR-J03-001/002; FR-J04-002/003 | RiskFinding, Commitment, ImprovementAction, Evidence, SourceReference | Action follow-up without treating activity as outcome. |
| Outcome Verification Context | J03, J04 | FR-J03-002/003; FR-J04-002/003 | OutcomeVerification, ImprovementAction, Commitment, RiskFinding, Evidence, Service, operational observations | Verify improvement or preserve not-yet-verifiable outcome. |
| Integration / Data Confidence Context | J01, J02, J03, J04 | FR-X01–FR-X10; FR-J01-001/002; FR-J02-003; FR-J03-002/003; FR-J04-001/002/003 | Evidence, SourceReference, all applicable canonical context | Interpret availability, provenance, uncertainty, and limits. |

Coverage result: J01–J04 each have a navigable UX path. No normative Journey lacks a UX experience. The closed functional requirements, canonical entities, and capability coverage remain unchanged.

## 9. Deferred Decisions and Non-Goals

- Visual design, interaction patterns, component library, design system, CSS, responsive behavior, accessibility implementation, frontend framework, routing implementation, APIs, data-fetching, permissions/RBAC implementation, physical states/enums, and telemetry are deferred.
- Corporate source details, authority applicability, precedence, identity mappings, credentials, and corporate access remain `TBD` unless otherwise confirmed by an approved source.
- OQ-009 remains OPEN and non-blocking for local V1. OQ-016 remains RESOLVED; integration-specific authority applicability may still be `TBD`.
- No separate role products/data models, individual-performance intelligence, public team rankings, automated organizational decisions, business/customer canonical entities, root-cause inference, or implementation is introduced.

## 10. Step 8 Quality Gate

Status: CLOSED / EXTERNAL QUALITY GATE PASSED.

Formal external Quality Gate result: PASS. The minimum information architecture, Technology Overview layers, role-appropriate decision depth, J01–J04 navigable UX paths, Evidence/explainability, partial/conflicting-state behavior, action-versus-outcome distinction, and explicit traceability are accepted. SPEC-BLOCKERS: 0. Steps 0–8 remain CLOSED; OQ-009 remains OPEN and non-blocking for local V1; OQ-016 remains RESOLVED. SDD status remains NOT_READY_FOR_IMPLEMENTATION; no implementation authorization is granted. Next Step: Step 9 — Architecture Specification.
