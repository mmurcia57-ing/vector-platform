# VECTOR — View Mission Differentiation

## Purpose
Challenge each primary view by decision mission rather than visual template reuse.

## Mission matrix

| View | Primary decision mission | Entry question | Primary manipulation | Expected exit | Structural implication |
|---|---|---|---|---|---|
| Panorama Ejecutivo | Executive triage across Technology | Where does Technology require attention and why? | Compare/select AreaDomain or high-attention condition | Area/service/risk context selected for deeper review | Executive attention map/summary; breadth before depth |
| Area Intelligence | Prioritize investigation/follow-up inside one AreaDomain | Which services concentrate attention, why, and what follow-up exists? | Compare services and attention concentration | Service/Risk selected; follow-up context understood | Service portfolio + attention concentration + follow-up chain |
| Service Intelligence | Understand one Service as operational correlation anchor | What is happening to this service and what evidence/context supports it? | Coordinate operational lenses over one service | Risk/evidence/change context selected | Service operational workspace; context/evidence before action |
| Risk Investigation | Explain and investigate one RiskFinding | What happened, what supports it, what is associated, and what remains uncertain? | Traverse timeline/evidence/change/topology/actions/outcome | Explainable decision/action/outcome state | Deep investigation workspace with temporal/relational lenses |
| Commitments & Improvements | Govern follow-up from declared commitment through outcome | What is committed, what changed, and is outcome verified? | Create/transition/renegotiate commitment; inspect evidence/outcome | Updated governed lifecycle or verified/pending outcome | Workflow/ledger structure; action controls and immutable history matter more than investigative dashboard |

## Current structural challenge

### Panorama
Current structure is broadly aligned to breadth-first triage, but the four-metric/dashboard pattern must not become the universal VECTOR template.

### Area
Now structurally differentiated as service portfolio + attention concentration + follow-up. This is intentionally not a metric-card clone of Panorama.

### Service
**GAP:** still inherits the historical four-metric + two-column dashboard template. Its mission is operational correlation and coordinated context, so it should be restructured around service condition, temporal/operational context and evidence, with lenses coordinating the same selected service.

### Risk
Most differentiated current view. It already behaves as a deeper investigation surface. Remaining gap is richer bounded topology/evidence behavior and scenario/state assurance, not wholesale dashboard reuse.

### Commitments
**GAP:** still begins with four KPI cards and dashboard panels even though its primary mission is governed workflow/lifecycle. It should lead with lifecycle/attention queue and history/action/outcome evidence rather than dashboard metrics.

## Design rule derived
Component reuse does not justify information-architecture reuse.

Before reusing a view template across hierarchy levels, prove **task/decision equivalence**:
`decision question → information required → manipulation → completion signal`.

If equivalence is absent, shared components may be reused but the information architecture must be independently justified.

## Next product actions
1. Preserve Panorama as breadth-first executive triage.
2. Preserve new Area portfolio workspace hypothesis and behaviorally test it.
3. Restructure Service into an operational correlation workspace.
4. Preserve Risk investigation structure while strengthening graph/state assurance.
5. Restructure Commitments into a lifecycle/workflow workspace.
6. Re-run J01–J04 and Functional Demo Parity after these changes.

## Gate
VIEW MISSION DIFFERENTIATION: **FAIL / CORRECTION IN PROGRESS**

Blocking product gaps: Service structure, Commitments structure, behavioral evidence for Area differentiation.
