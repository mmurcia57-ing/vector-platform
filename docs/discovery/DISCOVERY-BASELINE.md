# VECTOR — Discovery Baseline

> Status: DRAFT  
> Artifact type: Distilled Discovery Evidence  
> Normative level: Pre-specification  
> Previous product codename: CRIO

---

## 1. Purpose of this document

This document distills the relevant discovery performed before formal VECTOR specification.

It preserves:

- confirmed facts;
- accepted hypotheses;
- stakeholder and technical evidence;
- known product intentions;
- known source systems;
- constraints;
- open questions and TBDs;
- decisions already agreed during discovery.

This document is NOT the final product specification.

Raw conversation transcripts are historical evidence only and are not normative specifications.

When a discovery item is formalized later through an approved specification, ADR or CLOSED decision, that artifact becomes the higher-authority source.

---

## 2. Product identity

Current product name:

VECTOR

Previous codename:

CRIO

Status:

CONFIRMED

The rename from CRIO to VECTOR does not by itself redefine product scope, architecture or capabilities.

VECTOR preserves the conceptual work developed under CRIO unless a later formal decision explicitly changes it.

---

## 3. Product intent

VECTOR is intended to become a Technology Performance and Reliability Intelligence platform.

Its purpose is to help technology organizations understand:

- where reliability is degrading;
- where operational problems are concentrating;
- where temporary solutions are creating recurrence;
- where changes introduce risk;
- where technical or operational debt persists;
- how services, changes, incidents, problems, telemetry and engineering evidence are related;
- where execution commitments or improvement work are not producing sustainable outcomes;
- what evidence supports a decision.

VECTOR should primarily analyze the technology system, service, domain, process and operating model.

It must not become a simplistic employee productivity ranking system.

---

## 4. Core problem discovered

Technology operational information is distributed across multiple systems and processes.

Relevant information may exist independently in:

- monitoring events;
- observability platforms;
- incidents;
- problems;
- changes;
- CMDB;
- SLO/SLI information;
- repositories and pull requests;
- SRE assessments;
- commitments;
- improvement initiatives;
- resilience and DRP processes.

This fragmentation makes it difficult to answer questions such as:

- Why is a service repeatedly degrading?
- Is the same incident recurring?
- Was a temporary remediation converted into a structural solution?
- Did a recent change increase operational risk?
- Are high-risk engineering findings later reflected in production incidents?
- Where is operational debt accumulating?
- Which services or domains require intervention?
- What evidence supports a reliability or execution finding?

VECTOR seeks to correlate these signals into decision-oriented intelligence.

---

## 5. Current Product Boundary Hypothesis

Status:

HYPOTHESIS — ACCEPTED FOR DISCOVERY

VECTOR is currently modeled as one product composed of three logical areas:

### 5.1 Reliability Intelligence

Focused on technology and service reliability.

Candidate concerns include:

- incidents;
- problems;
- recurrent incidents;
- temporary fixes;
- monitoring events;
- SLO/SLI;
- changes;
- change risk;
- service health;
- operational debt;
- resilience;
- DRP;
- technical reliability findings;
- predictive risk.

### 5.2 Execution Intelligence

Focused on how technology executes and improves.

Candidate concerns include:

- outcomes;
- commitments;
- delivery quality;
- improvement work;
- capacity and allocation;
- technical debt reduction;
- engineering quality signals;
- autonomy and intervention patterns;
- execution trends.

Execution Intelligence must not be interpreted as individual productivity scoring.

### 5.3 Intelligence Core

Shared capabilities expected to support both domains.

Candidate concerns include:

- canonical domain model;
- source normalization;
- evidence model;
- provenance;
- confidence;
- source authority;
- service and dependency graph;
- correlation;
- metric/KPI engine;
- risk analysis;
- AI-assisted analysis;
- auditability.

The Product Boundary must be validated formally during Domain Modeling.

---

## 6. Product principles discovered

The following principles are considered strong discovery inputs.

### Evidence first

Every important conclusion should be explainable through underlying evidence.

### Data before perception

Objective evidence should be preferred over subjective judgment when reliable data exists.

### Context before judgment

Metrics must be interpreted using relevant operational and technical context.

### Trends before snapshots

VECTOR should prefer trends and historical evolution over isolated measurements.

### Drill-down before scoring

Aggregated indicators should allow navigation toward underlying evidence.

### Source authority

VECTOR may correlate information from multiple systems but must preserve which source is authoritative for each measurement or fact.

### Provenance

Normalized information must retain enough provenance to identify its origin.

### Confidence

Where applicable, VECTOR should express confidence or data-quality limitations.

### No simplistic individual ranking

The product should not reward or punish individuals through simplistic activity metrics.

Examples of dangerous metrics when used without context include:

- ticket counts;
- commits;
- pull request counts;
- hours;
- meetings;
- lines of code;
- raw incident volume.

### Avoid perverse incentives

VECTOR should not incentivize hiding incidents, problems, operational risk or technical debt.

---

## 7. Decision-oriented navigation

Discovery indicates that VECTOR should support a decision flow similar to:

Overview
→ What is deteriorating?
→ Why?
→ Where is it concentrated?
→ Which services/domains/dependencies are involved?
→ What evidence supports the finding?
→ What decision is required?
→ What action or commitment follows?

This is currently a UX/product principle, not a final screen specification.

---

## 8. Stakeholder evidence

A proposal from Dirección de Tecnología introduced an Operations Performance perspective.

Status:

ACCEPTED AS DISCOVERY INPUT

Value:

HIGH

The proposal contributed concepts such as:

- evidence-driven measurement;
- commitments;
- outcomes;
- capacity;
- improvement;
- trends;
- risk;
- department/service context;
- quality of delivery;
- executive visibility;
- drill-down;
- data confidence.

The stakeholder proposal is evidence and discovery material.

It must not be copied directly as the final VECTOR specification or dashboard.

Personal, interpersonal or private management information about stakeholders is explicitly excluded from VECTOR discovery and specification.

---

## 9. Candidate capability areas

The following capabilities have been discovered but are not all automatically V1 scope.

### Reliability-oriented candidates

- Reliability Intelligence
- Incident Intelligence
- Problem Intelligence
- Change Intelligence
- Event Intelligence
- Service Intelligence
- SLO/SLI Intelligence
- Operational Debt
- Resilience
- DRP
- Risk and Early Warning
- Predictive Reliability

### Execution-oriented candidates

- Strategy and Outcomes
- Commitment Management
- Delivery Quality
- Capacity and Allocation
- Improvement Portfolio
- Engineering Quality Evidence
- Execution Trends
- Calibration and Context
- Decision Intelligence

### Shared candidates

- Evidence Engine
- Service/Domain Map
- Graph Correlation
- Audit Trail
- AI-assisted analysis
- Executive View
- Source Confidence
- Metric/KPI Engine

These capabilities require prioritization during Scope & Capabilities specification.

---

## 10. Known target information sources

### ServiceNow ARIA

Status:

CONFIRMED BY STAKEHOLDER

Known role:

Centralizes events originating from monitoring tools.

Corporate configuration details:

TBD

---

### ServiceNow ITSM

Status:

CONFIRMED BY STAKEHOLDER

Known information includes:

- incidents;
- problems;
- changes;
- CMDB.

Custom tables, mappings, fields, ACLs and corporate configuration:

TBD

---

### Dynatrace

Status:

CONFIRMED TARGET SOURCE

Known role:

- observability;
- telemetry;
- SLO-related information;
- planned storage/access of SRE Skill evaluation data through Grail JSON.

Exact corporate integration:

TBD

---

### New Relic

Status:

CONFIRMED TARGET SOURCE

Known role:

Complementary observability source.

Exact VECTOR use and corporate mappings:

TBD

---

### Source Code Management

Status:

PARTIALLY DEFINED

Expected information:

- repositories;
- pull requests;
- commits;
- branches;
- engineering evidence.

Exact corporate SCM/source:

TBD

Local laboratory may use GitHub.

---

### Project / Initiative Portfolio

Status:

TBD

VECTOR may require information about projects, initiatives or improvement work.

The authoritative corporate source is not currently known.

Do not assume Jira, ServiceNow SPM or another platform without evidence.

---

### DRP / Resilience Source

Status:

TBD

Logical capability is known.

Corporate authoritative system or systems are not yet confirmed.

---

### Commitments

Status:

TBD

Commitments may be owned by VECTOR or integrated from another source.

This requires later specification.

---

## 11. ServiceNow vendor reference

Evidence identifier candidate:

EVD-001

Source:

ServiceNow/ServiceNowDocs

Type:

Vendor Reference Documentation

Status:

VERIFIED

Authority:

HIGH for standard ServiceNow product capabilities and terminology.

Useful for:

- Incident Management;
- Problem Management;
- Change Management;
- CMDB;
- CSDM;
- Event Management;
- AIOps;
- integration concepts;
- standard product relationships.

It does NOT establish:

- bank-specific configuration;
- custom tables;
- custom fields;
- ARIA implementation details;
- bank ACLs;
- permissions;
- actual corporate relationships.

Vendor documentation must not be confused with corporate implementation evidence.

---

## 12. SRE Skill v2.1

Evidence identifier candidate:

EVD-002

Name:

skills-sre-checklist

Version:

2.1

Type:

Engineering Reliability Evidence

Environment:

CI / GitHub Actions

Purpose:

Incremental SRE evaluation for Pull Requests and periodic repository-wide quality assessment.

### Evaluated pillars

The Skill evaluates:

- observability;
- resilience;
- automation;
- security.

Incident Management is intentionally not evaluated by the Skill.

For Incident Management:

evaluated_by = servicenow

### Scan semantics

Supported scan types:

- incremental;
- full_rescan_periodico;
- full_rescan_cold_start.

Important rule:

incremental
→ authoritative = false

full_rescan_*
→ authoritative = true

VECTOR must preserve this semantic.

An incremental PR result must not be interpreted as an authoritative global repository or service score.

### Finding lifecycle

Known finding states:

- NUEVO
- PERSISTENTE
- RESUELTO
- RE-DETECTADO

Findings include a stable fingerprint derived from rule/pillar/file/symbol context.

This enables VECTOR to potentially analyze:

- persistent technical debt;
- resolved findings;
- recurrence;
- aging findings;
- engineering risk trends.

### Relevant output concepts

The JSON includes concepts such as:

- PR number;
- Base SHA;
- Head SHA;
- scan type;
- coverage age;
- last full scan;
- pillar scores;
- authoritative flag;
- findings;
- severity;
- confidence;
- file;
- line;
- risk;
- recommendation;
- finding status;
- gate decision.

### VECTOR integration concept

Logical source:

SREAssessmentSource

Potential canonical objects:

- SREAssessment
- SREFinding
- Repository
- PullRequest
- Evidence

VECTOR responsibilities:

- ingest;
- normalize;
- preserve provenance;
- preserve authority semantics;
- correlate;
- trend;
- expose evidence.

VECTOR must not:

- modify original evidence;
- reinterpret incremental results as authoritative;
- convert SRE findings into individual productivity scoring;
- replace the authority of the originating system.

---

## 13. Cross-source correlation opportunity

A major product hypothesis is that VECTOR can correlate evidence across the engineering and production lifecycle.

Example conceptual chain:

Repository
→ Pull Request
→ SRE Assessment
→ SRE Finding
→ Deployment
→ Change
→ Service
→ Monitoring Event
→ Incident
→ Problem
→ SLO Impact

This relationship chain is one of the main reasons a graph model may be useful.

Graph technology is not yet selected.

The graph must be justified by required relationships and queries, not selected merely because graph databases are available.

---

## 14. Example intelligence hypotheses

These examples are discovery hypotheses, not finalized requirements.

VECTOR may eventually help answer:

- Do services with persistent resilience findings experience higher incident recurrence?
- Did a change associated with a high-severity SRE finding precede a production degradation?
- Which services repeatedly resolve incidents through temporary remediation?
- Which unresolved problems are associated with recurring operational events?
- Where is operational debt increasing while SLO performance deteriorates?
- Which technical findings repeatedly reappear after being marked resolved?

Formal requirements will be created later.

---

## 15. Lab to Corporate strategy

Status:

ACCEPTED PRINCIPLE

VECTOR must be specified for the real corporate target environment while allowing a local implementation.

Architecture principle:

Canonical Model
→ Integration Contracts
→ Adapters

Local laboratory adapters may use:

- mocks;
- synthetic fixtures;
- personal GitHub;
- ServiceNow Personal Developer Instance;
- local databases.

Corporate implementations will use adapters for authorized enterprise systems.

The product domain must not depend directly on vendor-specific fields.

---

## 16. Integration maturity model

The following maturity model is accepted as a working framework:

L0 — Contract Defined  
L1 — Mock  
L2 — Sandbox  
L3 — Corporate Read-only  
L4 — Corporate Verified  
L5 — Production

Initial local VECTOR development will primarily operate at L1 and L2.

---

## 17. ServiceNow PDI strategy

A ServiceNow Personal Developer Instance may be used as a laboratory sandbox.

Potential local validation includes:

- incidents;
- problems;
- changes;
- CMDB;
- API contracts.

A PDI does not prove compatibility with the bank's actual ServiceNow configuration.

Corporate integration will require a Corporate Discovery Delta.

---

## 18. Corporate Discovery Delta

When VECTOR moves from laboratory to the bank environment, the product should not be re-specified from zero.

Instead, compare:

Lab specification
vs.
Corporate reality

and identify deltas such as:

- APIs;
- permissions;
- mappings;
- custom fields;
- custom tables;
- identities;
- data quality;
- correlation identifiers;
- source authority;
- network/security constraints.

These deltas become an enterprise integration backlog.

---

## 19. Synthetic data principle

Local VECTOR data should be coherent, not random.

Synthetic scenarios should reproduce meaningful relationships.

Example:

Service
→ Change
→ Deployment
→ Pull Request
→ SRE finding
→ monitoring degradation
→ event
→ incident
→ temporary fix
→ recurrent incident
→ unresolved problem
→ SLO degradation

This allows end-to-end validation of VECTOR intelligence without corporate production data.

---

## 20. Environment portability

External dependencies should remain behind contracts and adapters.

Expected conceptual portability includes:

| Capability | Lab | Corporate Target |
|---|---|---|
| Incidents | ServiceNow PDI / Mock | Corporate ServiceNow ITSM |
| Problems | ServiceNow PDI / Mock | Corporate ServiceNow ITSM |
| Changes | ServiceNow PDI / Mock | Corporate ServiceNow ITSM |
| CMDB | ServiceNow PDI / Mock | Corporate ServiceNow CMDB |
| Events | Synthetic / Mock | ServiceNow ARIA |
| Observability | Mock | Dynatrace |
| Complementary observability | Mock | New Relic |
| SRE Skill | JSON fixture | Dynatrace/Grail JSON |
| SCM | Personal GitHub | Corporate SCM TBD |
| Projects | Synthetic | TBD |
| DRP | Synthetic | TBD |
| Identity | Local | Corporate IAM TBD |

This table is discovery-level and must be validated during Integration Specification.

---

## 21. UX discovery

A stakeholder dashboard concept was reviewed.

Status:

NON-NORMATIVE UX REFERENCE

Useful concepts:

- stable navigation;
- overview;
- trends;
- drill-down;
- departments/domains;
- risks;
- commitments;
- operations;
- reliability indicators.

VECTOR must not blindly copy the stakeholder dashboard.

The final UX should be designed around investigation and decision-making rather than merely presenting KPI cards.

---

## 22. SDD methodology agreed

VECTOR will be built using Specification-Driven Development.

Working sequence:

Discovery
→ Product Definition
→ Scope & Capabilities
→ Product Boundary / Domain
→ Functional Specification
→ Data
→ Graph
→ Integrations
→ UX
→ Architecture
→ Security/NFR/Observability
→ AI Behavior
→ Acceptance
→ Implementation Plan
→ Tasks
→ Traceability
→ Adversarial Review
→ READY FOR IMPLEMENTATION

Implementation is forbidden before the relevant specification reaches its required readiness gate.

---

## 23. Progressive specification

The entire product does not necessarily need to finish all specification before every implementation task begins.

A vertical may become:

READY FOR BUILD

when all specifications required for that vertical are CLOSED and it has no unresolved transversal blocker.

This allows progressive delivery without reverting to vibe coding.

---

## 24. Quality Gate philosophy

Artifact states:

NOT READY
→ blocker exists.

READY WITH DEBT
→ implementable, with documented non-blocking debt.

CLOSED
→ Definition of Ready satisfied.

A CLOSED artifact should not be reopened simply because a potentially better idea appears.

A CLOSED artifact may be reopened only due to:

- genuine contradiction;
- invalidated assumption;
- authoritative new evidence;
- implementation blocker;
- safety/security issue.

---

## 25. Anti-loop rule

Before requesting additional specification detail, ask:

Would the absence of this information force an implementer to invent a relevant decision?

If YES:

Specification blocker.

If NO:

Backlog, improvement or future debt.

This rule exists to prevent endless AI-driven refinement.

---

## 26. AI execution model

ChatGPT role:

- SDD mentor;
- discovery partner;
- product/architecture reasoning;
- decision support;
- quality gate auditor;
- adversarial reviewer.

Codex role:

- materialize approved specifications;
- update files;
- execute scoped implementation tasks;
- run tests;
- report blockers.

During specification, Codex must not independently redefine the product.

---

## 27. AI model escalation principle

Use the lowest-capability model that can reliably execute the task.

Escalation should happen because task complexity requires it, not automatically.

Current working mapping may change over time and is not a permanent product requirement.

---

## 28. Git and auditability

VECTOR specifications and implementation will be version controlled from the beginning.

Expected practice:

- incremental changes;
- review before closure;
- descriptive commits;
- no implementation before required specification gate;
- Git history as audit trail.

GitHub Projects may later be used for implementation execution and backlog management.

GitHub Projects does not replace product specifications.

---

## 29. Known TBDs

The following are known unresolved items and must not be invented:

- authoritative source for projects/initiatives;
- authoritative DRP/resilience source;
- corporate SCM implementation;
- corporate IAM integration;
- commitment system ownership;
- detailed ServiceNow ARIA mappings;
- detailed ServiceNow ITSM mappings;
- exact Dynatrace/Grail integration;
- exact New Relic integration;
- corporate API permissions;
- final technology stack;
- final graph technology;
- final persistence technology;
- final deployment architecture;
- final V1 capability scope.

Additional TBDs may be discovered during formal specification.

---

## 30. Explicit exclusions

The Discovery Baseline intentionally excludes:

- personal information about stakeholders;
- interpersonal management situations;
- private management context;
- unsupported assumptions about corporate systems;
- production credentials;
- internal secrets;
- customer data;
- personally identifiable production data;
- speculative corporate configurations.

Only professional, technical, operational and product-relevant evidence may progress into VECTOR specification.

---

## 31. Current discovery conclusion

Discovery has produced enough evidence to begin formal VECTOR specification.

Known foundations include:

- product intent;
- initial product boundary;
- core reliability use cases;
- execution intelligence hypothesis;
- source authority principles;
- initial source inventory;
- ServiceNow vendor evidence;
- SRE Skill evidence;
- Lab-to-Corporate portability strategy;
- SDD execution and quality methodology.

Discovery does not imply that all product requirements are already known.

Unknown information remains explicitly TBD until resolved through specification or authoritative evidence.