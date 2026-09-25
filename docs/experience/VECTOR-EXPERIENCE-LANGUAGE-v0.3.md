# VECTOR Experience Language v0.3

Status: EXPERIMENTALLY VALIDATED  
Scope: Experience/design guardrails for VECTOR V1  
Normative boundary: This artifact specializes the CLOSED UX and interaction contracts without reopening or overriding them.

## 1. Purpose

This artifact captures reusable experience principles demonstrated through J04, J03, and J02 visual experiments. It does not redefine the canonical model, journeys, requirements, architecture, Source Authority, or CLOSED SDD decisions.

The CLOSED experience progression remains:

`ATTENTION → CONTEXT → EXPLANATION → EVIDENCE / ACTION / OUTCOME`

The CLOSED investigation progression remains:

`Technology Overview → Area/Domain → Service → RiskFinding → Evidence / Action / Outcome`

## 2. Experience invariants

### EL-01 — Context Preservation
Drill-down preserves enough provenance and broader analytical context for the user to understand where the current focus came from. Replacing one isolated screen with another without continuity fails this rule.

### EL-02 — Focus Hierarchy
The perceptual hierarchy is `Focus > Near Context > Global Context`. The current investigation focus must dominate without erasing the context required to interpret it.

### EL-03 — Entity → Space
An entity that needs to expose internal composition or investigative context may transform into an organizing space while preserving its identity and provenance. This is conditional; not every entity becomes a space.

### EL-04 — Semantic Zoom
A granularity transition changes what is represented and how it is represented. Camera movement or geometric enlargement alone is not semantic zoom.

### EL-05 — Progressive Disclosure
Details and relationships appear when they become pertinent to the current investigation. Future or unrelated context must not be exposed merely because it exists.

### EL-06 — Existence ≠ Relation
Coexistence, grouping, and demonstrated relationship are distinct semantics. Visual proximity must not be interpreted as a relationship.

### EL-07 — Evidence-Bound Relationships
A visual relationship is rendered only when VECTOR can support the relationship with the applicable canonical/evidence context. Composition must not invent edges.

### EL-08 — Cardinality Adaptation
Representation changes with perceptual scale and cardinality: individual objects may become aggregations, clusters, density, or bounded subsets. More data does not imply more simultaneously visible objects.

### EL-09 — Subset ⊂ Universe
An investigated subset must preserve understandable relationship to its broader universe when that universe is required for interpretation.

### EL-10 — Relationship Lens
Relationships may reorganize emphasis around the current focus while preserving necessary universe/context. Only pertinent, supported relationships are exposed; generic hairball graph behavior is not acceptable.

### EL-11 — Informational Space
Position, depth, scale, layers, density, containment, and grouping must serve an interpretable function. Scenic spatiality without informational purpose is not a VECTOR requirement.

### EL-12 — 3D Justification
A third spatial dimension is justified only when it materially improves containment, orientation, focus/context, density management, or granularity transition. If removing 3D causes no functional loss, 3D is not required.

### EL-13 — 2D / Spatial Complementarity
Exact quantities, text, evidence, navigation, and controls may remain conventional 2D where that representation is superior. Spatial representation and precise 2D evidence are complementary, not competing modes.

### EL-14 — Anti-Dashboard
The primary experience is organized by investigation and model context, not by independent KPI cards or unrelated panels. Cards and charts are permitted as local evidence representations; they must not become the default information architecture.

### EL-15 — No Invention
Entities, relationships, states, cardinalities, causal claims, and semantically meaningful visual attributes must be grounded in supplied evidence or explicitly identified synthetic data. Unknown must not be rendered as nonexistent.

### EL-16 — Context Transformation
As investigation progresses, the current Focus may become Near Context or an organizing space for the next state. This preserves continuity instead of behaving as navigation across unrelated pages.

### EL-17 — Execution State ≠ Verified Outcome
Execution completion must not visually imply verified improvement. `COMPLETED ⇒ IMPROVED` is prohibited. A completed action may coexist with `PERSISTENT` or not-yet-verifiable OutcomeVerification.

### EL-18 — Evidence Resolution Fidelity
Visual and semantic resolution must not exceed evidence resolution.

`Visual / semantic resolution ≤ Evidence resolution`

Examples:
- period-level observation → period-level representation;
- timestamped evidence → event-level temporal placement may be justified;
- time-series evidence → continuous temporal representation may be justified.

A visualization must not manufacture event timestamps, continuous curves, exact windows, or other precision absent from the evidence.

## 3. Conditional pattern — Temporal Informational Space

Time may become an organizing dimension when the investigative question and available evidence are temporal.

For J02, `BEFORE / DURING / AFTER` may form one continuous investigative space while:
- Service remains the correlation anchor;
- Change/Deployment placement respects available temporal resolution;
- identity state remains distinct from association uncertainty;
- temporal/contextual association does not imply causation;
- EL-18 bounds the visual resolution.

This is a conditional projection pattern, not a universal VECTOR axis or mandatory layout.

## 4. Non-canonical visual choices

The experiments do not canonize dark mode, neon, glow, isometry, perspective, red-as-risk, trapezoids, glass cards, sidebars, bottom timelines, square nodes, radial layouts, animation style, or fixed BEFORE-left/AFTER-right placement.

These may be implementation choices only when they satisfy the CLOSED SDD contracts and this experience language.

## 5. Quality Gate

A candidate VECTOR experience must answer PASS to the applicable questions:
1. Is current Focus distinguishable from Near and Global Context?
2. Is provenance/context preserved through investigation?
3. Can coexistence be distinguished from demonstrated relationship?
4. Are visible relationships evidence-supported?
5. Does representation adapt rather than overload at high cardinality?
6. Does a subset remain interpretable relative to its universe when required?
7. Does spatial treatment carry information rather than decoration?
8. Do cards/charts support investigation instead of defining the whole architecture?
9. Is execution state visually distinct from verified outcome?
10. Does visual precision remain at or below evidence precision?
11. Are temporal associations prevented from becoming implicit causal claims?
12. Has the design avoided inventing unavailable information?

A failed applicable invariant is an experience Quality Gate failure, not authorization to reinterpret a CLOSED product decision.

## 6. SDD compatibility

v0.3 is additive. It does not reopen CLOSED Steps 0–16. It specializes deferred visual/interaction choices while preserving UXI-01..UXI-15, J01–J04, canonical entities/relationships, Evidence-first behavior, bounded graph semantics, identity/correlation/causation separation, and execution/outcome separation.
