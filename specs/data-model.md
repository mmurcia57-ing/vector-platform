# VECTOR — Data Specification

## Status

- Status: CLOSED
- Step: Step 5 — Data Specification
- Quality Gate: PASSED
- SDD status: NOT_READY_FOR_IMPLEMENTATION
- SPEC-BLOCKERS: 0

Canonical Identity != Source Identity; Identity != Correlation; Correlation != Causation; Canonical Representation != Source Authority. Identity resolution is CONFIRMED, INFERRED, or UNRESOLVED. `occurredAt`, `observedAt`, `ingestedAt`, `effectiveFrom`, and `effectiveTo` apply only where relevant. Data Confidence is contextual, not numerical. Physical persistence is deferred.

## Minimum Data Contracts

### MDC-01 — AreaDomain
Purpose: Technology domain context.
Canonical identity: Vendor-independent AreaDomain identity.
Minimum semantic attributes: Name; domain context.
Temporal semantics: effectiveFrom/effectiveTo when context changes.
Required information: Identity and name.
Optional information: Validity interval.
Origin classification: MIXED / AUTHORITY-DEPENDENT.
Source Authority: Retained per claim; otherwise TBD.
Provenance / SourceReference: SourceReference for sourced context.
Uncertainty / confidence considerations: Incomplete/conflicting context is explicit.
Historical requirement: Applicable historical domain context.
Supports: J01, J04; E01, C01, C10.
Explicitly deferred: Hierarchy and cardinality.

### MDC-02 — Service
Purpose: Primary technology correlation anchor.
Canonical identity: Vendor-independent Service identity.
Minimum semantic attributes: Name; AreaDomain; condition context.
Temporal semantics: effectiveFrom/effectiveTo; ingestedAt for claims.
Required information: Identity, name, available AreaDomain.
Optional information: ConfigurationItem and operational links.
Origin classification: MIXED / AUTHORITY-DEPENDENT.
Source Authority: Retained per claim.
Provenance / SourceReference: SourceReference for source identity/provenance.
Uncertainty / confidence considerations: Identity state and unavailable context explicit.
Historical requirement: J01–J04 context.
Supports: J01–J04; R01–R06, C01–C10.
Explicitly deferred: Matching algorithms and topology.

### MDC-03 — ConfigurationItem
Purpose: Technology context relevant to Service.
Canonical identity: Vendor-independent ConfigurationItem identity.
Minimum semantic attributes: Semantic role/type; Service context.
Temporal semantics: effectiveFrom/effectiveTo when applicable.
Required information: Identity and role when represented.
Optional information: Service context.
Origin classification: MIXED / AUTHORITY-DEPENDENT.
Source Authority: Retained per claim; unknown is TBD.
Provenance / SourceReference: SourceReference for sourced identity/context.
Uncertainty / confidence considerations: Missing context does not block J01.
Historical requirement: Applicable technology context.
Supports: J01, J04; C01, C04.
Explicitly deferred: CMDB mapping and cardinality.

### MDC-04 — MonitoringEvent
Purpose: Observed operational signal.
Canonical identity: Vendor-independent MonitoringEvent identity.
Minimum semantic attributes: Signal category; condition; Service context.
Temporal semantics: occurredAt and/or observedAt; ingestedAt.
Required information: Identity, category, time, Service when known.
Optional information: Source severity/context.
Origin classification: SOURCE-PROVIDED.
Source Authority: Producing source retains authority.
Provenance / SourceReference: SourceReference and Evidence required.
Uncertainty / confidence considerations: Completeness/freshness explicit.
Historical requirement: Recurrence and J02 windows.
Supports: J01, J02, J04; R01, R05, C03, C09.
Explicitly deferred: Event schema.

### MDC-05 — Incident
Purpose: Operational disruption context.
Canonical identity: Vendor-independent Incident identity.
Minimum semantic attributes: Incident, Service, Problem context.
Temporal semantics: occurredAt and/or observedAt; ingestedAt.
Required information: Identity and occurrence context.
Optional information: Problem link/status context.
Origin classification: SOURCE-PROVIDED.
Source Authority: ITSM authority retained when known.
Provenance / SourceReference: SourceReference required.
Uncertainty / confidence considerations: Missing links explicit.
Historical requirement: Recurrence and comparison.
Supports: J01–J04; R02, R03, R04, C09.
Explicitly deferred: Workflow/state model.

### MDC-06 — Problem
Purpose: Persistent/recurrent condition.
Canonical identity: Vendor-independent Problem identity.
Minimum semantic attributes: Condition; Incident/Service context.
Temporal semantics: observedAt; effectiveFrom/effectiveTo.
Required information: Identity and condition.
Optional information: Incident/resolution context.
Origin classification: MIXED / AUTHORITY-DEPENDENT.
Source Authority: Retained per sourced claim.
Provenance / SourceReference: SourceReference and Evidence where sourced.
Uncertainty / confidence considerations: Does not establish root cause.
Historical requirement: Persistence/recurrence.
Supports: J01, J03, J04; R03, C06, C09.
Explicitly deferred: Root-cause algorithms.

### MDC-07 — Change
Purpose: Declared modification context.
Canonical identity: Vendor-independent Change identity.
Minimum semantic attributes: Declared change; Deployment context.
Temporal semantics: occurredAt; effectiveFrom/effectiveTo.
Required information: Identity and declared change.
Optional information: Deployment/Service context.
Origin classification: SOURCE-PROVIDED.
Source Authority: Source retains authority.
Provenance / SourceReference: SourceReference required.
Uncertainty / confidence considerations: Association is non-causal.
Historical requirement: J02 comparison.
Supports: J02, J04; R04, C03, C04.
Explicitly deferred: Causal attribution.

### MDC-08 — Deployment
Purpose: Delivery-to-Service operational context.
Canonical identity: Vendor-independent Deployment identity.
Minimum semantic attributes: Deployment; Change; Service context.
Temporal semantics: occurredAt; ingestedAt.
Required information: Identity and deployment time/context.
Optional information: Change context.
Origin classification: SOURCE-PROVIDED.
Source Authority: Source retains authority.
Provenance / SourceReference: SourceReference required.
Uncertainty / confidence considerations: Association is not causation.
Historical requirement: J02 windows.
Supports: J02, J04; R04, C03, C04.
Explicitly deferred: Deployment architecture.

### MDC-09 — SLO
Purpose: Service-level objective context.
Canonical identity: Vendor-independent SLO identity.
Minimum semantic attributes: Objective; Service context.
Temporal semantics: effectiveFrom/effectiveTo.
Required information: Identity and objective.
Optional information: Service/source target detail.
Origin classification: SOURCE-PROVIDED.
Source Authority: Measurement source retains authority.
Provenance / SourceReference: SourceReference required.
Uncertainty / confidence considerations: Completeness/authority explicit.
Historical requirement: Objective history.
Supports: J01, J04; R06, C05.
Explicitly deferred: SLO calculation.

### MDC-10 — SLOObservation
Purpose: Observation of SLO/SLI behavior.
Canonical identity: Vendor-independent SLOObservation identity.
Minimum semantic attributes: Observation; SLO context; value/context.
Temporal semantics: observedAt and/or occurredAt; ingestedAt.
Required information: Identity, observation, time, SLO context.
Optional information: Service context.
Origin classification: SOURCE-PROVIDED.
Source Authority: Measurement source retains authority.
Provenance / SourceReference: SourceReference and Evidence required.
Uncertainty / confidence considerations: Freshness/completeness explicit.
Historical requirement: Trend/comparison.
Supports: J01–J04; R06, C05, C09.
Explicitly deferred: Formula/threshold.

### MDC-11 — Commitment
Purpose: Declared intent to address risk/condition/outcome.
Canonical identity: Vendor-independent Commitment identity.
Minimum semantic attributes: Commitment; RiskFinding; due/status context.
Temporal semantics: occurredAt; effectiveFrom/effectiveTo.
Required information: Identity and declared context.
Optional information: Due date/status.
Origin classification: MIXED / AUTHORITY-DEPENDENT.
Source Authority: VECTOR may be authoritative when native; external authority TBD.
Provenance / SourceReference: Sourced commitment retains provenance.
Uncertainty / confidence considerations: External ownership explicit.
Historical requirement: Action/outcome history.
Supports: J03, J04; E02, C10.
Explicitly deferred: Corporate Commitment system.

### MDC-12 — ImprovementAction
Purpose: Concrete execution advancing Commitment.
Canonical identity: Vendor-independent ImprovementAction identity.
Minimum semantic attributes: Action; Commitment; execution/status context.
Temporal semantics: occurredAt; effectiveFrom/effectiveTo.
Required information: Identity, action, Commitment context.
Optional information: Completion/status.
Origin classification: MIXED / AUTHORITY-DEPENDENT.
Source Authority: Retained by source or native origin.
Provenance / SourceReference: Provenance for sourced execution.
Uncertainty / confidence considerations: Completion != outcome.
Historical requirement: Execution history.
Supports: J03, J04; E03, C10.
Explicitly deferred: Execution workflow/schema.

### MDC-13 — OutcomeVerification
Purpose: Evidence-backed structural outcome verification.
Canonical identity: Vendor-independent OutcomeVerification identity.
Minimum semantic attributes: IMPROVED/PERSISTENT/insufficient evidence; Evidence basis; before/after context.
Temporal semantics: observedAt and occurredAt when applicable.
Required information: Identity, outcome context, Evidence basis.
Optional information: Comparable history.
Origin classification: VECTOR-NATIVE / DERIVED.
Source Authority: VECTOR authoritative for native verification, not source facts.
Provenance / SourceReference: Derived provenance and Evidence required.
Uncertainty / confidence considerations: Insufficient evidence explicit.
Historical requirement: Before/after verification.
Supports: J03, J04; E03, C09, C10.
Explicitly deferred: Physical enum/formula.

### MDC-14 — Evidence
Purpose: Traceable supporting/correlation record.
Canonical identity: Vendor-independent Evidence identity.
Minimum semantic attributes: Type; supported claim/context; provenance; limitations.
Temporal semantics: occurredAt, observedAt, ingestedAt when relevant.
Required information: Identity, supported context, provenance.
Optional information: Source-native content/reference.
Origin classification: MIXED / AUTHORITY-DEPENDENT.
Source Authority: Source-specific; VECTOR native correlation only.
Provenance / SourceReference: SourceReference required for external Evidence.
Uncertainty / confidence considerations: Availability/limitations explicit.
Historical requirement: Auditability and verification history.
Supports: J01–J04; C02, C03, C07.
Explicitly deferred: Physical Evidence storage.

### MDC-15 — RiskFinding
Purpose: Explainable derived risk/finding.
Canonical identity: Vendor-independent RiskFinding identity.
Minimum semantic attributes: Condition; Service; explanation; Evidence basis.
Temporal semantics: observedAt/derived context; ingestedAt for sources.
Required information: Identity, explainable condition, Evidence basis.
Optional information: Attention context.
Origin classification: VECTOR-NATIVE / DERIVED.
Source Authority: VECTOR authoritative for derived finding, not source facts.
Provenance / SourceReference: Evidence and source provenance required.
Uncertainty / confidence considerations: Insufficient Evidence prevents conclusions.
Historical requirement: Persistence/decision context.
Supports: J01–J04; R01, R03, C06, C07, C10.
Explicitly deferred: Risk formula/threshold.

### MDC-16 — MetricObservation
Purpose: Contextual metric/KPI observation.
Canonical identity: Vendor-independent MetricObservation identity.
Minimum semantic attributes: Metric; value/context; Service/SLO context.
Temporal semantics: occurredAt and/or observedAt; ingestedAt.
Required information: Identity, metric context, time.
Optional information: SLO context.
Origin classification: SOURCE-PROVIDED.
Source Authority: Measurement source retains authority.
Provenance / SourceReference: SourceReference and Evidence required.
Uncertainty / confidence considerations: Freshness/completeness explicit.
Historical requirement: Trend and before/after comparison.
Supports: J01, J03, J04; R06, C05, C09.
Explicitly deferred: KPI formula.

### MDC-17 — SourceReference
Purpose: Source identity/provenance for canonical entity or claim.
Canonical identity: Vendor-independent SourceReference identity.
Minimum semantic attributes: Source/system; source-native identifier; referenced object/claim; authority context.
Temporal semantics: observedAt and ingestedAt when relevant.
Required information: Identity, source context, referenced context.
Optional information: Native identifier/authority when unknown.
Origin classification: MIXED / AUTHORITY-DEPENDENT.
Source Authority: Describes authority; never transfers it to VECTOR.
Provenance / SourceReference: Not applicable for this entity at Step 5; it is the provenance reference.
Uncertainty / confidence considerations: Unknown authority/unresolved identity explicit.
Historical requirement: Traceability across Journey history.
Supports: J01–J04; C02, C03, C07.
Explicitly deferred: Vendor mapping and precedence.

## Cross-Entity Data Rules

AreaDomain → Service → ConfigurationItem preserves available technology context. Service anchors operational context. Incident may contextualize Problem without proving root cause. Change → Deployment → Service is temporal/contextual and Evidence-backed, never causal. SLOObservation contextualizes SLO. Evidence supports RiskFinding. RiskFinding → Commitment → ImprovementAction → OutcomeVerification; execution completion does not prove improvement. SourceReference preserves source identity/provenance/authority, not canonical identity.

## Data Coverage Matrix

| MDC | Canonical Entity | Journey(s) | Functional Requirement(s) | MUST Capability/Capabilities |
|---|---|---|---|---|
| MDC-01 | AreaDomain | J01,J04 | FR-J01-001;FR-J04-001/002 | E01,C01,C10 |
| MDC-02 | Service | J01–J04 | FR-J01-001;FR-J02-001/002;FR-J04-001/002 | R01–R06,C01–C10 |
| MDC-03 | ConfigurationItem | J01,J04 | FR-J01-001;FR-J04-002 | C01,C04 |
| MDC-04 | MonitoringEvent | J01,J02,J04 | FR-J01-001/002;FR-J02-002 | R01,R05,C03,C09 |
| MDC-05 | Incident | J01–J04 | FR-J01-001/002;FR-J02-002;FR-J03-002 | R02,R03,R04,C09 |
| MDC-06 | Problem | J01,J03,J04 | FR-J01-001/002;FR-J03-002 | R03,C06,C09 |
| MDC-07 | Change | J02,J04 | FR-J02-001/002/003 | R04,C03,C04 |
| MDC-08 | Deployment | J02,J04 | FR-J02-001/002/003 | R04,C03,C04 |
| MDC-09 | SLO | J01,J04 | FR-J01-001;FR-J04-001 | R06,C05 |
| MDC-10 | SLOObservation | J01–J04 | FR-J01-001;FR-J02-002;FR-J03-002/003 | R06,C05,C09 |
| MDC-11 | Commitment | J03,J04 | FR-J03-001/002/003;FR-J04-002/003 | E02,C10 |
| MDC-12 | ImprovementAction | J03,J04 | FR-J03-001/002/003;FR-J04-002/003 | E03,C10 |
| MDC-13 | OutcomeVerification | J03,J04 | FR-J03-002/003;FR-J04-002/003 | E03,C09,C10 |
| MDC-14 | Evidence | J01–J04 | FR-J01-002;FR-J02-002/003;FR-J03-003;FR-J04-002 | C02,C03,C07 |
| MDC-15 | RiskFinding | J01–J04 | FR-J01-002/003;FR-J02-002;FR-J03-001;FR-J04-001/002 | R01,R03,C06,C07,C10 |
| MDC-16 | MetricObservation | J01,J03,J04 | FR-J01-001;FR-J03-002/003;FR-J04-001 | R06,C05,C09 |
| MDC-17 | SourceReference | J01–J04 | FR-J01-001/002;FR-J02-001/003;FR-J03-001;FR-J04-002 | C02,C03,C07 |

Coverage result: 17/17 MDCs represented; J01–J04 data-supported; 18/18 MUST capabilities data-representable; orphan MUST capabilities: 0.

## Deferred Decisions

Physical databases/graphs, SQL/NoSQL, schemas, keys, indexes, APIs, cardinalities, retention, formulas, thresholds, corporate precedence, conflict UI, and deployment architecture are deferred.

## Step 5 Quality Gate

Status: CLOSED / EXTERNAL QUALITY GATE PASSED. SPEC-BLOCKERS: 0.
