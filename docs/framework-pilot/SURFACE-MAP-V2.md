# VECTOR — Product Surface Map V2

Status: **CURRENT RUNTIME INVENTORIED / EXPERIENCE TRACEABILITY RESTORED**
Runtime changes: **NONE**

## Why this exists
A prior high-fidelity concept mixed shell, Service and Risk Investigation semantics and was incorrectly presented as a candidate for generic VECTOR expectation validation. This map binds redesign work to actual product surfaces before further visual validation.

## Current runtime surfaces

| Current surface | Runtime route / evidence | Current purpose | V2 disposition | Candidate role |
|---|---|---|---|---|
| Panorama Ejecutivo | `/` / `overview` | Portfolio/technology attention: areas, evidence-backed risks, overdue commitments, evidence quality | **MODIFY** | Executive/attention surface inside shared Context + Continuity shell |
| Área / Dominio | `/areas` | Area decision workspace: service portfolio, attention concentration, follow-up | **KEEP + MODIFY EXPERIENCE** | Area context surface; preserve system/process semantics |
| Servicio | `/services/:id` | Operational condition of one service; findings, evidence, SLO/incidents/changes when available | **KEEP + DEEPEN** | Primary operational-context surface |
| Investigación de Riesgo | `/risks/:id` | Deep evidence/relationship/change/AI-assisted investigation | **KEEP + DEEPEN** | Investigation mode; strongest home for topology + temporal + evidence lenses |
| Compromisos y Mejoras | `/commitments` | Governed commitment lifecycle, renegotiation, execution status, outcome-pending distinction | **KEEP + INTEGRATE** | Action/Outcome surface linked to originating context/evidence |

## Cross-cutting representations

| Representation | Classification | Rule |
|---|---|---|
| Workspace Rail | **SHELL** | Navigation/orientation across product surfaces; not a business surface itself |
| Context Envelope | **NEW SHELL CONTRACT** | Area/Service/Risk/Condition + period + evidence quality/provenance persist across transitions where applicable |
| Operational Canvas | **LENS / COMPONENT** | Attention/service orientation; not automatically the Operational view |
| Topology / SpatialGraph | **INVESTIGATION LENS** | Evidence-backed bounded relationships; strongest fit in Service/Risk investigation; never universal product center |
| Temporal Spine | **CROSS-CUTTING LENS** | Events/change/action/outcome chronology; coordinated with selected context |
| Evidence Inspector | **CROSS-CUTTING LENS** | Facts/derived/correlation/hypothesis/limitation with provenance |
| Decision Queue | **WORKFLOW COMPONENT** | Bridges attention/investigation to governed decision/action |
| Commitment lifecycle | **DOMAIN SURFACE + CONTINUITY SPINE** | First-class surface and linked continuation from originating context |
| Outcome verification | **DOMAIN CAPABILITY / CROSS-CUTTING TERMINUS** | Separate from execution completion; returns verification state to original context |

## Surface-by-surface V2 contract

### S1 — Panorama Ejecutivo
Primary question:
**¿Dónde requiere atención Tecnología y por qué?**

Preserve:
- area/portfolio orientation;
- evidence quality;
- evidence-backed attention;
- commitment/outcome context.

Change:
- reduce dashboard/card grammar where it obscures decision flow;
- allow drill-in without losing selected period/context;
- do not embed a giant technical topology as the executive default.

### S2 — Área / Dominio
Primary question:
**¿Dónde se concentra la atención dentro del área, qué la explica y qué seguimiento requiere?**

Preserve:
- AreaDomain accountability;
- service portfolio;
- system/process focus;
- no individual scoring/ranking.

Change:
- stronger continuity into Service/Risk and back;
- evidence quality and outcome state remain visible.

### S3 — Servicio — Operational Context
Primary question:
**¿Qué está ocurriendo en este servicio, qué evidencia lo sustenta y qué contexto falta?**

This is the closest current surface to a **vista operacional**.

Preserve:
- service identity and area context;
- condition;
- findings/evidence;
- SLO/incidents/changes only where data exists.

Change/deepen:
- topology as an optional powerful relationship lens;
- temporal lens;
- evidence/provenance inspector;
- explicit missing/stale/partial context;
- route into Risk Investigation while retaining Service context.

### S4 — Investigación de Riesgo
Primary question:
**¿Qué sabemos, qué se relaciona, qué cambió, qué no sabemos y qué decisión está justificada?**

Preserve:
- evidence-first investigation;
- graph boundedness;
- change association ≠ causation;
- AI bounded by evidence.

Change/deepen:
- strongest coordinated topology + timeline + evidence workspace;
- evidence handles/provenance;
- facts vs deterministic findings vs correlations vs hypotheses vs limitations;
- governed transition to decision/commitment/action.

### S5 — Compromisos y Mejoras
Primary question:
**¿Qué se comprometió, qué cambió en la ejecución y qué resultado está realmente verificado?**

Preserve:
- immutable history/renegotiation semantics;
- area accountability;
- execution completion ≠ outcome verification;
- no individual scoring.

Change:
- retain originating service/risk/evidence context;
- execution evidence;
- outcome verification flows back to Panorama/Area/Service/Risk context.

## Candidate E clarified

**Context + Continuity Workspace is a SHELL/EXPERIENCE CONTRACT, not a sixth business view.**

It governs:
1. context persistence;
2. semantic/epistemic presentation;
3. transitions between S1–S5;
4. shared lenses;
5. decision/action/outcome continuity.

It does NOT erase S1–S5.

## Prior generated visual disposition
The previously generated dark topology/timeline/AI image is classified:

**REJECT AS GENERIC VECTOR EXPERIENCE TARGET.**

It mixed:
- S3 Service;
- S4 Risk Investigation;
- global shell/navigation;
- unsupported presentation details.

Potentially reusable only as a visual/reference hypothesis for **S4 Risk Investigation**, after removing unsupported claims/data and binding it to the actual Risk Investigation contract.

It is not evidence for S1 Panorama, S2 Area, S3 Service as a whole, or S5 Commitments.

## Next materialization order
To avoid another ambiguous validation:
1. materialize **SHELL** separately;
2. materialize **S1 Panorama Ejecutivo**;
3. materialize **S3 Servicio / Operational Context**;
4. materialize **S4 Investigación de Riesgo**;
5. materialize **S5 Compromisos/Outcome**;
6. verify S2 Area continuity;
7. demonstrate one end-to-end journey crossing surfaces without context loss.

Each image/prototype must be labeled with:
- surface ID/name;
- journey/question;
- preserved semantics;
- conceptual vs executable status.

## Gate
**SURFACE TRACEABILITY PASS — VISUAL EXPECTATION VALIDATION NOT YET RE-RUN.**

No runtime redesign is authorized yet.
