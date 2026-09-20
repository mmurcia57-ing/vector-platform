# VECTOR — EXT-004 Commitment Lifecycle & Reliability Intelligence

## Status
**READY_FOR_IMPLEMENTATION** — controlled product-depth extension derived autonomously from the Product Completion Discovery pilot.

## Why this extension exists
EXT-001 proved that VECTOR can register and list commitments, but the Product Depth Gate found that representation is not equivalent to lifecycle completion. A commitment capability is useful only when VECTOR can preserve what was agreed, accountability, target date, subsequent renegotiation, execution, evidence, outcome verification and closure history without converting the result into individual-performance scoring.

## Core question
**What did an accountable area commit to, what changed, is it at risk or overdue, what action followed, and did evidence verify the intended outcome?**

## Protected semantics
- Commitment accountability is AreaDomain-level. responsibleParty remains descriptive only.
- No individual productivity/ranking/scoring.
- COMPLETED means execution completion, not verified improvement.
- Renegotiation before due date is distinguishable from silent overdue.
- Historical dates are never overwritten without retaining the prior value and reason.
- Outcome claims require OutcomeVerification/Evidence.
- Corporate Source Authority remains TBD.

## Lifecycle
DRAFT/OPEN → IN_PROGRESS → COMPLETED | CANCELLED, with orthogonal deterministic conditions:
ON_TRACK | AT_RISK | OVERDUE | RENEGOTIATED | OUTCOME_PENDING | OUTCOME_VERIFIED | OUTCOME_PERSISTENT.

## Functional delta
1. Register declaration, accountable AreaDomain, intended result and target/due date.
2. Update execution state through an explicit lifecycle operation.
3. Renegotiate due date only with reason and timestamp; preserve original/prior date.
4. Retain immutable lifecycle history for local VECTOR-native commitments.
5. Expose overdue and due-soon/risk context deterministically from available dates; do not predict failure.
6. Expose execution completion separately from outcome verification.
7. Provide management aggregates for active, in-progress, completed, overdue, renegotiated and completed-without-outcome-verification.
8. Expose a Commitment Reliability Rate only when the denominator is explicit: commitments fulfilled by current agreed date / commitments due in evaluated period. A pre-due-date renegotiation changes the current agreed date but remains visible as renegotiated history.
9. Navigate Commitment → technical context → Action → OutcomeVerification → Evidence where available.
10. Provide filters for area, service, lifecycle condition and due context.

## Experience
The Commitment workspace is a first-class Action & Outcome surface, not a CRUD form. It must answer what is due/overdue, what changed, which commitments were renegotiated before due date, which completed commitments lack verified outcome evidence, what technical condition motivated the commitment, and whether the underlying condition improved, persisted or remains unverifiable.

## State completeness
Loading, empty, partial evidence, stale context, validation failure, authorization denied, system error/retry, create success, lifecycle-update success, renegotiation confirmation and outcome-pending states are required.

## Contract delta
- GET commitments includes lifecycle/reliability aggregates.
- POST commitment remains authenticated/authorized/audited.
- PATCH lifecycle is authenticated/authorized/audited.
- POST renegotiation is authenticated/authorized/audited.
- GET commitment history returns immutable local lifecycle events.

## Acceptance
- Original due date remains reconstructable after renegotiation.
- Renegotiation without reason is rejected.
- Renegotiation after current due date is not counted as pre-due-date renegotiation.
- Completed commitment without OutcomeVerification is explicitly outcome-pending.
- Overdue is deterministic.
- Reliability denominator and period are visible; no percentage is produced for empty denominator.
- No individual score/rank appears.
- Mutation authorization fails closed and audit is emitted.
- Current canonical entity count remains 17.

## Boundaries
Corporate ingestion/authority, predictive commitment failure, individual evaluation and portfolio/capacity intelligence remain outside EXT-004.

## Gate
No SPEC-BLOCKER for local implementation. Corporate ownership/Source Authority remains external/TBD.
