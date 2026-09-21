# S3 — Servicio / Vista Operacional — Expectation Validation

Status: **EXPECTATION ALIGNED — VISUAL DIRECTION ACCEPTED / SEMANTIC NORMALIZATION REQUIRED**
Surface: **S3 — Servicio / Operational Context**
Primary question: **¿Qué está ocurriendo en este servicio, qué evidencia lo sustenta y qué contexto operacional falta?**
Runtime: **UNCHANGED**

## Human validation
The user accepted the generated S3 direction with “ok”.

This validates the surface-level experience direction, not every literal datum, infrastructure element, metric, AI statement, action, environment or ownership field rendered by the concept.

## Accepted experience direction
- visual continuity with accepted S1 Panorama;
- service identity/context is explicit;
- denser technical surface than S1;
- topology/dependencies are a major lens without becoming the whole product;
- SLI/SLO/metric signals can coexist with topology when supported;
- incidents, changes and evidence are coordinated around the selected service;
- time window is explicit;
- drill-down paths toward deeper investigation are visible;
- operational evidence is more prominent than executive aggregation;
- high information density remains structured and navigable.

## Illustrative-only / unsupported until contracted
Do NOT infer product requirements or facts from:
- “Product Service” or any displayed domain/service name;
- PostgreSQL, Redis, Customer Service, external APIs;
- AWS/ECS or deployment topology;
- SLO targets or numeric values;
- health score;
- instance/node counts;
- owner/team/repository/runbook/documentation;
- production environment;
- deployment IDs/versions/timestamps;
- incident/change IDs;
- VECTOR AI/Beta;
- suggested actions;
- Prod/Lab switch;
- “real-time”/freshness claims not backed by evidence.

## Canonical S3 semantics to preserve
From current runtime/product contracts:
- Service identity and AreaDomain context;
- operational condition context;
- evidence-backed risk findings;
- evidence and provenance where available;
- explicit evidence quality: available / partial / stale / missing;
- SLO / incident / change projections only where the canonical model can safely represent them;
- route to Risk Investigation;
- no invented causal relationship;
- missing telemetry/context remains visible rather than becoming “healthy”.

## Topology contract for S3
Topology is an **operational relationship lens**:
- source-backed/canonical relations must be distinguishable from inferred/read-side relations;
- bounded graph and truncation/freshness must be visible;
- similarity is not identity;
- proximity is not causality;
- accessible non-spatial relationship representation is required;
- deeper causal/investigation reasoning belongs primarily to S4.

## Temporal contract for S3
Temporal representation may coordinate signals, incidents, changes, actions and outcomes where supported.
Temporal proximity is association/context, not causal proof.

## AI boundary
Any future AI assistance must:
- cite available evidence handles/provenance;
- distinguish hypothesis from deterministic/source-backed finding;
- preserve unresolved candidates and limitations;
- not silently create authoritative root cause or suggested action;
- keep action/write authority separately governed.

## Disposition
**ADOPT visual grammar / ADAPT operational information architecture / REJECT unsupported literal content.**

## Gate
**S3 EXPECTATION ALIGNED.**

S3 may advance toward the cross-surface Experience Contract after S4 and S5 validation. No runtime redesign is authorized yet.
