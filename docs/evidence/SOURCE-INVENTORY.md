# VECTOR — Source & Integration Inventory

> Artifact type: Source Inventory
> Status: ACTIVE
> Purpose: Register known and candidate information sources required by VECTOR and preserve their authority, maturity and unresolved integration details.

Historical discovery note: expected concepts and candidate chains in this
inventory are evidence from discovery, not canonical V1 entities or normative
relationships unless adopted by the closed specifications.

---

## Source Principles

A Source is not automatically authoritative for every datum it provides.

For every source, VECTOR must distinguish:

- source identity;
- business/technical role;
- authoritative scope;
- integration maturity;
- known objects;
- known identifiers;
- limitations;
- unresolved mappings;
- provenance requirements.

Unknown corporate implementation details remain TBD.

---

## Integration Maturity Levels

- L0 — Contract Defined
- L1 — Mock
- L2 — Sandbox
- L3 — Corporate Read-only
- L4 — Corporate Verified
- L5 — Production

---

## SRC-001 — ServiceNow ARIA

Status:

CONFIRMED TARGET SOURCE

Role:

Centralized operational event source receiving events from monitoring tools.

Expected VECTOR concepts:

- MonitoringEvent
- Service reference
- Configuration Item reference
- event severity
- event timestamp
- source monitoring system
- event lifecycle

Authoritative scope:

Operational event information represented by ARIA.

Corporate implementation details:

TBD

Known unresolved items:

- exact tables;
- custom fields;
- correlation identifiers;
- APIs;
- authentication;
- ACLs;
- event normalization rules;
- service/CI mappings.

Lab strategy:

Mock / synthetic event adapter.

Initial maturity:

L1 — Mock

Corporate target maturity:

L3+ after authorized integration.

---

## SRC-002 — ServiceNow ITSM

Status:

CONFIRMED TARGET SOURCE

Role:

Authoritative ITSM source for operational management records.

Known information:

- Incident
- Problem
- Change
- CMDB / Configuration Item

Expected VECTOR concepts:

- Incident
- Problem
- Change
- Service
- ConfigurationItem
- Evidence

Authoritative scope:

ServiceNow-managed ITSM records and CMDB information, subject to corporate implementation validation.

Corporate implementation details:

TBD

Known unresolved items:

- custom tables;
- custom fields;
- state mappings;
- priority/severity mappings;
- service relationships;
- CI relationships;
- assignment/domain mappings;
- APIs;
- authentication;
- ACLs;
- permissions.

Lab strategy:

ServiceNow Personal Developer Instance and/or Mock adapter.

Initial maturity:

L1 / L2

Corporate target maturity:

L3+ after authorized integration.

---

## SRC-003 — Dynatrace

Status:

CONFIRMED TARGET SOURCE

Role:

Primary observability and reliability information source.

Expected information:

- telemetry;
- service health;
- metrics;
- traces;
- logs;
- SLO-related evidence;
- reliability signals;
- Grail-based technical evidence where available.

Expected VECTOR concepts:

- ServiceTelemetry
- SLO
- ReliabilitySignal
- Evidence
- SREAssessment
- SREFinding

Authoritative scope:

Dynatrace-native observability information and data stored in authorized Dynatrace/Grail datasets.

Corporate implementation details:

TBD

Known unresolved items:

- exact Grail datasets;
- DQL contracts;
- service identifiers;
- entity mappings;
- API access;
- permissions;
- retention;
- SLO mapping;
- SRE Skill ingestion path.

Lab strategy:

Mock observability adapter and JSON fixtures.

Initial maturity:

L1 — Mock

Corporate target maturity:

L3+ after authorized integration.

---

## SRC-004 — New Relic

Status:

CONFIRMED TARGET SOURCE

Role:

Complementary observability source.

Expected VECTOR concepts:

- ServiceTelemetry
- ReliabilitySignal
- Evidence

Authoritative scope:

New Relic-native observability information for services monitored by that platform.

Exact VECTOR use:

TBD

Known unresolved items:

- covered services;
- NRQL contracts;
- entity identifiers;
- API access;
- permissions;
- overlap with Dynatrace;
- source precedence;
- correlation rules.

Lab strategy:

Mock adapter.

Initial maturity:

L1 — Mock

Corporate target maturity:

TBD

---

## SRC-005 — SRE Skill v2.1

Status:

VERIFIED TECHNICAL SOURCE

Logical source name:

SREAssessmentSource

Producer:

CI / GitHub Actions

Expected VECTOR concepts:

- Repository
- PullRequest
- SREAssessment
- SREFinding
- Evidence

Known identifiers:

- repository
- pr_number
- base_sha
- head_sha
- fingerprint

Authoritative scope:

Technical controls explicitly evaluated by the Skill.

Known evaluated pillars:

- observability
- resilience
- automation
- security

Known authority semantics:

incremental
→ authoritative = false

full_rescan_periodico
→ authoritative = true

full_rescan_cold_start
→ authoritative = true

Incident Management authority:

evaluated_by = servicenow

Expected corporate storage:

Dynatrace / Grail JSON

Exact corporate ingestion path:

TBD

Lab strategy:

Versioned JSON fixtures.

Initial maturity:

L1 — Mock / Fixture

Corporate target maturity:

L3+ after Grail integration is validated.

---

## SRC-006 — Source Code Management

Status:

PARTIALLY DEFINED

Role:

Engineering lifecycle source.

Expected information:

- repositories;
- pull requests;
- commits;
- branches;
- merge information;
- engineering metadata.

Expected VECTOR concepts:

- Repository
- PullRequest
- Commit
- Branch
- Deployment linkage
- Evidence

Corporate authoritative platform:

TBD

Lab strategy:

Personal GitHub repository and/or synthetic fixtures.

Known unresolved items:

- corporate SCM platform;
- APIs;
- repository identifiers;
- branch policies;
- PR metadata availability;
- deployment correlation identifiers;
- permissions.

Initial maturity:

L1 / L2

Corporate target maturity:

TBD

---

## SRC-007 — Project / Initiative Portfolio

Status:

TBD

Role:

Potential source for projects, initiatives and improvement work.

Expected VECTOR concepts:

- Project
- Initiative
- ImprovementItem
- Commitment
- Outcome

Corporate authoritative source:

TBD

Important rule:

Do not assume Jira, ServiceNow SPM, Azure DevOps or another portfolio tool without evidence.

Lab strategy:

MockProjectPortfolioAdapter with synthetic data.

Initial maturity:

L1 — Mock

Corporate target maturity:

TBD

---

## SRC-008 — DRP / Resilience Source

Status:

TBD

Role:

Potential source for resilience, continuity and DRP information.

Expected VECTOR concepts:

- RecoveryPlan
- RecoveryCapability
- ResilienceAssessment
- Risk
- Evidence

Corporate authoritative source:

TBD

Lab strategy:

Synthetic fixtures / Mock adapter.

Initial maturity:

L1 — Mock

Corporate target maturity:

TBD

---

## SRC-009 — Commitments Source

Status:

TBD

Role:

Potential source for commitments, actions and execution follow-up.

Expected VECTOR concepts:

- Commitment
- Action
- OwnerContext
- DueDate
- Status
- Evidence

System ownership:

TBD

Possible models:

- VECTOR-owned;
- integrated from an external corporate source.

No final decision has been made.

Lab strategy:

VECTOR-local synthetic data or Mock adapter.

Initial maturity:

L1

Corporate target maturity:

TBD

---

## SRC-010 — Identity Provider

Status:

TBD

Role:

Authentication and identity context for corporate usage.

Expected VECTOR concepts:

- UserIdentity
- Role
- Permission
- OrganizationalContext

Corporate IAM:

TBD

Important rule:

No corporate IAM implementation may be assumed during Lab specification.

Lab strategy:

Local development identity.

Initial maturity:

L1

Corporate target maturity:

TBD

---

## Cross-Source Correlation Requirement

VECTOR is expected to correlate information across multiple sources.

Candidate conceptual chain:

Repository
→ PullRequest
→ SREAssessment
→ SREFinding
→ Deployment
→ Change
→ Service
→ MonitoringEvent
→ Incident
→ Problem
→ SLO Impact

The exact correlation model is not yet specified.

Correlation identifiers that are unavailable or unknown must remain TBD.

---

## Source Authority Rule

When two sources contain related or overlapping information:

1. Preserve provenance from both sources.
2. Identify the authoritative source for the specific fact.
3. Do not silently overwrite one source with another.
4. Record conflicts when necessary.
5. Define precedence only through an explicit specification or decision.

---

## Environment Portability Rule

Every external corporate dependency must be isolated through an Integration Contract and Adapter.

Target pattern:

Canonical Model
→ Integration Contract
→ Lab Adapter
→ Corporate Adapter

A Lab implementation must not embed corporate vendor fields directly into the VECTOR domain model.
