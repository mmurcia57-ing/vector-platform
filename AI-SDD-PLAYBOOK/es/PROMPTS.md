# AI-SDD-PLAYBOOK Prompts

Version: v0.1
Status: BOOTSTRAP / PARTIALLY VALIDATED

## 1. Initialization

Materializa Step 0 — Initialization. Crea solo governance, registers, evidence inventory, Discovery Baseline e initial Quality Gate. Separa raw evidence de normative specification. Levanta SPEC-BLOCKER si falta una decision que obligaria a inventar.

## 2. Product Definition materialization

Materializa solo approved Product Definition decisions. No rediseñes, agregues capabilities, elijas technology ni promociones stakeholder Evidence a specification. Preserva CLOSED decisions y TBDs.

## 3. Capability materialization

Materializa el Master Capability Map aprobado. Para cada capability usa Capability ID, Name, Purpose, Core Question, Expected Outcome y Depends On. Mantén exactas las classifications MUST, SHOULD y POST-V1. Prueba Journey coverage y orphan MUST count.

## 4. Audit

Audita este artifact contra Source of Truth. Verifica que los artifacts previstos esten dentro del validation scope; luego revisa semantic fidelity, counts, IDs, references, classifications, coverage, TBDs y CLOSED decision protection. Reporta SPEC-BLOCKER en vez de resolver silenciosamente una contradiction.

## 5. Semantic drift correction

Corrige solo el materialization defect. Restaura exactamente approved semantics. No rediseñes scope, introduzcas decisions ni cambies statuses. Repite structural y semantic checks.

## 6. Blocker reporting

Deten implementation o closure. Registra un SPEC-BLOCKER con conflicting artifacts, authority order, missing decision, impact y required resolution. No inventes una resolucion.

## 7. Model escalation check

Antes de escalar modelos, evalua prompt ambiguity, context size, task breadth y scope reduction. Usa el modelo de menor capacidad que complete confiablemente la tarea. Trata los nombres de modelos como NON-NORMATIVE tooling examples.
