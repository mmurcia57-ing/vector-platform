# VECTOR V1 Local Release Readiness

## Status

- Evaluation date: 2026-09-09
- Scope: current specified local V1 plus approved EXT-001
- Result: READY
- SPEC-BLOCKERS: 0
- Corporate production readiness: TBD and outside this local V1 result

## Executed evidence

| Gate | Result | Evidence |
|---|---|---|
| Java 21 backend regression | PASS | 70 tests across 24 suites; 0 failures/errors |
| Spring Boot startup and health | PASS | `/actuator/health` returned `UP` |
| Live experience HTTP path | PASS | overview, Service, RiskFinding, Evidence, Commitment, ImprovementAction and OutcomeVerification returned from the local deterministic path |
| Frontend tests | PASS | 2 tests; 0 failures |
| Frontend lint | PASS | `oxlint` completed without findings |
| Frontend production build | PASS | TypeScript and Vite production build completed |
| Frontend runtime smoke | PASS | Vite root returned HTTP 200 with the application mount |
| Git whitespace validation | PASS | `git diff --check` reported no errors |

The live deterministic path returned one attention AreaDomain, one Service, one
RiskFinding, two Evidence records, one completed Commitment, one completed
ImprovementAction, and one Evidence-backed `PERSISTENT` OutcomeVerification.
This proves `COMPLETED != IMPROVED` in the runnable local experience.

## Semantic and architectural audit

- Exactly 17 V1 canonical entity types remain registered.
- J01, J02, J03 and J04 executable tests pass.
- Evidence provenance and Source Authority remain distinct from canonical representation.
- Identity remains distinct from correlation; correlation does not claim causation.
- Neo4j remains a rebuildable projection; the live empty projection is exposed as partial rather than current or authoritative.
- External integrations remain read-only. L1/L2 fixtures retain unknown corporate authority and unresolved identity where applicable.
- AI output remains non-authoritative, authorization-bounded, validated and explicitly unavailable on provider failure.
- Execution remains distinct from outcome; a completed action can produce a `PERSISTENT` outcome.
- No individual productivity or employee scoring is present.
- OQ-009 remains OPEN; external Commitment Source Authority remains TBD.

## Acceptance and performance

- GS-01 through GS-12 are traceable to executable semantic test evidence.
- The 18 required AI evaluation scenarios are represented by the invariant-based acceptance suite.
- Security, resilience, graph recovery, malformed AI output and provider-outage negative paths pass.
- The configurable local BASELINE harness passes and reports local-only p95 metadata.
- STRESS remains a characterization profile rather than a corporate capacity commitment or V1 pass/fail threshold. It was not generated in the unit release pipeline.

## Local run commands

Backend:

```powershell
docker run --rm -p 8080:8080 -v "${PWD}/backend:/workspace" -v "C:\Users\Usuario\.m2:/root/.m2" -w /workspace maven:3.9-eclipse-temurin-21 mvn spring-boot:run
```

Frontend, from `frontend/`:

```powershell
npm run dev
```

Open `http://localhost:5173/`. The SPA uses its configured `/api` proxy to the
backend at `http://localhost:8080`.

## Limitations

This result is local release readiness using deterministic fixtures. It does
not establish corporate capacity, availability, source mappings, credentials,
Source Authority, AI provider/model selection, or production deployment
readiness. Those values remain TBD where the CLOSED specifications say so.
