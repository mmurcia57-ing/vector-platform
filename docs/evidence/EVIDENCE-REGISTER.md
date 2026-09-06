# VECTOR — Evidence Register

> Artifact type: Evidence Ledger
> Status: ACTIVE
> Purpose: Register discovery, stakeholder, technical and vendor evidence used by VECTOR.

---

## Evidence Principles

Evidence is not automatically a requirement.

Every evidence item must preserve:

- source
- evidence type
- authority
- provenance
- scope
- limitations
- confidence
- status

Evidence may support a decision or requirement, but it must not silently become one.

---

## Evidence Statuses

- PROPOSED
- VERIFIED
- ACCEPTED_AS_DISCOVERY_INPUT
- PARTIALLY_VERIFIED
- SUPERSEDED
- REJECTED

---

## EVD-001 — ServiceNow Vendor Documentation

Status: VERIFIED

Source:

ServiceNow/ServiceNowDocs

Evidence type:

Vendor Reference Documentation

Authority:

HIGH for standard ServiceNow capabilities, terminology and documented product behavior.

Relevant domains:

- Incident Management
- Problem Management
- Change Management
- CMDB
- CSDM
- Event Management
- AIOps
- integration concepts
- standard product relationships

May support:

- standard ServiceNow object understanding;
- vendor terminology;
- integration discovery;
- standard platform capability analysis.

Does NOT establish:

- bank-specific configuration;
- custom tables;
- custom fields;
- ARIA implementation details;
- corporate ACLs;
- corporate permissions;
- actual bank relationships;
- actual corporate mappings.

Confidence:

HIGH for vendor product documentation.

Limitations:

Corporate implementation must be validated independently.

---

## EVD-002 — SRE Skill v2.1

Status: VERIFIED

Name:

skills-sre-checklist

Version:

2.1

Evidence type:

Engineering Reliability Evidence Contract

Environment:

CI / GitHub Actions

Authority:

HIGH for the controls explicitly evaluated by the Skill.

Known evaluated pillars:

- observability
- resilience
- automation
- security

Known non-evaluated pillar:

incident_management

For incident_management:

evaluated_by = servicenow

Known scan types:

- incremental
- full_rescan_periodico
- full_rescan_cold_start

Authority semantics:

incremental
→ authoritative = false

full_rescan_periodico
→ authoritative = true

full_rescan_cold_start
→ authoritative = true

Known finding states:

- NUEVO
- PERSISTENTE
- RESUELTO
- RE-DETECTADO

Known output concepts include:

- pr_number
- base_sha
- head_sha
- scan_type
- coverage_age_days
- last_full_scan_date
- pillars
- score_percentage
- authoritative
- findings
- fingerprint
- severity
- confidence
- file
- line
- title
- risk
- recommendation
- status
- gate_decision

Potential VECTOR use:

- engineering reliability evidence;
- technical debt persistence;
- recurrence analysis;
- trend analysis;
- correlation with change/deployment/service/incident data.

VECTOR limitations:

- must not reinterpret incremental scans as authoritative global scores;
- must not convert findings into individual productivity scores;
- must preserve provenance and source authority;
- must not modify original evidence semantics.

Confidence:

HIGH

---

## EVD-003 — Dirección de Tecnología Operations Performance Proposal

Status: ACCEPTED_AS_DISCOVERY_INPUT

Evidence type:

Stakeholder Product Proposal

Authority:

HIGH for stakeholder needs and product intent.

Authority for final specification:

LOW

The proposal contributes concepts such as:

- evidence-driven measurement;
- commitments;
- outcomes;
- capacity;
- improvement;
- trends;
- risk;
- department/service context;
- delivery quality;
- executive visibility;
- drill-down;
- data confidence.

May support:

- Product Discovery;
- capability discovery;
- UX discovery;
- metric candidate discovery;
- evidence-model discovery.

Does NOT automatically establish:

- final requirements;
- final scope;
- final architecture;
- final UX;
- final metrics;
- final scoring model.

Explicit exclusion:

Personal, interpersonal and private management information related to stakeholders is not evidence for VECTOR and must not be used.

Confidence:

HIGH for stated stakeholder intent.

---

## EVD-004 — Historical Discovery Conversation

Status: ACCEPTED_AS_DISCOVERY_INPUT

Source:

docs/discovery/raw/discovery-chat.txt

Evidence type:

Historical Discovery Transcript

Authority:

LOW for normative specification.

Purpose:

Historical backup used to recover context and trace the origin of ideas, decisions and hypotheses.

Normative rule:

This transcript is NOT a Source of Truth.

Higher-authority artifacts include:

- CLOSED Decisions;
- ADRs;
- approved Specifications;
- verified Evidence.

Limitations:

The transcript may contain:

- exploratory reasoning;
- rejected alternatives;
- incomplete hypotheses;
- superseded ideas;
- conversational examples.

It must not be used directly to override approved artifacts.

---

## EVD-005 — VECTOR Discovery Baseline

Status: VERIFIED

Source:

docs/discovery/DISCOVERY-BASELINE.md

Evidence type:

Distilled Discovery Evidence

Authority:

MEDIUM

Purpose:

Structured extraction of the relevant Discovery context completed before formal specification.

Contains:

- confirmed facts;
- accepted hypotheses;
- product intent;
- known sources;
- constraints;
- TBDs;
- evidence summaries;
- SDD working principles.

Limitations:

It is Pre-specification.

When a topic is formalized through:

- CLOSED Decision;
- ADR;
- approved Specification;

the newer higher-authority artifact becomes normative.

Confidence:

HIGH as a Discovery summary.

---

## Evidence Usage Rule

Before using any evidence item to create a requirement or decision:

1. Identify the Evidence ID.
2. Check its authority.
3. Check what it can establish.
4. Check its limitations.
5. Separate evidence-derived facts from inference.
6. Record assumptions as HYPOTHESIS or TBD.
7. Do not silently elevate Evidence into Specification.