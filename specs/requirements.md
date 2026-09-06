# VECTOR — Functional Specification

## 1. Status

- Status: MATERIALIZED / PRE-AUDIT
- Step: Step 4 — Functional Specification
- Quality Gate: EXTERNAL REVIEW PENDING
- SDD status: NOT_READY_FOR_IMPLEMENTATION
- SPEC-BLOCKERS: 0

This specification materializes the approved Step 4 functional baseline. It does not define algorithms, formulas, thresholds, technical schemas, APIs, architecture, UX design, source precedence, or implementation.

## 2. Functional Scope and Domain Constraints

The functional model uses exactly 17 mandatory canonical entities: AreaDomain, Service, ConfigurationItem, MonitoringEvent, Incident, Problem, Change, Deployment, SLO, SLOObservation, Commitment, ImprovementAction, OutcomeVerification, Evidence, RiskFinding, MetricObservation, and SourceReference.

Service is the primary technology correlation anchor. Repository, PullRequest, Branch, and SREAssessment remain non-MUST candidate extensions. Person, Employee, ProductivityScore, and individual-performance entities are outside the V1 canonical model.

## 3. J01 — Persistent Reliability Risk

### FR-J01-001 — Analyze Service Operational Context

- Journey: J01 — Persistent Reliability Risk
- Related MUST Capabilities: R01, R05, R06, C01, C02, C05, C09
- Functional behavior: VECTOR must analyze a Service within its AreaDomain and bring together available canonical technology context connecting AreaDomain, Service, and ConfigurationItem, together with available MonitoringEvent, Incident, Problem, SLO, SLOObservation, MetricObservation, and Evidence context.
- Preconditions / required context: Service and available operational context; Problem is optional.
- Expected observable outcome: A user can inspect the available operational context for the selected Service.
- Partial / insufficient-data behavior: Absence of Problem or unavailable ConfigurationItem context does not prevent J01 analysis; unavailable context is explicitly represented.
- Relevant canonical entities: AreaDomain, Service, ConfigurationItem, MonitoringEvent, Incident, Problem, SLO, SLOObservation, MetricObservation, Evidence, SourceReference.
- Evidence / explainability: All included context retains Evidence and provenance where available.
- Deferred details: Recurrence formulas, thresholds, and algorithms.

### FR-J01-002 — Explain Degradation and Recurrence Context

- Journey: J01 — Persistent Reliability Risk
- Related MUST Capabilities: R01, R02, R03, C03, C04, C06, C07, C09
- Functional behavior: VECTOR must analyze available degradation and recurrence context, relate relevant Incident and Problem context through explicit correlation semantics, and produce an explainable RiskFinding when justified.
- Preconditions / required context: Evidence-backed relevant context; identity/correlation may be CONFIRMED, INFERRED, or UNRESOLVED.
- Expected observable outcome: A RiskFinding exposes degradation/recurrence context, related Incident/Problem context where available, and an explanation.
- Partial / insufficient-data behavior: UNRESOLVED identity/correlation is not invented; insufficient Evidence is shown as limited or inconclusive.
- Relevant canonical entities: Service, MonitoringEvent, Incident, Problem, Evidence, RiskFinding, SourceReference.
- Evidence / explainability: Recurrence does not imply root cause; conclusions are traceable to Evidence/provenance.
- Deferred details: Recurrence, risk, and prioritization formulas.

### FR-J01-003 — Present Attention Context

- Journey: J01 — Persistent Reliability Risk
- Related MUST Capabilities: R01, R03, C06, C07, C10
- Functional behavior: VECTOR must present explainable attention/prioritization context for a justified persistent reliability condition.
- Preconditions / required context: An explainable RiskFinding with supporting Evidence.
- Expected observable outcome: A user can understand why attention is highlighted and drill down to Service, condition, and Evidence.
- Partial / insufficient-data behavior: When Evidence is insufficient, no unjustified priority conclusion is produced; limitations are explicit.
- Relevant canonical entities: AreaDomain, Service, RiskFinding, Evidence, SourceReference.
- Evidence / explainability: Attention context must not be an opaque score.
- Deferred details: Prioritization formula and thresholds.

## 4. J02 — Change-Associated Degradation

### FR-J02-001 — Analyze Change and Deployment Context

- Journey: J02 — Change-Associated Degradation
- Related MUST Capabilities: R04, C01, C02, C03, C04
- Functional behavior: VECTOR must analyze Change and Deployment context for a Service with inspectable Before / During / After operational context.
- Preconditions / required context: Change, Deployment, Service, and available temporal/contextual Evidence.
- Expected observable outcome: A user can inspect Change, Deployment, Service, analyzed context/window, and relevant source context.
- Partial / insufficient-data behavior: Missing signals or evidence are explicitly represented; analysis may remain limited.
- Relevant canonical entities: Change, Deployment, Service, Evidence, SourceReference.
- Evidence / explainability: Source identity and provenance are preserved.
- Deferred details: Analysis windows, algorithms, formulas, and thresholds.

### FR-J02-002 — Correlate Degradation Without Causal Attribution

- Journey: J02 — Change-Associated Degradation
- Related MUST Capabilities: R01, R02, R04, R05, R06, C03, C06, C07, C09
- Functional behavior: VECTOR may communicate Evidence-backed temporal/contextual association between Change/Deployment and operational degradation signals.
- Preconditions / required context: Relevant MonitoringEvent, SLOObservation, Incident, and Correlation Evidence where available.
- Expected observable outcome: A RiskFinding/Explanation identifies the association, the inspected context, and its limitations.
- Partial / insufficient-data behavior: Insufficient Evidence yields an inconclusive or limited result, not invented positive or negative causality.
- Relevant canonical entities: Change, Deployment, Service, MonitoringEvent, SLOObservation, Incident, Evidence, RiskFinding, SourceReference.
- Evidence / explainability: INFERRED correlation preserves method, confidence, and provenance. Correlation != Causation.
- Deferred details: Correlation algorithms, confidence formulas, thresholds, and mathematical windows.

### FR-J02-003 — Preserve Correlation Uncertainty

- Journey: J02 — Change-Associated Degradation
- Related MUST Capabilities: C02, C03, C06, C07, C10
- Functional behavior: VECTOR must distinguish identity resolution state from correlation/association uncertainty in the decision information it presents. Identity resolution may be CONFIRMED, INFERRED, or UNRESOLVED; correlation/association remains Evidence-backed without introducing a separate correlation enum or state machine.
- Preconditions / required context: Available SourceReference and Evidence/provenance context.
- Expected observable outcome: Users can inspect identity resolution state separately from correlation/association uncertainty, including applicable method, confidence, and supporting Evidence, without treating an INFERRED relationship as CONFIRMED.
- Partial / insufficient-data behavior: UNRESOLVED identity or correlation/association remains unresolved and does not block maximum justifiable partial intelligence.
- Relevant canonical entities: SourceReference, Evidence, Service, Change, Deployment, RiskFinding.
- Evidence / explainability: Identity != Correlation. Correlation/association does not imply causation; no implicit causal attribution or silent promotion of inference is permitted.
- Deferred details: Matching and confidence computation.

## 5. J03 — Structural Improvement Verification

### FR-J03-001 — Link Risk to Commitment and Improvement Action

- Journey: J03 — Structural Improvement Verification
- Related MUST Capabilities: R03, E02, E03, C03, C06, C07, C10
- Functional behavior: VECTOR must represent the semantic chain RiskFinding → Commitment → ImprovementAction → OutcomeVerification.
- Preconditions / required context: A RiskFinding and available Commitment/ImprovementAction context.
- Expected observable outcome: A user can inspect declared Commitment and concrete ImprovementAction in relation to the RiskFinding.
- Partial / insufficient-data behavior: Missing Commitment or ImprovementAction is explicit and does not fabricate execution context.
- Relevant canonical entities: RiskFinding, Commitment, ImprovementAction, Evidence, SourceReference.
- Evidence / explainability: Relationship semantics and provenance are inspectable.
- Deferred details: Execution-status schema and source ownership details.

### FR-J03-002 — Distinguish Execution from Outcome

- Journey: J03 — Structural Improvement Verification
- Related MUST Capabilities: R01, R02, R03, R04, R06, E01, E02, E03, C05, C06, C07, C09, C10
- Functional behavior: VECTOR must distinguish Commitment/ImprovementAction execution completion from demonstrated technology outcome.
- Preconditions / required context: Execution context and available new operational Evidence.
- Expected observable outcome: A completed Commitment or ImprovementAction may be shown together with continued Incident, recurrence, or SLO degradation and the semantic equivalent of “Action completed, but structural improvement has not been demonstrated.”
- Partial / insufficient-data behavior: Outcome is explicitly insufficient evidence / not yet verifiable when Evidence cannot justify verification.
- Relevant canonical entities: Commitment, ImprovementAction, Incident, Problem, SLOObservation, MetricObservation, Evidence, RiskFinding, OutcomeVerification.
- Evidence / explainability: OutcomeVerification is Evidence-backed.
- Deferred details: Before/after formulas, periods, and thresholds.

### FR-J03-003 — Verify Structural Outcome

- Journey: J03 — Structural Improvement Verification
- Related MUST Capabilities: R01, R02, R03, R04, R06, E01, E02, E03, C02, C03, C05, C06, C07, C09, C10
- Functional behavior: VECTOR must compare relevant prior and new operational Evidence to produce an OutcomeVerification.
- Preconditions / required context: Available RiskFinding, Commitment, ImprovementAction, new operational Evidence, and comparable context where sufficient.
- Expected observable outcome: OutcomeVerification communicates one minimum V1 semantic: IMPROVED, PERSISTENT, or insufficient evidence / not yet verifiable.
- Partial / insufficient-data behavior: Lack of comparable Evidence results in insufficient evidence / not yet verifiable, not a fabricated outcome.
- Relevant canonical entities: RiskFinding, Commitment, ImprovementAction, OutcomeVerification, Service, MonitoringEvent, Incident, SLOObservation, Evidence, MetricObservation.
- Evidence / explainability: Before/after context and limitations are inspectable.
- Deferred details: Final technical enum/schema, formulas, periods, and thresholds.

## 6. J04 — Area / Domain Decision View

### FR-J04-001 — Identify Areas and Services Requiring Attention

- Journey: J04 — Area / Domain Decision View
- Related MUST Capabilities: R01, R02, R03, R04, R05, R06, E01, C01, C05, C06, C07, C09, C10
- Functional behavior: VECTOR must allow Technology Leadership and Area/Domain Leadership to identify Areas/Domains and Services requiring attention through explainable risk, impact, persistence, and context.
- Preconditions / required context: AreaDomain, Service, and available Evidence-backed conditions.
- Expected observable outcome: A user can select an AreaDomain and inspect Services requiring attention.
- Partial / insufficient-data behavior: Incomplete context yields maximum justifiable decision information with explicit limitations.
- Relevant canonical entities: AreaDomain, Service, MonitoringEvent, Incident, Problem, SLOObservation, MetricObservation, Evidence, RiskFinding.
- Evidence / explainability: Prioritization is not exclusively an opaque score.
- Deferred details: Prioritization and KPI formulas.

### FR-J04-002 — Drill Down from Area to Outcome

- Journey: J04 — Area / Domain Decision View
- Related MUST Capabilities: R01, R02, R03, R04, R05, R06, E01, E02, E03, C01, C02, C03, C04, C05, C06, C07, C09, C10
- Functional behavior: VECTOR must support drill-down: Area/Domain → Service → Condition/Risk → Evidence → Action → Outcome.
- Preconditions / required context: Selected AreaDomain and available linked canonical context.
- Expected observable outcome: A user can inspect the Service, RiskFinding, explanation, Evidence, Commitment, ImprovementAction, and OutcomeVerification relevant to a decision.
- Partial / insufficient-data behavior: Missing optional context does not prevent partial drill-down; unavailable links and uncertainty are explicit.
- Relevant canonical entities: AreaDomain, Service, RiskFinding, Evidence, SourceReference, Commitment, ImprovementAction, OutcomeVerification.
- Evidence / explainability: Each material conclusion exposes why it exists and supporting provenance.
- Deferred details: UX visual design, access control, and implementation navigation.

### FR-J04-003 — Preserve Human Accountability

- Journey: J04 — Area / Domain Decision View
- Related MUST Capabilities: E01, E02, E03, C07, C10
- Functional behavior: VECTOR must provide Decision Intelligence and action/outcome context without automated organizational or employment decisions.
- Preconditions / required context: Decision view context where available.
- Expected observable outcome: The view supports human-accountable attention and follow-up; activity volume is not an automatic productivity proxy.
- Partial / insufficient-data behavior: Uncertainty and missing context are communicated rather than substituted with individual scoring.
- Relevant canonical entities: AreaDomain, Service, RiskFinding, Commitment, ImprovementAction, OutcomeVerification, Evidence.
- Evidence / explainability: Decision information remains explainable and traceable.
- Deferred details: Organizational workflows and access controls.

## 7. Cross-Journey Functional Rules

| ID | Rule |
|---|---|
| FR-X01 — Evidence Traceability | Every material conclusion must be traceable to Evidence and provenance. |
| FR-X02 — Explainability | Every RiskFinding, prioritization, or relevant derived conclusion must expose why it exists. |
| FR-X03 — Uncertainty Preservation | Insufficient, INFERRED, or UNRESOLVED information must not be silently converted into certainty. |
| FR-X04 — Correlation != Causation | Temporal/contextual association must not automatically become causal attribution. |
| FR-X05 — Source Authority Preservation | Canonicalization must not transfer Source Authority to VECTOR. |
| FR-X06 — Missing Context | Absence of an optional source/entity must not automatically prevent partial journey execution when sufficient Evidence exists. |
| FR-X07 — No Silent Conflict Resolution | Conflicting claims must not be silently resolved; detailed precedence, representation, and confidence behavior are deferred. |
| FR-X08 — Execution != Outcome | Completed Commitment/ImprovementAction does not itself prove structural improvement. |
| FR-X09 — Human-Accountable Decisions | VECTOR provides Decision Intelligence but does not automatically make organizational or employment decisions. |
| FR-X10 — Graceful Partial Intelligence | Incomplete information returns the maximum justifiable result with explicit limitations rather than fabricated information. |

## 8. Functional Coverage Matrix

| Requirement | Journey | MUST Capability | Canonical Entity/Entities |
|---|---|---|---|
| FR-J01-001 | J01 | R01 Service Reliability Intelligence | AreaDomain, Service, MonitoringEvent, SLO, SLOObservation, MetricObservation, Evidence |
| FR-J01-002 | J01 | R02 Incident Intelligence | Service, Incident, Evidence |
| FR-J01-002 | J01 | R03 Problem & Recurrence Intelligence | Service, Incident, Problem, Evidence, RiskFinding |
| FR-J02-002 | J02 | R04 Change Risk Intelligence | Change, Deployment, Service, MonitoringEvent, SLOObservation, Incident, Evidence, RiskFinding |
| FR-J01-001 | J01 | R05 Event Intelligence | Service, MonitoringEvent, Evidence |
| FR-J01-001 | J01 | R06 SLO / SLI Intelligence | Service, SLO, SLOObservation, MetricObservation, Evidence |
| FR-J04-001 | J04 | E01 Area / Domain Performance Intelligence | AreaDomain, Service, RiskFinding, Evidence |
| FR-J03-001 | J03 | E02 Commitment Intelligence | RiskFinding, Commitment, Evidence |
| FR-J03-003 | J03 | E03 Improvement & Outcome Intelligence | Commitment, ImprovementAction, OutcomeVerification, Evidence |
| FR-J01-001 | J01 | C01 Canonical Technology Context | AreaDomain, Service, ConfigurationItem |
| FR-J01-002 | J01 | C02 Evidence, Provenance & Source Authority | Evidence, SourceReference |
| FR-J02-003 | J02 | C03 Cross-Source Correlation | Change, Deployment, Service, Evidence, SourceReference |
| FR-J04-002 | J04 | C04 Relationship / Service Graph | AreaDomain, Service, RiskFinding, Commitment, ImprovementAction, OutcomeVerification |
| FR-J04-001 | J04 | C05 Metric & KPI Intelligence | Service, SLO, SLOObservation, MetricObservation |
| FR-J01-002 | J01 | C06 Risk & Finding Intelligence | Service, Problem, RiskFinding, Evidence |
| FR-J04-002 | J04 | C07 Explainability | RiskFinding, Evidence, SourceReference |
| FR-J03-003 | J03 | C09 Trend & Historical Analysis | MonitoringEvent, Incident, Problem, SLOObservation, MetricObservation, OutcomeVerification |
| FR-J04-003 | J04 | C10 Decision Intelligence | AreaDomain, Service, RiskFinding, Commitment, ImprovementAction, OutcomeVerification |

Coverage result: 18/18 MUST capabilities functionally represented. Orphan MUST capabilities: 0.

## 9. Deferred Decisions

- Recurrence, risk, KPI, prioritization, confidence, and correlation formulas; thresholds; algorithms; and analysis windows.
- Technical schemas, cardinalities, persistence architecture, graph persistence, database technology, APIs, backend/frontend frameworks, deployment architecture, infrastructure topology, UX visual design, RBAC implementation, and AI model/provider.
- Detailed source-conflict precedence, representation, and confidence behavior.

## 10. Step 4 Quality Gate

Proposed status: PRE-AUDIT / EXTERNAL REVIEW PENDING

J01–J04 behavior is materialized, all 18 MUST capabilities are represented, no orphan MUST is identified, and the approved domain and cross-journey semantics are preserved. Step 4 is not CLOSED until external Quality Gate review passes.
