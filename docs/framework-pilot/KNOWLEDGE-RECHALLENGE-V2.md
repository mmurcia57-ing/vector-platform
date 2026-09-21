# VECTOR — Knowledge Re-Challenge V2

Status: **IN PROGRESS — PRODUCT RUNTIME UNCHANGED**
Branch: `experiment/vector-knowledge-rechallenge-v2`
Baseline: `framework/vector-full-lifecycle-pilot` @ `9b7b23cffaa133c204772ec4d6fcb7d4273c8100`

## Purpose
Re-challenge VECTOR using the expanded Engineering Intelligence Knowledge Base before another runtime redesign.

This branch intentionally starts from the preserved stable pilot baseline. It does **not** inherit the rejected/experimental North-Star runtime migration.

## Framework revision applied
Knowledge Expansion behavior includes:
- autonomous Knowledge Gap detection;
- implementation-first evidence inspection when available;
- contradiction/counterexample search;
- evidence-saturation stopping;
- reusable KB ingestion before product re-challenge;
- Framework regressions FR-052–FR-056.

## New evidence clusters to challenge
1. Evidence-backed service/dependency topology:
   Coroot, HyperDX, Pixie, Caretta, Uptrace, Jaeger UI.
2. Spatial + temporal investigation:
   Skyhook Radar, HyperDX, Robusta, SigNoz, Jaeger UI.
3. Multi-signal operational context:
   OpenObserve, SigNoz, Coroot, Uptrace.
4. Incident/investigation → action:
   Keep, OneUptime, Cribl APM, Robusta.
5. Temporal graph / identity / provenance:
   Toise.
6. Evidence-gated agentic investigation:
   NightWarden plus existing RCA-Agent/OpenSRE KB material.
7. Counterexample/contrast:
   Grafana/Perses dashboard/panel composition as strong visualization systems that do not by themselves establish VECTOR's desired persistent investigation IA.

## Reusable KB patterns now applicable
- Operational Intelligence Workspace
- Evidence-Preserving Entity Resolution
- Investigation-to-Action Continuity
- Evidence-Gated Agentic Investigation

## Product assumptions under challenge

### A1 — “Futuristic” means graph-dominant presentation
**Challenge:** unsupported as a product principle.
The stronger evidence is task continuity and coordinated representations. A graph is appropriate when spatial/dependency orientation answers the current question; it must not dominate merely for aesthetic identity.

### A2 — One graph can carry topology, causality and investigation
**Challenge:** reject.
Topology/dependency, temporal sequence, trace structure, evidence and hypotheses answer different questions. Correlation/proximity must not become causality.

### A3 — Operational relationships can be visually inferred for readability
**Challenge:** tighten.
Canonical edges require identifiable relationship evidence. Inferred/read-side links, if used, must be semantically/visually distinct and preserve provenance.

### A4 — Entity resolution can merge similar observations
**Challenge:** reject by default.
Preserve source identity. Similarity is not identity. Any uncertain equivalence must be represented as reversible, provenance-bearing resolution rather than silent destructive merge.

### A5 — Investigation is the endpoint
**Challenge:** reject for VECTOR.
External implementations are strongest through investigation and increasingly workflow/action. VECTOR's existing product intent extends through commitment/action and explicit outcome verification.

### A6 — Action/workflow completion demonstrates success
**Challenge:** reject.
Execution evidence and verified outcome remain distinct.

### A7 — AI narrative may serve as investigation explanation
**Challenge:** constrain.
AI-generated interpretation must reference available evidence, preserve unresolved candidates/hypotheses and remain distinct from deterministic/source-backed findings. Write/action authority is separately governed.

### A8 — Dashboard-first IA is the safe baseline
**Challenge:** reject as universal default.
Dashboard/panel systems remain useful representations, but VECTOR's end-to-end task requires persistent context across orientation, evidence, investigation, decision, action and verification.

## Revised candidate product grammar

```
ATTEND
  → SELECT CONTEXT
  → ORIENT
      ├─ spatial/dependency when useful
      └─ temporal/change when useful
  → INSPECT SOURCE EVIDENCE
  → INVESTIGATE
      ├─ deterministic findings
      ├─ correlations
      ├─ hypotheses
      └─ limitations/unknowns
  → DECIDE
  → COMMIT / ACT
  → CAPTURE EXECUTION EVIDENCE
  → VERIFY OUTCOME
  → LEARN / CLOSE
```

Representations are coordinated projections over persistent selected context; none is automatically the permanent center of the product.

## Epistemic contract
VECTOR must preserve visible distinctions between:
- observed/source-backed fact;
- deterministic derived finding;
- correlation/association;
- hypothesis;
- limitation/unknown;
- governed decision;
- commitment/action;
- execution evidence;
- verified outcome.

Invariants:
- `similarity ≠ identity`
- `correlation ≠ causation`
- `projection ≠ source truth`
- `action completed ≠ outcome verified`

## Differentiation hypothesis
The strongest current OSS evidence clusters around `ATTEND → INVESTIGATE`, with some products extending into workflow/action. The comparatively weakly integrated area is durable continuity through governed decision, commitment/action and **verified outcome**.

Therefore VECTOR should test—not assume—the differentiation hypothesis:

> Operational intelligence that preserves evidence and context from signal through decision/action to verified outcome.

This is a product hypothesis, not a validated market claim.

## Experience implications for the next candidate
Do not immediately redesign runtime.

The next Experience Target must compare materially different structures, at minimum:
1. **Context-Centered Workspace** — persistent selected operational context; topology/timeline/evidence appear as task-specific lenses.
2. **Investigation-Centered Workspace** — evidence/timeline first; topology is an expandable relationship lens.
3. **Execution-Continuity Workspace** — attention/investigation feeds directly into commitments/actions/outcomes while retaining evidence lineage.

The previous topology-dominant North-Star may compete as a historical candidate, but is not the default winner.

## Gate
Current disposition:
**KNOWLEDGE RE-CHALLENGE READY FOR ALTERNATIVE GENERATION — NOT READY FOR RUNTIME REDESIGN**

Required before runtime changes:
- materialize/compare the three candidate structures;
- preserve baseline as a candidate;
- run Expectation Challenge;
- select/adapt/reject with trace to KB evidence;
- define an updated Experience Contract;
- only then implement on this branch.

## Framework learning validation
This execution is an L2 fixture for:
- FR-052: Knowledge Gap caused autonomous expansion rather than user-operated research;
- FR-053: implementation evidence inspected beyond README/screenshots;
- FR-054: counterexamples/contradictions included;
- FR-055: expansion stopped on decision sufficiency/diminishing new dispositions rather than arbitrary count;
- FR-056: reusable findings ingested into KB before this re-challenge.

Product-specific validation remains pending; these cases are not L3 until corrected product behavior is subsequently exercised.
