# EXT-005 — Demonstrable Experience & Scenario Assurance

## 1. Status
- Type: controlled product/experience assurance extension
- Origin: rigorous re-evaluation against Knowledge Base + Framework + current pilot implementation
- Status: **READY_FOR_IMPLEMENTATION**
- Scope: current local V1 demonstrability and interaction completeness
- Canonical/domain semantics: PRESERVED
- EXT-003/EXT-004 semantics: PRESERVED
- New master capability: NONE
- Corporate production claims: NONE
- SPEC-BLOCKER: 0 for local scenario-driven implementation

## 2. Why this extension exists
Previous convergence proved substantial implementation, contracts and engineering assurance, but overstated the strength of the **demonstrable product experience**.

Knowledge Base challenge criteria require:
- a happy-path screenshot is not complete interaction design;
- a static mock alone is not READY for stateful software;
- important tasks should be challenged across applicable interaction/system states;
- prototypes are warranted where navigation, complex manipulation, progressive disclosure or comprehension remain uncertain.

The current VECTOR implementation still contains controls that are visually represented but not behaviorally demonstrable, a relationship list that does not yet satisfy the intended graph-investigation experience, and insufficient executable scenario coverage for the state matrix already required by EXT-003.

Therefore:
> Rendered != Interactive != Demonstrable != Assured.

## 3. Product findings

### DX-01 — Static tab affordances
Service, Risk and Commitment surfaces render tab-like labels such as SLO, Incidents, Changes, Evidence, Timeline, Relationships, Actions and Results, but several are static `span`/`b` elements rather than functioning navigation or view controls.

Requirement:
- every element visually presented as an actionable tab/control must either function in the demo or be rendered unambiguously as non-interactive information;
- no false affordance.

### DX-02 — Graph is represented, not yet demonstrable as investigation
Current graph renders typed relationship rows and permits focus selection. This preserves semantics but does not yet close EXT-003 PD-06 as an investigative interaction.

Required demo behavior:
- visible nodes/typed edges;
- selected/focus node;
- bounded user-controlled expansion;
- detail/evidence context for selected node;
- visible truncation/freshness/limitation state;
- no unrestricted hairball;
- correlation never presented as causation;
- non-graph accessible alternative for decision-relevant information.

The graph may use React Flow or another fit implementation; renderer choice is subordinate to behavior.

### DX-03 — Scenario coverage is too narrow
The demo must not depend on one dataset state/happy path.

Create deterministic demo scenarios/fixtures that exercise materially different product conditions.

Minimum scenario pack:
1. **Persistent reliability risk** — J01, recurrence/history/evidence.
2. **Change-associated degradation** — J02, before/during/after, association != causation.
3. **Structural improvement verified** — J03 with `IMPROVED`.
4. **Action completed, outcome pending** — completion != improvement.
5. **Persistent after intervention** — J03 with `PERSISTENT`.
6. **Leadership/area attention** — J04 with multiple services/conditions and drill-down.
7. **Partial/stale evidence** — usable partial intelligence with visible freshness/missing context.
8. **Conflicting/insufficient evidence** — no silent winner/no fabricated conclusion.
9. **Unauthorized mutation** — denied action without data leakage.
10. **Dependency unavailable/recovery** — isolated degradation and retry.
11. **Graph investigation** — bounded expansion/focus/evidence context.
12. **AI unavailable** — safe non-authoritative degradation.

A scenario selector may be local/demo-only. It must not be represented as a production feature.

### DX-03B — Expanded adversarial operational scenario catalog

The minimum 12-scenario pack is a floor, not sufficient breadth for VECTOR product validation.

The demo/assurance catalog SHALL exercise materially different failure and non-failure conditions across reliability, change, recurrence, evidence, execution and dependency context.

#### Reliability / service condition
13. **SLO burn without incident** — degraded objective evidence exists before/without an Incident record; VECTOR must not require an incident to surface attention.
14. **Incident spike with SLO still inside objective** — operational events exist but available objective evidence does not justify declaring SLO failure.
15. **Latency degradation with stable error rate** — one signal degrades while another remains stable; avoid collapsing service health into one synthetic verdict.
16. **Error-rate degradation with stable latency** — inverse multi-signal condition.
17. **Intermittent/flapping condition** — repeated short degradations separated by apparent recovery; recurrence/history must remain visible.
18. **Recovery after degradation** — current condition recovered while historical risk/evidence remains inspectable; current state != erased history.
19. **Multiple simultaneous service risks** — one Service has distinct supported RiskFindings; do not merge them into one generic risk.
20. **Area concentration across services** — multiple services in one AreaDomain require attention for different reasons; Area view must support comparison rather than generic scoring.

#### Change / temporal association
21. **Degradation begins after a change** — temporal association is visible; causality remains unproven.
22. **Degradation predates the change** — VECTOR must prevent the change from being presented as origin merely because it is nearby in time.
23. **Change during an existing degradation** — before/during/after context must preserve pre-existing condition.
24. **Multiple changes inside the observation window** — evidence is insufficient to attribute the condition to a single change without additional support.
25. **Rollback followed by recovery** — recovery is observable after rollback, but causal language remains bounded by evidence.
26. **Successful change with no degradation** — change presence alone must not generate a risk/failure narrative.

#### Recurrence / problem intelligence
27. **Repeated incidents with same supported pattern** — recurrence should be surfaced with evidence/history.
28. **Similar incidents with insufficient identity resolution** — do not silently merge potentially different problems.
29. **Temporary fixes followed by recurrence** — completed actions exist but condition returns; execution != structural improvement.
30. **Long quiet period followed by recurrence** — historical relationship remains discoverable without implying continuous degradation.

#### Commitment / execution / outcome
31. **Commitment overdue without renegotiation** — due-date reliability impact is explicit.
32. **Commitment renegotiated before due date** — immutable history preserved; not classified the same as silent lateness.
33. **Commitment completed with no outcome evidence** — outcome remains pending.
34. **Action completed and condition improved** — outcome may become IMPROVED only with comparable supporting evidence.
35. **Action completed and condition persists** — PERSISTENT remains visible; completion does not hide failure to improve.
36. **Multiple actions against one risk** — preserve action/evidence/outcome lineage instead of crediting the latest action automatically.
37. **Outcome evidence conflicts** — no fabricated winner; limitation/conflict visible.

#### Evidence / observability quality
38. **Telemetry source unavailable** — dependency/data-source degradation is visible and does not become a false healthy state.
39. **One source stale, another fresh** — freshness is source/evidence specific; avoid flattening all evidence into one timestamp.
40. **Missing source authority** — evidence may be displayed with its authority boundary; no promotion to authoritative fact.
41. **Duplicate evidence records** — avoid inflating confidence/recurrence from duplicates.
42. **Conflicting signals** — e.g. one observation supports degradation while another does not; expose disagreement.
43. **Observation gap** — no data for a time interval; absence of evidence != evidence of normal operation.
44. **Late-arriving evidence** — temporal semantics distinguish observed/event time from ingestion/availability time.
45. **Identity-resolution ambiguity** — unresolved entity mapping blocks unsafe correlation.

#### Dependency / topology / blast-radius reasoning
46. **Shared dependency with multiple affected services** — topology helps discover common context; common dependency != proven root cause.
47. **Dependency degraded but selected service unaffected** — avoid propagating failure merely because a relationship exists.
48. **Partial topology/truncated graph** — user sees boundedness and can expand within limits.
49. **Relationship exists with stale provenance** — relationship freshness/authority is visible.
50. **High-degree node** — graph remains bounded/readable and provides accessible non-graph inspection.

#### Security / control / degraded product behavior
51. **Read allowed, mutation denied** — investigation remains usable while unauthorized state change is rejected safely.
52. **Expired/invalid authorization context** — no data leakage and clear recovery path.
53. **Backend projection dependency timeout** — affected surface degrades independently where possible and supports retry.
54. **AI provider unavailable** — deterministic evidence workflow remains usable.
55. **AI suggestion conflicts with deterministic evidence** — AI remains advisory and cannot override governed evidence.
56. **Unsupported scenario/context token** — explicit unsupported/empty state; never silently fall back to a misleading healthy dataset.

#### Negative controls
57. **Healthy/normal evidence set** — VECTOR must be able to show no supported attention finding; a demo must not manufacture risk.
58. **Change-only normal scenario** — normal change activity without degradation must remain non-problematic.
59. **Incident resolved with verified recovery** — preserve history while current condition is recovered.
60. **Insufficient evidence for any conclusion** — explicit UNKNOWN/INSUFFICIENT state rather than ATTENTION by default.

#### Scenario acceptance rule
Each scenario must specify:
`scenario → source facts → expected derived/non-derived conclusion → affected view → user decision → prohibited inference → expected state/telemetry → executable test`.

A scenario is not complete because it appears in a selector. It is complete only when the data differs materially, the product behavior differs appropriately, and the expected conclusion/prohibited inference is executable.

#### Capability-gap rule
If the current canonical/projection model cannot represent a scenario without fabricating semantics, classify it as **PRODUCT CAPABILITY GAP** rather than forcing it into existing fields.

### DX-04 — Experience State Matrix lacks executable proof
EXT-003 already requires state completeness. Current executable frontend evidence does not demonstrate the full matrix across critical surfaces.

Required:
`Scenario → Task → State → User action → UI behavior → Contract/data → Policy → Backend behavior → User feedback → Telemetry → Test evidence`.

At minimum, each critical surface must have executable proof for applicable:
- loading;
- populated;
- empty;
- partial/stale;
- conflict/insufficient evidence;
- permission denied;
- dependency error;
- retry/recovery.

### DX-05 — Visible context controls are not controls

The header renders `Últimos 30 días` and `Contexto local` as static labels.

Observed implementation:
- period display is not user-selectable;
- API context uses a fixed `local-dataset-v1` period token;
- `Contexto local` does not expose an inspectable/selectable context model to the evaluator.

This conflicts with the existing interaction contract where Period is shared analytical context and applicable context changes coordinate dependent projections.

Requirement:
- Period must become a real control for the demo, with deterministic scenario-backed options and coordinated refresh; or the UI must explicitly label a fixed dataset context without false filter affordance.
- Replace ambiguous `Contexto local` wording with a defined inspectable context concept. If it represents demo/local data source context, make that explicit and non-production.
- Every visible filter/context chip must map to state + behavior + evidence, or be visually non-actionable explanatory metadata.

### DX-06 — Language decision lost from governed baseline

A pending Spanish-language capability was expected by product intent but is not traceable in the inspected UX/interaction specification.

Classification: **INTENT/SPEC TRACEABILITY GAP**.

Requirement:
- establish the authoritative language requirement before implementation;
- for the current local demo, remove mixed English/Spanish user-facing copy;
- if language switching is confirmed in scope, implement a real locale control and scenario/test evidence rather than a decorative selector;
- do not invent additional supported languages.

### DX-07 — Configuration surface missing from product model

No first-class Configuration/Settings experience is traceable in the inspected UX/interaction baseline.

Classification: **PRODUCT DISCOVERY GAP / REQUIREMENT TO RECONCILE**, not permission to invent settings.

Required reconciliation:
- identify which user-adjustable concerns genuinely require configuration in current scope (for example locale, demo context/period, display/accessibility preferences, integration/source context only if authorized);
- separate user preferences from administrative/integration configuration;
- define permissions, persistence and defaults before implementing stateful settings;
- if no current-scope setting is justified, explicitly record the non-goal.

### DX-08 — Behavioral assurance is too shallow
Current Testing Library coverage demonstrates workspace grammar/navigation rail but does not execute J01–J04 as complete user scenarios. Source-text tests are supplementary, not behavioral assurance.

Required:
- component/integration tests for meaningful controls;
- J01–J04 scenario tests;
- graph interaction test;
- commitment lifecycle user interaction tests;
- negative/denied/recovery tests;
- accessibility checks for critical interactive paths;
- contract-backed fixture consistency.

## 4. Demonstrable experience model

```
Scenario
  ↓
Signal / Context
  ↓
User decision question
  ↓
Interactive control
  ↓
State transition / navigation
  ↓
Evidence / relationship / action
  ↓
Outcome or explicit limitation
  ↓
Executable assertion
```

A demo scenario is complete only when a reviewer can perform the intended task rather than infer it from labels.

## 5. Visible Affordance Inventory Gate

Before demo convergence, inventory every visible element that communicates actionability or selectable context.

Minimum columns:
`Surface | Visible element | User expectation | Intended behavior | Implemented? | State source | Scenario | Behavioral test | Disposition`.

The inventory MUST include at least:
- primary navigation;
- every tab;
- buttons;
- links;
- filters;
- period/date controls;
- context selectors/chips;
- language/locale control when in scope;
- configuration entry points when in scope;
- graph nodes/expand/focus controls;
- retry/recovery controls;
- commitment lifecycle controls.

A visible element may be classified:
- FUNCTIONAL;
- INTENTIONALLY INFORMATIONAL;
- DISABLED WITH EXPLANATION;
- OUT OF SCOPE AND REMOVED;
- GAP.

No GAP may remain in a demo-convergence PASS.

## 6. Demo acceptance matrix

| Surface | Required demonstrable behavior |
|---|---|
| Command | select attention item and enter relevant context |
| Area | select service/risk and preserve area context |
| Service | inspect meaningful operational categories or remove false tab affordances |
| Risk | inspect timeline/evidence/change association/relationships/actions |
| Graph | focus + bounded expand + inspect context + accessible alternative |
| Commitments | create, transition, renegotiate and observe resulting state |
| Outcome | distinguish execution complete / pending / improved / persistent |
| Quality | expose stale/partial/conflict/insufficient states |
| Security | demonstrate denied mutation safely |
| Recovery | demonstrate dependency failure and retry |
| AI | demonstrate available/unavailable boundary without authority inflation |

## 7. Demo Quality Gate
PASS only if:
1. no false interactive affordances remain;
2. J01–J04 are executable as user scenarios;
3. minimum scenario pack is deterministic and selectable/testable;
4. graph investigation is demonstrable beyond relationship rows;
5. state-matrix evidence exists for critical surfaces;
6. tests exercise behavior rather than only source tokens;
7. demo limitations are explicit;
8. visual polish does not substitute for interaction proof.

## 8. Work packages
- WP1 Scenario fixture/model + local scenario selector/harness.
- WP2 Visible Affordance Inventory; implement/remove/reclassify static tabs, period/context controls and other false affordances.
- WP2B Reconcile language and Configuration/Settings product intent before implementation.
- WP3 Graph investigation interaction.
- WP4 J01–J04 executable demo paths.
- WP5 State/degradation/permission/recovery scenarios.
- WP6 Behavioral + accessibility + contract-backed assurance.
- WP7 Visual/interaction QA and demo script.
- WP8 Convergence evidence and reclassification.

Dependencies:
`WP1 → {WP2,WP3,WP4,WP5} → WP6 → WP7 → WP8`.

## 9. Boundary
This extension improves the local demonstrable product. It does not:
- invent corporate data;
- claim production readiness;
- add POST-V1 capabilities;
- introduce individual ranking;
- replace valid EXT-003/004 semantics;
- require a redesign merely for novelty.

## 10. Gate
- Product gap demonstrated: PASS
- Knowledge challenge applicable: PASS
- Current implementation evidence inspected: PASS
- Controlled delta defined: PASS
- No master scope inflation: PASS
- Acceptance evidence defined: PASS
- SPEC-BLOCKER: 0

**EXT-005: READY_FOR_IMPLEMENTATION**
