# VECTOR — Implementation Tasks and GitHub Project Population Plan

## 1. Status and governance

- Status: CLOSED / FINAL EXTERNAL QUALITY GATE PASSED
- Step: Step 14 — Task Decomposition + GitHub Projects
- SDD status: READY_FOR_IMPLEMENTATION
- SPEC-BLOCKERS: 0

This is the normative implementation inventory. GitHub Project is execution-state only; SDD remains Source of Truth. A task authorizes implementation only when its Ready criteria and dependencies are satisfied.

## 2. Task inventory

Abbreviations: `Acc` = acceptance references; `DoD` = Definition of Done. Every task has Ready criterion: dependencies done, relevant contract closed, scope/acceptance known, and no blocking TBD.

| ID / title | Vertical; capability / journey | Objective, scope and modules | Dependencies / SDD / Acc | Tests / DoD / TBD |
|---|---|---|---|---|
| TASK-FND-001 Local engineering foundation | IP-00; all / cross | SPA, BFF, config, test, telemetry foundation. | None; Step 9/10; AT-05/06/17. | Smoke; DoD reproducible foundation. Blocking: final gate. |
| TASK-FND-002 Configuration and secrets boundary | IP-00; C02 / all | External config, policy provenance, secret boundary. | FND-001; NFR-07..10, SEC-04/07; AT-06. | Negative config/secret; DoD no hardcoding. |
| TASK-DATA-001 Golden generator and seed harness | IP-01; all / J01–04 | Deterministic GOLDEN generator/profile metadata/oracle harness. | FND-001; AT-08/09/13/14, GS-01..12. | Repeatability; DoD same seed/config. |
| TASK-CAN-001 Canonical contracts and SQLite adapter | IP-01; C01 / all | 17-entity ports, persistence adapter/repositories. | DATA-001; Steps 3/5/9; AT-02/09. | Contract/data; DoD provenance preserved. |
| TASK-EVD-001 Evidence and SourceReference path | IP-02; C02/C07 / all | Evidence, provenance, authority, limitation path. | CAN-001; Steps 4/5/7; GS-01/02/08. | Traceability; DoD no authority transfer. |
| TASK-INT-001 Deterministic metrics and intelligence | IP-02; R01–R06,C05,C06 / J01/02 | Observations, recurrence, correlation, RiskFinding. | EVD-001; AT-02/09, GS-02/03/07. | Semantic negatives; DoD no LLM/KPI/causation shortcut. |
| TASK-BFF-001 Experience projection contracts | IP-03; C01,C02,C07 / J01–04 | BFF projections/context propagation/REST boundary. | CAN-001,EVD-001; Steps 8/9; AT-04. | API/auth; DoD no vendor/storage model SPA leak. |
| TASK-UX-001 First visible investigation slice | IP-03; C01,C02,C07 / J01 | Overview→Area→Service→Risk→Evidence. | BFF-001,INT-001; GS-02/07. | UX/E2E; DoD evidence-first continuity. |
| TASK-GRP-001 Transactional outbox/projector | IP-04; C04 / all | Canonical-to-outbox async graph projection. | CAN-001; Step 9; AT-20, GS-11. | Retry/idempotency; DoD graph outage preserves canonical commit. |
| TASK-GRP-002 GRC projection and bounded query | IP-04; C04 / J01–04 | GRC-01..18 mapping/rebuild/query service. | GRP-001,EVD-001; Step 6/9; AT-02/04. | Direction/rebuild/stale; DoD graph projection only. |
| TASK-UX-002 Bounded graph experience | IP-04; C04 / J01–04 | Contextual graph selection/expansion/stale state. | GRP-002,BFF-001; UXI-08/09; GS-11. | UX degradation; DoD no hairball/currentness lie. |
| TASK-J01-001 Persistent reliability journey | IP-05; R01–R06 / J01 | Complete persistent risk, recurrence, Evidence path. | UX-001,GRP-002; GS-02/07/08. | E2E/negative; DoD J01 traceable. |
| TASK-J02-001 Change association journey | IP-06; R04,C03 / J02 | Change/deployment correlation and identity limits. | J01-001,GRP-002; GS-03/09/10. | Causation/identity negatives; DoD correlation never causal. |
| TASK-J03-001 Commitment/action/outcome core | IP-07; E02,E03 / J03 | Commitment/action/deterministic verification chain. | INT-001,EVD-001; GS-04/05/06. | Outcome oracle; DoD execution != outcome. |
| TASK-J03-002 Structural improvement experience | IP-07; E02,E03,C10 / J03 | BFF/UX outcome context and Evidence drill-down. | J03-001,BFF-001; AT-04. | E2E; DoD outcomes visible. |
| TASK-J04-001 Leadership decision journey | IP-08; E01,C10 / J04 | Domain roll-up/full retained-context drill-down. | J01-001,J02-001,J03-002; GS-12. | E2E; DoD human-accountable context. |
| TASK-INTG-001 Contract-compatible local adapters | IP-09; C02,C03 / all | Fixture/mock adapter contracts and provenance. | CAN-001,EVD-001; Step 7; AT-03. | Contract; DoD read-only/ACL preserved. |
| TASK-INTG-002 ARIA and SRE provider routes | IP-09; R05,C02 / J01/02 | ARIA→MonitoringEvent; direct SRE Skill ports. | INTG-001; Step 10; AT-03,GS-11. | Routing; DoD SRE not through ARIA, provider TBD. |
| TASK-INTG-003 Sandbox/corporate adapter evolution | IP-09; integration / all | ServiceNow PDI, SCM fixture/provider, later adapters. | INTG-001; OQ-009/TBDs; AT-03. | Sandbox contract; DoD no redesign. |
| TASK-SEC-001 Authorization and audit enforcement | IP-11; cross / all | Identity abstraction, roles, audit/least privilege. | FND-002,BFF-001; SEC-01..09; AT-06. | Negative auth; DoD fail secure. |
| TASK-OBS-001 Telemetry and health/degradation | IP-11; cross / all | Component health, metrics, logs, background visibility. | FND-001,GRP-001; OBS-01..06; AT-05/20. | Fault telemetry; DoD no silent failure. |
| TASK-RES-001 Failure isolation and recovery | IP-11; cross / all | Backpressure, adapter/Neo4j recovery, convergence. | GRP-001,INTG-001; RES-01..05; AT-20. | Controlled fault; DoD no loss/duplication. |
| TASK-NFR-001 BASELINE workload and performance harness | IP-12; cross / J01–04 | Local BASELINE mixed workload/metadata. | J01-001,J02-001,J03-002,J04-001,OBS-001; AT-16..21. | p95/mixed; DoD local correctness-first results. |
| TASK-AI-001 AIProvider and governed context | IP-10; AI-18..26 / J01–04 | Provider abstraction, authorized context, policy/version. | INT-001,BFF-001,SEC-001; AT-07/10. | Permission/config; DoD no provider/domain coupling. |
| TASK-AI-002 AI investigation capabilities | IP-10; AI-12..17 / J01–04 | Explain/summarize/hypothesize/recommend/outcome/assistant. | AI-001,J01-001,J02-001,J03-002,J04-001; AT-07. | Behavioral oracle; DoD no authority/execution/causation. |
| TASK-AI-003 AI degradation and consumption observability | IP-10/11; AI-19/25 / all | Provider failure, output validation/provenance/telemetry. | AI-001,OBS-001; AT-07,GS-11. | Malformed/outage; DoD deterministic VECTOR continues. |
| TASK-ACC-001 Golden and semantic acceptance suite | IP-12; all / J01–04 | GS-01..12, deterministic oracles, semantic negatives. | DATA-001 through J04-001; AT-01/02/08/09/11. | Evidence; DoD all scenarios traceable. |
| TASK-ACC-002 Security/resilience/AI acceptance suite | IP-12; cross / all | Negative auth, recovery, 18 AI scenarios. | SEC-001,RES-001,AI-002/003; AT-05/06/07/10/20. | Fault/AI evaluator; DoD safe behavior. |
| TASK-REL-001 Release-readiness evidence | IP-12; all / J01–04 | Consolidate BASELINE, STRESS, traceability/results. | NFR-001,ACC-001,ACC-002; AT-12/17/19/21. | Review; DoD no code-only Done claim. |

## 3. Dependency and Ready policy

All tasks are V1/P0 unless a later approved release changes this. Family prefix encodes work type. Dependencies form the Step 13 DAG and have no unexplained cycle. Initial Ready count is **1**: `TASK-FND-001`. `TASK-FND-002` becomes Ready only after FND-001 completes. Tasks are never bulk-marked Ready.

## 4. GitHub Project population plan

Workflow: `Backlog → Ready → In Progress → Review / Validation → Done`. Fields: Status, Vertical, Capability, Journey, Priority, Work Type, Dependencies, SDD References, Acceptance References, Release (`V1`). The inventory is the deterministic one-Issue/Project-item-per-Task-ID source; references and dependencies must be copied without semantic change.

Authenticated GitHub CLI/API access was unavailable at materialization; no Project, Issue, field, URL, or identifier is fabricated. Remaining external operation: authenticate with permitted owner access; create/reuse `VECTOR V1`; configure workflow/fields; create/reuse exactly this inventory without duplicates; link items; keep all Backlog until final gate and Ready criteria permit otherwise. This is non-blocking for SDD. EV-001..EV-007 are excluded from V1 population.

## 5. Step 14 internal quality result

Final external Quality Gate result: PASS. All 18 MUST capabilities, J01–J04, acceptance obligations, Definitions of Done, Ready criteria, and dependencies have task coverage. No corporate TBD is promoted to fact, no Evolution item enters V1, and no task asks an implementer to invent product behavior. Step 14 is CLOSED.
