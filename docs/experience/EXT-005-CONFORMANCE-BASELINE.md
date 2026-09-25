# EXT-005 — Initial Experience Conformance Matrix

Baseline: main @ 13c8996424af569444efab559fb740e4e18fb345
Status: PRE-IMPLEMENTATION BASELINE

| Rule | Baseline | Primary evidence / gap | Target |
|---|---|---|---|
| EL-01 Context Preservation | PASS | route/query/session context and breadcrumbs | Preserve |
| EL-02 Focus Hierarchy | PARTIAL | selected focus exists; Near/Global hierarchy inconsistent | PASS |
| EL-03 Entity → Space | FAIL | Area/Service/Risk remain page/panel compositions | PASS |
| EL-04 Semantic Zoom | FAIL | route/view replacement; no representation-level granularity transition | PASS |
| EL-05 Progressive Disclosure | PARTIAL | drill-down exists; views expose precomposed blocks | PASS |
| EL-06 Existence != Relation | PASS | typed graph relationships | Preserve |
| EL-07 Evidence-Bound Relationships | PASS | BFF graph relations carry evidence/source references | Preserve |
| EL-08 Cardinality Adaptation | FAIL | lists/cards scale by adding objects | PASS |
| EL-09 Subset within Universe | PARTIAL | logical context retained; broader universe loses perceptual presence | PASS |
| EL-10 Relationship Lens | PARTIAL | bounded graph exists but is primarily a source-predicate-target list panel | PASS |
| EL-11 Informational Space | FAIL | grid/panel layout dominates; space carries little model meaning | PASS |
| EL-12 3D Justification | N/A | no 3D requirement | N/A unless introduced |
| EL-13 2D / Spatial Complementarity | PARTIAL | exact 2D evidence exists; spatial complement absent | PASS |
| EL-14 Anti-Dashboard | FAIL | gold-metrics/gold-main-grid/gold-panel dominate | PASS |
| EL-15 No Invention | PASS | current inspected projection remains evidence/model bound | Preserve |
| EL-16 Context Transformation | FAIL | focus opens another composition rather than transforming context | PASS |
| EL-17 Execution != Verified Outcome | PASS | execution/outcome separation explicit | Preserve |
| EL-18 Evidence Resolution Fidelity | PARTIAL | observedAt rendered; no presentation-level precision guardrail | PASS |

## Hard regression constraints

1. Do not change canonical entity/relationship semantics.
2. Do not weaken bounded graph limits.
3. Do not infer absent evidence.
4. Do not turn temporal association into causation.
5. Do not make COMPLETED imply IMPROVED.
6. Do not satisfy Anti-Dashboard merely by restyling cards.
7. Do not claim semantic zoom from animation/camera movement alone.
8. Do not promote experiment-specific aesthetics to product requirements.
