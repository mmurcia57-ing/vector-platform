# VECTOR — AI Behavior, Governance & Provider Specification

## 1. Status and scope

- Status: CLOSED / EXTERNAL QUALITY GATE PASSED
- Step: Step 11 — AI Behavior, Governance & Provider Specification
- External Quality Gate: PASS
- SDD status: NOT_READY_FOR_IMPLEMENTATION
- SPEC-BLOCKERS: 0

This specification defines VECTOR V1 AI epistemic boundaries, capabilities,
security inheritance, provider abstraction, context construction, structured
outputs, provenance, behavior governance, evaluation, consumption
observability, and graceful degradation. It does not create application code,
AI SDK integration, embedded prompts, infrastructure, or a physical DTO/schema.

Steps 0–10 remain CLOSED. Step 11 is MATERIALIZED / PRE-AUDIT. Step 12 has not
started.

## 2. AI role and governing pipeline

The normative conceptual pipeline is:

`Source Data → Integration Adapters → Canonical Model → Deterministic Processing → Validated Metrics / Evidence / Correlation / RiskFinding → Structured AI Context → AI Provider Contract → Explanation / Hypothesis / Recommendation → Human Decision`

The prohibited shortcut is:

`raw telemetry/source data → LLM → authoritative VECTOR truth`

The LLM is not Source Authority, canonical data authority, KPI calculation
engine, identity authority, causal-inference authority, OutcomeVerification
authority, or autonomous decision authority. AI-generated explanation is not
automatically Evidence; an AI hypothesis is not automatically RiskFinding; an
AI recommendation is not automatically Commitment; and AI explanation of
OutcomeVerification is not its authority.

The closed chain remains:

`RiskFinding → Commitment → ImprovementAction → OutcomeVerification`

## 3. AI epistemic and safety boundaries

### AI-01 — Deterministic before generative

KPI, metric, count, aggregation, time window, SLO state, aging, before/after
comparison, and other deterministically computable values are calculated
outside the LLM. AI receives validated results and cannot invent or
authoritatively recalculate them.

### AI-02 — Evidence-grounded generation

Decision-relevant explanations and recommendations are grounded in available
VECTOR Evidence and canonical context. Claims remain traceable where
applicable to Evidence, MetricObservation, RiskFinding, SourceReference,
canonical context, and validated deterministic intelligence. Insufficient
Evidence remains explicit.

### AI-03 — No causation fabrication

AI may describe temporal association, correlation, contextual relationship, or
an investigation hypothesis. It must not promote these to proven root cause or
causation without authorized supporting Evidence. `Correlation != Causation`.

### AI-04 — Preserve uncertainty

AI context and output preserve `CONFIRMED`, `INFERRED`, `UNRESOLVED`,
confidence, uncertainty, conflicts, freshness, missing context, and partial
intelligence. AI must not silently clean uncertainty to strengthen an answer.

### AI-05 — No silent conflict resolution

Conflicting claims remain conflicting unless an applicable explicit Source
Authority rule establishes a canonical representation. Even then, conflict
provenance remains traceable. AI must not fabricate consensus.

### AI-06 — Human-accountable decisions

AI may explain, summarize, formulate hypotheses, recommend next actions, and
assist investigation. It does not autonomously approve Changes, close external
ITSM records, perform production remediation, make organizational/employment
decisions, evaluate individual productivity, or make authoritative governance
decisions. Human accountability remains explicit.

### AI-07 — Permission inheritance

AI never expands acting-user permissions. It may not access Evidence, data,
services, tools, or actions that the acting identity cannot access through
VECTOR. Graceful degradation never bypasses authorization.

### AI-08 — No hidden knowledge as authority

General model knowledge may explain concepts or suggest investigation
directions, but is never represented as VECTOR Evidence or source-derived fact.
Outputs distinguish VECTOR evidence/fact, VECTOR derived intelligence, and AI
general knowledge/inference.

### AI-09 — Structured AI context

The backend/application constructs a bounded, authorization-aware investigation
context. Conceptual contents may include Service, Period, RiskFinding,
Evidence[], MetricObservations[], SourceReferences[], Correlations[],
IdentityState, Conflicts[], DataConfidence, Freshness, MissingContext,
Commitment, ImprovementAction, and OutcomeVerification. This is conceptual;
no physical Java DTO or schema is frozen here.

### AI-10 — Explainability contract

Decision-relevant output distinguishes observed/source facts, derived VECTOR
intelligence, correlations, hypotheses/uncertainty, recommended next actions,
and supporting Evidence references.

### AI-11 — No missing-data hallucination

AI preserves these distinctions: no telemetry ≠ Service healthy; no Incident ≠
no failure; completed ImprovementAction ≠ verified improvement; no Evidence ≠
no risk; missing data ≠ normal behavior. AI never fills evidence gaps with
invented operational continuity.

## 4. V1 AI capabilities

### AI-12 — RiskFinding explanation (V1)

Explain why VECTOR produced or flagged a RiskFinding using validated context.
AI explains; it does not create authoritative metrics or causation.

### AI-13 — Evidence summarization (V1)

Summarize relevant Evidence while preserving navigation/traceability to
supporting Evidence and its limitations.

### AI-14 — Investigation hypothesis generation (V1)

Generate clearly labelled investigation hypotheses. `Hypothesis != Fact` and
`Hypothesis != Proven Causation`.

### AI-15 — Recommended next actions (V1)

Recommend investigation/follow-up actions. `AI RECOMMENDS != AI EXECUTES`.
There is no autonomous production or ITSM mutation in V1.

### AI-16 — Outcome explanation (V1)

Explain deterministic OutcomeVerification and supporting before/after Evidence.
Preserve `IMPROVED`, `PERSISTENT`, insufficient Evidence, and not-yet-
verifiable semantics. AI does not decide an outcome merely because an action
completed.

### AI-17 — Contextual investigation assistant (V1)

Provide bounded assistance inside Risk Investigation for why VECTOR flags a
service/risk, supporting Evidence, correlations, recurrence, associated
changes, action/outcome context, missing information, and next steps. Context
is limited to authorized and relevant investigation scope.

## 5. Explicitly OUT / POST-V1

The following remain OUT/POST-V1: autonomous incident remediation, autonomous
production Changes, autonomous ServiceNow closure, AI Change approval,
unrestricted enterprise chatbot, unrestricted source/tool access, AI-generated
authoritative KPI values, unsupported AI root-cause declaration, and individual
employee/productivity evaluation. They are not current implementation
requirements.

## 6. Provider, context, output, and provenance architecture

### AI-18 — AI provider abstraction

VECTOR depends on an AIProvider-style semantic contract/capability, not
directly on OpenAI, Anthropic/Claude, Gemini, or another vendor. Conceptual
capabilities include explainRiskFinding, summarizeEvidence,
generateInvestigationHypotheses, recommendNextActions, explainOutcome, and
answerInvestigationQuestion. These are examples, not final Java method names.
Provider-specific adapters remain behind the abstraction.

### AI-19 — Graceful AI degradation

AI provider failure does not make deterministic VECTOR unavailable. When AI is
unavailable, Technology Overview, Service Intelligence, RiskFinding, Evidence,
deterministic metrics, graph, Commitment/ImprovementAction, and
OutcomeVerification continue when their dependencies are healthy. AI-specific
explanation, summary, and assistant functions may be explicitly unavailable or
degraded; no fallback answer is fabricated. `AI unavailable != VECTOR
unavailable`.

### AI-20 — Bounded AI context

Context is authorization-aware, investigation-scoped, relevant, bounded,
provenance-aware, and uncertainty-aware. Entire CMDBs, all incidents, all logs,
or unrestricted enterprise data are not indiscriminately sent to a model.
Token/context limits, Evidence limits, windows, and truncation policies are
configurable where appropriate; exact values remain implementation/NFR/
acceptance decisions.

### AI-21 — Structured and validated output

Decision-relevant capabilities prefer structured, validatable output over
uncontrolled free text. A conceptual output may contain explanation,
evidenceReferences[], hypotheses[], recommendedActions[], limitations,
uncertainty, and provenance/context metadata. The backend validates output
before representing it as VECTOR AI-generated intelligence. Invalid output
fails safely. No physical schema is frozen here.

### AI-22 — AI output provenance

Users and systems can distinguish VECTOR FACT, VECTOR DERIVED INTELLIGENCE,
and AI-GENERATED EXPLANATION/RECOMMENDATION. AI output never silently becomes
canonical source fact. Sufficient traceability identifies that output was
AI-generated and the governed context/version supporting it, subject to later
retention decisions.

### AI-23 — Governed AI behavior

Important prompts, instructions, and behavior are not arbitrary strings
scattered through application code. Behavior is governable/versionable through
the conceptual chain `AI Capability → Prompt/Instruction Policy → Version →
AIProvider`. Exact registry/storage is implementation-level.

### AI-24 — AI evaluation required

Step 12 must define positive and adversarial evaluation for AI-12..AI-17,
including correlation versus causation, conflicting claims, missing telemetry,
INFERRED and UNRESOLVED identity, insufficient Evidence, completed action
without verified outcome, source/provenance distinctions, permission
boundaries, and unsupported causal claims. Plausible prose alone is not
acceptance evidence.

### AI-25 — AI consumption observability

AI usage is observable by request count, latency, success/failure, provider,
model, capability, input/output consumption where exposed by the provider, and
fallback/degradation behavior. Provider-specific billing formulas are not
invented. Budgets/limits are configurable where appropriate.

### AI-26 — Configurable provider/model policy

Provider/model selection is policy/configuration, not scattered hardcoded
constants. The conceptual chain is `AI Capability → AI Policy/Configuration →
Provider → Model → parameters/limits`. A provider/model change cannot alter
semantic or security invariants, enable unauthorized access, fabricate Source
Authority, silently resolve conflicts, assert unsupported causation, invent
authoritative KPIs, or judge individual productivity.

## 7. AI configuration governance

Step 10 NFR-07..NFR-10 principles apply. Potential configurable candidates are
provider, model, capability-to-model mapping, context/token and Evidence limits,
timeout, retry/fallback, consumption budget/limits, prompt/instruction version,
and sampling/generation parameters where applicable. These need not all be
user-configurable, runtime-changeable, or managed through a VECTOR UI.

Semantic and security invariants are not configurable, including Evidence and
Source Authority semantics, Identity != Correlation, Correlation != Causation,
permission inheritance, human accountability, and the deterministic-before-
generative boundary. Step 16 must later inspect for hidden hardcoded AI
policies and hidden AI authority.

## 8. AI provider security and integration boundary

AI provider integration is backend-mediated through provider adapters and
semantic ports. Secrets are externalized; least privilege applies; provider
credentials never reach the SPA; provider-specific models remain isolated; and
provider failure is observable. No corporate AI provider or model is selected
without authoritative evidence. Local V1 provider choice may remain
configurable/implementation-level.

The acting identity's authorization is enforced before context construction,
provider invocation, tool use, and output exposure. AI never expands
permissions. Detailed AI behavior remains this step; future AI contracts and
evaluation are refined through Step 12 and later governance.

## 9. Step 12 evaluation handoff

Step 12 must validate at minimum:

1. grounded RiskFinding explanation;
2. Evidence-summary traceability;
3. hypothesis labelling;
4. no unsupported causation;
5. next-action recommendation without execution;
6. OutcomeVerification explanation;
7. contextual-assistant authorization;
8. conflicting claims;
9. missing context;
10. INFERRED/UNRESOLVED identity;
11. insufficient Evidence;
12. unavailable AI provider;
13. malformed/invalid structured output;
14. permission-restricted Evidence;
15. provider/model configuration;
16. AI consumption observability;
17. deterministic results unchanged by provider/model switch;
18. absence of hidden AI authority.

## 10. TBD and debt governance

| Item | Classification | Status |
|---|---|---|
| Corporate AI provider/model | Corporate TBD | Authoritative evidence required |
| Corporate token budgets/cost limits | Corporate TBD | Authoritative evidence required |
| Exact model names and provider parameters | Implementation/configuration TBD | No winner selected |
| Prompt templates and registry/storage | Implementation decision | Governed/versioned later |
| AI response retention/persistence | Implementation/corporate TBD | Later governance/evidence |
| Context sizes, Evidence limits, truncation | Configurable operational policy | Validate in Step 12/implementation |
| AI timeout/retry/fallback | Configurable operational policy | Validate against Step 10/12 |
| Final DTOs/interfaces | Implementation decision | Do not freeze here |
| Provider-specific consumption/billing | Provider/corporate TBD | Do not invent formulas |
| Positive/adversarial evaluation thresholds | Step 12 acceptance decision | Define before acceptance gate |
| Corporate AI governance controls | Corporate TBD | Authoritative evidence required |
| Policy/threshold administration UI | Future evaluation/debt | Not current V1 scope |

These TBDs do not reopen Steps 0–10 and are not automatically Evolution
Backlog items.

## 11. Step 11 Quality Gate

Formal external Quality Gate result: PASS. The gate verified:

- AI-01..AI-26, including explicit V1 AI-12..AI-17 and OUT/POST-V1 scope;
- deterministic-before-generative processing, Evidence grounding, uncertainty,
  conflict visibility, provenance, and no missing-data hallucination;
- permission inheritance, fail-secure behavior, human accountability, and the
  AI permission boundary for Step 11;
- provider abstraction, backend mediation, provider substitutability, bounded
  context, structured validation, output provenance, and graceful degradation;
- governed/versioned behavior without prompts scattered in code;
- AI consumption observability and configurable provider/model policy;
- Step 12 evaluation handoff is substantive;
- Steps 0–10, 17 entities, 18 GRC, 62/62, J01–J04, UXI-01..UXI-15,
  NFR/SEC/OBS/RES, OQ-009, and OQ-016 remain unchanged;
- no implementation, SDK, prompt code, infrastructure, provider credentials,
  hidden authority, or untracked artifact outside SDD-010 scope is introduced;
- `work-prep/` remains untouched and untracked.

Step 11 is CLOSED after the formal external Quality Gate PASS with
SPEC-BLOCKERS: 0. Steps 0–11 remain CLOSED. Step 12 is NEXT. SDD status
remains NOT_READY_FOR_IMPLEMENTATION; no implementation is authorized.
