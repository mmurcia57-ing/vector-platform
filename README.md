# VECTOR — Framework Pilot Branch

> Branch: `framework/vector-full-lifecycle-pilot`
>
> This branch is the isolated brownfield calibration product for the Engineering Intelligence & Execution Framework. It is materially ahead of VECTOR `main`. Do not infer current state from the historical foundation baseline.

## Current local pilot baseline

The branch currently contains:
- 17 canonical entity semantics and local SQLite canonical persistence;
- Evidence, SourceReference and provenance paths;
- deterministic intelligence;
- graph projection/query and durable local projection recovery evidence;
- J01–J04 product/experience paths;
- React/Vite intelligence workspace;
- Spring Boot BFF/backend;
- Commitment Management including EXT-004 lifecycle/reliability depth;
- local HTTP identity/authorization/audit boundary;
- governed AI abstraction with safe unavailable behavior;
- observability abstractions;
- BASELINE/MIXED/STRESS local characterization;
- backend and frontend automated assurance;
- Product Completion Discovery and Capability Depth pilot evidence.

Current pilot evidence is maintained under:
- `docs/framework-pilot/`;
- `specs/extensions/EXT-003-product-depth-intelligence-workspace.md`;
- `specs/extensions/EXT-004-commitment-lifecycle-reliability.md`.

## Product boundary

The approved master capability map remains broader than this release. V1 implements minimum end-to-end slices of the approved V1 MUST capabilities. A local V1 END_TO_END claim is not an assertion that the exhaustive master capability is complete.

POST-V1/evolution scope remains governed and is not silently promoted.

## External/corporate boundaries

The local pilot does not fabricate or claim:
- corporate IAM/IdP;
- corporate Source Authority mappings;
- production credentials;
- corporate AI provider/model;
- real corporate source mappings;
- production volume/capacity;
- production topology/approvals;
- corporate availability/SLO targets.

These remain external/TBD unless authoritative evidence is supplied.

## Local prerequisites

- JDK 21.
- Node.js 24.x.
- Docker Desktop / Docker Compose where the selected local runtime requires it.

## Validation

Typical branch validation:

```powershell
.\backend\mvnw.cmd test
npm --prefix frontend test
npm --prefix frontend run lint
npm --prefix frontend run build
git diff --check
```

Use the current GitHub Actions evidence and `docs/framework-pilot/` reports for release/convergence claims; command examples above do not themselves constitute evidence.

## Architecture invariants

- SPA → BFF; browser does not directly access persistence, graph storage or provider credentials.
- Canonical persistence remains distinct from Source Authority.
- Graph is a deterministic/rebuildable projection/read model, not canonical truth.
- Correlation does not imply causation.
- Execution completion does not imply verified improvement.
- AI assistance does not replace Evidence or human accountability.
- Individual performance/ranking is not part of the V1 local pilot.

## Historical foundation

The original foundation-only README described the state around the early `TASK-FND-002` baseline. That state remains available in Git history for provenance, but is no longer presented as the current branch runtime/product state because doing so caused governance drift during brownfield reconciliation.

## Release statement

This branch is a **local evidence-backed Framework pilot baseline**.

It is **not represented as corporate production ready**, and promotion/merge remains a separate release decision.
