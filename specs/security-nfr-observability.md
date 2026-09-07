# VECTOR — Security, NFR, Observability, Resilience & Configuration Specification

## 1. Status and scope

- Status: CLOSED / EXTERNAL QUALITY GATE PASSED
- Step: Step 10 — Security, NFR, Observability, Resilience & Configuration
- External Quality Gate: PASS
- SDD status: NOT_READY_FOR_IMPLEMENTATION
- SPEC-BLOCKERS: 0

This specification defines measurable local V1 acceptance defaults, security
boundaries, configuration governance, self-observability, resilience, and
integration boundaries. It does not implement application code, infrastructure,
production configuration, vendor integrations, schemas, endpoints, or a
corporate target that lacks authoritative evidence.

Steps 0–9.5 remain CLOSED. Step 10 is MATERIALIZED / PRE-AUDIT. Step 11 has
not started.

## 2. Closed baseline dependencies

The following remain unchanged and normative: exactly 17 canonical V1 entities,
18 GRC relationships, 62/62 Capability × Journey coverage, J01–J04, Service as
the primary technology correlation anchor, `Identity != Correlation`,
`Correlation != Causation`, Evidence First, Source Authority and provenance,
graceful partial intelligence, visible conflicts, and the chain
`RiskFinding → Commitment → ImprovementAction → OutcomeVerification`.

The V1 architecture remains SPA + BFF, React + TypeScript, Java 21 + Spring
Boot, modular monolith, SQLite as local canonical persistence adapter, and
Neo4j as deterministic graph projection/read model. The synchronization model
remains SQLite → transactional lightweight outbox → in-process asynchronous
projector → Neo4j with eventual consistency, retry, idempotency, and
deterministic rebuild. No external broker is required for V1.

External integrations are read-only by default. The SPA never accesses
external sources, SQLite, Neo4j, or Cypher directly. BFF is experience-facing,
not the Integration domain; adapters remain behind ports/contracts.

Step 9.5 UXI-01..UXI-15, Technology Overview, Service Intelligence, Risk
Investigation, context continuity, bounded graph exploration, progressive graph
disclosure, and the attention → context → explanation → evidence/action/outcome
model remain preserved. No Person, Employee, ProductivityScore, or individual
ranking semantics are introduced.

## 3. Local V1 versus corporate targets

`CORPORATE TARGET = TBD until authoritative corporate evidence exists.` Local
V1 defaults below are measurable, configurable acceptance baselines. They are
not corporate SLAs, production capacity commitments, or hardcoded business
constants.

## 4. Non-functional requirements

### NFR-01 — Experience-oriented performance

The following configurable local V1 acceptance defaults apply to representative
synthetic data and workload:

| Experience | Local V1 default |
|---|---:|
| Technology Overview p95 | ≤ 2 s |
| Service Intelligence p95 | ≤ 2 s |
| Risk Investigation p95 | ≤ 2 s |
| Evidence/detail p95 | ≤ 1.5 s |
| Initial contextual graph p95 | ≤ 2 s |
| Incremental graph expansion p95 | ≤ 2 s |

Corporate p95/p99 targets remain TBD. The values are acceptance configuration,
not business logic.

### NFR-02 — Synthetic capacity profile

Step 12 must define reproducible synthetic capacity/load data exercising
Services, Incidents, MonitoringEvents, SLOObservations, Evidence,
MetricObservations, RiskFindings, and graph relationships. Exact volumes belong
to Step 12 acceptance/load design unless later evidence requires earlier
refinement. Synthetic volume is never represented as corporate expected load.

### NFR-03 — Concurrency

Corporate concurrency is TBD. Local V1 must use a reproducible concurrency
profile evaluating interactive reads while ingestion, deterministic
intelligence, graph projection, and concurrent-user activity operate together.

### NFR-04 — Projection lag

Graph projection lag is measurable. Pending, failed, and stale projections are
observable and exposed to UX freshness/staleness semantics. A projection beyond
the configured freshness/lag threshold is not represented as fully current.
Corporate threshold is TBD; local threshold is configurable and validated
against the V1 capacity profile.

### NFR-05 — Graceful degradation

Partial dependency failure does not automatically cause total VECTOR failure
when continuation is semantically safe. Neo4j unavailability does not destroy
canonical state and need not prevent non-graph intelligence queries.

### NFR-06 — No silent overload

Operationally meaningful states include healthy, degraded, stale, unavailable,
and processing backlog. Physical enum names remain implementation-level unless
later required. Bounded limits must not silently present incomplete data as
complete or correct intelligence.

### NFR-07 — Configurable operational thresholds

Operational values that may reasonably vary are policy/configuration rather than
hidden source-code constants. The governed set includes p95/p99 targets,
projection lag, freshness/staleness, graph expansion, timeouts, retry/backoff,
batch size, processing concurrency, ingestion capacity, backlog, analysis
windows, pagination/result limits, and health criteria.

Conceptual precedence is `Platform Default → Environment Override →
Service/Context Override` where semantically applicable. Code defines metric
meaning and invariant behavior; configuration/policy defines operational values.

### NFR-08 — Configuration provenance and auditability

Decision-relevant configuration is conceptually traceable by effective value,
origin (default/override), environment/context, and effective time/version when
applicable. No physical configuration schema is defined here.

### NFR-09 — Configurability classification

Every relevant candidate is classified as one of: A. Semantic Invariant, B.
Operational Policy, C. Domain Policy, D. Integration Configuration, or E.
Presentation Preference. Semantic invariants are never runtime configuration.

Non-configurable examples include correlation ≠ causation, Identity ≠
Correlation, Source Authority semantics, GRC predicate semantics, Service as
correlation anchor, the RiskFinding → Commitment → ImprovementAction →
OutcomeVerification chain, absence of evidence ≠ evidence of absence/health,
and external integrations read-only in V1.

### NFR-10 — Configuration Matrix

| Configuration item | Class | Configurable | Scope | Default concept/value | Override | Runtime change | Audit | V1 UI | Owner/governance |
|---|---|---|---|---|---|---|---|---|---|
| Local experience p95 defaults | B Operational Policy | Yes | Environment/experience | NFR-01 values | Platform → environment | TBD | Yes | TBD | TBD |
| Corporate p95/p99 targets | B | TBD | Corporate | TBD | TBD | TBD | Yes | TBD | TBD |
| Projection lag threshold | B | Yes | Environment/context | Local configurable threshold | Platform → environment → context | TBD | Yes | No/TBD | TBD |
| Freshness/staleness threshold | B | Yes | Source/context | TBD local validation | Same hierarchy | TBD | Yes | No/TBD | TBD |
| Graph expansion bounds | B | Yes | Experience/context | TBD; bounded | Same hierarchy | TBD | Yes | No/TBD | TBD |
| Request/adapter timeouts | B | Yes | Environment/provider | TBD | Platform → environment → provider | TBD | Yes | No | TBD |
| Retry count/backoff | B | Yes | Provider/workload | TBD | Same hierarchy | TBD | Yes | No | TBD |
| Outbox batch size | B | Yes | Worker/environment | TBD | Platform → environment | TBD | Yes | No | TBD |
| Worker concurrency | B | Yes | Worker/environment | TBD | Platform → environment | TBD | Yes | No | TBD |
| Ingestion throughput policy | B | Yes | Environment/source | TBD | Platform → environment → source | TBD | Yes | No | TBD |
| Processing backlog threshold | B | Yes | Worker/context | TBD | Same hierarchy | TBD | Yes | No | TBD |
| Analysis window | C Domain Policy | Yes | Journey/context | Existing approved semantics; value TBD | Context only where valid | TBD | Yes | TBD | TBD |
| Pagination/result limits | B | Yes | Experience/query | TBD; bounded | Platform → environment → context | TBD | No/TBD | No | TBD |
| Operational health criteria | B | Yes | Component | TBD | Platform → environment → component | TBD | Yes | No | TBD |
| Source endpoint/connection | D Integration Configuration | Yes | Provider/environment | TBD | Environment/provider | TBD | Yes | No | TBD |
| Source mapping/identity method | D | Yes/TBD | Provider/context | TBD unless approved | Governed override | TBD | Yes | No | TBD |
| Source coverage/completeness flag | D | Yes/TBD | Source | ARIA completeness TBD | Governed source override | TBD | Yes | No | TBD |
| Presentation density/filter preference | E Presentation Preference | Yes | User/session | TBD | User preference | TBD | No/TBD | TBD | User/product governance TBD |
| Correlation ≠ causation | A Semantic Invariant | No | All | Closed invariant | None | No | N/A | No | Source of Truth |
| Source Authority semantics | A Semantic Invariant | No | All | Closed invariant | None | No | N/A | No | Source of Truth |

No V1 admin console is required by this matrix. A future Policy & Threshold
Management UI remains later evaluation/debt, not silently added scope.

## 5. Security model

### SEC-01 — Authentication boundary

Local V1 requires an identity/authentication abstraction. Exact corporate IAM/SSO
provider is TBD. Domain/application logic remains independent of an identity
vendor.

### SEC-02 — Authorization / RBAC

The conceptual access distinction is Viewer, Analyst/Operator, and
Administrator. Names may be refined later and do not assert corporate roles.
Investigative read access is not configuration/policy mutation access.

### SEC-03 — Least privilege

Adapters, backend, graph, and persistence receive only permissions required for
their responsibilities. Credentials from one source do not grant access to
another.

### SEC-04 — Secrets

Passwords, tokens, secrets, and credentials are never hardcoded, committed, or
exposed to the SPA. Local V1 uses secure externalized configuration. Exact
corporate secret-management technology is TBD.

### SEC-05 — Auditability

Relevant VECTOR-native mutations and governed configuration changes preserve,
where applicable, who, what, when, and previous/effective state or equivalent
traceability. Logs are not automatically canonical Evidence or Source
Authority.

### SEC-06 — Sensitive-data minimization

Ingest only data required by approved journeys/capabilities. No unnecessary
person, employee, or personal data enters V1.

### SEC-07 — Configuration governance

Operational Policy and Domain Policy values capable of materially changing
intelligence/results require appropriate authorization and auditability.
Presentation preferences need not have equivalent governance.

### SEC-08 — Fail secure

Authentication, authorization, and secret-management failure never degrades to
open access. Graceful Partial Intelligence applies to data/dependency
availability, not security bypass.

### SEC-09 — Security portability

The local architecture permits later corporate IAM, authorization, and secret
management without redesigning the canonical model or journeys.

AI boundary for Step 11: AI never expands user permissions. An AI capability
cannot read Evidence, invoke tools, or execute an operation that the acting
identity is not authorized to perform through VECTOR. Detailed AI behavior
remains Step 11.

## 6. VECTOR self-observability

### OBS-01 — VECTOR must be observable

Telemetry must distinguish issues across HTTP/API, ingestion, normalization,
canonical persistence, deterministic intelligence, outbox, graph projection,
and external adapters. OpenTelemetry is only a candidate portable option, not a
closed vendor/tool decision.

### OBS-02 — Component health

Health is diagnosable by BFF/API, canonical persistence, intelligence,
projection, Neo4j, and integration adapters. An aggregate health view must not
conceal material component degradation; one global UP/DOWN signal is
insufficient.

### OBS-03 — Operational metric families

Metric families, rather than vendor-specific names, include:

- Experience: latency, errors, request volume.
- Ingestion: received/accepted/rejected, processing duration.
- Intelligence: processing duration, failures, backlog.
- Graph projection: pending, failed, retries, lag, rebuild state.
- Integrations: availability, freshness, failure, last successful ingestion or
  synchronization where appropriate.
- Data/intelligence quality: coverage, conflict presence, unresolved/inferred
  identity where applicable.
- Runtime: errors, availability, and meaningful resource saturation.

### OBS-04 — Technical traceability

Relevant processing is diagnosable across source ingestion → normalization →
canonical persistence → deterministic intelligence → RiskFinding → graph
projection → BFF query. This does not require every step to be one distributed
trace; correlation/operation identifiers or equivalent traceability are
required.

### OBS-05 — Structured logging

Logs should be structured and context-rich. Passwords, tokens, secrets, and
unnecessary sensitive information are never logged. Operational logs do not
automatically become Evidence or Source Authority.

### OBS-06 — Background work visibility

Asynchronous work cannot fail silently. Outbox pending/backlog, projection lag,
projection failure, retries, and rebuild activity/status must be diagnosable and
must be capable of influencing UX freshness/staleness representation where
relevant.

## 7. Resilience and recovery

### RES-01 — Failure isolation

Failure of a non-essential dependency degrades only dependent functionality
where semantically safe. If Neo4j is unavailable, canonical persistence remains
available, non-graph intelligence may remain available, graph context is
unavailable/stale, and VECTOR never presents it as current.

### RES-02 — Recovery without semantic corruption

Recovery preserves idempotency, avoids semantic duplication, preserves
deterministic graph rebuild, allows ingestion/backlog recovery, and preserves
Evidence/provenance integrity. It never invents Evidence or RiskFindings to
hide missing periods.

### RES-03 — Backpressure and overload

Ingestion and background work use bounded processing/backpressure semantics.
Unbounded memory/work growth and background saturation must not silently destroy
interactive read behavior. Exact mechanisms and limits remain implementation/NFR
decisions.

### RES-04 — Observable degradation

The conceptual propagation is:

`Technical condition → Operational health → Data freshness/coverage →
Intelligence confidence/context → UX representation`

Technical degradation is not automatically a wrong business/reliability
conclusion.

### RES-05 — Recovery targets

Corporate RTO, RPO, and availability objectives are TBD. Local V1 acceptance
may later test Neo4j outage/restart, adapter outage, projection backlog,
projector recovery, and deterministic graph rebuild. Exact scenarios belong
primarily to Step 12.

## 8. Observability integration boundaries

### OBS-INT-01 — Backend-mediated integration

Approved observability sources communicate through backend Integration Ports and
provider-specific adapters. The SPA never accesses Dynatrace, Elastic,
ServiceNow, Neo4j, vendor credentials, vendor query languages, or vendor models
directly.

### OBS-INT-02 — Provider substitutability

Application capabilities depend on semantic VECTOR contracts, not providers.
Conceptual examples include `MonitoringEventProvider`,
`SreAssessmentProvider`, and `TelemetryProvider`; these are not mandatory final
Java interface names.

### OBS-INT-03 — BFF is not the Integration domain

BFF owns experience-oriented use cases/projections. The Integration module owns
source communication, vendor DTO isolation, normalization, and mapping, even
when all execute inside the same modular monolith.

## 9. Observability source routing and provenance

### 9.1 Operational events

The expected route is:

`Observability tools → ARIA Events / ServiceNow → VECTOR ARIA Events adapter → MonitoringEvent`

ARIA Events is the expected aggregation boundary for events already centralized
there. Duplicate ingestion of the same MonitoringEvent from ARIA and its origin
is avoided. The completeness of relevant corporate events in ARIA is a working
corporate assumption to verify, not an absolute fact; coverage remains TBD.

### 9.2 SRE Skill

SRE Skill v2.1 results do not pass through ARIA Events. The expected route is:

`SRE Skill → JSON assessment/result → Dynatrace/Grail OR Elastic → VECTOR SRE Skill adapter → VECTOR Evidence/intelligence`

Dynatrace/Grail versus Elastic remains TBD. The contract is provider
independent and no winner is invented.

### 9.3 Other observability data

Purpose-specific direct integrations to Dynatrace, Elastic, New Relic, or
another source may exist for metrics, traces, SLO-related data, topology,
assessment detail, or technical Evidence when ARIA does not provide it
adequately. Availability and requirement of each data type remain TBD until
confirmed. Each integration has explicit semantic purpose and avoids duplicate
ownership/ingestion.

### 9.4 Provenance safeguard

When ARIA forwards an event originally generated by another tool, original
source/provenance is preserved where available. Ingestion through ARIA does not
make VECTOR the Source Authority and does not silently transfer authority.

## 10. TBD and debt register

| Item | Classification | Status/owner of next decision |
|---|---|---|
| Corporate load, concurrency, p95/p99, capacity | Corporate TBD | Authoritative corporate evidence required |
| Corporate RTO/RPO/availability | Corporate TBD | Authoritative corporate evidence required |
| Corporate IAM/SSO | Corporate TBD | Authoritative security evidence required |
| Corporate secret manager | Corporate TBD | Authoritative security evidence required |
| Dynatrace/Grail versus Elastic for SRE Skill | Corporate/integration TBD | Source decision required; no winner selected |
| ARIA event completeness/coverage | Corporate/integration TBD | Verification debt |
| Physical timeouts, retries, backoff, batches, concurrency | Implementation/NFR parameter | Later implementation planning/validation |
| Physical outbox/schema/index settings | Implementation decision | Later implementation planning |
| Physical graph query/expansion limits | Implementation/NFR parameter | Step 10/implementation validation |
| Projection lag/freshness/monitoring thresholds | Local policy + corporate TBD | Validate local; corporate evidence later |
| Synthetic capacity/load volumes | Step 12 acceptance/load decision | Define reproducible scenarios in Step 12 |
| Final observability vendor/tool and names | Implementation decision | Preserve metric families first |
| Policy & Threshold Management UI | Future evaluation/debt | Not current V1 scope |

These TBDs do not reopen closed steps and are not silently converted into
Evolution Backlog items.

## 11. Step 12 acceptance implications

Step 12 should define reproducible synthetic datasets and scenarios for the
three local experience targets, concurrent background/user workload, projection
lag and stale UX, Neo4j outage/restart, adapter outage, outbox/projector
backlog, retry/idempotency, deterministic rebuild, conflicting/partial source
context, authorization failure, secret-management failure, and sensitive-data
redaction. Synthetic results must remain local acceptance evidence, not
corporate commitments.

## 12. Step 10 Quality Gate

Formal external Quality Gate result: PASS. The gate verified semantically:

- NFR-01..NFR-10, including a substantive Configuration Matrix;
- SEC-01..SEC-09 and the AI permission boundary input to Step 11;
- OBS-01..OBS-06 and OBS-INT-01..OBS-INT-03;
- RES-01..RES-05;
- local defaults distinguished from corporate TBDs;
- ARIA operational-event routing and SRE Skill direct routing;
- provider-independent contracts, provenance, no duplicate MonitoringEvent
  ownership, and BFF/Integration separation;
- no hardcoded operational policies, configurable invariants, security bypass,
  silent async failure, stale-as-current graph, or unbounded processing;
- Steps 0–9.5, UXI-01..UXI-15, J01–J04, 17 entities, 18 GRC, and 62/62 remain
  unchanged;
- OQ-009 remains OPEN/non-blocking and OQ-016 remains RESOLVED;
- no implementation or infrastructure has been introduced;
- SDD-010 includes tracked and untracked files, with `work-prep/` unmodified
  and untracked.

Step 10 is CLOSED after the formal external Quality Gate PASS with
SPEC-BLOCKERS: 0. Steps 0–10 remain CLOSED. Step 11 is NEXT. SDD status
remains NOT_READY_FOR_IMPLEMENTATION; no implementation is authorized.
