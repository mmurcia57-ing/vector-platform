# VECTOR — Framework L2 Behavioral Regression

## Framework under test
Consolidated lifecycle with mandatory Knowledge Challenge, Functional Demo Parity, Visible Affordance, hierarchical convergence and Framework Regression.

## Execution principle
Known user-reported defects were not used as the execution checklist. The Framework was applied to current specifications, implementation and tests; findings below were rediscovered from evidence.

## Findings

| Regression | Evidence discovered | Gate result |
|---|---|---|
| FR-010 false affordance | Service tabs and Risk tabs are rendered as static `span`/`b` elements under `gold-tabs` | FAIL — Visible Affordance |
| FR-011 hard-coded selectable context | Header renders `Últimos 30 días` while runtime requests use constant `PERIOD = "local-dataset-v1"` | FAIL — Functional Demo Parity |
| FR-022 ambiguous context | Header renders `Contexto local` without an inspectable/selectable context model | FAIL — Visible Affordance / comprehension |
| FR-014 complex visualization | Graph renders relationship rows and permits focus; no bounded user-controlled expansion is present | FAIL — Demonstrable Graph Experience |
| FR-017 source-presence assurance | EXT-003 and EXT-004 frontend tests read `ExperienceViewport.tsx` and regex-match tokens; behavioral Testing Library coverage is limited to workspace rail/legend | FAIL — Behavioral UX Assurance |
| FR-015/016 scenario/state completeness | No deterministic selectable scenario harness proves J01–J04 plus alternate/degraded/denied/recovery states | FAIL — Scenario/State Completeness |
| FR-021 locale consistency | User-facing copy mixes Spanish and English terms; inspected governed UX baseline does not contain the previously expected locale-switch requirement | FAIL — Intent/Spec reconciliation pending |
| FR-013 configuration discovery | No governed first-class settings/configuration decision is evidenced for user-adjustable concerns | OPEN GAP — Configuration Discovery |
| FR-018 demo parity | Current local experience substitutes not only data sources but material behavior/controls | FAIL — Functional Demo Parity |

## Preserved evidence
The regression does not erase:
- canonical/domain semantics;
- existing backend implementation;
- contract/security/durability evidence already proven at its applicable boundary;
- EXT-004 commitment lifecycle semantics;
- current valid primary workspace navigation behavior.

## Hierarchical convergence

| Gate | Result |
|---|---|
| Context Readiness | PASS for current local V1 semantic boundary; targeted intent reconciliation remains for locale/settings |
| Knowledge Challenge | OPEN — structural challenge required |
| Product/Capability Depth | Prior local V1 semantic/path evidence retained; not equivalent to demonstrable UX |
| Structural/Functional Design | OPEN / RE-EVALUATION REQUIRED |
| Functional Demo Parity | FAIL |
| Visible Affordance | FAIL |
| Scenario/State Completeness | FAIL |
| Behavioral UX Assurance | FAIL |
| Engineering Assurance | PASS/PARTIAL by previously evidenced sub-gates; no blanket PASS |
| Production Readiness | NOT CLAIMED |
| Framework Regression L2 | PASS as defect-detection exercise: expected adversarial conditions were rediscovered |

## Interpretation
The consolidated Framework behaves materially differently from the earlier execution: these defects now block experience/overall convergence rather than being hidden by backend/spec/test presence.

This is L2 detection evidence, not L3 corrected-product evidence.

## Next mandatory phase
Execute Knowledge Challenge and structural alternatives before implementing EXT-005. The current UI is a baseline candidate, not protected final structure.
