# VECTOR — Open Questions & TBD Register

> Artifact type: Open Questions Register
> Status: ACTIVE
> Purpose: Preserve unresolved questions and prevent AI agents from silently inventing missing information.

---

## Open Question States

- OPEN
- BLOCKING
- DEFERRED
- RESOLVED
- SUPERSEDED

---

## Classification Rule

An unresolved item is BLOCKING only when:

its absence would force an implementer to invent a relevant product, architecture, data, security or integration decision.

If implementation can proceed safely without resolving it, the item remains:

OPEN

or

DEFERRED

until the appropriate specification stage.

---

## OQ-001 — Final Product Boundary

Status:

OPEN

Question:

Is the current Product Boundary hypothesis valid as one VECTOR product composed of:

- Reliability Intelligence
- Execution Intelligence
- Intelligence Core

Resolution stage:

Product Boundary / Domain Modeling

Current impact:

Not blocking Discovery.

---

## OQ-002 — Final V1 Scope

Status:

OPEN

Question:

Which capabilities belong to the first functional VECTOR V1?

Resolution stage:

Scope & Capabilities

Current impact:

Will become blocking before V1 implementation planning.

---

## OQ-003 — Final Technology Stack

Status:

OPEN

Question:

Which implementation technologies will be used for:

- frontend;
- backend;
- persistence;
- graph capability;
- integration layer;
- testing;
- observability;
- local deployment.

Resolution stage:

Architecture Specification

Current impact:

Not blocking Product Definition or Domain Modeling.

---

## OQ-004 — Graph Technology

Status:

OPEN

Question:

Does VECTOR require a graph database, graph projection, relational graph model or another approach?

Resolution criteria:

The technology must be justified by required relationships, traversals, queries, scale and operational constraints.

Resolution stage:

Graph Specification / Architecture

Current impact:

Not blocking Discovery.

---

## OQ-005 — Corporate Project / Initiative Source

Status:

OPEN

Question:

What is the authoritative corporate source for:

- projects;
- initiatives;
- improvement portfolio;
- strategic work.

Known answer:

TBD

Important constraint:

Do not assume Jira, ServiceNow SPM, Azure DevOps or another platform.

Resolution stage:

Corporate Integration Discovery

Current impact:

Not blocking Lab implementation if an Integration Contract and Mock adapter are defined.

---

## OQ-006 — Corporate DRP / Resilience Source

Status:

OPEN

Question:

What system or systems are authoritative for DRP, continuity and resilience information?

Known answer:

TBD

Resolution stage:

Integration Specification / Corporate Discovery Delta

Current impact:

Not blocking Lab implementation.

---

## OQ-007 — Corporate SCM

Status:

OPEN

Question:

Which corporate Source Code Management platform or platforms will provide:

- repository;
- Pull Request;
- commit;
- branch;
- engineering metadata.

Known answer:

TBD

Resolution stage:

Integration Specification / Corporate Discovery Delta

Current impact:

Not blocking Lab implementation.

---

## OQ-008 — Corporate IAM

Status:

OPEN

Question:

Which Identity Provider and authorization model will VECTOR use in the corporate environment?

Known answer:

TBD

Resolution stage:

Security Architecture / Corporate Discovery Delta

Current impact:

Not blocking local development identity.

---

## OQ-009 — Commitments Ownership

Status:

OPEN

Question:

Will Commitment data be:

- owned natively by VECTOR;
- integrated from an external corporate source;
- hybrid.

Resolution stage:

Functional Specification / Domain Modeling

Current impact:

Potential blocker for Execution Intelligence implementation.

---

## OQ-010 — ServiceNow ARIA Mapping

Status:

OPEN

Question:

What are the actual corporate:

- tables;
- fields;
- APIs;
- identifiers;
- event normalization rules;
- service mappings;
- CI mappings;
- ACLs;
- permissions.

Known answer:

TBD

Resolution stage:

Corporate Discovery Delta

Current impact:

Not blocking Lab implementation.

---

## OQ-011 — ServiceNow ITSM Mapping

Status:

OPEN

Question:

What are the actual corporate mappings for:

- Incident;
- Problem;
- Change;
- CMDB;
- Service;
- Configuration Item.

Known unknowns include:

- custom tables;
- custom fields;
- state mappings;
- priority mappings;
- relationships;
- APIs;
- ACLs;
- permissions.

Resolution stage:

Corporate Discovery Delta

Current impact:

Not blocking ServiceNow PDI or Mock implementation.

---

## OQ-012 — Dynatrace / Grail Integration

Status:

OPEN

Question:

What are the final corporate contracts for:

- Grail datasets;
- DQL;
- entity identifiers;
- API access;
- SLO data;
- SRE Skill JSON ingestion.

Known answer:

TBD

Resolution stage:

Integration Specification / Corporate Discovery Delta

Current impact:

Not blocking JSON fixture-based Lab implementation.

---

## OQ-013 — New Relic Integration

Status:

OPEN

Question:

What is New Relic's final role relative to Dynatrace?

Questions include:

- covered services;
- authoritative measurements;
- overlap;
- source precedence;
- NRQL contracts;
- identifiers;
- API permissions.

Resolution stage:

Integration Specification / Corporate Discovery Delta

Current impact:

Not blocking initial Lab implementation.

---

## OQ-014 — Cross-Source Identity and Correlation

Status:

OPEN

Question:

Which identifiers will reliably correlate:

Repository
→ PullRequest
→ Deployment
→ Change
→ Service
→ MonitoringEvent
→ Incident
→ Problem
→ SLO

Potential identifiers may exist, but no final mapping is approved.

Resolution stage:

Domain Model / Data / Graph / Integration Specification

Current impact:

Will become blocking before correlation implementation.

---

## OQ-015 — Canonical Domain Model

Status:

OPEN

Question:

What are the final canonical entities, relationships and boundaries VECTOR requires?

Current candidates include:

- Service
- ConfigurationItem
- Incident
- Problem
- Change
- MonitoringEvent
- SLO
- Repository
- PullRequest
- Deployment
- SREAssessment
- SREFinding
- Commitment
- Project
- Risk
- Evidence

Resolution stage:

Product Boundary / Domain Modeling

Current impact:

Blocking before Data and Integration Contracts are finalized.

---

## OQ-016 — Source Conflict Resolution

Status:

OPEN

Question:

How should VECTOR behave when two sources provide conflicting values for the same conceptual fact?

Known principle:

Preserve provenance and Source Authority.

Still TBD:

- conflict representation;
- precedence rules;
- confidence impact;
- user-visible behavior.

Resolution stage:

Data / Integration Specification

Current impact:

Not blocking Discovery.

---

## OQ-017 — Data Confidence Model

Status:

OPEN

Question:

How will VECTOR calculate and represent Data Confidence?

Discovery concepts include:

- HIGH
- MEDIUM
- LOW

but no formula or rules are yet approved.

Resolution stage:

Data / Functional Specification

Current impact:

Not blocking Discovery.

---

## OQ-018 — KPI / Metric Model

Status:

OPEN

Question:

Which metrics and KPIs will exist in VECTOR V1 and how will each define:

- owner;
- formula;
- source;
- frequency;
- baseline;
- target;
- confidence;
- drill-down evidence.

Resolution stage:

Functional Specification / Data Specification

Current impact:

Blocking before final dashboards and scoring behavior are implemented.

---

## OQ-019 — AI Capabilities

Status:

OPEN

Question:

Which VECTOR decisions or analyses may use AI and which must remain deterministic?

Potential candidates include:

- evidence summarization;
- correlation explanation;
- anomaly explanation;
- risk hypothesis generation;
- recommended investigation path.

Resolution stage:

AI Behavior Specification

Current impact:

Not blocking core deterministic product specification.

---

## OQ-020 — Local V1 Deployment Shape

Status:

OPEN

Question:

What is the minimum local deployment topology required for the functional V1?

Examples may include:

- containers;
- local database;
- graph capability;
- frontend;
- API;
- fixtures;
- adapters.

No architecture is selected yet.

Resolution stage:

Architecture Specification

Current impact:

Not blocking Discovery.

---

## OQ-021 — Environment Portability Validation

Status:

OPEN

Question:

What exact checks must pass before declaring the Lab architecture portable to the corporate environment?

Known principle:

External dependencies must be behind contracts and adapters.

Resolution stage:

Architecture / Integration Quality Gate

Current impact:

Not blocking Discovery.

---

## OQ-022 — Final Acceptance Model

Status:

OPEN

Question:

What end-to-end scenarios prove that VECTOR V1 is functionally complete?

Resolution stage:

Acceptance Specification

Current impact:

Will become blocking before READY FOR IMPLEMENTATION.

---

## OQ-023 — Playbook Full Bilingual Structure

Status:

OPEN

Question:

What is the final reusable AI-SDD-PLAYBOOK structure?

Known decision:

The Spanish learning version keeps technical terminology in English and operational explanations in Spanish.

A separate English version will be maintained.

Resolution stage:

SDD Playbook construction

Current impact:

Not blocking VECTOR product specification.

---

## Resolution Rule

When an Open Question is resolved:

1. Change its state to RESOLVED.
2. Reference the Decision, ADR or Specification that resolves it.
3. Do not delete the historical question.
4. If the answer invalidates a CLOSED Decision, raise a SPEC-BLOCKER before changing that decision.