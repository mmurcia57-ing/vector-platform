# VECTOR — EXT-001 Commitment Management MVP

## 1. Metadata

- Status: READY_FOR_IMPLEMENTATION
- External Extension Quality Gate: PASSED
- SPEC-BLOCKERS: 0
- Scope: local VECTOR-native MVP
- Canonical model: unchanged; exactly 17 V1 entities
- OQ-009: OPEN; corporate Commitment ownership/source authority remains TBD
- DEC-EXT001-001: APPROVED controlled amendment — Commitment is accountable to
  exactly one existing AreaDomain; no organizational canonical entity is added.

## 2. Purpose

Provide a small, traceable Management Experience for recording commitments,
following execution, and connecting technological commitments to later evidence
and outcome verification.

## 3. Problem Statement

VECTOR needs to preserve what was agreed, responsible area, due date, execution
state, supporting evidence, and—where technical context exists—whether the
underlying condition improved. VECTOR does not measure individual effort or
productivity.

## 4. Scope

EXT-001 supports local manual commitment registration, area/responsibility,
due date, explicit execution status, deterministic overdue derivation, intended
result, Evidence/SourceReference, optional Service/ConfigurationItem/RiskFinding
context, the existing ImprovementAction → OutcomeVerification chain, aggregate
counts (active, in progress, completed, overdue), list/filter/detail, creation
entry point, and navigation to related SRE context.

Administrative commitments may exist without technical context. Technological
commitments may reference Service, ConfigurationItem, and RiskFinding.

## 5. Non-Goals

No corporate ingestion or system of record, AI extraction/creation, predictive
commitments, Capacity Intelligence, Organizational Friction, productivity or
employee scoring, new organizational/person entities, objectives/decisions
model, SRE redesign, or new architecture style.

## 6. Preserved CLOSED Decisions

The platform, domains, Service-centered reliability semantics, CI technical
context, 17-entity model, Evidence-first semantics, provenance, Source
Authority, Identity != Correlation, Correlation != Causation, human
accountability, BFF boundary, and Neo4j projection-only role remain unchanged.

## 7. Functional Requirements

- FR-EXT-001: register a local commitment manually.
- FR-EXT-002: assign area/responsibility and due date.
- FR-EXT-003: preserve explicit execution status and derive overdue deterministically.
- FR-EXT-004: preserve Evidence and SourceReference provenance.
- FR-EXT-005: optionally connect technical context and the existing action/outcome chain.
- FR-EXT-006: expose deterministic aggregate/list/detail projections and filters.
- FR-EXT-007: expose limitations and missing context without fabricated ownership.

## 8. Data / Canonical Model Impact

The existing Commitment entity is reused: declared Commitment, exactly one
structural accountable AreaDomain, RiskFinding context where applicable, and
due/status context. Optional `responsibleParty` is a 0..1 descriptive/reference
value. No new canonical entity or duplicate field is introduced. AreaDomain, Service, ConfigurationItem,
RiskFinding, Evidence, SourceReference, ImprovementAction, and
OutcomeVerification retain their existing contracts.

## 9. Commitment Semantics

Execution state, overdue derivation, and outcome verification are separate.
Completion does not imply improvement. A completed technological commitment may
have OutcomeVerification `PERSISTENT` or not yet verifiable.

The conceptual classification is limited to general/administrative and
technological/improvement commitments; no new normative taxonomy is created.

## 10. Source Authority / Provenance

Local manual commitments may use VECTOR-native provenance within the existing
Source Authority model. External corporate authority and ownership remain TBD
under OQ-009. Canonicalization never transfers external authority, and
conflicting claims remain distinguishable.

## 11. Management Experience Projection

The Management Experience provides Panorama Ejecutivo, Area/Management
Intelligence, and Commitments & Improvements projections with summary counts,
filters, list/detail, create commitment, due/execution/overdue state,
provenance, and outcome visibility where applicable. It uses BFF-mediated
access and does not become a separate product.

## 12. SRE Experience Integration

Technological commitments are navigable toward Service/RiskFinding/Evidence
context. SRE views may expose associated commitments as context without
changing the Service-centered interaction model. Administrative commitments do
not require SRE context.

## 13. Execution vs Outcome Rules

`Commitment = COMPLETED` means execution state only. It does not assert
`OutcomeVerification = IMPROVED`. New operational Evidence and the existing
deterministic verification semantics are required for improvement claims.

## 14. Acceptance Criteria

- Administrative commitment works without Service, CI, or RiskFinding.
- Technological commitment preserves optional technical links.
- Due/overdue state is deterministic and separate from outcome.
- Evidence and SourceReference remain visible with provenance.
- Completed action with insufficient Evidence remains not yet verifiable.
- No individual score, causal claim, authority invention, or new entity appears.
- Management and SRE projections preserve shared context and partial-data behavior.

## 15. Traceability

EXT-001 extends E02 and E03, uses J03/J04, and preserves the chain
`RiskFinding → Commitment → ImprovementAction → OutcomeVerification`.
It consumes existing C01, C02, C07, and C10 semantics and remains compatible
with AT-01/04/09 and GS-04/05/06/12.

## 16. Deferred Evolution Items

Corporate ingestion, AI extraction, predictive commitments, broader management
intelligence, Capacity Intelligence, Organizational Friction, Objectives &
Outcomes, and Decision Management remain outside this extension.

## 17. Open Questions

Corporate Commitment Source Authority and ownership (OQ-009), future external
mapping, and corporate operational policy remain TBD. No corporate source is
assumed.

## 18. SPEC-BLOCKER Assessment

`EXT001-SB-001 — Organizational Responsibility Representation` is RESOLVED by
DEC-EXT001-001. MDC-11 now requires the deterministic structural relationship
`Commitment --ACCOUNTABLE_TO--> AreaDomain` with exactly one existing
AreaDomain target. Optional `responsibleParty` does not create organizational
semantics or authority.

## 19. Extension Readiness Recommendation

READY_FOR_IMPLEMENTATION. The controlled amendment is materialized and the
External Extension Quality Gate has PASSED. Implementation remains subject to
the approved EXT-001 task decomposition and acceptance strategy.
