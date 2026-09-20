> **Framework pilot branch notice (EXT-003):** this branch is materially ahead of the historical foundation state described below. It now contains canonical SQLite persistence, Evidence/provenance, deterministic intelligence, graph projection/query, J01–J03 services, experience projections, commitment management, security/AI/observability abstractions, automated backend tests, the Product Completion Discovery pilot, and the EXT-003 Intelligence Workspace evolution. The historical sections are retained for provenance; current pilot truth is tracked under `docs/framework-pilot/` and `specs/extensions/EXT-003-product-depth-intelligence-workspace.md`. Corporate IAM, Source Authority mappings, production volumes and corporate production readiness remain TBD.

# VECTOR local engineering foundation

This repository currently contains the technical foundation through
`TASK-FND-002`.
It is not a product slice: canonical entities, persistence schemas, graph
projection, external integrations, journeys, and final UX are intentionally
deferred to their approved follow-on tasks.

## Prerequisites

- JDK 21 (`JAVA_HOME` and `java` must resolve to Java 21).
- Node.js 24.x for the SPA toolchain.
- Docker Desktop / Docker Compose for the local Neo4j runtime.

The backend includes the Maven Wrapper; a global Maven installation is not
required. SQLite is the approved local canonical persistence adapter, but its
schema and adapter are intentionally deferred to `TASK-CAN-001`. The local
runtime directory is prepared and ignored by Git for that later task.

## Local configuration and runtime

1. Copy `.env.example` to `.env` and replace the password placeholder with a
   local-only value. Do not commit `.env`.
2. Start Neo4j: `docker compose up -d neo4j`.
3. Verify it is ready: `docker compose ps` and
   `docker compose logs neo4j`.

Neo4j is a future rebuildable projection/read model, not canonical truth or
Source Authority. No application code connects to it in this foundation.

The backend reads the non-sensitive runtime label from
`VECTOR_RUNTIME_ENVIRONMENT`, defaulting safely to `local`. An explicit blank
value is rejected at startup. The frontend example in `frontend/.env.example`
contains only a public BFF base URL. Never place passwords, tokens, credentials,
or provider configuration in `VITE_*` variables: those values are embedded in
the browser bundle.

## Run locally

Install SPA dependencies:

```powershell
npm --prefix frontend install
```

Start the backend (requires JDK 21):

```powershell
.\backend\mvnw.cmd spring-boot:run
```

Verify backend health:

```powershell
Invoke-WebRequest http://localhost:8080/actuator/health
```

Start the SPA technical shell:

```powershell
npm --prefix frontend run dev
```

The SPA has no direct database, Neo4j, Cypher, provider, or credential access.
It is a technical runtime shell only; experience/BFF work begins in its
approved later task.

## Deterministic synthetic seed harness

`TASK-DATA-001` provides an in-memory seed harness under
`backend/src/main/java/com/vector/bff/seed`. It produces synthetic,
versioned fixtures from explicit profile, seed, version, and effective
configuration inputs. The `GOLDEN` manifest carries GS-01 through GS-12 as
non-evaluated oracle descriptors; it does not claim that later canonical or
journey logic has been implemented. SMALL, BASELINE, and STRESS records are
generated on demand and are never committed as large datasets.

## Validation

```powershell
.\backend\mvnw.cmd test
npm --prefix frontend test
npm --prefix frontend run build
docker compose config
git diff --check
```

`AT-17` performance execution is not yet applicable: representative workloads
and vertical slices are intentionally absent. This foundation establishes only
the readiness needed for later performance work.
