# VECTOR — Acceptance & Test Strategy

## 1. Status and scope

- Status: CLOSED / EXTERNAL QUALITY GATE PASSED
- Step: Step 12 — Acceptance & Test Strategy
- External Quality Gate: PASS
- SDD status: NOT_READY_FOR_IMPLEMENTATION
- SPEC-BLOCKERS: 0

This is the normative acceptance strategy for VECTOR V1. It defines semantic,
security, resilience, performance, UX, integration, and AI acceptance without
creating test code, fixtures, infrastructure, or selecting test tooling.
Preparatory files under `work-prep/` were reviewed only as non-normative input;
they are neither modified nor silently promoted to Source of Truth.

Steps 0–11 remain CLOSED. Step 12 is MATERIALIZED / PRE-AUDIT. Step 13 has not
started.

## 2. Acceptance principles

Acceptance proves behavior, not merely HTTP success or counts. The central
traceability chain is:

`Requirement → Acceptance Scenario → Test → Result`

All acceptance preserves exactly 17 canonical entities, 18 GRC predicates,
62/62 Capability × Journey coverage, J01–J04, UXI-01..UXI-15, Source Authority,
provenance, `Identity != Correlation`, `Correlation != Causation`, Evidence
First, graceful partial intelligence, and
`RiskFinding → Commitment → ImprovementAction → OutcomeVerification`.

The Golden Dataset is for deterministic semantic correctness. It is isolated
from synthetic capacity profiles. All synthetic data is synthetic/demo-safe and
never represented as corporate production data or load.

## 3. Acceptance requirements

### AT-01 — J01–J04 end-to-end acceptance

Prove J01 Persistent Reliability Risk, J02 Change-Associated Degradation, J03
Structural Improvement Verification, and J04 Area/Domain Decision View
end-to-end through the approved canonical, Evidence, UX, and outcome semantics.

### AT-02 — Semantic invariant acceptance

Test negative and positive behavior for Correlation != Causation, Identity !=
Correlation, Source Authority, provenance, conflicting claims, uncertainty,
missing context, canonical semantics, and relevant GRC predicates.

### AT-03 — Provider/adapter contract acceptance

Demonstrate provider substitutability with contract-compatible mock, sandbox,
or provider implementations, without redesigning VECTOR. Cover ARIA Events →
MonitoringEvent and SRE Skill provider semantics. ARIA completeness remains
TBD; SRE Skill does not route through ARIA; Dynatrace/Grail versus Elastic
remains TBD.

### AT-04 — Investigative UX acceptance

Cover normal, partial, stale, conflicting, INFERRED, UNRESOLVED, insufficient
Evidence, no Commitment, no completed action, completed action without
verifiable outcome, IMPROVED, PERSISTENT, AI unavailable, and graph
unavailable/stale states. UX must preserve context and never make incomplete
context appear complete.

### AT-05 — NFR and resilience acceptance

Validate Step 10 local V1 performance defaults, observability, overload,
failure isolation, projection lag, recovery, and graceful degradation. Local
acceptance baselines are not corporate SLAs/SLOs.

### AT-06 — Security and authorization acceptance

Include negative authorization paths, least privilege, secret non-exposure,
fail-secure behavior, and proof that AI never expands acting-user permissions.

### AT-07 — AI behavioral acceptance

Consume all 18 Step 11 AI handoff scenarios. AI wording may vary; epistemic,
semantic, authorization, and safety invariants may not.

### AT-08 — Golden Scenario Dataset

Use a small deterministic canonical Golden Dataset for semantic/functional
correctness, including every canonical entity where relevant and GS-01..GS-12.

### AT-09 — Deterministic acceptance oracle

For critical deterministic behavior, preserve machine-verifiable
`INPUT → EXPECTED → ACTUAL → PASS/FAIL` outcomes, including canonical state,
GRC relationships, RiskFinding/outcome semantics, and degradation state.

### AT-10 — Behavioral AI oracle

Evaluate groundedness, uncertainty, no unsupported causation, valid Evidence
references, permissions, no invented metrics, and no hidden AI authority—not
exact prose.

### AT-11 — Requirement-to-test traceability

Maintain Requirement → Acceptance Scenario → Test → Result traceability without
freezing physical test filenames. This traceability feeds Step 15.

### AT-12 — Build readiness gate

A vertical is READY FOR BUILD only when relevant Functional, Data, Graph,
Integration, UX/Interaction, Architecture, Security/NFR, AI where applicable,
and Acceptance contracts are sufficiently closed and no transversal
SPEC-BLOCKER forces implementation invention.

### AT-13 — Golden Dataset isolation

Golden data remains semantic/functional correctness data and is never inflated
into performance/capacity data.

### AT-14 — Deterministic seed reproducibility

The same specification, profile, seed, VECTOR version, and effective
configuration produce the same deterministic dataset and expected deterministic
results. LLM wording may vary.

### AT-15 — Scenario composition

Golden scenarios may share AreaDomain, Service, and context—especially for
J04—while each remains independently identifiable and diagnosable.

### AT-16 — Synthetic capacity profiles

Define SMALL, BASELINE, and STRESS as reproducible configurable local profiles.
SMALL supports development/CI/debugging confidence; BASELINE is local V1
acceptance; STRESS characterizes limits/degradation/recovery. `BASELINE !=
corporate expected load`; `STRESS != V1 pass/fail capacity commitment`.

### AT-17 — Performance acceptance

BASELINE preserves Step 10 local defaults: Technology Overview, Service
Intelligence, Risk Investigation, initial contextual graph, and incremental
graph expansion p95 ≤ 2.0 s; Evidence/detail p95 ≤ 1.5 s. Each result records
profile, seed, VECTOR version, effective configuration, environment, workload,
and measurement method. These are local V1 acceptance targets, not corporate
SLAs/SLOs.

### AT-18 — Mixed workload

BASELINE evaluates reproducible coexistence of interactive reads, ingestion,
deterministic intelligence, outbox processing, Neo4j projection, and local
concurrent users. Corporate concurrency remains TBD.

### AT-19 — Stress characterization

STRESS characterizes degradation point, bottlenecks, backlog, bounded behavior,
observability, recovery, and semantic integrity. Missing BASELINE latency under
STRESS is not automatically V1 acceptance failure; semantic corruption, silent
incompleteness, or unsafe behavior fails under every profile.

### AT-20 — Resilience recovery oracle

For controlled Neo4j or adapter failure, validate:

`failure → observable degradation → backlog/lag → recovery → backlog convergence → restored projection/service behavior`

Canonical truth, canonical mutations, Evidence/provenance integrity,
idempotency, non-duplication, deterministic graph convergence, and observable
failure/recovery must be preserved.

### AT-21 — Performance does not override correctness

| Result | Outcome |
|---|---|
| Fast and semantically wrong | FAIL |
| Fast but incomplete data presented as complete | FAIL |
| Correct but outside required BASELINE latency | PERFORMANCE FAIL |
| Correct, semantically complete, and within target | PASS |

## 4. Golden Dataset and deterministic oracle design

The Golden Dataset has a stable identifier, profile `GOLDEN`, a documented
seed, deterministic timestamps, synthetic source references, and explicit
expected canonical/GRC results. It includes all 17 canonical entities across
the scenario set, with synthetic AreaDomain/Service context reused only where
diagnosability remains explicit.

Each deterministic oracle records scenario ID, input/version/configuration,
expected canonical state, expected GRC paths, expected RiskFinding/outcome
state, actual result, and PASS/FAIL. AI outputs use the behavioral oracle in
section 8 rather than exact-text assertions.

## 5. Golden scenarios

### GS-01 — Healthy / Stable Service

- Purpose: prove available stable observations do not fabricate RiskFinding.
- Inputs/preconditions: synthetic Service with current stable observations,
  Evidence, and SourceReferences; no approved risk condition.
- Expected canonical/relationships: valid context and Evidence paths; no
  fabricated RiskFinding.
- UX/AI expectation: stable/limited context is distinguishable from a health
  guarantee; AI does not invent risk or hidden continuity.
- MUST: preserve provenance and applicable limitations.
- MUST NOT: infer risk, causation, or authority.
- Deterministic oracle: no RiskFinding from the defined stable input.
- Coverage: J01/J04; FR-X01, FR-X05, FR-X06.

### GS-02 — Persistent Reliability Risk

- Purpose: exercise J01 recurrence/degradation investigation.
- Inputs/preconditions: repeated incidents, degrading SLO/metrics, linked
  MonitoringEvents, Evidence, and Service context.
- Expected canonical/relationships: Evidence-backed persistent RiskFinding
  concerning Service, with normative GRC paths.
- UX/AI expectation: explainable attention and Evidence drill-down; AI grounds
  explanation in provided context.
- MUST: preserve recurrence, time, provenance, and uncertainty.
- MUST NOT: declare root cause.
- Deterministic oracle: expected RiskFinding and Evidence/GRC path exist.
- Coverage: J01; R01–R06, C02/C06/C07/C10.

### GS-03 — Change-Associated Degradation

- Purpose: exercise J02 temporal/contextual association.
- Inputs/preconditions: degradation window with Change/Deployment near the
  Service/CI and supporting/limiting Evidence.
- Expected canonical/relationships: explicit association/correlation context.
- UX/AI expectation: association, confidence, and limits visible.
- MUST: preserve `Correlation != Causation`.
- MUST NOT: call Change/Deployment the proven cause.
- Deterministic oracle: correlation relationship/state is present only when
  evidence supports it; causal claim is absent.
- Coverage: J02; R04, C03/C04/C07.

### GS-04 — Structural Improvement

- Purpose: exercise J03 with sufficient before/after Evidence.
- Inputs/preconditions: completed ImprovementAction, comparable pre/post
  operational Evidence, and valid Commitment/RiskFinding chain.
- Expected canonical/relationships: OutcomeVerification `IMPROVED` only with
  sufficient comparable Evidence.
- UX/AI expectation: execution remains distinct from verified outcome.
- MUST: trace Evidence through outcome verification.
- MUST NOT: treat completion alone as improvement.
- Deterministic oracle: `IMPROVED` requires declared sufficient evidence set.
- Coverage: J03; E02/E03, C07/C09/C10.

### GS-05 — Persistent After Action

- Purpose: prove completed action can yield `PERSISTENT`.
- Inputs/preconditions: completed ImprovementAction with continued degradation
  and post-action Evidence.
- Expected canonical/relationships: OutcomeVerification `PERSISTENT` with
  Evidence-backed path.
- UX/AI expectation: no falsely successful action narrative.
- MUST: separate execution and outcome.
- MUST NOT: label action as improvement.
- Deterministic oracle: continued condition produces `PERSISTENT`.
- Coverage: J03/J04; E03, C09/C10.

### GS-06 — Action Not Yet Verifiable

- Purpose: preserve insufficient/not-yet-verifiable outcome semantics.
- Inputs/preconditions: completed action but insufficient observation/Evidence.
- Expected canonical/relationships: action context exists; OutcomeVerification
  remains insufficient/not yet verifiable.
- UX/AI expectation: limitation is explicit.
- MUST: retain available action/Evidence provenance.
- MUST NOT: emit `IMPROVED` or fabricate continuity.
- Deterministic oracle: no verified improvement from insufficient evidence.
- Coverage: J03; FR-X06/FR-X08/FR-X10.

### GS-07 — Missing Telemetry

- Purpose: prove missing/stale telemetry is not healthy.
- Inputs/preconditions: unavailable or stale telemetry with defined freshness
  limitation and available partial context.
- Expected canonical/relationships: SourceReference/provenance and missing
  context retained.
- UX/AI expectation: stale/partial/insufficient state is visible.
- MUST: permit safe partial investigation.
- MUST NOT: represent Service as healthy due to absence.
- Deterministic oracle: missing/stale state and limitation are present.
- Coverage: J01/J04; FR-X06/FR-X10, UXI-11/12/15.

### GS-08 — Conflicting Claims

- Purpose: prove competing sources remain inspectable.
- Inputs/preconditions: conflicting claims, timestamps, provenance, and
  applicable authority context.
- Expected canonical/relationships: competing Evidence/SourceReferences remain
  represented.
- UX/AI expectation: conflict and limits visible; AI does not fabricate consensus.
- MUST: preserve conflict provenance and authority distinction.
- MUST NOT: silently select a winner without applicable rule.
- Deterministic oracle: both claims and conflict marker remain.
- Coverage: J01/J04; FR-X05/FR-X07, UXI-13.

### GS-09 — Inferred Identity

- Purpose: protect `INFERRED` identity semantics.
- Inputs/preconditions: mapping evidence supports inferred but not confirmed
  identity.
- Expected canonical/relationships: identity state `INFERRED` with method,
  confidence, and Evidence/provenance.
- UX/AI expectation: visibly inferred and traceable.
- MUST: preserve Identity != Correlation.
- MUST NOT: promote to `CONFIRMED`.
- Deterministic oracle: identity state remains `INFERRED`.
- Coverage: J02; FR-X03, UXI-14.

### GS-10 — Unresolved Identity

- Purpose: protect unresolved source context.
- Inputs/preconditions: insufficient mapping Evidence for an external identity.
- Expected canonical/relationships: source context retained with `UNRESOLVED`.
- UX/AI expectation: explicit limit without blocking unrelated context.
- MUST: retain SourceReference/provenance.
- MUST NOT: create confirmed Service relationship.
- Deterministic oracle: state remains `UNRESOLVED` and no unsupported mapping.
- Coverage: J02/J04; FR-X03/FR-X06, UXI-14.

### GS-11 — Partial Integration / Stale Projection

- Purpose: demonstrate graceful partial intelligence and visible degradation.
- Inputs/preconditions: controlled adapter limitation or Neo4j projection lag/
  outage while canonical state remains available.
- Expected canonical/relationships: canonical state intact; graph/read status
  pending, stale, partial, or unavailable as applicable.
- UX/AI expectation: unaffected investigation continues; graph/AI limitations
  are explicit.
- MUST: expose freshness/coverage/degradation.
- MUST NOT: treat graph availability as canonical truth availability.
- Deterministic oracle: canonical mutations preserved; lag/backlog/status shown.
- Coverage: J01–J04; NFR-04/05/06, RES-01/04, UXI-11/12.

### GS-12 — Leadership Decision Journey

- Purpose: exercise J04 end-to-end with context continuity.
- Inputs/preconditions: AreaDomain with Service, RiskFinding, Evidence,
  Commitment, ImprovementAction, OutcomeVerification, and limitations.
- Expected canonical/relationships: approved drill-down chain and Service
  correlation anchor remain available.
- UX/AI expectation: Technology Overview → Area/Domain → Service → Risk → Why
  → Evidence → Action → Outcome with retained context.
- MUST: preserve human-accountable decision context.
- MUST NOT: introduce individual ranking or unsupported causal claims.
- Deterministic oracle: expected path, context, and limitations are available.
- Coverage: J04; E01–E03, C01–C10, UXI-01..UXI-15.

## 6. Synthetic capacity profiles

The following are configurable local lab defaults, selected to retain coherent
ratios and make full-history, unbounded-query, projector, and mixed-read-path
defects observable. They are not corporate facts or commitments.

| Dimension | SMALL | BASELINE | STRESS |
|---|---:|---:|---:|
| AreaDomains | 3 | 20 | 80 |
| Services | 25 | 250 | 1,000 |
| ConfigurationItems | 100 | 2,000 | 10,000 |
| MonitoringEvents | 1,000 | 50,000 | 250,000 |
| Incidents | 100 | 5,000 | 25,000 |
| Problems | 20 | 800 | 4,000 |
| Changes / Deployments | 50 / 50 | 2,500 / 2,500 | 12,500 / 12,500 |
| SLOs / SLOObservations | 25 / 1,000 | 250 / 100,000 | 1,000 / 500,000 |
| MetricObservations | 1,000 | 100,000 | 500,000 |
| Evidence | 500 | 25,000 | 125,000 |
| RiskFindings | 25 | 1,500 | 7,500 |
| Commitments / Actions / Verifications | 15 / 15 / 10 | 800 / 800 / 600 | 4,000 / 4,000 / 3,000 |
| History window | configurable short | configurable representative | configurable extended |
| Graph density | bounded sparse | bounded representative | bounded high/characterization |
| Local concurrent users | 1 | configurable default 10 | configurable default 25 |

Generation requires explicit profile, seed, schema/specification version,
VECTOR version, effective configuration, and recorded generator version.
Values may be overridden through Step 10 configuration governance. Overrides
must retain provenance and must not make semantic invariants configurable.

## 7. Test strategy and pyramid

| Test type | Primary purpose | Automation / review |
|---|---|---|
| Unit / semantic-domain | Deterministic calculations, invariants, outcome semantics | Automatable |
| Contract / adapter | Provider substitutability, read-only boundaries, ARIA/SRE semantics | Automatable where feasible |
| Repository/data | Canonical persistence, provenance, Source Authority, seed reproducibility | Automatable |
| Graph projection/query | GRC direction, bounded paths, eventual consistency, rebuild | Automatable |
| Integration / API-BFF | Experience projections, authorization propagation, error/degradation | Automatable |
| UX/component | Context continuity, difficult states, semantic distinction | Automatable plus human UX review |
| Journey/E2E | J01–J04 and Golden scenarios | Automatable where feasible plus acceptance review |
| Security negative | Denied access, secret exposure, AI permission inheritance | Automatable |
| Resilience/fault | Adapter/Neo4j failure, backlog, retry, recovery | Automatable/controlled environment |
| Performance | BASELINE targets, mixed workload, metadata capture | Automatable measurement |
| AI behavioral evaluation | Grounding/safety/invariant preservation | Evaluator plus human review where needed |

Tool/framework selection remains a Step 13 implementation-plan decision.

## 8. AI behavioral oracle and evaluation

AI acceptance is invariant-based. For AI-12..AI-17 each output is evaluated for
grounding in authorized context, valid Evidence references, uncertainty/conflict
preservation, no unsupported causation, no invented metrics, no hidden AI
authority, permission respect, explicit limitations, recommendation without
execution, and clear AI-generated provenance.

The 18 Step 11 evaluation scenarios are incorporated as the required minimum:
grounded explanation; Evidence-summary traceability; hypothesis labelling; no
unsupported causation; recommendation without execution; outcome explanation;
assistant authorization; conflicts; missing context; INFERRED/UNRESOLVED
identity; insufficient Evidence; provider unavailable; malformed structured
output; permission-restricted Evidence; provider/model configuration;
consumption observability; deterministic truth unchanged by provider/model
switch; and absence of hidden AI authority.

## 9. Requirement-to-acceptance traceability

| Requirement set | Acceptance scenarios | Test evidence |
|---|---|---|
| J01 | GS-02, GS-07, GS-08, GS-11 | E2E path, semantic oracle, UX state evidence |
| J02 | GS-03, GS-09, GS-10 | Association/identity negative tests, GRC oracle |
| J03 | GS-04, GS-05, GS-06 | Outcome deterministic oracle and Evidence path |
| J04 | GS-01, GS-02, GS-08, GS-12 | Context-continuity and drill-down acceptance |
| 18 MUST capabilities | GS-01..GS-12 and AT-02/03/04/11 | Capability-to-scenario matrix/result recorded for Step 15 |
| Step 10 NFR/SEC/OBS/RES | AT-05/06/16..21, GS-11 | Performance, fault, security, observability results |
| Step 11 AI-01..AI-26 | AT-07/10, section 8 | Behavioral evaluation evidence |

All 18 MUST capabilities remain acceptance-traceable; Step 15 will materialize
the detailed Requirement → Acceptance Scenario → Test → Result inventory.

| MUST capability | Acceptance scenario(s) | Primary oracle/evidence |
|---|---|---|
| R01 Service Reliability Intelligence | GS-02, GS-07, GS-11 | Persistent-risk and partial-data semantic oracle |
| R02 Incident Intelligence | GS-02 | Incident/Evidence recurrence path |
| R03 Problem & Recurrence Intelligence | GS-02, GS-07 | Recurrence path and missing-Problem negative case |
| R04 Change Risk Intelligence | GS-03, GS-09, GS-10 | Association-without-causation oracle |
| R05 Event Intelligence | GS-02, GS-07 | MonitoringEvent/provenance/freshness evidence |
| R06 SLO / SLI Intelligence | GS-02, GS-04, GS-05 | Observation trend and before/after evidence |
| E01 Area / Domain Performance Intelligence | GS-12 | AreaDomain drill-down/context-continuity evidence |
| E02 Commitment Intelligence | GS-04, GS-05, GS-06 | RiskFinding-to-Commitment/action chain |
| E03 Improvement & Outcome Intelligence | GS-04, GS-05, GS-06 | IMPROVED/PERSISTENT/not-verifiable oracle |
| C01 Canonical Technology Context | GS-01, GS-02, GS-12 | AreaDomain/Service/CI context paths |
| C02 Evidence, Provenance & Source Authority | GS-01, GS-02, GS-08 | Evidence/SourceReference and authority evidence |
| C03 Cross-Source Correlation | GS-03, GS-08, GS-09 | Correlation, conflict, identity negative tests |
| C04 Relationship / Service Graph | GS-02, GS-03, GS-11, GS-12 | Bounded GRC graph/projection/recovery evidence |
| C05 Metric & KPI Intelligence | GS-02, GS-04, GS-05 | Deterministic metric/SLO oracle, never AI calculation |
| C06 Risk & Finding Intelligence | GS-02, GS-07 | Evidence-backed finding/insufficient-evidence oracle |
| C07 Explainability | GS-02, GS-03, GS-04, GS-08 | Evidence drill-down and AI behavioral oracle |
| C09 Trend & Historical Analysis | GS-02, GS-04, GS-05 | Historical/before-after deterministic oracle |
| C10 Decision Intelligence | GS-04, GS-05, GS-06, GS-12 | Attention/action/outcome decision context |

## 10. NFR, security, and resilience acceptance

BASELINE performance records the section 3 AT-17 local thresholds and required
metadata. Mixed workload uses the profile's configurable users, deterministic
ingestion, intelligence, outbox, and projection activity. Projection lag and
graph freshness must be visible.

Security negative paths prove authentication/authorization failures fail
securely; adapter/provider credentials remain unavailable to SPA; least
privilege prevents cross-source privilege transfer; and AI cannot access or
expose permission-restricted Evidence. Logs/results must not expose secrets.

Fault acceptance controls Neo4j and adapter interruption. It validates the
AT-20 recovery oracle and confirms AI failure alone does not make deterministic
VECTOR unavailable. No performance optimization can hide incomplete results.

## 11. Step 12 Quality Gate

Formal external Quality Gate result: PASS. The gate verified that AT-01..AT-21
and GS-01..GS-12 are semantically
specified; J01–J04 E2E coverage exists; all 18 MUST capabilities are
acceptance-traceable; semantic invariants have negative tests; relevant 17
entities and 18 GRC predicates are protected; 62/62 and UXI-01..UXI-15 remain
preserved; Step 10 and Step 11 have test/evaluation strategies; all 18 AI
handoff scenarios are incorporated; Golden data is deterministic; profiles are
explicitly local; BASELINE pass/fail and STRESS characterization are distinct;
recovery has an oracle; difficult data states and security negative paths are
tested; Step 15 traceability can be fed; implementation-critical decisions are
not left to Codex; and SPEC-BLOCKERS is 0.

Step 12 is CLOSED after the formal external Quality Gate PASS. Steps 0–12
remain CLOSED. Step 13 is NEXT. SDD status remains NOT_READY_FOR_IMPLEMENTATION;
no implementation is authorized.

## 12. Step 13 implementation-plan handoff

Candidate incremental verticals are: (1) canonical/Evidence ingestion with
Golden Dataset and semantic oracle; (2) Service/RiskFinding/Evidence journey
with BFF/UX projection; (3) Commitment/action/outcome verification; (4) graph
projection, bounded graph, lag/recovery; (5) integration adapter contracts;
(6) security/configuration/observability; and (7) AI provider abstraction and
bounded assistant after deterministic paths are available.

Step 13 must sequence dependencies on a deterministic dataset/generator,
contract-compatible adapters, canonical persistence, graph projection, BFF/UX
projection, acceptance automation, and AI provider abstraction. Physical test
tooling, schemas, provider/model, timeouts/retries, exact load environment,
and corporate TBDs remain decisions to sequence; corporate TBDs do not block
local V1 where adapters/contracts provide safe local evidence.

## 13. TBD and debt register

| Item | Classification | Status |
|---|---|---|
| Corporate capacity/concurrency/SLA | Corporate TBD | Does not constrain local profiles |
| Exact lab hardware/environment | Implementation/acceptance TBD | Record with each run |
| Physical generator/storage/tooling | Step 13 implementation decision | Not selected here |
| Exact graph/query limits and workload tuning | Configurable local policy | Validate via BASELINE/STRESS |
| ARIA completeness and SRE provider | Corporate/integration TBD | Preserve Step 7/10 status |
| Corporate AI provider/model/budgets | Corporate TBD | Preserve Step 11 status |
| Detailed traceability inventory | Step 15 deliverable | Strategy defined here |

These items do not reopen closed decisions and are not automatically Evolution
Backlog entries.
