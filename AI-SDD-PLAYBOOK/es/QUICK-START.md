# AI-SDD-PLAYBOOK Quick Start

Version: v0.1
Status: BOOTSTRAP / PARTIALLY VALIDATED

## 1. Start safely

1. Leer governance y el Project State actual.
2. Identificar Evidence, specifications y decisions CLOSED aprobadas como Source of Truth.
3. Declarar el step, scope y open questions actuales.
4. No implementar mientras el SDD status aplicable no sea READY FOR IMPLEMENTATION.

## 2. Run validated Steps 0–2

### Step 0 — Initialization

Crear governance, registers, Discovery Baseline, evidence inventory e initial Quality Gate. Separar raw evidence de normative specification.

### Step 1 — Product Definition

Materializar Product Problem, Mission, Outcomes, Users, Value Proposition, Non-Goals y Principles aprobados. Tratar stakeholder input como Evidence hasta que sea aprobado.

### Step 2 — Scope & Capabilities

Crear Master Capability Map con Capability ID, Name, Purpose, Core Question, Expected Outcome y Depends On. Clasificar cada capability como MUST, SHOULD o POST-V1. Definir Vertical Journeys y probar que cada MUST tiene Journey coverage.

### Step 3 — Product Boundary / Domain Model

Resolver Product Boundary y Bounded Context semantic ownership antes de detailed Functional Specification. Definir el minimo vendor-independent Canonical Domain Model, preservar Source Authority e identity uncertainty, y confirmar que correlation no implica causation.

## 3. Audit before closure

- Aplicar anti-loop rule: preguntar si la ausencia de la informacion obligaria al implementer a inventar una relevant decision. Si YES, levantar SPEC-BLOCKER. Si NO, registrar Backlog / Future Improvement / documented debt. Una missing decision no es automaticamente un SPEC-BLOCKER.
- Revisar CLOSED decision protection y semantic fidelity.
- Reconciliar inventories, classifications, counts, IDs, references y coverage.
- Preservar Source Authority, Evidence, TBDs y target-environment uncertainty.
- Usar Mermaid-first para visuales normativos del repositorio.

## 4. Close and hand off

Pasar el Quality Gate aplicable, inspeccionar git diff y staged content, y luego hacer commit del bloque CLOSED. Hacer push tras accepted closure. GitHub Projects handoff es ROADMAP / PARTIALLY DEFINED y nunca reemplaza specification.

## 5. ROADMAP / NOT YET VALIDATED

Los Steps 4–16 no estan validados. Aplicar solo governing principles, no reglas metodologicas detalladas, hasta que esos stages sean ejercidos y validados.
