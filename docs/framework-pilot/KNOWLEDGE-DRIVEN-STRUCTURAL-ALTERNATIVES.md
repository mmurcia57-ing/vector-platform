# VECTOR — Knowledge-Driven Structural Alternatives

## Status
Design study required by the consolidated Framework Knowledge Challenge.

No alternative is selected by visual preference. Canonical/domain semantics remain unchanged.

## 1. Decision tasks
The structure must support:
- J01: understand persistent reliability risk and evidence;
- J02: inspect change-associated degradation without asserting causality;
- J03: follow finding → commitment/action → evidence → outcome verification;
- J04: leadership/area attention → service/risk drill-down with context preserved.

Shared context that must remain visible/persistent where applicable:
- period/time scope;
- AreaDomain;
- Service;
- RiskFinding;
- evidence freshness/coverage/limitations.

## 2. KB impact matrix

| KB knowledge | Challenged assumption | Product implication | Disposition |
|---|---|---|---|
| Product/UX/Functional Engineering | Existing screens/tabs can define IA | Derive IA from tasks/decisions and stateful behavior | ADOPT |
| Progressive Disclosure Command Center (hypothesis) | Dense dashboard/static categories are sufficient | Keep decision summary first; progressively disclose service → investigation → topology/timeline/evidence; keep filters/time visible | EXPERIMENT |
| UX/Accessibility Baseline | Visual polish + partial state handling is enough | Persistent context, filtering, drill-down/return, degraded states, keyboard/accessibility become structural concerns | ADOPT |
| Experience State Matrix (hypothesis) | One dataset/happy path demonstrates experience | Scenario-driven state matrix becomes pilot experiment | EXPERIMENT |
| Technical UI/Graph Rendering | React Flow predetermined by prior preference | Renderer follows task/scale/interaction/accessibility evaluation | ADAPT |
| Dynatrace Smartscape | Graph as isolated visualization | Topology can be contextual troubleshooting/traversal surface | ADAPT, no product copy |
| Backstage Catalog Graph | All relationships can collapse into one graph | Keep declared/catalog/runtime/temporal provenance distinct | ADOPT semantic guardrail |
| OpenSRE / RCA Agent | Graph itself provides intelligence | Evidence collection/context and topology support investigation; explanation remains evidence-backed | ADAPT |
| End-to-End Experience Contract (hypothesis) | Source token/contract presence proves UX | Trace critical action through UI→contract→policy→backend→telemetry→test | EXPERIMENT/ADOPT FOR PILOT |
| Reference-to-Design Evidence | Existing design should be retained or external reference copied | Alternatives must map to VECTOR task/problem and record decision | ADOPT |

## 3. Alternative A — Current multi-page hierarchy, corrected
Structure:
`Command → Area → Service → Risk → Action/Outcome`

Service retains category navigation; Risk retains evidence/timeline/relations/actions sections, but every tab/control becomes functional.

Strengths:
- lowest migration cost;
- preserves existing routes/components;
- simple mental hierarchy.

Risks:
- context reconstruction across pages;
- service/risk categories can fragment one investigation;
- topology, temporal evidence and action/outcome remain separated;
- likely reinforces screen-first structure that KB challenge is questioning.

Use as baseline, not default winner.

## 4. Alternative B — Persistent Investigation Workspace
Structure:
- persistent left rail for Command / Services / Investigations / Actions & Outcomes;
- persistent context bar for Period / Area / Service / Risk / data-quality context;
- central investigation workspace coordinated by the current decision question;
- switchable coordinated lenses: Timeline, Evidence, Topology/Relations, Change Association, Action/Outcome;
- selected item opens contextual detail without losing investigation state;
- leadership Command view drills into the same workspace rather than a separate experience.

Conceptual flow:
`Command → Focus context → Investigate in coordinated workspace → Decide/Act → Verify`.

Strengths:
- aligns with progressive disclosure and context-preservation hypotheses;
- keeps time/filter/context visible;
- graph becomes one investigative lens rather than a decorative destination;
- J01/J02/J03 can share the same evidence context;
- supports scenario-driven states coherently.

Risks:
- larger frontend restructuring;
- requires explicit state model and URL/deep-link design;
- density/accessibility must be tested.

## 5. Alternative C — Timeline-first Reliability Case
Structure:
- Command identifies a condition;
- opening it creates/opens an investigation case centered on a temporal spine;
- changes, incidents, SLO/metric signals, evidence, commitments and outcomes attach to the timeline;
- topology/graph is contextual expansion from timeline entities;
- Action/Outcome closes the case.

Strengths:
- strong fit for J01/J02 temporal reasoning;
- evidence chronology is explicit;
- change association and recurrence become natural.

Risks:
- may overfit incident/change investigation;
- J04 leadership overview and broad service intelligence become secondary;
- could incorrectly imply a formal "case" domain concept not currently canonical;
- would require careful implementation so UI container does not become a new canonical entity.

## 6. Comparative assessment

| Criterion | A Current corrected | B Investigation Workspace | C Timeline-first |
|---|---:|---:|---:|
| Preserve context across J01–J04 | Medium | High | High for J01/J02 |
| Progressive disclosure | Medium | High | High |
| Graph as investigative aid | Medium | High | High |
| Temporal reasoning | Medium | High | Very High |
| Action/outcome continuity | Medium | High | High |
| Leadership → operational drill-down | High | High | Medium |
| Risk of overfitting one journey | Low | Low/Medium | High |
| Migration effort | Low | Medium/High | High |
| Reuse current valid implementation | High | Medium/High | Medium |
| Avoid new domain semantics | High | High | Medium |

Scores are qualitative design-study observations, not acceptance proof.

## 7. Current decision
**ADAPT Alternative B as the leading prototype hypothesis.**

Rationale:
- best cross-journey fit without introducing a new canonical "case" concept;
- materially applies KB progressive-disclosure/context-preservation principles;
- preserves valid routes/domain/backend while allowing frontend structural change;
- makes Period/context first-class;
- gives topology/timeline/evidence/action coordinated roles;
- can absorb current valid components rather than rewrite backend/domain logic.

Alternative A remains control/baseline. Alternative C contributes a temporal-spine pattern inside J01/J02 but is not selected as overall IA.

This is a **prototype decision**, not final usability proof.

## 8. Required prototype deltas for Alternative B
1. persistent context bar with functional Period and explicit demo-data/environment context;
2. coherent Spanish default copy; locale requirement reconciled before adding language selector;
3. Settings/Configuration discovery resolved; no invented admin settings;
4. Service static tabs replaced by functional investigative lenses or clearly informational sections;
5. Risk static tabs replaced by coordinated functional lenses;
6. timeline/evidence/change/topology/action/outcome preserve selected context;
7. graph/topology supports focus, bounded expansion, node context/evidence and accessible list equivalent;
8. scenario harness changes data/state without changing the product interaction contract;
9. URL/state supports return/deep-link context;
10. behavioral tests execute J01–J04 and material degraded states.

## 9. Gate
- KB retrieval: PASS
- KB impact classification: PASS
- Material alternatives: PASS (A/B/C)
- Structural comparison: PASS
- Prototype hypothesis selected: PASS — Alternative B
- Usability/behavioral validation: NOT YET
- Functional Demo Parity: FAIL until implementation
- Structural Design Gate: **READY FOR FUNCTIONAL PROTOTYPE, NOT CONVERGED**
