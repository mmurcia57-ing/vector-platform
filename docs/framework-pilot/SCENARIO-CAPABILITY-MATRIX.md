# VECTOR — Scenario Capability Matrix

## Purpose
Use adversarial scenarios to discover missing product capability, not merely to populate a demo selector.

## Classification
- **REPRESENTABLE** — current canonical/projection semantics can express the decision safely.
- **PARTIAL** — domain concept exists but current experience projection/demo does not expose enough state.
- **GAP** — additional governed product representation/contract is required.
- **EXTERNAL BOUNDARY** — requires corporate authority/integration behavior; local demo may simulate only the boundary.

| Scenario family | Current status | Why / next implication |
|---|---|---|
| Persistent recurrence | REPRESENTABLE | Incident/Event/Evidence → RiskFinding path exists; strengthen temporal scenario fixtures |
| Outcome pending / persistent | REPRESENTABLE | Commitment/ImprovementAction/OutcomeVerification semantics exist |
| Partial/stale/insufficient evidence | REPRESENTABLE | ProjectionQuality supports partial/stale/missing/limitations |
| SLO burn / multi-signal service condition | PARTIAL | SLO/Metric concepts exist in product scope, but current Experience projection lacks real SLO/metric series |
| Incident spike / incident state | PARTIAL | canonical Incident exists, but Service experience currently reports incidents as unavailable |
| Change temporal association | PARTIAL | intended J02 exists; local Experience fixture/projection must expose before/during/after change evidence |
| Multiple changes / rollback | PARTIAL | requires richer temporal change projection, not causal shortcuts |
| Multiple simultaneous risks | REPRESENTABLE structurally | lists support multiple RiskFindings; deterministic fixtures need breadth |
| Area multi-service concentration | PARTIAL | new Area workspace supports it, but local fixture currently has only one service |
| Renegotiation / overdue | REPRESENTABLE | EXT-004 lifecycle supports due date/history; demo fixtures/tests need explicit variants |
| Conflicting evidence | PARTIAL | quality limitations can expose conflict, but explicit conflict semantics/testing need strengthening |
| Duplicate/late evidence | PARTIAL | provenance/temporal semantics exist; dedup/late-arrival behavior requires executable assurance |
| Identity ambiguity | REPRESENTABLE in canonical metadata | scenario and UI boundary still missing |
| Shared dependency / blast-radius context | PARTIAL | graph relationships can express context; demo dataset lacks multi-service dependency topology |
| High-degree/truncated topology | REPRESENTABLE | bounded graph service has limits/truncation; needs adversarial fixture and behavioral proof |
| Read allowed / mutation denied | EXTERNAL BOUNDARY + local simulation | AuthZ contract exists; corporate IAM remains TBD |
| Projection dependency timeout/retry | PARTIAL | recovery state required by EXT-005; current local source is in-process deterministic |
| AI unavailable/conflicting suggestion | EXTERNAL BOUNDARY + local safe degradation | AI is advisory; provider authority remains bounded |
| Healthy/no-risk negative control | GAP in demo harness | current base fixture intentionally produces a RiskFinding; add no-finding fixture to prove VECTOR does not manufacture attention |
| Unsupported scenario token | REPRESENTABLE | current source returns explicit unsupported quality state |

## Highest-value next demo fixtures

The next fixtures should maximize capability discovery rather than quantity:

1. **Healthy negative control** — proves no automatic risk manufacture.
2. **Multi-service Area concentration** — exercises the newly differentiated Area workspace.
3. **Change-associated degradation** — closes J02 temporal behavior.
4. **Degradation predates change** — adversarial anti-causality control for J02.
5. **Temporary fix + recurrence** — exercises recurrence + execution != outcome.
6. **SLO burn without incident** — exposes whether Service Intelligence can reason beyond incident records.
7. **Incident spike with objective still inside target** — inverse control.
8. **Shared dependency / multiple services** — exercises graph/topology as investigative context.
9. **Conflicting evidence** — proves VECTOR can refuse a conclusion.
10. **Overdue vs renegotiated commitment pair** — proves Commitment Reliability semantics.
11. **Dependency timeout/recovery** — proves degraded product behavior.
12. **Unauthorized mutation** — proves read/investigate can remain separate from mutate.

## Product discovery findings
The expanded scenario catalog exposes a material limitation of the current local demo model:

> The current `PreparedExperienceContext` is strong for Risk/Evidence/Commitment/Outcome demonstration but too thin for a convincing Service Intelligence product because it does not carry first-class operational time-series/SLO, Incident, Change or dependency-condition projections.

This is not solved by adding more labels to the selector.

Before claiming broad VECTOR demonstrability, the product must decide which of those already-approved canonical concepts need first-class experience projections for V1 journeys, especially J02 and Service Intelligence.

No corporate thresholds, source systems, volumes or SLO targets are inferred here.

## Gate
- Scenario breadth: EXPANDED
- Negative controls: REQUIRED
- Multi-service fixture: REQUIRED
- J02 temporal adversarial pair: REQUIRED
- Service operational projection depth: **PRODUCT GAP DISCOVERED**
- Corporate integration specifics: TBD / bounded
- Demo convergence: FAIL until prioritized scenarios have executable evidence
