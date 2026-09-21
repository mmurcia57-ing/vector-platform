# VECTOR — Experience Contract V3

Status: **READY FOR IMPLEMENTATION — LOCAL/DEMO EXPERIENCE SCOPE**
Supersedes as redesign authority: prior unbound/topology-dominant experience hypotheses.
Preserves: canonical product semantics, EXT-003, EXT-004, scenario/epistemic constraints.
Runtime at contract creation: **UNCHANGED**.

## Product experience thesis
VECTOR is an **Operational Intelligence Workspace** that preserves context and evidence from attention through investigation, governed action and verified outcome.

It is not:
- a collection of dashboards;
- a universal topology canvas;
- an incident-only RCA tool;
- a task/backlog manager;
- an individual productivity/ranking system.

## Approved surface system

### S1 — Panorama Ejecutivo
Question: **¿Dónde requiere atención Tecnología y por qué?**
Experience direction: approved/aligned.
Role: portfolio attention and evidence/commitment/outcome orientation.

### S2 — Área / Dominio
Question: **¿Dónde se concentra la atención dentro del área, qué servicios la explican y qué seguimiento requiere?**
Experience direction: approved.
Role: AreaDomain portfolio and concentration/follow-up context.

### S3 — Servicio / Vista Operacional
Question: **¿Qué está ocurriendo en este servicio, qué evidencia lo sustenta y qué contexto falta?**
Experience direction: approved/aligned.
Role: operational service context with task-relevant topology/time/evidence lenses.

### S4 — Investigación de Riesgo
Question: **¿Qué sabemos, qué se relaciona, qué cambió, qué no sabemos y qué decisión está justificada?**
Experience direction: approved.
Role: evidence-first investigation with facts/findings/correlations/hypotheses/limitations explicitly separated.

### S5 — Compromisos y Outcomes
Question: **¿Qué se comprometió, qué cambió en la ejecución y qué resultado está realmente verificado?**
Experience direction: approved.
Role: governed lifecycle and outcome verification, not generic task management.

## Shell — Context + Continuity
The shell is not a sixth business surface.

It provides:
- coherent global navigation;
- persistent Context Envelope;
- selected period;
- evidence quality/freshness;
- origin/return path;
- shared visual language;
- cross-surface continuity.

## Visual grammar
Accepted direction across S1–S5:
- light primary workspace;
- dark navigation/anchoring rail;
- modern, high-density but organized composition;
- strong hierarchy;
- compact status semantics;
- surface-specific information density;
- topology/temporal/evidence visualizations only where task-relevant;
- no requirement to reproduce illustrative values/content from concept images.

Corporate theming and exact visual tokens remain implementation/design-system concerns unless already canonical.

## Epistemic grammar
The executable experience MUST distinguish as applicable:
- OBSERVED FACT / SOURCE EVIDENCE
- DETERMINISTIC DERIVED FINDING
- CORRELATION / ASSOCIATION
- HYPOTHESIS
- LIMITATION / UNKNOWN
- GOVERNED DECISION
- COMMITMENT / ACTION
- EXECUTION EVIDENCE
- VERIFIED OUTCOME

## Identity/relationship grammar
- source/canonical identity is preserved;
- similarity does not silently merge entities;
- inferred equivalence/relationship is explicit, reversible and provenance-bearing when used;
- canonical vs inferred graph relations are visually/semantically distinguishable;
- topology is bounded and exposes truncation/freshness;
- accessible non-spatial representation is required.

## Continuity grammar
Primary loop:
`ATTEND → SELECT/PRESERVE CONTEXT → ORIENT → INVESTIGATE → DECIDE → COMMIT/ACT → CAPTURE EXECUTION EVIDENCE → VERIFY OUTCOME → LEARN/CLOSE`

Reference journey:
`S1 → S2 → S3 → S4 → S5 → S4/S3/S2/S1`.

Detailed acceptance: `docs/framework-pilot/E2E-CONTEXT-CONTINUITY-JOURNEY-V3.md`.

## Required implementation deltas
1. **Context Envelope** across navigation.
2. **S1 visual/IA migration** without unsupported metrics.
3. **S2 Area visual/IA migration** preserving process/system boundary.
4. **S3 operational projection depth** for available SLO/metric, Incident, Change and dependency context plus unavailable/stale/partial states.
5. **S4 epistemic investigation workspace** with evidence provenance and bounded graph/time lenses.
6. **S4 → decision/action lineage**.
7. **S5 approved commitments/outcomes experience** while preserving EXT-004 semantics.
8. **Outcome return propagation** to originating context.
9. **Behavioral scenario fixtures** including negative/adversarial controls.
10. **Accessibility and non-spatial alternatives** for graph-heavy interactions.
11. **Localization boundary** for deterministic backend/user-facing text.
12. **Demo parity:** local deterministic demo experience must exercise the same functional interaction contract intended for production; only data/integration/authority may differ.

## Prohibited implementation shortcuts
- inventing SLOs, thresholds, source systems, environments, topology or corporate authority;
- copying concept-image data as fixtures without explicit deterministic fixture semantics;
- using visual adjacency as causality;
- fabricated AI confidence/probabilities;
- making unsupported AI recommendations authoritative;
- hiding unavailable data behind healthy/zero states;
- individual ranking/scoring;
- source-token tests as sole behavioral assurance;
- replacing all surfaces with one graph;
- reducing S5 to CRUD/tasks;
- declaring outcome from execution completion.

## State completeness
Each applicable surface must support:
- loading;
- empty;
- healthy/no-attention negative control;
- partial;
- stale;
- unavailable/missing;
- error/retry;
- authorized read / denied mutation where applicable;
- successful action;
- outcome pending;
- outcome verified;
- outcome persistent/unverifiable where canonical semantics support it.

## Implementation sequencing
Recommended dependency order:
1. shared visual tokens/layout + Context Envelope;
2. canonical projection/view-model deltas;
3. S1/S2 navigation continuity;
4. S3 operational context;
5. S4 investigation;
6. decision/action lineage;
7. S5 experience migration;
8. outcome return propagation;
9. adversarial scenario fixtures;
10. behavioral/accessibility/engineering assurance.

This sequence may be adapted if repository dependency evidence requires it.

## Gates
- Knowledge Re-Challenge: PASS
- Experience-to-Surface Traceability: PASS
- S1 Expectation: PASS
- S2 Expectation: PASS
- S3 Expectation: PASS
- S4 Expectation: PASS
- S5 Expectation: PASS
- Cross-surface E2E contract: PASS WITH IMPLEMENTATION GAPS
- Semantic normalization: PASS at contract level
- Runtime implementation: NOT STARTED under V3
- Behavioral assurance: NOT STARTED under V3
- Production readiness: NOT CLAIMED

## Decision
**READY FOR IMPLEMENTATION — LOCAL/DEMO EXPERIENCE SCOPE.**

This authorizes implementation on `experiment/vector-knowledge-rechallenge-v2` only.
It does not authorize changes to VECTOR `main` or the preserved stable pilot branch.
