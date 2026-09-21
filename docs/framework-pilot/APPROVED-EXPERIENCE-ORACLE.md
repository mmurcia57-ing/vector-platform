# APPROVED EXPERIENCE ORACLE — VECTOR

Status: DECISION / ACTIVE  
Date: 2026-09-21

## Purpose

This file closes the continuity defect discovered during the knowledge rechallenge: repository history alone is not sufficient to determine the approved product experience.

The implementation MUST reconcile three authorities before changing an approved surface:

1. **Closed conversation decisions and approved visual artifacts** — visual/interaction oracle.
2. **Experience Contract V3 and executable backend/frontend contracts** — behavioral and epistemic authority.
3. **engineering-intelligence-knowledge-base** — external challenge baseline and reusable engineering patterns.

A later commit name, experiment, or document called "North-Star" MUST NOT silently supersede an approved visual artifact.

## Approved visual grammar

The approved VECTOR family uses:
- dark navy product sidebar;
- light/white analytical workspace;
- corporate blue as primary interaction color;
- red/orange/yellow/green semantic operational accents;
- dense executive/product cards, tables, timelines and evidence panels;
- shared VECTOR / Tecnología con Propósito product shell;
- surfaces differentiated by decision job rather than duplicated dashboard layouts.

Approved artifact family recovered from conversation/library:
- Executive Panorama Golden;
- Area / Domain Golden — "Panel de Confiabilidad Canales PN";
- Service Golden — "Panel de inteligencia del servicio de autenticación";
- Risk Investigation Goldens — "Panel VECTOR de investigación de riesgos" and "Investigación de riesgo RF-023 en VECTOR";
- Commitments / Outcomes Golden;
- executable HTML replica: `vector_replica_quality.html`.

These artifacts are **design evidence**, not production data contracts. Literal sample values from a Golden MUST NOT be invented in runtime; live/mock projection data supplies values.

## Continuity Gate

Before declaring an experience iteration converged:

- [ ] identify the approved oracle(s) for every affected surface;
- [ ] prove the delivered branch contains a material implementation of the approved visual grammar;
- [ ] preserve closed behavioral/epistemic decisions;
- [ ] prove S1–S5 remain semantically differentiated;
- [ ] run behavioral tests, lint and build;
- [ ] run backend tests;
- [ ] where browser/screenshot tooling is available, compare rendered runtime against the approved oracle;
- [ ] if visual comparison cannot be executed, state that boundary explicitly — CI PASS is not visual fidelity PASS.

## Regression rule

**Conversation/Decision Continuity Gate**: an implementation cannot replace an approved experience target merely because a newer repository commit, branch, or experiment exists. Repository state, closed decisions, and approved artifacts MUST be reconciled first.

**Experience Target Materialization Gate**: technical CI cannot close a visual/product evolution unless the target experience is materially present in the delivered tree.

## Current implementation evidence

Branch: `experiment/vector-knowledge-rechallenge-v2`

Reconciliation commits:
- `c14d748` — approved Golden executive experience restored over V3 runtime.
- `b85b46a` — approved Golden visual grammar materialized.
- `bf45bde` — S2–S5 bound to approved Golden shell.
- `72b95d2` — surface differentiation reinforced.

GitHub Actions run `35592783202`:
- frontend tests: PASS
- lint: PASS
- frontend build: PASS
- backend Maven tests: PASS

Boundary: browser screenshot/pixel comparison against the recovered image artifacts has not been executed in this environment. Do not label visual fidelity as fully converged until that evidence exists.
