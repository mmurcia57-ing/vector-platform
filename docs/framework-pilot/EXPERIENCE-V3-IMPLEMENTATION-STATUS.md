# VECTOR — Experience V3 Implementation Status

Status: **IN_PROGRESS — EXECUTABLE ASSURANCE ACTIVE**
Branch: `experiment/vector-knowledge-rechallenge-v2`
Protected: `main`, `framework/vector-full-lifecycle-pilot`, `experiment/vector-north-star-runtime`.

## Contract baseline
- Experience Contract V3: READY FOR IMPLEMENTATION — LOCAL/DEMO EXPERIENCE SCOPE
- E2E Context Continuity: PASS WITH EXPLICIT IMPLEMENTATION GAPS
- S1–S5 approved experience decisions remain closed.

## Implemented and executable

### V3-01 Context Envelope — IMPLEMENTED / CI PASS
Cross-surface Area / Service / Risk / period / evidence-quality context is rendered and navigation preserves applicable context without asserting causality.

### V3-02 S1 Panorama — IMPLEMENTED / CI PASS
Executive triage remains portfolio-oriented and evidence-bounded. Missing aggregate outcome data is explicit rather than synthesized.

### V3-03 S2 Area — IMPLEMENTED / CI PASS
Area is structurally distinct from Panorama: service portfolio, attention concentration and area follow-up are separate decision tasks.

### V3-04 S3 Service operational projection — IMPLEMENTED FOR AVAILABLE LOCAL CONTRACT / CI PASS
Service condition, evidence, findings, change context and dependency investigation are exposed where supported. SLO/Incident/Change projections that are not present in the local dataset remain explicitly unavailable rather than zero/healthy.

### V3-05 S4 epistemic investigation — IMPLEMENTED / CI PASS
Evidence, deterministic explanation, temporal association, bounded graph, limitations, advisory AI, action and outcome remain semantically separated. Change association explicitly preserves correlation != causation.

### V3-06 Investigation → governed action lineage — IMPLEMENTED / CI PASS
S4 can transition to S5 while preserving Area/Service/Risk/condition context. Commitment creation now sends the already-supported canonical `serviceId` and `riskFindingId`; no new Decision entity was invented.

### V3-07 S5 commitments/outcomes — IMPLEMENTED / CI PASS
EXT-004 lifecycle semantics remain intact: execution status, renegotiation history, reliability denominator and OUTCOME_PENDING remain distinct from outcome verification.

### V3-08 Outcome return propagation — IMPLEMENTED / CI PASS
The executable `buildJourneyUrl` contract preserves Area/Service/Risk plus Commitment/Action/Outcome identifiers across forward and return navigation. S5 provides explicit return to originating Risk when lineage exists. TechnologyOverview now projects Commitment → ImprovementAction → OutcomeVerification continuity so S1 can expose verified outcome state and S2 can derive area-scoped action/outcome follow-up without rewriting historical evidence.

### V3-09 adversarial deterministic scenarios — IMPLEMENTED / CI PASS
Executable coverage now includes:
1. degradation after change without causal promotion;
2. degradation predating change inverse control;
3. completed action with persistent condition;
4. completed action with outcome pending / insufficient evidence;
5. verified improvement with distinct synthetic post-action evidence;
6. conflicting evidence without silent authority selection;
7. stale/partial evidence;
8. healthy/no-risk negative control;
9. renegotiated versus overdue lifecycle behavior;
10. unauthorized mutation denial.

The demo selector exposes the relevant evidence scenarios; security/lifecycle adversarials remain backend behavioral tests where that is the correct boundary.

### V3-10 accessibility/non-spatial — IMPLEMENTED / CI PASS
Bounded topology includes an accessible non-spatial relationship representation and explicit focus/freshness/truncation semantics.

### V3-11 localization boundary — IMPLEMENTED / CI PASS
Core S1–S5 questions, navigation/lens controls and shared workspace semantics respect the ES/EN UI boundary. Backend evidence/domain values remain source data and are not translated into altered semantics.

### V3-12 engineering assurance — PASS
`vector-assurance` is now branch-aware for this experiment and executes:
- backend: `mvn -B test`;
- frontend: `npm ci`, `npm test`, `npm run lint`, `npm run build`.

Latest verified run after outcome propagation: **PASS** on commit `86ea47b460678fabafab7e7507f85d923ff167b7`.

## Defects discovered by executable assurance
1. CI was scoped only to the historical pilot branch.
2. legacy assurance depended on literal UI copy/source tokens.
3. a Vitest assertion read source through a filesystem URL instead of testing behavior.
4. frontend `Quality` shape diverged from backend `ProjectionQuality` (`partial/confidence` vs `missingContext/confidenceContext`).
5. Commitment creation dropped Service/Risk lineage already supported by the backend contract.

All five were corrected on the active experimental branch.

## Framework / KB learning
The VECTOR pilot supplied the real-product stress test required by KB pattern `patterns/end-to-end-experience-contract.md`.
That pattern was promoted from HYPOTHESIS to **DECISION — ADOPT** with these generalized rules:
- branch-aware assurance;
- backend/frontend contract-shape parity;
- canonical lineage over navigation-only context;
- behavioral evidence over copy/source-token assurance.

KB commit: `85c1b723682bb86be629bb5d9096800b873c9e35`.

## Remaining convergence gates
- execute final post-status CI and preserve its evidence;
- browser-level visual/click-through E2E remains NOT EXECUTED by the current GitHub connector; do not relabel jsdom/contract assurance as browser evidence.

## Truth boundary
VECTOR is now materially beyond the original V3-01-only status and has executable CI evidence.

Contract, component, backend and build assurance are converged for the local/demo V3 scope.

**Browser-level E2E/visual assurance remains NOT EXECUTED**, therefore this status does not claim browser-assured FUNCTIONAL DEMO CONVERGED, LOCAL/RELEASE CONVERGED or PRODUCTION READY.
