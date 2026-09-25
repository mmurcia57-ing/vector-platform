# EXT-005 — Experience Language v0.3 Conformance

Status: READY_FOR_IMPLEMENTATION
Release classification: V1.1 experience evolution
Work type: UX / Experience conformance
Canonical scope change: NONE

## 1. Purpose

Bring the current VECTOR runtime experience into conformance with the experimentally validated VECTOR Experience Language v0.3 without reopening CLOSED product semantics, J01–J04, canonical entities/relationships, Source Authority, BFF boundaries, or architecture decisions.

The governing experience sequence remains:

`ATTENTION → CONTEXT → EXPLANATION → EVIDENCE / ACTION / OUTCOME`

The governing investigation progression remains:

`Technology Overview → Area/Domain → Service → RiskFinding → Evidence / Action / Outcome`

## 2. Evidence

Current-main inspection found strong semantic preservation but structural experience gaps.

PASS: Context Preservation, Existence != Relation, Evidence-Bound Relationships, No Invention, Execution State != Verified Outcome.

PARTIAL: Focus Hierarchy, Progressive Disclosure, Subset within Universe, Relationship Lens, 2D/Spatial Complementarity, Evidence Resolution Fidelity.

FAIL: Entity → Space, Semantic Zoom, Cardinality Adaptation, Informational Space, Anti-Dashboard, Context Transformation.

3D Justification is N/A unless a future implementation introduces 3D.

## 3. Scope

### WP1 — Conformance harness
Create executable structural assertions for EL-01..EL-18 where source/runtime behavior can be tested. Tests must detect regression to independent dashboard-card composition and must not rely only on string-presence checks.

### WP2 — Persistent investigation space
Evolve the current route experience so Area, Service, and Risk focus transform within a persistent investigation space. Preserve period, AreaDomain, Service, RiskFinding and provenance context. Do not replace context continuity with unrelated full-page compositions.

Acceptance:
- Focus, Near Context and Global Context are distinguishable.
- Moving Area → Service → Risk changes granularity while preserving origin/context.
- Browser/deep-link restoration continues to work.

### WP3 — Panorama / J04 anti-dashboard structure
Replace KPI/card-first composition as the primary architecture with an attention-first model view. Exact metrics/cards may remain subordinate evidence.

Acceptance:
- attention is the primary entry point;
- selected subset remains interpretable relative to the broader technology universe;
- high cardinality has an aggregation/cluster/density strategy rather than unlimited visible cards;
- no new KPI or organizational-performance semantics are introduced.

### WP4 — Service / Risk investigative transformation
Make selected Service the correlation anchor and RiskFinding the explainable investigative focus. Bounded graph is a relationship lens, not an isolated graph panel or generic topology browser.

Acceptance:
- only supported relationships are rendered;
- bounded expansion remains enforced;
- evidence/provenance/quality stay perceptible;
- spatial organization carries interpretable context.

### WP5 — J03 Action / Outcome verification space
Preserve Commitment and ImprovementAction as context while evidence comparison becomes the verification focus.

Acceptance:
- COMPLETED never visually implies IMPROVED;
- OutcomeVerification remains independent;
- exact evidence can remain 2D;
- action/outcome is integrated with the investigation rather than a disconnected panel chain.

### WP6 — J02 temporal projection and Evidence Resolution Fidelity
Implement temporal informational space only when supported by the journey/evidence.

Acceptance:
- BEFORE/DURING/AFTER can form one continuous investigation space;
- Change/Deployment placement never exceeds source temporal resolution;
- period-level evidence stays period-level;
- no fabricated event timestamps, continuous SLO curve, exact change/deployment time, or confidence score;
- identity certainty remains distinct from association uncertainty;
- correlation never becomes causation.

### WP7 — Cardinality and semantic-zoom behavior
Introduce explicit representation transitions for low/medium/high cardinality and focus transitions.

Acceptance:
- more data does not map mechanically to more simultaneously visible objects;
- semantic zoom changes representation/granularity, not only size/camera;
- subset/universe continuity is retained where required.

### WP8 — Convergence Quality Gate
Verify spec ↔ frontend ↔ BFF ↔ tests ↔ evidence.

Required gate:
- frontend tests;
- backend acceptance/semantic tests affected by the change;
- EL-01..EL-18 conformance matrix with PASS/PARTIAL/N/A and evidence;
- no CLOSED semantic contract changed;
- no unsupported entity/relation/state/causal claim introduced.

## 4. Explicit non-goals

- No new canonical entities or GRC relationships.
- No new executive KPI, capacity/productivity or individual-performance semantics.
- No generic graph explorer.
- No provider-specific Source Authority invention.
- No redesign driven by dark mode, neon, glow, isometry, 3D, or other experiment aesthetics.
- No mandatory 3D.
- No causal inference from temporal proximity.
- No replacement of BFF projection boundaries.

## 5. Implementation order

`WP1 → WP2 → (WP3, WP4, WP5, WP6) → WP7 → WP8`

WP3–WP6 may proceed independently only after WP2 establishes the persistent investigation-space contract.

## 6. Quality Gate

Implementation is not complete because the screens look closer to experimental images. It is complete only when applicable Experience Language invariants are demonstrably satisfied while canonical semantics remain unchanged.
