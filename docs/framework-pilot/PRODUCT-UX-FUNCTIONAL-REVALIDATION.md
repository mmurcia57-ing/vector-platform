# VECTOR — Product / UX / Functional Revalidation for Full Lifecycle Pilot

## Status
- Branch: `framework/vector-full-lifecycle-pilot`
- Scope: complete VECTOR product, not the currently materialized screens
- Result: **UX RE-DIRECTION REQUIRED; PRODUCT NOT COMPLETE**
- Closed domain semantics: preserved
- New product capabilities authorized by this document: NONE
- SPEC-BLOCKERS: 0 for local UX/functional refinement

## 1. Correction of completion model

VECTOR is not complete when V1.1 screens render.

Completion has independent dimensions:

1. **Product coverage** — approved capabilities intended for the target release are implemented or explicitly deferred.
2. **Journey coverage** — target users can complete decision journeys end-to-end.
3. **Functional coverage** — requirements and business rules are executable.
4. **Experience quality** — the product is understandable, navigable, state-complete, accessible and decision-oriented.
5. **Engineering conformance** — frontend, BFF, security, data, graph, integrations and observability preserve approved contracts.
6. **Assurance** — behavior is demonstrated by appropriate tests/evidence.
7. **Operability/release** — degradation, telemetry, recovery and release evidence are sufficient for the intended environment.

A pass in one dimension does not imply completion in another.

## 2. Product scope interpretation

The 31-capability map remains the master product map. V1/V1.1 is only a release slice.

The pilot must classify each capability into:
- IMPLEMENTED;
- PARTIAL;
- APPROVED-REMAINING;
- FUTURE/EVOLUTION;
- EXTERNAL-DEPENDENCY;
- NOT-YET-SPECIFIED.

This prevents current UI implementation from silently becoming the product boundary.

## 3. UX direction

The current experience is a valid functional baseline, but it is not the desired final experience.

The new direction is an **Intelligence Workspace**, not a collection of dashboards/cards.

### Experience principles

**Decision-first**
Every major surface begins with the question the user is trying to answer, not with entity CRUD or metric inventory.

**Narrative drill-down**
The experience should feel like traversing an investigation:
`Attention → Signal → Context → Explanation → Evidence → Decision/Action → Outcome`.

**Spatial + analytical context**
Use topology/relationship views only when relationships materially help reasoning. Graph is an investigative instrument, not decorative background and not the default visualization for every page.

**Progressive disclosure**
Executive users see decision context first; SRE/analytical users can progressively expose evidence, timelines, correlations, source quality and technical topology without losing the analytical context.

**Evidence-visible**
A decision-relevant statement must make its evidence/provenance/uncertainty inspectable.

**State-complete**
The UI is designed for loading, empty, partial, stale, conflicting, unauthorized, unavailable, error, insufficient evidence, success and mutation-in-progress states where applicable.

**Distinct modes**
Management and SRE experiences share context and semantics but do not have to look like the same dashboard with different labels.

## 4. Proposed experience architecture

### A. Command / Intelligence Home
Purpose: orient the user to what changed, what requires attention, why, and what needs a decision.

Not a KPI wall.

Primary constructs:
- attention landscape;
- significant change since prior context;
- persistent conditions;
- unresolved/insufficient evidence;
- commitments/outcomes needing intervention;
- contextual entry into Area, Service or Investigation.

### B. Area / Domain Intelligence
Purpose: answer where attention is concentrated and what patterns connect services, risks, delivery/execution and outcomes.

Should combine:
- decision narrative;
- service/risk landscape;
- trend/context;
- active commitments and structural outcomes;
- evidence/data-quality boundary;
- drill-down.

### C. Service Reliability Workspace
Purpose: provide a service-centered operational investigation surface.

Should coordinate:
- reliability state/trend;
- SLO/SLI context where available;
- incidents/problems/events;
- changes/deployments;
- recurring conditions;
- risk findings;
- technical/topological context;
- evidence;
- action/outcome continuity.

### D. Investigation Workspace
Purpose: answer “what is happening, why is it highlighted, what supports it, what changed, and what should be examined next?”

Core interaction:
- investigation narrative;
- time/context axis;
- Evidence panel;
- bounded relationship graph;
- source/provenance/quality inspector;
- correlation/uncertainty indicators;
- linked action/outcome chain.

### E. Execution / Improvement Workspace
Purpose: move from finding to accountable intervention without confusing activity with outcome.

Core:
`RiskFinding → Commitment → ImprovementAction → OutcomeVerification`.

Includes portfolio/list modes only as secondary navigation.

### F. Change / Deployment Intelligence
Purpose: materialize J02 as a real analytical experience rather than a disclaimer embedded elsewhere.

Core:
- before/during/after context;
- Change/Deployment identity;
- operational observations;
- association strength/context without causal claim;
- evidence and conflicting/missing context;
- service/risk navigation.

### G. Decision / Leadership Workspace
Purpose: materialize J04 as an evidence-backed decision surface, not merely a roll-up.

It should expose:
- what requires attention;
- why;
- persistence/trend;
- affected context;
- what is being done;
- whether prior actions worked;
- confidence/limitations;
- drill-down to supporting evidence.

No individual ranking or opaque universal score.

## 5. UX state matrix

Each interactive surface must explicitly consider applicable states:

| State | Required experience |
|---|---|
| Initial/loading | preserve requested context; no false healthy/empty signal |
| Loaded | clear decision purpose and next action |
| Empty | explain what is absent and what that does/not mean |
| Partial | usable available evidence + visible missing context |
| Stale | last-known data with freshness warning |
| Conflicting | competing claims inspectable; no silent winner |
| Insufficient evidence | explicit limitation; no fabricated conclusion |
| Unauthorized | no data leakage; explain access boundary safely |
| Dependency unavailable | degrade only dependent function |
| Mutation pending | disable duplicate action and expose progress |
| Mutation success | confirmation + resulting state |
| Mutation failure | recoverable error without losing user input/context |
| Deep-link restoration | reconstruct valid context or explain missing/invalid context |
| No matching result | distinguish search/filter result from absent underlying data |

React's declarative state model reinforces designing explicit visual states before wiring behavior. This pilot will turn those states into behavioral tests, not source-text regex checks.

## 6. Accessibility and interaction baseline

Target baseline: WCAG 2.2 AA for local product experience where applicable.

Minimum pilot checks:
- semantic headings/landmarks;
- keyboard operability;
- visible/non-obscured focus;
- accessible labels/names;
- status/error communication;
- color not sole carrier of meaning;
- contrast;
- pointer target sizing/spacing;
- responsive reading/interaction order;
- dialogs/drawers/popovers, if used, manage focus correctly;
- data/graph interactions have non-graph alternatives for decision-relevant information.

W3C WCAG 2.2 adds criteria including Focus Not Obscured and Target Size (Minimum); these are particularly relevant to a dense analytical workspace.

## 7. Functional engineering chain

For every protected interactive capability, the pilot will require:

`Outcome/Requirement → Persona/Goal → Journey → Task/Process → UI State → Interaction → Frontend Action → API/Message Contract → AuthN Context → AuthZ Policy → Backend Use Case → Domain/Data Rule → Response/Event → UI State → Telemetry/Audit → Test/Evidence`.

A missing link is a GAP even when the screen looks complete.

## 8. Current UX findings

### UX-GAP-01 — Current shell is page/dashboard oriented
The present sidebar + cards/panels implementation materializes navigation but does not yet demonstrate the intended intelligence-workspace experience.

### UX-GAP-02 — Current tests do not prove experience behavior
Regex/source tests cannot prove actual user interaction, focus, error recovery, API state transition or accessibility.

### UX-GAP-03 — J02 lacks a first-class experience
Non-causation language exists, but the full before/during/after Change/Deployment investigation experience is not demonstrated.

### UX-GAP-04 — J04 needs a first-class decision surface
Panorama is useful baseline evidence but does not by itself prove the complete leadership decision journey.

### UX-GAP-05 — State coverage is incomplete
Current code includes loading/error and selected partial states, but the full semantic state model is not consistently materialized.

### UX-GAP-06 — Visual differentiation is insufficient
Management, SRE, evidence, graph and action/outcome contexts need stronger information architecture and visual grammar while preserving one coherent product.

### UX-GAP-07 — Product capability completeness remains open
Many master capabilities are outside the currently materialized V1.1 experience. They must remain visible in the product completion model rather than disappearing because the release slice has UI.

## 9. Design system direction

Do not lock VECTOR into a generic admin-template aesthetic.

Build a reusable visual grammar for:
- Attention;
- Evidence;
- Observed fact;
- Derived intelligence;
- Correlation;
- Uncertainty;
- Action;
- Verified outcome;
- Stale/partial context.

The design may use richer spatial composition, timelines, relationship maps, contextual rails, layered detail and progressive disclosure. Visual novelty must improve reasoning; it must not reduce accessibility or evidence clarity.

## 10. Implementation strategy

Do not rewrite the entire frontend.

1. Preserve working API/domain semantics.
2. Establish behavioral test harness.
3. Build the new application shell / intelligence workspace primitives.
4. Refactor one complete vertical slice first: Service → Risk Investigation → Evidence/Graph → Action/Outcome.
5. Validate UX, accessibility and contracts.
6. Extend the proven pattern to Management, Area, Commitment, J02 and J04.
7. Only then remove obsolete presentation paths.
8. Keep product-capability completion tracking separate from UX completion.

## 11. Gate

**PRODUCT / UX / FUNCTIONAL REVALIDATION: PASS TO ENGINEERING DESIGN AND IMPLEMENTATION PLANNING WITH REQUIRED UX REDIRECTION**

This is not a product-complete declaration.

The next gate must prove frontend↔BFF contracts, HTTP security enforcement, architecture fitness, state/behavior test strategy, and the exact remaining work packages before implementation changes are made.
