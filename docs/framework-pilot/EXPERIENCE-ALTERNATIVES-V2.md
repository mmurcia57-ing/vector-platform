# VECTOR — Experience Alternatives V2

Status: **ALTERNATIVE CHALLENGE COMPLETE — MATERIALIZATION REQUIRED**
Basis: Knowledge Re-Challenge V2 + expanded Engineering Intelligence KB.
Runtime: **UNCHANGED**.

## Decision method
Alternatives are compared against VECTOR's accepted product intent and the expanded KB. This is not a visual-style contest.

Required product properties:
- persistent operational context;
- evidence-backed relationships;
- spatial and temporal orientation when task-relevant;
- explicit epistemic separation;
- investigation → governed decision → commitment/action → execution evidence → outcome verification;
- progressive disclosure;
- system/process focus, no individual ranking;
- bounded complexity and accessible alternatives.

## Alternative A — Context-Centered Workspace

### Thesis
The stable center of VECTOR is the **selected operational context** (Area/Service/Risk/Condition), not a dashboard page or permanent graph.

### Structure
```
GLOBAL ATTENTION / SEARCH / PERIOD
┌──────────────┬──────────────────────────────────┬─────────────────────┐
│ Context      │ Active Lens                      │ Evidence/Intelligence│
│ Area         │ Overview | Topology | Time       │ facts               │
│ Service      │ Evidence | Investigation         │ derived findings    │
│ Risk         │                                  │ hypotheses/limits   │
└──────────────┴──────────────────────────────────┴─────────────────────┘
│ Decision → Commitment/Action → Execution Evidence → Outcome          │
└───────────────────────────────────────────────────────────────────────┘
```

Topology, timeline, trace/evidence and action/outcome are **lenses over the same selected context**.

### Strengths
- best match to KB convergence that multiple representations answer different questions;
- avoids making topology decorative or universally dominant;
- strong context continuity;
- naturally supports progressive disclosure;
- can preserve existing VECTOR canonical semantics with lower NO-REDO risk;
- lets future data sources add lenses without changing product identity.

### Risks
- may feel conventional if lens transitions are implemented as ordinary tabs/pages;
- requires strong interaction design to make context persistence perceptible;
- attention/orientation must remain fast at portfolio level.

### KB trace
ADOPT: Operational Intelligence Workspace, HyperDX contextual focus, Jaeger representation separation, Toise identity/provenance.
ADAPT: Coroot/Pixie topology drill-down.
REJECT: graph dominance for aesthetic identity.

### Disposition
**ADOPT AS BASE ARCHITECTURAL GRAMMAR.**

---

## Alternative B — Investigation-Centered Workspace

### Thesis
VECTOR centers the experience on an **investigation case/condition**. Timeline/evidence are primary; topology appears as a relationship lens.

### Structure
```
ATTENTION → OPEN INVESTIGATION
┌───────────────────────────────────────────────────────────────────────┐
│ Investigation: condition / service / period / evidence quality       │
├──────────────────────────────┬────────────────────────────────────────┤
│ Temporal Evidence Spine      │ Finding / Hypothesis / Limitation      │
│ change · incident · SLO      │ Evidence references                    │
│ action · outcome             │ Relationship lens / topology           │
├──────────────────────────────┴────────────────────────────────────────┤
│ Decision → Action → Verification                                     │
└───────────────────────────────────────────────────────────────────────┘
```

### Strengths
- strongest fit for J01/J02 and evidence-first RCA;
- temporal association/anti-causality can be communicated clearly;
- maps well to Keep/Coroot/Cribl investigation continuity and NightWarden evidence-gated reasoning;
- excellent for deep troubleshooting.

### Risks
- weaker for portfolio/area orientation and non-incident performance management;
- can overfit VECTOR into an incident/RCA product;
- commitments/outcomes not born from a formal investigation may become second-class.

### KB trace
ADOPT: evidence-gated investigation, timeline/change context.
ADAPT: Keep incident workspace, Coroot RCA, Radar/Robusta temporal changes.
REJECT as universal shell because VECTOR mission exceeds incident investigation.

### Disposition
**ADAPT AS A DEEP MODE, NOT THE PRODUCT'S UNIVERSAL CENTER.**

---

## Alternative C — Execution-Continuity Workspace

### Thesis
VECTOR centers the product on the **closed-loop reliability/performance lifecycle**:
`Finding → Decision → Commitment/Action → Execution Evidence → Outcome Verification`.

### Structure
```
ATTENTION / FINDINGS
┌───────────────────────┬──────────────────────┬────────────────────────┐
│ Why attention exists  │ Decision / Commitment│ Verification           │
│ evidence / time / map │ action / due/history │ outcome evidence       │
└───────────────────────┴──────────────────────┴────────────────────────┘
         ↑ persistent source context and evidence lineage ↑
```

### Strengths
- strongest differentiation hypothesis from the OSS matrix;
- directly preserves EXT-004 semantics;
- makes `action completed ≠ outcome verified` visible;
- supports management/process intelligence without individual scoring;
- closes the part of the loop many observability products fragment.

### Risks
- weak as the sole shell for exploratory technical investigation;
- can regress into task/commitment management software if evidence/orientation is secondary;
- requires careful linkage to operational context to avoid CRUD/workflow feel.

### KB trace
ADOPT: Investigation-to-Action Continuity.
ADAPT: Keep/OneUptime/Robusta action/workflow patterns.
VECTOR-specific retained semantic: explicit outcome verification and commitment lifecycle.
REJECT as sole experience center.

### Disposition
**ADOPT AS PERSISTENT LIFECYCLE SPINE, NOT SOLE SHELL.**

---

## Historical Candidate D — Topology-Dominant Dual Canvas

### Thesis
Topology is the dominant operational field with persistent timeline and intelligence inspector.

### Strengths
- materially different from dashboard baseline;
- strong spatial orientation and technical character;
- validated as visually closer to the desired ambition.

### Risks exposed by expanded KB
- graph dominance is not supported for every VECTOR task;
- can imply relationships/causality through composition;
- may over-prioritize spectacle over decision fit;
- portfolio, commitment and outcome work can become appendages;
- one canvas risks conflating distinct representations.

### Disposition
**ADAPT / DEMOTE.** Preserve its spatial interaction quality as the Topology lens and as a North-Star visual-language reference. Reject it as the universal product shell.

---

# Synthesis — Candidate E: Context + Continuity Workspace

The evidence does not support selecting A, B or C in isolation. The strongest candidate is a controlled synthesis:

- **A provides the shell:** persistent selected context.
- **B provides the deep investigation mode:** evidence/time/hypotheses with topology when useful.
- **C provides the lifecycle spine:** decision → commitment/action → execution evidence → verified outcome.
- **D contributes spatial/technical visual ambition:** topology lens, focus/expand mechanics and advanced interaction language without graph dominance.

## Candidate E grammar

```
ATTEND
  ↓
SELECT / PRESERVE CONTEXT
  ├── ORIENT: overview / topology / time
  ├── INVESTIGATE: evidence / findings / hypotheses / limitations
  └── EXECUTE: decision / commitment / action
                         ↓
                 EXECUTION EVIDENCE
                         ↓
                  VERIFY OUTCOME
                         ↓
                    LEARN / CLOSE
```

All modes operate on a shared Context Envelope:
- selected Area/Service/Risk/Condition;
- period/time window;
- evidence quality/freshness;
- provenance/identity state;
- scenario/demo boundary where applicable.

## Material difference from prior designs
- Not dashboard-first.
- Not graph-first.
- Not incident-first.
- Not commitment/task-manager-first.
- Context is the stable center; representations change according to the decision/task.
- The execution/outcome chain remains visible without forcing every investigation into a CRUD workflow.

## Epistemic requirements
Every representation preserves:
- source fact;
- deterministic finding;
- association/correlation;
- hypothesis;
- limitation/unknown;
- governed decision;
- commitment/action;
- execution evidence;
- verified outcome.

Relations:
- canonical/source-backed;
- derived/inferred;
- unresolved identity association;
must not share indistinguishable semantics.

## Decision
**Candidate E — Context + Continuity Workspace: SELECT FOR EXPERIENCE TARGET MATERIALIZATION.**

This is a design hypothesis selected for materialization, not runtime implementation approval.

## Next gate
Materialize Candidate E at sufficient fidelity to test:
1. whether context truly persists across lenses;
2. whether Topology feels powerful without dominating;
3. whether Timeline/Evidence supports J02 and anti-causality;
4. whether Investigation preserves evidence/provenance;
5. whether Decision/Commitment/Action/Outcome feels like one continuity chain;
6. whether the experience remains materially more advanced than the stable dashboard baseline;
7. whether it supports healthy/stale/partial/unknown/outcome-pending scenarios.

Only after Expectation Challenge may an updated Experience Contract authorize runtime redesign.
