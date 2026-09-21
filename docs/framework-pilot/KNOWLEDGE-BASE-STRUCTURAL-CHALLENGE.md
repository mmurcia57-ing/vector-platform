# VECTOR — Knowledge Base Structural Challenge

## Status
**STRUCTURAL RE-EVALUATION REQUIRED**

This artifact corrects the earlier research-application approach. It does not assume the current VECTOR information architecture or mock structure is optimal merely because it is implemented.

## 1. Problem discovered
The Engineering Intelligence Knowledge Base contains materially relevant research and patterns, but prior VECTOR iterations used only a subset as validation criteria.

The missing step was:
`Knowledge → challenge current design assumption → alternative → adopt/adapt/reject/defer → product structure change/evidence`.

Therefore prior preservation of the mock/IA is not sufficient evidence of design fitness.

## 2. Applicable KB clusters

### KBC-01 — Product, UX & Functional Engineering
Status: verified synthesis.

Challenges:
- screen/dashboard-first preservation;
- incomplete interaction models;
- weak state completeness;
- static mock treated as implementation-ready.

VECTOR implication:
Re-evaluate IA and interaction model from outcomes/tasks/J01–J04, not from current screens.

Decision: **ADOPT as structural challenge method**.

### KBC-02 — Experience State Matrix
Status: hypothesis-for-pilot.

Challenges:
- single happy-path demo;
- screen inventory hiding loading/empty/denied/stale/error/recovery gaps.

VECTOR implication:
Run the pattern as the intended VECTOR experiment. EXT-005 scenario harness is the product vehicle.

Decision: **ADOPT FOR EXPERIMENT**, not yet universal truth.

### KBC-03 — Graph / technical visualization corpus
Relevant verified/reference candidates include React Flow, Cytoscape.js, Sigma.js and graph/topology references.

Challenges:
- relationship rows accepted as sufficient graph experience;
- renderer selected without demonstrating investigative task fit;
- current graph interaction may be structurally underpowered.

VECTOR implication:
Define the graph user task first, then run a bounded visualization spike comparing at least the current approach with one fit alternative if required. Evaluate focus, progressive expansion, evidence inspection, semantic edge direction, accessibility, performance and boundedness.

Decision: **ADAPT / EXPERIMENT REQUIRED**.

No library is selected by research alone.

### KBC-04 — Runtime topology / SRE intelligence references
Relevant corpus includes topology, observability and SRE references such as Dynatrace topology, Backstage Catalog Graph, OpenSRE, RCA Agent, ServiceRadar and temporal operational graph hypotheses.

Challenges:
- generic dashboard composition;
- weak relationship between service context, topology, evidence, change and investigation;
- insufficient temporal/structural investigative affordances.

VECTOR implication:
Extract interaction/decision patterns, not copy products. Challenge whether Service/Risk investigation should combine topology/relationship context, temporal evidence and decision/action context more coherently than the current static sections.

Decision: **ADAPT / PRODUCT DESIGN STUDY REQUIRED**.

### KBC-05 — End-to-end assurance chain
KB requires trace from user/persona through UX state, frontend, contract, authN/authZ, backend, domain/data, response, telemetry/audit and evidence.

Challenges:
- source-token tests;
- visible controls without behavior;
- mock/runtime divergence.

VECTOR implication:
EXT-005 behavioral/scenario assurance must trace critical interactions through the full chain.

Decision: **ADOPT**.

### KBC-06 — Accessibility/WCAG
Challenges:
- accessibility treated mainly as implementation hardening;
- complex graph interaction without equivalent accessible decision path.

VECTOR implication:
Critical scenario and graph design must include keyboard/focus/name-role-value/status and accessible equivalent evidence.

Decision: **ADOPT**.

### KBC-07 — Reference-to-Design Evidence
Challenges:
- copying visually attractive command-center/graph references;
- conversely, ignoring useful references because product structure already exists.

VECTOR implication:
Every material reference/pattern must be classified and mapped to VECTOR's own user/problem before adoption.

Decision: **ADOPT**.

## 3. Structural questions reopened
The following are reopened for design fitness, not semantic scope:
1. Is the current Technology → Area → Service → Risk hierarchy the best primary navigation, or should part of it become persistent contextual navigation?
2. Should Service Intelligence be structured around static category tabs, an investigative timeline, topology/context, or coordinated views?
3. Should Risk Investigation use Evidence/Timeline/Relations/Correlations/Actions/Outcome as tabs, coordinated panels, progressive disclosure or another structure?
4. What is the actual graph task and which interaction model best performs it?
5. How should period/context filtering persist across the investigative workspace?
6. Where do language/preferences/settings belong?
7. What experience structure best supports J01–J04 without requiring users to reconstruct context?
8. What information density/command-center pattern best serves leadership vs SRE/Operations while retaining one product model?

These questions may change layout, IA and component structure while preserving canonical semantics.

## 4. Constraints that remain CLOSED
Structural re-evaluation MUST NOT silently change:
- 17 canonical entities;
- 18 normative relationships;
- fact/intelligence/correlation/outcome distinctions;
- correlation != causation;
- execution != outcome;
- human accountability;
- no individual ranking/productivity;
- corporate Source Authority/IAM/TBD boundaries;
- approved V1 vs POST-V1 scope.

## 5. Required design experiment
Before EXT-005 implementation is considered converged:

### Phase A — Task/decision model
For J01–J04 define:
- entry trigger;
- decision question;
- information required;
- manipulations/actions;
- context that must persist;
- alternate/degraded states;
- completion signal.

### Phase B — Alternative structures
Produce at least two materially different IA/interaction alternatives for the unresolved structural questions. One may be the current structure as baseline.

Do not produce cosmetic variants.

### Phase C — KB impact matrix
For every applicable KB cluster:
`Knowledge → challenged assumption → alternative → decision → affected artifact`.

### Phase D — Functional demo prototype
Implement the selected structure in the local demo environment with synthetic/scenario data and production-equivalent experience behavior.

### Phase E — Scenario/behavior assurance
Run J01–J04 plus EXT-005 alternate/degraded scenarios and visible-affordance inventory.

## 6. Gate
Current mock structure is **NOT PRESERVED AS FINAL BY NO-REDO**.

It is a baseline candidate that must compete against evidence-backed alternatives.

**KNOWLEDGE-DRIVEN STRUCTURAL DESIGN GATE: OPEN / NOT PASSED.**
