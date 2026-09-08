# VECTOR — AGENTS Governance

## Current Project Phase

CURRENT PROJECT PHASE: IMPLEMENTATION

Implementation is authorized only when:

SDD_STATUS = READY_FOR_IMPLEMENTATION

Implementation must be limited to a task that is Ready under
`specs/implementation-plan.md` and `specs/implementation-tasks.md`, and must
preserve all applicable CLOSED SDD contracts and acceptance requirements.

## Allowed Work

During IMPLEMENTATION, AI agents may:

- Execute only approved Ready-task scope.
- Create source code, tests, and local infrastructure only when required by
  that task and its applicable CLOSED SDD contracts.
- Create or update implementation documentation, contracts, and acceptance
  evidence within approved task scope.
- Identify contradictions, blockers, assumptions, and TBDs.

## Forbidden Work

During IMPLEMENTATION, AI agents must not:

- Expand product scope without explicit approval.
- Invent corporate systems, fields, APIs, permissions or data sources.
- Convert assumptions into facts.
- Reopen CLOSED decisions because of optional improvements.
- Implement a task whose Ready criteria, dependencies, acceptance, or required
  SDD contracts are not sufficient; report a SPEC-BLOCKER instead.

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

## Diagram Documentation Rule

- Prefer Mermaid for normative and explanatory diagrams stored in repository Markdown.
- Diagrams must remain text-based and versionable.
- Mermaid diagrams must not introduce architecture, technology or behavior decisions not already approved.
- ASCII diagrams are non-normative conversational support only.

## AI Model Policy

Use the lowest-capability model that can reliably perform the task.

Escalate only when:

- ambiguity cannot be resolved by improving the prompt;
- the task requires broader synthesis;
- cross-artifact reasoning is necessary;
- the current model repeatedly fails a well-specified task.

## Autonomous Implementation Protocol

This protocol governs every implementation task. CLOSED decisions remain
normative; missing product semantics must never be invented.

### Source of Truth and task selection

Before work, an agent SHALL read the exact Ready task definition, its
dependencies, Ready Criteria, Definition of Done, SDD References, and
Acceptance References. It SHALL implement only a currently Ready task,
respect the declared DAG, and never silently expand into another Ready task or
select a blocked task.

### Preflight

Before editing, verify the branch and `HEAD == origin/main`, inspect Git
status for unexpected tracked changes, and preserve `work-prep/` untouched.
`work-prep/` is non-normative, must remain untracked and unstaged, and must
not be read as a Source of Truth.

### Autonomous execution and repair

Use the loop:

`inspect → plan → implement → compile/build → test → validate → diagnose → correct → retest`.

Routine implementation defects may be corrected autonomously, including
compilation, tests, lint, serialization, mappings, configuration, imports,
local persistence, build, test fixtures, and implementation-related
documentation. Limit correction to five cycles for the same root cause; then
stop rather than loop indefinitely.

### Stop conditions

Stop rather than improvise if work would modify a CLOSED decision; invent a
requirement, product decision, corporate assumption, credential, or
architecture; alter Source Authority semantics; conflate Identity and
Correlation; introduce causation without evidence; touch production; perform a
destructive action; modify `work-prep/`; expand task scope; or implement a
blocked/downstream task. Report exactly:

`STOP — [exact reason]`

### Architecture, security, and AI invariants

Preserve these invariants:

- The SPA never accesses SQLite, Neo4j, or external providers directly;
  integrations are backend-mediated.
- The canonical model is vendor-independent. SQLite local canonical
  persistence remains replaceable, Neo4j is not authority, and data follows
  `Source → Adapter → Normalization → Canonical`.
- Identity != Correlation; Correlation != Causation; canonicalization does not
  transfer Source Authority; conflicting claims are not silently collapsed.
- External integrations are read-only by default in V1. Secrets never enter
  source control, the frontend, logs, or documentation.
- Do not introduce unnecessary microservices, brokers, event sourcing, or
  heavy CQRS.
- For AI-related work, deterministic validated intelligence precedes the LLM.
  The LLM is not Source Authority, does not invent KPIs, silently resolve
  conflicts, declare causation without proof, or expand user permissions.

### Validation discipline

Classify every applicable validation as `PASS`, `FAIL`, `NOT EXECUTED`, or
`NOT APPLICABLE`; never claim PASS without execution. Where applicable,
validate compile/build, the complete relevant and regression test suites,
runtime startup/health, security scan, semantic anti-drift, `git diff --check`,
and `git status`.

### Gate and Git discipline

Until an External Implementation Quality Gate approves the work, agents must
not run `git add`, `git commit`, `git push`, or update GitHub Project. Keep
implementation local and uncommitted for that gate. An implementation agent
does not self-close a task. Only after external approval may work be staged
selectively, committed, pushed, marked Done, and used to recalculate the Ready
Set.

A successful implementation must finish exactly:

`READY FOR IMPLEMENTATION QUALITY GATE`

Otherwise finish with the required STOP format.
