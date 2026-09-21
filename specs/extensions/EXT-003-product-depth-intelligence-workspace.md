# EXT-003 — Product Depth & Intelligence Workspace

## 1. Status

- Type: controlled product/engineering extension
- Origin: autonomous Product Completion Discovery
- Status: **READY_FOR_IMPLEMENTATION**
- Scope rule: FIX/HARDEN current V1 product depth only
- Canonical model: PRESERVED
- GRC semantics: PRESERVED
- Corporate IAM / Source Authority / production volumes: TBD, NOT invented
- Visual implementation: may change materially
- Valid domain/evidence semantics: NO-REDO

## 2. Why this extension exists

The closed V1 SDD implemented minimum end-to-end slices. The brownfield pilot proved that specification conformance is not equivalent to product completion.

VECTOR's mission requires users to:
`Understand → Explain → Prioritize → Act → Verify`.

Current implementation establishes this loop structurally, but does not yet provide sufficient depth for several V1 MUST capabilities or sufficient behavioral assurance.

EXT-003 closes those demonstrated gaps without silently promoting POST-V1 capabilities.

## 3. Experience architecture

VECTOR evolves from a set of dashboard-like views into an **intelligence workspace / technology control plane**.

Interaction grammar:

`SIGNAL → FOCUS → EXPLAIN → RELATE → DECIDE → ACT → VERIFY`

Three coordinated experience layers:

### A. Command
Answers:
- Where does attention belong?
- What persists or is worsening?
- Which conditions are unsupported by enough evidence?
- What actions/commitments are stalled?
- Which outcomes are verified vs still uncertain?

### B. Investigation
Answers:
- What happened?
- What changed over time?
- What evidence supports the condition?
- Which incidents/events/changes/SLO signals are related?
- What is correlation vs observed fact vs derived intelligence?
- What relationships matter in the bounded graph?

### C. Action & Outcome
Answers:
- What decision/action exists?
- Who/what context owns the action where known?
- What is its lifecycle?
- What evidence would verify improvement?
- Did the condition improve, persist, or remain unverifiable?

## 4. Required functional deltas

### PD-01 Temporal Intelligence
Current V1 MUST C09/R03/R06/J01/J03 require temporal reasoning.

Add an explicit temporal projection contract capable of representing:
- period/context;
- ordered observations;
- condition/trend semantics;
- before/current/after where applicable;
- freshness;
- evidence references;
- insufficient history.

No forecasting is introduced.

### PD-02 Operational Signal Context
Expose V1 MUST Incident/Event/SLO-SLI context in investigation without creating new canonical entities.

Required behavior:
- incident/event/SLO signals are inspectable as evidence-backed context;
- provenance/freshness remain visible;
- absence of a signal is not interpreted as health;
- signal types can participate in a service/risk timeline.

### PD-03 J02 Change Investigation
Materialize a complete behavioral J02 experience:
`Service/Risk → Change/Deployment → Before/During/After → Related Evidence → Qualified Association`.

The UI MUST state that temporal/contextual association is not proven causation.

### PD-04 J04 Decision Workspace
Area/Domain becomes a decision-oriented workspace, not merely grouping/navigation.

It must expose, where evidence exists:
- attention concentration;
- persistence/trend;
- critical findings;
- commitments/action state;
- verified/persistent/unverifiable outcomes;
- evidence/confidence limitations.

No individual ranking or unsupported composite productivity score.

### PD-05 Investigation Timeline
Risk/Service investigation receives a time-oriented evidence lane integrating available relevant facts/intelligence while preserving semantic type.

### PD-06 Bounded Relationship Workspace
Graph remains bounded and contextual, but becomes an actual investigative interaction rather than only relationship rows.

Requirements:
- explicit selected/focus node;
- typed relationships;
- user-controlled bounded expansion;
- detail/evidence context;
- no unrestricted hairball;
- correlation never rendered as causation.

### PD-07 Action/Outcome Continuity
Commitment/action/outcome transitions preserve analytical context and make verification status explicit.

### PD-08 Experience State Completeness
Critical views MUST support applicable:
`loading, populated, empty, partial, stale, conflict, insufficient-evidence, permission-denied, dependency-error, retry/recovery`.

States that cannot occur in the local deterministic adapter may be fixture/test-driven.

### PD-09 Explainability Grammar
The UI MUST distinguish:
- observed/source fact;
- derived intelligence;
- correlation;
- uncertainty/confidence limitation;
- action;
- verified outcome.

### PD-10 Bounded AI Investigation Surface
Current C11/AI infrastructure may be exposed only as evidence-grounded assistance.

The experience MUST:
- cite/support claims with Evidence references;
- expose limitations;
- preserve human accountability;
- never silently mutate authoritative facts;
- degrade safely when provider unavailable.

A corporate provider/model is not selected by this extension.

## 5. Engineering deltas

### ENG-01 Contract boundary
Create a machine-verifiable frontend↔BFF experience contract. Prefer a generated/validated schema artifact over manually duplicated DTO assumptions.

### ENG-02 HTTP authorization conformance
Protected mutations must prove:
`request identity/context → authorization → use case → audit evidence`.

Local deterministic identity/test context is allowed. Corporate IdP remains TBD.

### ENG-03 Architecture fitness
Executable checks protect:
- SPA communicates through BFF;
- canonical store remains authority;
- graph remains projection;
- AI cannot become authoritative persistence;
- provider-specific integration remains behind adapter boundaries.

### ENG-04 Observability continuity
Critical paths must emit enough correlated telemetry to diagnose success/failure through BFF/use-case/persistence/projection boundaries where implemented.

### ENG-05 Projection durability evidence
Do not represent in-memory seeded graph behavior as durable runtime proof. Add/test durable local projection/recovery semantics consistent with approved architecture, or explicitly retain a bounded local-only limitation if environment prevents it.

### ENG-06 Frontend behavioral assurance
Critical interactions require executable behavioral tests. Source-text/regex tests remain supplementary only.

### ENG-07 Accessibility
Critical journeys require automated accessibility checks plus keyboard/focus semantics where applicable.

### ENG-08 NFR characterization
Run local BASELINE plus mixed/STRESS characterization using declared local profiles. Do not extrapolate to corporate capacity.

## 6. Journey closure matrix

| Journey | Trigger | Understand/Explain | Decide/Act | Verify | EXT-003 target |
|---|---|---|---|---|---|
| J01 Persistent Reliability | attention signal | temporal recurrence + evidence | inspect/action context | persistence/improvement state | CLOSED behaviorally |
| J02 Change Degradation | service/risk/change | before/during/after + association | qualified investigation | subsequent condition | CLOSED behaviorally |
| J03 Structural Improvement | risk/action | evidence + lifecycle | commitment/action | comparable outcome evidence | CLOSED behaviorally |
| J04 Area Decision | area attention | persistence/risk/evidence limits | decision/action queue | outcomes/follow-up | CLOSED behaviorally |

## 7. Experience state matrix

| Surface | Loading | Empty | Partial/Stale | Conflict/Insufficient | Denied/Error | Recovery |
|---|---|---|---|---|---|---|
| Command | REQUIRED | REQUIRED | REQUIRED | REQUIRED | REQUIRED | REQUIRED |
| Area Decision | REQUIRED | REQUIRED | REQUIRED | REQUIRED | REQUIRED | REQUIRED |
| Service Investigation | REQUIRED | REQUIRED | REQUIRED | REQUIRED | REQUIRED | REQUIRED |
| Risk Investigation | REQUIRED | REQUIRED | REQUIRED | REQUIRED | REQUIRED | REQUIRED |
| Change/J02 | REQUIRED | REQUIRED | REQUIRED | REQUIRED | REQUIRED | REQUIRED |
| Commitment/Outcome | REQUIRED | REQUIRED | REQUIRED | REQUIRED | REQUIRED | REQUIRED |
| Graph | REQUIRED | REQUIRED | REQUIRED | REQUIRED | REQUIRED | REQUIRED |
| AI Assistance | REQUIRED | REQUIRED | REQUIRED | REQUIRED | REQUIRED | REQUIRED |

## 8. UX quality requirements

The final experience must:
- look and behave as one coherent intelligence workspace;
- prioritize decision signal over raw metric density;
- use progressive disclosure rather than giant dashboards;
- maintain analytical context through navigation;
- make time/history visually meaningful;
- keep evidence one interaction away from material derived claims;
- avoid graph hairballs;
- preserve readable hierarchy at common desktop widths;
- remain usable by keyboard for critical paths;
- avoid color-only semantic communication;
- provide clear loading/failure/retry feedback;
- support responsive degradation without losing decision meaning.

Exact visual design is intentionally implementation-driven and may materially differ from the current UI.

## 9. Acceptance evidence

EXT-003 cannot close from screenshots alone.

Required:
1. backend unit/integration tests for new projections/use cases;
2. contract compatibility tests;
3. frontend component/behavior tests;
4. journey tests for J01–J04;
5. accessibility automated checks;
6. negative security tests at HTTP boundary;
7. telemetry/diagnosability checks;
8. graph recovery/durability evidence;
9. local BASELINE + mixed/STRESS results;
10. frontend production build;
11. convergence matrix from requirements to implementation/tests/evidence.

## 10. Work packages / dependency order

### WP1 — Contracts & test harness
ENG-01, behavioral frontend harness, accessibility harness.

### WP2 — Temporal/signal intelligence
PD-01, PD-02, supporting backend projections/tests.

### WP3 — Intelligence workspace shell
Command/Investigation/Action layers, state grammar, navigation/context continuity.

### WP4 — J02 + temporal investigation
PD-03, PD-05.

### WP5 — J04 decision workspace
PD-04 plus outcome/action continuity.

### WP6 — Graph investigation
PD-06.

### WP7 — Security/observability/durability
ENG-02, ENG-03, ENG-04, ENG-05.

### WP8 — AI surface
PD-10 with safe degradation.

### WP9 — Assurance/NFR
ENG-06..08, journey/accessibility/security/performance validation.

### WP10 — Convergence/governance
Traceability, stale docs/issues, release evidence, pilot learning.

Dependencies:
`WP1 → WP2 → WP3 → {WP4,WP5,WP6} → WP7 → WP8 → WP9 → WP10`

Parallel work is allowed only where contracts are stable.

## 11. READY FOR IMPLEMENTATION gate

- Problem/gaps evidence-backed: PASS
- Current-release boundary protected: PASS
- Canonical/GRC invariants protected: PASS
- Functional deltas explicit: PASS
- UX states explicit: PASS
- Contract/security/observability implications explicit: PASS
- Acceptance evidence explicit: PASS
- Work packages/dependencies explicit: PASS
- Corporate unknowns remain TBD: PASS
- SPEC-BLOCKER: NONE

**EXT-003: READY FOR IMPLEMENTATION**
