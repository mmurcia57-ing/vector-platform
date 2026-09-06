# AI-SDD-PLAYBOOK

Version: v0.1
Status: BOOTSTRAP / PARTIALLY VALIDATED

## 1. Purpose and validation boundary

Este playbook reusable registra metodologia SDD ejercida hasta Integration Specification. Los Steps 0–6 estan VALIDATED. Step 6 esta CLOSED / VALIDATED. Step 7 esta CLOSED / EXTERNAL QUALITY GATE PASSED. Step 8 es el NEXT; los Steps 9–16 permanecen ROADMAP / NOT YET VALIDATED y ningun step autoriza implementacion por si solo.

## 2. SDD operating model

Understand → Decide → Materialize → Audit → Quality Gate → Commit → Next Step

ChatGPT actua como mentor, partner de reasoning/discovery, soporte de decisiones de producto/arquitectura, auditor de Quality Gate y adversarial reviewer. Codex actua como specification materialization agent, scoped execution agent, test executor y blocker reporter.

## 3. Validated lifecycle

### Step 0 — Initialization

Establecer governance del repositorio: AGENTS.md, Project State, Decision Register, Open Questions Register, Evidence Register, Source Inventory, Discovery Baseline, politica de SPEC-BLOCKER e initial Quality Gate. Raw evidence es insumo historico; normative specification es una especificacion aprobada o una decision CLOSED.

### Step 1 — Product Definition

Materializar Product Problem, Product Mission, Product Outcomes, Target Users, Value Proposition, Product Non-Goals y Product Principles. Stakeholder input es Evidence, no approved specification. Cerrar el Product Definition Quality Gate solo sin expandir scope.

### Step 2 — Scope & Capabilities

Crear el Master Capability Map. Cada capability usa Capability ID, Name, Purpose, Core Question, Expected Outcome y Depends On. Clasificar cada una una sola vez como MUST, SHOULD o POST-V1.

V1 usa minimum useful end-to-end slices: la porcion util minima requerida por los Vertical Journeys aprobados, no implementacion exhaustiva. Definir Vertical Journeys, Capability-to-Journey Coverage Matrix y orphan MUST detection. Cerrar Scope Quality Gate solo cuando counts, classifications, IDs y coverage reconcilian.

### Step 3 — Product Boundary / Domain Model

Resolver Product Boundary antes de detailed Functional Specification cuando los domains podrian confundirse. Definir semantic ownership de Bounded Context sin inferir deployment architecture. Definir un minimo vendor-independent Canonical Domain Model justificado por required capabilities y journeys, preservar Source Authority separada de Canonical Representation y hacer explicita la incertidumbre de identity/correlation.

### Step 4 — Functional Specification

Status: CLOSED / VALIDATED. Usar Journeys como functional-specification backbone; definir observable behavior, Cross-Journey Functional Rules, insufficient-data behavior, Evidence/Explainability requirements y Functional Coverage Matrix. Separar requirements de algorithms, formulas y architecture decisions. Evitar orphan MUST capabilities y preservar Progressive Specification compatibility.

### Step 5 — Data Specification

Status: CLOSED / VALIDATED. Definir Minimum Data Contracts desde functional behavior, preservar canonical/source identity, provenance, Source Authority, temporal semantics cuando sean relevantes y Data Confidence contextual antes de scoring formulas. Cubrir cada canonical entity, Journey y MUST capability sin seleccionar physical persistence.

### Step 6 — Graph / Evidence Specification

Status: CLOSED / VALIDATED. Definir directional, typed semantic relationships y journey traversability; preservar Evidence/provenance y temporal context sin seleccionar graph technology ni promover correlation a causation.

### Step 7 — Integration Specification

Status: CLOSED / EXTERNAL QUALITY GATE PASSED. Definir integration contracts vendor-independent detras de un Adapter / Anti-Corruption Layer; distinguir integration de Source Authority; usar mocks o sandboxes contract-compatible cuando corporate access no este disponible; preservar provenance, incertidumbre de identity/correlation y graceful partial intelligence sin inventar corporate details.

## 4. Governing rules

### Closed Baseline vs Evolution Backlog

Los Steps 0–6 son una baseline semantica cerrada. Los items del Evolution Backlog registran evolucion futura acotada y no deben reabrir, reinterpretar ni introducir drift semantico en la baseline cerrada.

### Anti-loop rule

Pregunta: “Would the absence of this information force an implementer to invent a relevant decision?”

YES → SPEC-BLOCKER
NO → Backlog / Future Improvement / documented debt

Una mejor idea por si sola no reabre una decision CLOSED.

### CLOSED decision protection

Una decision CLOSED solo puede reabrirse por real implementation blocker, contradiction, invalidated assumption, authoritative new evidence o security/safety concern. No reinterpretar silenciosamente una decision CLOSED.

### Semantic Drift Rule

Un artifact puede pasar checks de counts, sintaxis y estructura y aun asi desviarse de una decision CLOSED aprobada. Los Quality Gates validan semantic fidelity ademas de structural correctness.

### Mathematical and referential consistency

Los Quality Gates validan consistencia entre inventories, classifications, counts, coverage matrices, IDs, references y Traceability structures. Un mismatch es un specification defect.

### Progressive Specification / Vertical Readiness

La especificacion global puede continuar mientras verticals independientes avanzan. Un vertical llega a READY FOR BUILD solo cuando todas sus specifications requeridas son CLOSED, ningun transversal blocker sin resolver lo afecta y Acceptance Criteria estan definidos. Progressive Specification nunca evita specification.

### Model Escalation Policy

Usar el modelo de menor capacidad que complete confiablemente la tarea. Antes de escalar, inspeccionar prompt ambiguity, context size, task breadth y si el scope puede reducirse.

#### NON-NORMATIVE / CURRENT TOOLING EXAMPLE

- Level 1 execution: GPT-5.6 Luna
- Level 2 multi-file synthesis: GPT-5.6 Terra
- Level 3 deep reasoning: GPT-5.6 Sol

Los nombres de modelos pueden cambiar y no son parte de la metodologia SDD.

### Target-Environment-Aware Specification

Cuando el target environment real no esta disponible: modelar known target sources, definir vendor-independent contracts, usar contract-compatible mocks/sandboxes, dejar corporate details desconocidos como TBD y no inventar target-environment details.

Patron de portability: Canonical Model → Integration Contract → Adapter.

### Mermaid-first rule

Los diagramas normativos de repositorio son Mermaid-first, text-based y versionable. No pueden introducir unapproved semantics. ASCII es solo soporte conversacional y no es formato normativo de repositorio.

## 5. Delivery discipline

Cada bloque SDD CLOSED debe ser committed despues de pasar su Quality Gate; push ocurre tras accepted closure. No hacer commit de blockers conocidos como CLOSED. Usar git diff antes de commit e inspeccionar staged content, no solo summaries.

GitHub Projects handoff es ROADMAP / PARTIALLY DEFINED: specification blockers permanecen en SDD; known executable work pasa a Project backlog; future improvements pasan a backlog; Projects no reemplaza specification.

## 6. Lessons

- SDD-001 — Separate requirement from solution proposal.
- SDD-002 — Stakeholder input is Evidence, not specification.
- SDD-003 — Potential improvement does not keep specification open.
- SDD-004 — CLOSED reopens only for a blocker or equivalent approved condition.
- SDD-005 — Validate Product Boundary before functional specification.
- SDD-006 — READY FOR IMPLEMENTATION is a Quality Gate, not a feeling.
- SDD-007 — Quality Gates validate mathematical and referential consistency between inventories, classifications, and Traceability structures.
- SDD-008 — Repository diagrams are Mermaid-first.
- SDD-009 — Structural correctness does not guarantee semantic fidelity; audit semantic drift against CLOSED decisions.
- SDD-010 — Audit Scope Verification: un Quality Gate debe verificar que los artifacts previstos para el audit estan realmente incluidos en el validation scope. Un validation command exitoso no es evidencia de quality cuando los artifacts previstos quedaron fuera de ese scope.
- SDD-011 — Resolver Product Boundary antes de detailed Functional Specification cuando multiples domains podrian confundirse.
- SDD-012 — Bounded Context es un semantic ownership boundary, no physical deployment architecture.
- SDD-013 — Canonical Model es vendor-independent y se justifica por required capabilities y journeys, no por cada source object.
- SDD-014 — Canonical Representation no implica Source Authority.
- SDD-015 — Identity y correlation son conceptos separados.
- SDD-016 — Inferred identity/correlation preserva uncertainty, provenance y Evidence.
- SDD-017 — Correlation no debe promoverse a causation sin Evidence suficiente o approved semantics.
- SDD-018 — Un minimo Canonical Domain Model excluye source-system objects no requeridos e individual-performance entities.
- SDD-019 — Minimum Data Contracts se justifican por functional behavior, no por source schemas.
- SDD-020 — Preservar provenance, temporal semantics y Data Confidence contextual antes de physical persistence o scoring formulas.
- SDD-021 — Conflicting claims permanecen explicitos cuando no existe un applicable authority rule.
- SDD-022 — Predicate semantic fidelity requiere una afirmacion verdadera SOURCE + PREDICATE + TARGET.
- SDD-023 — Traversal direction no cambia la normative predicate direction.
- SDD-024 — La cobertura pairwise Capability × Journey debe reconciliar con Supports detallados de las relaciones.
- SDD-025 — Connected semantic subgraphs no deben reducirse a linear narrative edges.
- SDD-026 — Persistir el cierre de un external Quality Gate en los artifacts Source of Truth del repositorio.
- SDD-027 — Integration no establece Source Authority; la autoridad debe permanecer explicita por claim suministrado o como `TBD`.
- SDD-028 — Un Adapter / Anti-Corruption Layer protege la semantica del Canonical Model frente a source models vendor-specific.
- SDD-029 — Un mock o sandbox contract-compatible puede validar una ruta local sin afirmar corporate connectivity, mapping o authority.
- SDD-030 — Graceful partial integration expone contexto missing, stale, partial y unresolved en lugar de fabricar certeza.
- SDD-031 — Una recomendacion interna de pasar al Quality Gate externo no equivale a cierre; el estado CLOSED requiere persistir el resultado formal externo en los artifacts Source of Truth del repositorio.

## 7. ROADMAP / NOT YET VALIDATED

- Step 7 Integrations — CLOSED / EXTERNAL QUALITY GATE PASSED
- Step 8 UX — NEXT
- Step 9 Architecture
- Step 10 Security + NFR + Observability
- Step 11 AI Behavior
- Step 12 Acceptance / Test Strategy
- Step 13 Implementation Plan
- Step 14 Task Decomposition
- Step 15 Traceability
- Step 16 Final Adversarial Review

No se establecen reglas metodologicas detalladas para estos Steps mas alla de los principios validados de este playbook.
