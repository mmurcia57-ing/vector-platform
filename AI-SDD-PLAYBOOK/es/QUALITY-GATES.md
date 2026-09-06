# AI-SDD-PLAYBOOK Quality Gates

Version: v0.1
Status: BOOTSTRAP / PARTIALLY VALIDATED

## 1. Common audit logic

Cada Quality Gate verifica Source of Truth, scope fidelity, TBDs explicitos, CLOSED decision protection, semantic fidelity y mathematical/referential consistency. Sintaxis o counts validos por si solos son insuficientes.

Usar anti-loop rule: preguntar si la ausencia de la informacion obligaria al implementer a inventar una relevant decision. Si YES, levantar SPEC-BLOCKER. Si NO, registrar Backlog / Future Improvement / documented debt. Una missing decision no es automaticamente un SPEC-BLOCKER.

Antes de confiar en un validation result, verificar que los artifacts previstos para el audit estan realmente incluidos en el validation scope. Un command exitoso no es evidencia de quality cuando los artifacts previstos quedaron fuera de ese scope.

## 2. Step 0 — Initialization Quality Gate

Confirmar que repository governance, AGENTS.md, Project State, Decision Register, Open Questions Register, Evidence Register, Source Inventory, Discovery Baseline, raw-evidence classification y blocker policy existen y son coherentes.

## 3. Step 1 — Product Definition Quality Gate

Confirmar que Product Problem, Mission, Outcomes, Target Users, Value Proposition, Non-Goals y Principles reflejan approved decisions. Confirmar que stakeholder input no fue promovido a specification sin aprobacion.

## 4. Step 2 — Scope Quality Gate

Confirmar que cada capability tiene el validated definition contract; classifications son mutuamente excluyentes; inventory counts reconcilian; Vertical Journeys estan definidos; cada MUST mapea a al menos un Journey; orphan MUST count es cero; y la matrix mantiene semantic fidelity con approved journeys.

## 5. Environment Portability Gate

Status: ROADMAP / NOT YET VALIDATED. El principio de portability validado es Canonical Model → Integration Contract → Adapter; target-environment details permanecen TBD cuando son desconocidos.

## 6. Step 3 — Product Boundary / Domain Model Quality Gate

Confirmar que Product Boundary y Bounded Context responsibilities son explicitos; los boundaries permanecen semanticos y no se convierten en deployment decisions; el minimo vendor-independent Canonical Domain Model se justifica por required capabilities y journeys; Source Authority permanece distinta de Canonical Representation; identity states preservan uncertainty y provenance; correlation es Evidence-backed y no se promueve a causation; J01–J04 son representables; y todas las MUST capabilities permanecen soportadas.

## 7. ROADMAP / NOT YET VALIDATED

Los Quality Gates para Steps 4–16 no estan validados. Pueden usar common audit logic, pero no tienen gate definition detallada en v0.1.
