# VECTOR — Graph / Evidence Specification

## 1. Status

- Status: CLOSED
- Step: Step 6 — Graph / Evidence Specification
- Quality Gate: PASSED
- SDD status: NOT_READY_FOR_IMPLEMENTATION
- SPEC-BLOCKERS: 0

This specification defines semantic relationships and traversability only. It does not select a graph product, query language, storage model, physical schema, keys, indexes, cardinalities, or performance target.

## 2. Semantic Rules

Relationships are directional, typed, and explicit; `RELATED_TO` is not normative. A semantic relationship has one normative predicate direction. A Journey may navigate it from either endpoint; reverse traversal does not reverse, rename, or alter the predicate. For example, `Evidence --SUPPORTS--> RiskFinding` may be navigated as `RiskFinding ← SUPPORTS -- Evidence`. Canonical Identity != Source Identity; Identity != Correlation; Correlation != Causation; Canonical Representation != Source Authority; Execution != Outcome. Service remains the primary technology correlation anchor.

EXT-001 controlled amendment: `Commitment --ACCOUNTABLE_TO--> AreaDomain` is an
extension-specific structural relationship, with exactly one existing
AreaDomain target per Commitment. Reverse traversal is allowed; organizational
hierarchy, person/team ownership, and Source Authority are not inferred. This
does not add a V1 GRC contract: the closed inventory remains GRC-01..GRC-18.

Evidence and SourceReference are distinct: Evidence supports a claim, association, explanation, uncertainty, or verification; SourceReference preserves source identity, provenance, and known authority. Material relationships preserve applicable Evidence, provenance, temporal context (`occurredAt`, `observedAt`, `ingestedAt`, `effectiveFrom`/`effectiveTo`), identity resolution context, uncertainty, and Source Authority context. CONFIRMED, INFERRED, and UNRESOLVED apply to identity only; no correlation state machine is created.

Conflicting claims follow DEC-035: preserve competing claims and provenance; select only under an explicit applicable authority rule; otherwise represent conflict as unresolved. Unknown corporate authority remains TBD.

## 3. Relationship Contracts

| ID | Semantic name / predicate | Source → Target | Meaning | Evidence / provenance expectation | Temporal / uncertainty considerations | Supports | Guard |
|---|---|---|---|---|---|---|---|
| GRC-01 | `CONTEXTUALIZES_SERVICE` | AreaDomain → Service | Area/domain gives decision context to a Service. | Sourced context retains SourceReference. | Effective validity when context changes; identity uncertainty explicit. | E01:J03,J04; C01:J01,J02,J03,J04; C10:J01,J02,J03,J04 | Does not imply ownership or hierarchy cardinality. |
| GRC-02 | `HAS_TECHNOLOGY_CONTEXT` | Service → ConfigurationItem | Available ConfigurationItem contextualizes a Service. | SourceReference for sourced context. | Effective validity; missing CI does not block J01. | C01:J01,J02,J03,J04; C04:J01,J02,J03,J04 | Does not prescribe CMDB mapping or cardinality. |
| GRC-03 | `OBSERVES_CONDITION_OF` | MonitoringEvent → Service | Signal observes an operational condition of Service. | Evidence and source provenance retained. | occurredAt/observedAt; completeness/freshness context. | R01:J01,J02,J03,J04; R05:J01,J02,J04; C09:J01,J03,J04 | Does not imply incident, root cause, or causation. |
| GRC-04 | `AFFECTS_SERVICE` | Incident → Service | Incident provides disruption/degradation context. | SourceReference and Evidence where used. | Occurrence/observation time supports recurrence. | R01:J01,J02,J03,J04; R02:J01,J02,J03,J04; R03:J01,J03,J04; R04:J02,J03,J04; C06:J01,J03,J04; C09:J01,J03,J04 | Does not itself establish a Problem or cause. |
| GRC-05 | `PROVIDES_CONTEXT_FOR` | Incident → Problem | Incident may contextualize a persistent/recurrent condition. | Evidence/provenance retained. | Historical recurrence context. | R03:J01,J03,J04; C06:J01,J03,J04; C09:J01,J03,J04 | Does not assert root cause. |
| GRC-06 | `DECLARES_CONTEXT_FOR` | Change → Deployment | Change supplies declared modification context for Deployment. | SourceReference retained. | occurredAt/effective interval. | R04:J02,J03,J04; C03:J01,J02,J03,J04 | Does not imply deployment caused degradation. |
| GRC-07 | `DEPLOYS_TO` | Deployment → Service | Deployment supplies delivery-to-Service operational context. | Evidence/provenance retained. | occurredAt; before/during/after context. | R04:J02,J03,J04; C03:J01,J02,J03,J04; C04:J01,J02,J03,J04 | Does not imply causation or deployment architecture. |
| GRC-08 | `DEFINES_OBJECTIVE_FOR` | SLO → Service | SLO defines objective context for Service. | Measurement source and SourceReference retained. | Effective validity. | R06:J01,J02,J03,J04; C05:J01,J02,J03,J04 | Does not define SLO calculation. |
| GRC-09 | `OBSERVES_BEHAVIOR_OF` | SLOObservation → SLO | Observation records behavior against objective. | Evidence/source provenance retained. | observedAt/occurredAt supports trends. | R06:J01,J02,J03,J04; C05:J01,J02,J03,J04; C09:J01,J03,J04 | Does not itself determine priority. |
| GRC-10 | `SUPPORTS` | Evidence → RiskFinding | Evidence supports explainable derived finding. | Evidence must link to SourceReference when external. | Evidence availability/limitations explicit. | R01:J01,J02,J03,J04; R02:J01,J02,J03,J04; R03:J01,J03,J04; R04:J02,J03,J04; C02:J01,J02,J03,J04; C06:J01,J02,J03,J04; C07:J01,J02,J03,J04; C10:J01,J02,J03,J04 | Does not make an unsupported finding certain. |
| GRC-11 | `IS_ADDRESSED_BY` | RiskFinding → Commitment | The RiskFinding is addressed by a declared Commitment. | Evidence/provenance for finding and commitment. | Effective/due context when available. | E02:J03,J04; C10:J01,J02,J03,J04 | Does not imply action completion or outcome. |
| GRC-12 | `IS_ADVANCED_BY` | Commitment → ImprovementAction | The Commitment is advanced by a concrete ImprovementAction. | Provenance retained. | Execution time/status context. | E03:J03,J04; C10:J01,J02,J03,J04 | Does not reverse the approved chain. |
| GRC-13 | `IS_EVALUATED_BY` | ImprovementAction → OutcomeVerification | The ImprovementAction is evaluated by OutcomeVerification. | Subsequent operational Evidence required. | Before/after context; insufficient evidence explicit. | E03:J03,J04; C09:J01,J03,J04; C10:J01,J02,J03,J04 | Completion does not imply IMPROVED. |
| GRC-14 | `MEASURES_CONTEXT_FOR` | MetricObservation → Service | Metric observation provides contextual measurement. | SourceReference/Evidence retained. | occurredAt/observedAt supports history. | R06:J01,J02,J03,J04; C05:J01,J02,J03,J04; C09:J01,J03,J04 | Does not create an individual-performance score. |
| GRC-15 | `PRESERVES_PROVENANCE_FOR` | SourceReference → Canonical entity or claim | SourceReference identifies external identity, provenance, and known authority. | Required for sourced claims where available. | observedAt/ingestedAt when relevant; identity state may apply. | C02:J01,J02,J03,J04; C03:J01,J02,J03,J04; C07:J01,J02,J03,J04 | Does not become canonical identity or transfer authority. |
| GRC-16 | `CORRELATES_WITH_CONTEXT` | Change/Deployment → MonitoringEvent/Incident | Evidence-backed temporal/contextual association. | Correlation Evidence, provenance, method, uncertainty required. | Before/during/after and proximity; no correlation enum. | R04:J02,J03,J04; C03:J01,J02,J03,J04 | Never implies causation. |
| GRC-17 | `SUPPORTS_VERIFICATION` | Evidence → OutcomeVerification | Evidence supports before/after structural outcome claim. | Evidence and source provenance required. | Subsequent operational context; limitations explicit. | E03:J03,J04; C07:J01,J02,J03,J04; C09:J01,J03,J04; C10:J01,J02,J03,J04 | Insufficient Evidence remains not yet verifiable. |
| GRC-18 | `CONCERNS` | RiskFinding → Service | A RiskFinding concerns the relevant Service context whose risk, condition, degradation, recurrence, execution concern, or outcome context it describes. | The RiskFinding remains Evidence-backed; applicable Evidence and provenance remain traceable. | Applicable temporal context and uncertainty are preserved; no numeric confidence formula. | R01:J01,J02,J03,J04; C06:J01,J02,J03,J04; C07:J01,J02,J03,J04; C10:J01,J02,J03,J04 | Does not imply causation, root cause, ownership, blame, exclusivity, Source Authority transfer, or individual responsibility. |

## 4. Minimum Semantic Traversability

Journey navigation order != relationship predicate order. A Journey is satisfied by a connected semantic subgraph composed of explicit GRCs; consecutive concepts in a user drilldown or analytical narrative do not necessarily have a direct edge. Reverse traversal is allowed without changing predicate semantics.

- J01 subgraph: AreaDomain --CONTEXTUALIZES_SERVICE--> Service; MonitoringEvent --OBSERVES_CONDITION_OF--> Service; Incident --AFFECTS_SERVICE--> Service; Incident --PROVIDES_CONTEXT_FOR--> Problem (optional); SLO --DEFINES_OBJECTIVE_FOR--> Service; SLOObservation --OBSERVES_BEHAVIOR_OF--> SLO; MetricObservation --MEASURES_CONTEXT_FOR--> Service; RiskFinding --CONCERNS--> Service; Evidence --SUPPORTS--> RiskFinding; SourceReference preserves provenance. From Service, navigate `Service ← CONCERNS -- RiskFinding ← SUPPORTS -- Evidence`. Absence of Problem does not prevent traversal; recurrence is not root cause.
- J02 subgraph: Change --DECLARES_CONTEXT_FOR--> Deployment; Deployment --DEPLOYS_TO--> Service; Change/Deployment --CORRELATES_WITH_CONTEXT--> MonitoringEvent/Incident; RiskFinding --CONCERNS--> Service; Evidence --SUPPORTS--> RiskFinding. Temporal association and uncertainty remain Evidence-backed; correlation is not causation.
- J03 subgraph: RiskFinding --IS_ADDRESSED_BY--> Commitment --IS_ADVANCED_BY--> ImprovementAction --IS_EVALUATED_BY--> OutcomeVerification; Evidence --SUPPORTS--> RiskFinding; Evidence --SUPPORTS_VERIFICATION--> OutcomeVerification. Outcome is IMPROVED, PERSISTENT, or insufficient evidence/not yet verifiable; execution completion is not outcome proof.
- J04 subgraph: AreaDomain --CONTEXTUALIZES_SERVICE--> Service; from Service navigate `Service ← CONCERNS -- RiskFinding ← SUPPORTS -- Evidence`; RiskFinding --IS_ADDRESSED_BY--> Commitment --IS_ADVANCED_BY--> ImprovementAction --IS_EVALUATED_BY--> OutcomeVerification; Evidence --SUPPORTS_VERIFICATION--> OutcomeVerification; SourceReference preserves provenance. The drilldown Area/Domain → Service → Condition/Risk → Evidence → Action → Outcome is user navigation order, not a claim of direct adjacent edges.

## 5. Graph Coverage Matrix

| Capability | Journey(s) | Relationship Contract(s) | Semantic justification |
|---|---|---|---|
| R01 | J01, J02, J03, J04 | GRC-03, GRC-04, GRC-10, GRC-18 | Operational signals, incidents, Evidence, and RiskFinding-to-Service context support service reliability findings. |
| R02 | J01, J02, J03, J04 | GRC-04 | Incident context is explicitly connected to Service. |
| R03 | J01, J03, J04 | GRC-05, GRC-10 | Incident/Problem recurrence context supports findings. |
| R04 | J02, J03, J04 | GRC-06, GRC-07, GRC-16 | Change/deployment context and Evidence-backed association support change risk. |
| R05 | J01, J02, J04 | GRC-03 | MonitoringEvent observes Service condition. |
| R06 | J01, J02, J03, J04 | GRC-08, GRC-09, GRC-14 | SLO and metric observations provide service measurement context. |
| E01 | J03, J04 | GRC-01 | Area/domain context supports decision view. |
| E02 | J03, J04 | GRC-11 | RiskFinding is addressed by Commitment. |
| E03 | J03, J04 | GRC-12, GRC-13, GRC-17 | Commitment advances through action and Evidence-backed verification. |
| C01 | J01, J02, J03, J04 | GRC-01, GRC-02 | AreaDomain, Service, and ConfigurationItem provide canonical context. |
| C02 | J01, J02, J03, J04 | GRC-10, GRC-15 | Evidence and SourceReference preserve traceability. |
| C03 | J01, J02, J03, J04 | GRC-15, GRC-16 | Source identity and explicit temporal correlation are represented. |
| C04 | J01, J02, J03, J04 | GRC-02, GRC-07 | Service-centered technology and deployment relationships are explicit. |
| C05 | J01, J02, J03, J04 | GRC-08, GRC-09, GRC-14 | SLO and metric observation semantics support KPI context. |
| C06 | J01, J02, J03, J04 | GRC-05, GRC-10, GRC-18 | Problem context, Evidence, and RiskFinding-to-Service context support risk/finding intelligence. |
| C07 | J01, J02, J03, J04 | GRC-10, GRC-15, GRC-17, GRC-18 | Evidence, provenance, verification, and explicit RiskFinding context support explainability. |
| C09 | J01, J03, J04 | GRC-05, GRC-09, GRC-13, GRC-14, GRC-17 | Historical observations and outcome verification support trends. |
| C10 | J01, J02, J03, J04 | GRC-11, GRC-12, GRC-13, GRC-18 | Commitment/action/outcome semantics and Service-anchored findings support decision intelligence. |

Coverage result: 17/17 canonical entities are compatible with graph semantics; J01–J04 are graph-representable; 18/18 MUST capabilities are graph-representable; orphan MUST capabilities: 0. Required Capability/Journey pairs remain 62/62 supported, with zero unsupported or extra non-normative pairs.

EXT-001 extension coverage: every extension Commitment has exactly one
`ACCOUNTABLE_TO` AreaDomain; this is outside the closed 18/18 GRC inventory and
does not change the 17/18/62 V1 baseline.

## 6. Deferred Decisions

Graph database/product, relational versus graph persistence, query language, physical graph schema, cardinalities, keys, indexes, partitioning, materialization strategy, streaming, retention, performance targets, formulas, thresholds, identity matching algorithms, and corporate mappings/adapters remain deferred to later Steps 7, 9, or 10 as applicable.

## 7. Step 6 Quality Gate

Status: CLOSED / EXTERNAL QUALITY GATE PASSED. No physical persistence technology or corporate authority is selected.
