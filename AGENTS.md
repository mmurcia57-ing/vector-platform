# VECTOR — AGENTS Governance

## Current Project Phase

CURRENT PROJECT PHASE: SPECIFICATION

Implementation is forbidden until:

SDD_STATUS = READY_FOR_IMPLEMENTATION

## Allowed Work

During SPECIFICATION, AI agents may only:

- Create or update Markdown documentation.
- Create specifications.
- Create diagrams.
- Create ADRs.
- Define contracts.
- Define acceptance scenarios.
- Define plans and implementation tasks.
- Analyze evidence.
- Identify contradictions, blockers, assumptions and TBDs.

## Forbidden Work

During SPECIFICATION, AI agents must not:

- Create application source code.
- Create production infrastructure.
- Implement UI, backend, databases or integrations.
- Expand product scope without explicit approval.
- Invent corporate systems, fields, APIs, permissions or data sources.
- Convert assumptions into facts.
- Reopen CLOSED decisions because of optional improvements.

## Source of Truth Priority

When information conflicts, use this order:

1. CLOSED ADR / Decision
2. Approved Specification
3. Verified Evidence
4. Stakeholder-confirmed professional or technical information
5. Accepted Hypothesis
6. TBD

An AI agent MUST NOT silently override a higher-priority source.

## Epistemic Status

Use these states explicitly:

- CONFIRMED
- HYPOTHESIS
- TBD

If the information is not known, use TBD.
Do not guess.

## Contradiction Policy

If a contradiction affects implementation or a CLOSED decision:

STOP.

Create a SPEC-BLOCKER.

Do not reinterpret the decision.
Do not invent a resolution.

## CLOSED Decision Policy

A CLOSED decision may only be reopened if a real blocker, contradiction, invalidated assumption or new authoritative evidence makes implementation unsafe or impossible.

A better idea alone is not sufficient reason to reopen it.

## Specification Quality Rule

Before closing an artifact, ask:

"Would its absence force an implementer to invent a relevant decision?"

If YES:
the gap is a specification blocker.

If NO:
record it as debt, backlog or future improvement.

## Evidence Policy

Evidence is not automatically specification.

Every external input must be classified before becoming a requirement.

Preserve:

- source
- authority
- provenance
- confidence
- coverage semantics
- known limitations

## Source Authority Policy

VECTOR may correlate information from multiple systems, but must preserve which source is authoritative for each measurement or fact.

VECTOR must not silently replace source authority with its own interpretation.

## Privacy and Scope Guardrail

Personal, interpersonal or private management information about stakeholders is out of scope.

Only professional, technical, operational and product-relevant inputs may be incorporated into VECTOR specifications or evidence.

## Lab to Corporate Portability

VECTOR must be specified using:

Canonical Model
→ Integration Contracts
→ Adapters

Lab implementations may use mocks, fixtures or sandboxes.

Corporate sources that are not confirmed must remain TBD.

Do not invent corporate implementation details.

## AI Model Policy

Use the lowest-capability model that can reliably perform the task.

Escalate only when:

- ambiguity cannot be resolved by improving the prompt;
- the task requires broader synthesis;
- cross-artifact reasoning is necessary;
- the current model repeatedly fails a well-specified task.

## Implementation Rule

Once implementation begins, agents must:

- read AGENTS.md;
- read only the relevant approved specifications;
- not expand scope;
- not invent missing decisions;
- stop and report SPEC-BLOCKER when a required decision is missing.