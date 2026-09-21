# VECTOR — Full Knowledge Base Redesign Challenge

## Status
**FULL APPLICATION REDESIGN REQUIRED — FUNCTIONAL PROTOTYPE HYPOTHESIS**

This challenge supersedes the narrower assumption that correcting the existing multi-page layout is sufficient.

## Trigger
The complete Engineering Intelligence Knowledge Base was inventoried (63 repository artifacts) after the current VECTOR design remained visually and interactionally too close to a conventional card dashboard and underused graph/topology/temporal investigation patterns present in the corpus.

The KB is used as a challenge corpus, not a style catalog. Every applicable item must result in ADOPT / ADAPT / REJECT / DEFER with evidence boundary.

## Knowledge-to-design dispositions

| KB source/pattern | Candidate implication for VECTOR | Disposition | Boundary |
|---|---|---|---|
| progressive-disclosure-command-center | Decision-first hierarchy, progressive depth, visible time/context, topology/timeline/evidence | ADOPT | Pattern is hypothesis; validate with tasks |
| product-ux-functional-engineering | Journey/task/IA before visual novelty; prototype complex interaction | ADOPT | Visual novelty never substitutes usability |
| experience-state-matrix | Critical surfaces expose loading/empty/stale/partial/denied/recovery | ADOPT | Must become executable |
| reference-to-design-evidence | References generate alternatives, not copied requirements | ADOPT | Preserve provenance |
| Dynatrace Smartscape | Topology as contextual troubleshooting/traversal | ADAPT | Do not duplicate Dynatrace topology automatically |
| ServiceRadar | Telemetry + topology + graph + interactive visualization + blast-radius concept | ADAPT | Causal/performance claims remain project-reported |
| Toise | Temporal topology/current+historical state | ADAPT | Alpha upstream; do not adopt implementation blindly |
| OTel Entities | Identity governs topology merge | ADOPT | Spec still evolving; version exact schema later |
| Backstage Catalog Graph | Ownership/catalog graph distinct from runtime topology | ADOPT | Do not collapse graph semantics |
| OpenSRE | Investigation + specialists + topology + episodic context | ADAPT | Product UI need not copy agent architecture |
| RCA Agent | deterministic evidence first, probabilistic hypothesis second | ADOPT | Hypothesis never becomes causal fact automatically |
| evidence-first-agentic-RCA | signal/evidence/topology/hypothesis/validation/provenance | ADOPT | Framework pattern still hypothesis until lab evidence |
| ATSMATRIX | dynamic node/edge/status/message-flow interaction grammar | ADAPT FOR PROTOTYPE | Visual reference only; no production-scale claim |
| technical-ui-and-graph-rendering | choose React Flow/Cytoscape/Sigma by graph task/scale/algorithm needs | ADOPT | No library selected by aesthetics |
| Graphify | interactive graph as human exploration surface | ADAPT | Code KG != runtime topology |
| GraphRAG | graph/community retrieval may enrich relationship reasoning | DEFER | Requires experiment; not required for V1 UI |
| agent-observability | agent/tool evidence and traces may be observable | DEFER/POST-V1 | Do not inflate current V1 |
| UX/accessibility baseline | high-density UI still needs hierarchy, keyboard, states, responsive behavior | ADOPT | Futuristic != inaccessible |
| frontend-backend-contracts | interactive capabilities map to contracts/errors | ADOPT | Demo parity still required |
| security/authorization | principal+resource+action+context policy | ADOPT boundary | Corporate IAM specifics TBD |

## Redesign thesis

VECTOR should not be a collection of similarly shaped dashboards.

It should behave as an **Operational Intelligence Workspace** with three coordinated visual grammars:

### 1. Command / Attention Canvas
Purpose: executive and operational triage.

Visual grammar:
- high-density but progressive command surface;
- areas/services represented by condition and evidence-backed attention, not people scores;
- compact trends and temporal change;
- selected attention opens the same persistent investigation context;
- current period/data-quality/source context always visible.

### 2. Live Operational Topology
Purpose: understand service/dependency context and blast-radius candidates.

Visual grammar:
- services/CIs/dependencies as bounded nodes/typed edges;
- overlays for supported incidents, changes, events, SLO/metric conditions and active risk findings;
- time/context scrub or bounded temporal comparison where data supports it;
- focus/expand/return;
- freshness/provenance/identity state;
- accessible list/table equivalent;
- no relationship or visual proximity implies causality.

This is inspired by Smartscape + ServiceRadar + Toise + ATSMATRIX interaction grammar, adapted to VECTOR semantics.

### 3. Investigation / Evidence Workspace
Purpose: explain a selected condition and support action.

Visual grammar:
`signal → timeline → evidence → topology/scope → change association → hypothesis/limitation → commitment/action → outcome verification`

Observed facts, deterministic derived findings, AI hypotheses and human decisions must be visually distinguishable.

## Proposed application shell

```
┌──────────────────────────────────────────────────────────────────────────────┐
│ VECTOR | Context / Period | Evidence Quality | Search | Demo boundary       │
├───────────────┬───────────────────────────────────────┬──────────────────────┤
│ COMMAND       │                                       │ INTELLIGENCE CONTEXT │
│ Areas         │       OPERATIONAL CANVAS              │ Evidence             │
│ Services      │  topology / timeline / attention      │ Changes              │
│ Investigate   │  coordinated by selected context      │ Incidents            │
│ Actions       │                                       │ Hypotheses/limits     │
│ Outcomes      │                                       │ Provenance            │
├───────────────┴───────────────────────────────────────┴──────────────────────┤
│ TEMPORAL / EVENT SPINE: signals · incidents · changes · actions · outcomes  │
└──────────────────────────────────────────────────────────────────────────────┘
```

This is not a final pixel design. It is the next structural prototype hypothesis.

## View implications

### Panorama
Transform from KPI/card dashboard into Command/Attention Canvas:
- portfolio/area condition landscape;
- temporal attention movement;
- evidence/data-quality boundary;
- selected area/service enters persistent context;
- topology preview can show concentration/shared dependency context where supported.

### Area
Retain service portfolio concept but integrate topology/condition map and temporal attention instead of only rows/panels.

### Service
Become operational cockpit:
- selected service centered in bounded topology;
- surrounding dependencies;
- temporal signal/event/change spine;
- SLO/metric condition lanes;
- risk/evidence sidecar;
- drill into investigation without losing context.

### Risk
Become evidence-first investigation:
- deterministic facts first;
- temporal sequence;
- topology scope;
- change association;
- hypotheses/limitations explicitly separate;
- action/outcome continuation.

### Commitments
Remain governed workflow/ledger, but selected commitment links back to originating risk/service/evidence and forward to outcome verification.

## Futuristic design constraint
“Futuristic” SHALL mean:
- spatial/contextual continuity;
- dynamic but purposeful topology;
- coordinated temporal views;
- high information density with progressive disclosure;
- explicit provenance/freshness;
- fluid focus/expand/drilldown/return;
- visually distinct observed fact vs derived finding vs hypothesis vs decision;
- real state transitions and scenario changes.

It SHALL NOT mean:
- neon decoration without task value;
- animated graph hairballs;
- fake live telemetry;
- invented AI confidence;
- visual causal arrows unsupported by evidence;
- sacrificing accessibility/readability.

## Structural prototype candidates

### R1 — Contextual Operations Canvas
Persistent topology/canvas at center; command, evidence and timeline coordinate around selection.

### R2 — Temporal Investigation Workspace
Timeline/event spine dominates center; topology expands as contextual lens.

### R3 — Dual Canvas
Command/portfolio canvas for breadth; investigation canvas for depth, sharing persistent context and visual grammar.

**Leading hypothesis: R3 Dual Canvas**, because executive triage and deep investigation are different tasks while still requiring continuity.

R3 is not adopted until a functional prototype demonstrates J01–J04 and adversarial scenarios better than the current baseline.

## Mandatory prototype comparison
Compare current baseline vs R1/R2/R3 using:
- J01 persistent reliability risk;
- J02 degradation-after-change;
- J02 anti-causality control: degradation-before-change;
- J04 multi-service area concentration;
- shared-dependency topology scenario;
- partial/stale evidence;
- healthy negative control;
- commitment action/outcome continuation.

Record:
`task → baseline friction → candidate behavior → comprehension → context preservation → prohibited inference risk → accessibility → implementation implication → disposition`.

## Gate
- Full KB inventory: PASS
- Broad design challenge: PASS
- Existing layout protected by NO-REDO: NO
- Current corrected layout: BASELINE ONLY
- Alternative B: REOPENED / insufficiently broad
- R1/R2/R3 prototype comparison: REQUIRED
- Leading hypothesis: R3 Dual Canvas
- Visual system redesign: REQUIRED
- Graph/topology interaction redesign: REQUIRED
- Knowledge Challenge: OPEN until prototype dispositions are evidenced
- Functional Demo Convergence: FAIL
