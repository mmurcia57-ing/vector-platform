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

### DX-05 — Behavioral assurance is too shallow
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

## 5. Demo acceptance matrix

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

## 6. Demo Quality Gate
PASS only if:
1. no false interactive affordances remain;
2. J01–J04 are executable as user scenarios;
3. minimum scenario pack is deterministic and selectable/testable;
4. graph investigation is demonstrable beyond relationship rows;
5. state-matrix evidence exists for critical surfaces;
6. tests exercise behavior rather than only source tokens;
7. demo limitations are explicit;
8. visual polish does not substitute for interaction proof.

## 7. Work packages
- WP1 Scenario fixture/model + local scenario selector/harness.
- WP2 Remove false affordances or implement their behavior.
- WP3 Graph investigation interaction.
- WP4 J01–J04 executable demo paths.
- WP5 State/degradation/permission/recovery scenarios.
- WP6 Behavioral + accessibility + contract-backed assurance.
- WP7 Visual/interaction QA and demo script.
- WP8 Convergence evidence and reclassification.

Dependencies:
`WP1 → {WP2,WP3,WP4,WP5} → WP6 → WP7 → WP8`.

## 8. Boundary
This extension improves the local demonstrable product. It does not:
- invent corporate data;
- claim production readiness;
- add POST-V1 capabilities;
- introduce individual ranking;
- replace valid EXT-003/004 semantics;
- require a redesign merely for novelty.

## 9. Gate
- Product gap demonstrated: PASS
- Knowledge challenge applicable: PASS
- Current implementation evidence inspected: PASS
- Controlled delta defined: PASS
- No master scope inflation: PASS
- Acceptance evidence defined: PASS
- SPEC-BLOCKER: 0

**EXT-005: READY_FOR_IMPLEMENTATION**
