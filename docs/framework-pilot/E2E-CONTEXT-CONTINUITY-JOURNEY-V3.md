# VECTOR — E2E Context Continuity Journey V3

Status: **EXPERIENCE JOURNEY CONTRACTED / IMPLEMENTATION GAPS EXPLICIT**
Runtime: **UNCHANGED**
Scope: S1 → S2 → S3 → S4 → S5 → return projections.

## Purpose
Prove that the approved VECTOR surfaces form one operational-intelligence product rather than five attractive dashboards connected only by navigation.

## Deterministic reference case
The journey uses an abstract, non-corporate fixture. Names, thresholds, source systems and numeric values are intentionally unspecified.

Initial condition:
- one AreaDomain contains multiple Services;
- one Service has an evidence-backed persistent condition;
- evidence quality is sufficient to create attention but not sufficient to claim causality;
- a temporally nearby Change exists;
- investigation can establish facts/associations and leave hypotheses open;
- a governed Decision creates/links a Commitment and Action;
- Action can complete before OutcomeVerification exists;
- later evidence can verify IMPROVED / PERSISTENT / UNVERIFIABLE outcome.

No specific SLO target, telemetry vendor, infrastructure topology, owner or AI provider is assumed.

## Context Envelope

The cross-surface envelope MUST preserve, when applicable:
- `areaDomainId`
- `serviceId`
- `riskFindingId / investigationId`
- selected condition/focus
- selected/effective time window
- evidence quality/freshness
- provenance handles/references
- navigation origin / return target
- linked commitment/action/outcome identifiers after they exist

The envelope is a UX/navigation contract, not a claim that all fields belong in one canonical entity.

## Journey

### J-E2E-01 — S1 Panorama → S2 Area
User sees evidence-backed attention at portfolio level and selects an Area.

Must persist:
- evaluated period;
- evidence quality;
- reason for attention.

Must NOT:
- manufacture a risk from missing data;
- rank individuals;
- lose the reason that caused drill-in.

Acceptance:
S2 opens already scoped to the selected Area and can explain why the user arrived there.

### J-E2E-02 — S2 Area → S3 Service
User identifies the Service contributing to the Area condition.

Must persist:
- Area context;
- selected period;
- originating attention/finding link where applicable.

Acceptance:
S3 identifies the Service inside its Area and preserves the originating context without forcing a fresh search.

### J-E2E-03 — S3 Service → S4 Investigation
User moves from operational orientation to a specific Risk/Condition investigation.

S3 must expose only supported operational projections:
- service condition;
- evidence-backed findings;
- evidence quality;
- SLO/metric series if available;
- incidents if available;
- changes if available;
- dependency relationships/conditions if available.

Unavailable projections remain explicit.

Acceptance:
S4 receives the Service/Risk/period/evidence context and does not reinterpret temporal proximity or graph adjacency as causality.

### J-E2E-04 — S4 Evidence → Governed Decision
Investigation separates:
- source evidence/facts;
- deterministic findings;
- correlations/associations;
- hypotheses;
- limitations/unknowns.

A decision may be recorded only with its evidence/context boundary visible.

Acceptance:
An unresolved hypothesis can remain open. The user is not forced to declare root cause to continue with a risk-reduction action.

### J-E2E-05 — Decision → S5 Commitment/Action
A governed decision creates or links a Commitment/Action.

Must carry:
- originating Area/Service/Risk;
- intended result;
- agreed due date where applicable;
- decision/evidence references;
- accountability at allowed AreaDomain/process level.

Acceptance:
Commitment is not a detached task. Navigation back to the originating investigation/context remains possible.

### J-E2E-06 — Execution completion → Outcome Pending
Action/Commitment execution reaches completed state.

Required behavior:
- execution evidence can be attached/referenced;
- UI explicitly shows `COMPLETED / OUTCOME_PENDING`;
- no improvement is inferred from task completion.

Acceptance:
S1/S2/S3/S4 projections may show action completed, but MUST NOT show the underlying condition as improved until OutcomeVerification supports it.

### J-E2E-07 — Outcome Verification
Later evidence evaluates the intended result.

Allowed outcome classes are governed by existing canonical semantics; representative states:
- improved/verified;
- persistent/not improved;
- unverifiable/insufficient evidence.

Acceptance:
Outcome evidence is traceable and distinct from execution evidence.

### J-E2E-08 — Return / Learning propagation
User returns from S5 to S4/S3/S2/S1.

Must preserve:
- originating context;
- outcome state;
- evidence quality;
- historical decision/action link.

Expected projections:
- S4 shows investigation/action/outcome continuity;
- S3 shows current Service condition and outcome state where relevant;
- S2 updates Area attention/follow-up based on supported deterministic projection;
- S1 updates portfolio attention/commitment/outcome context without claiming causal success beyond evidence.

## Cross-surface invariants
1. similarity != identity
2. correlation != causation
3. projection != source truth
4. commitment != activity
5. execution completion != verified outcome
6. missing evidence != healthy
7. navigation context != canonical authority
8. AI hypothesis != deterministic finding
9. no individual ranking/scoring

## Capability assessment

| Transition | Current semantic support | Experience/implementation status |
|---|---|---|
| S1 → S2 | supported | MODIFY navigation/context persistence |
| S2 → S3 | supported | MODIFY navigation/context persistence |
| S3 → S4 | partial | GAP: richer Service operational projections + context handoff |
| S4 evidence model | supported in product intent/EXT-003 | DEEPEN provenance/epistemic presentation |
| S4 → decision/action | partial | DEEPEN governed transition |
| decision → S5 commitment | supported | INTEGRATE origin/evidence trace |
| commitment lifecycle | END_TO_END local per EXT-004 | PRESERVE |
| completed → outcome pending | supported | PRESERVE and make visually dominant |
| outcome verification | supported canonical concept | INTEGRATE return projection |
| S5 → S4/S3/S2/S1 return | partial | GAP: explicit cross-surface return/projection contract |

## Product gaps discovered
### GAP-E2E-01 — Context Envelope
A first-class cross-surface navigation/context contract is required.

### GAP-E2E-02 — Service Operational Projection Depth
S3 needs first-class experience projections for the already-approved operational concepts required by prioritized journeys:
- SLO/metric time series where available;
- Incident state;
- Change temporal context;
- dependency relationship/condition;
- explicit unavailable/stale/partial states.

This does not authorize invented corporate thresholds/sources.

### GAP-E2E-03 — Investigation → Decision lineage
Decision/Commitment creation/linking must preserve evidence/investigation origin.

### GAP-E2E-04 — Outcome return propagation
OutcomeVerification must project back to originating Risk/Service/Area/Panorama without rewriting historical evidence.

## Adversarial E2E variants required
Before ASSURED:
1. degradation after change;
2. degradation predates change;
3. action completed but condition persists;
4. action completed and evidence unavailable;
5. verified improvement;
6. conflicting evidence;
7. stale/partial evidence;
8. healthy/no-risk negative control;
9. renegotiated vs overdue commitment;
10. unauthorized mutation.

## Gate result
**E2E EXPERIENCE CONTRACT: PASS WITH EXPLICIT IMPLEMENTATION GAPS.**

No SPEC-BLOCKER exists for local/demo implementation because the gaps are product work, not missing user-only authority.

This document does not claim executable E2E assurance yet.
