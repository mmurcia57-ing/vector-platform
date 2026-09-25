import { useEffect, useMemo, useState } from "react";
import { createPortal } from "react-dom";

const PERIOD = "local-dataset-v1";
type Area={areaDomainId:string;name:string;attentionState:string};
type Service={serviceId:string;name:string;areaDomainId:string;conditionContext:string};
type Risk={riskFindingId:string;serviceId:string;condition:string;explanation:string};
type Evidence={evidenceId:string;supportedClaim:string;sourceReferenceIds:string[];observedAt:string};
type Commitment={commitmentId:string;declaration:string;statusContext?:string};
type Action={actionId:string;commitmentId:string;action:string;executionStatusContext:string};
type Outcome={verificationId:string;actionId:string;outcome:string;evidenceIds:string[]};
type Quality={sourceCoverage:string;freshness:string;confidence:string;uncertainty:string;limitations:string};
type Overview={areas:Area[];services:Service[];attentionFindings:Risk[];quality:Quality};
type Detail={service?:Service;riskFinding?:Risk;riskFindings:Risk[];evidence:Evidence[];commitments:Commitment[];improvementActions:Action[];outcomeVerifications:Outcome[];quality:Quality};
type Graph={graph:{relationships:{source:{canonicalId:string};predicate:string;target:{canonicalId:string}}[];freshness:string;truncated:boolean};state:string};

const read=async<T,>(url:string)=>{const r=await fetch(url);if(!r.ok)throw new Error(`Experience unavailable (${r.status})`);return r.json() as Promise<T>};
const route=()=>{const p=window.location.pathname.split("/").filter(Boolean);return {path:p[0]??"overview",id:decodeURIComponent(p[1]??"")}};
const label=(v="")=>v.replace(/^(area-|service-|evidence-|risk-finding:|commitment-|action-)/,"").replace(/[-:]/g," ").replace(/\b\w/g,l=>l.toUpperCase());
const evidenceResolution="period" as const;
const evidenceTime=(e:Evidence)=>evidenceResolution==="period"?"Observed in selected period":e.observedAt;

function Badge({children}:{children:string}){return <span className="x-badge">{children}</span>}
function Quality({quality}:{quality?:Quality}){if(!quality)return null;return <div className="x-quality" data-space-role="evidence"><b>Evidence boundary</b><span>{quality.sourceCoverage} · {quality.freshness}</span><small>{quality.uncertainty||quality.limitations||"No additional limitation exposed."}</small></div>}

export default function ExperienceViewport(){
  const [host,setHost]=useState<Element|null>(null);
  const [location,setLocation]=useState(route());
  const [overview,setOverview]=useState<Overview>();
  const [detail,setDetail]=useState<Detail>();
  const [graph,setGraph]=useState<Graph>();
  const [error,setError]=useState<string>();
  useEffect(()=>{const attach=()=>{const el=document.querySelector(".content-shell");if(!el)return false;setHost(el);return true};if(attach())return;const observer=new MutationObserver(()=>{if(attach())observer.disconnect()});observer.observe(document.body,{childList:true,subtree:true});return()=>observer.disconnect()},[]);
  useEffect(()=>{const change=()=>setLocation(route());window.addEventListener("popstate",change);return()=>window.removeEventListener("popstate",change)},[]);
  useEffect(()=>{void read<Overview>(`/api/experience/overview?period=${PERIOD}`).then(setOverview).catch(e=>setError(e.message))},[]);
  const params=new URLSearchParams(window.location.search);
  const selectedRisk=overview?.attentionFindings.find(r=>r.riskFindingId===(location.path==="risks"?location.id:params.get("riskFindingId")));
  const serviceId=location.path==="services"?location.id:(params.get("serviceId")||selectedRisk?.serviceId||"");
  useEffect(()=>{if(!serviceId){setDetail(undefined);setGraph(undefined);return}void read<Detail>(`/api/experience/services/${encodeURIComponent(serviceId)}?period=${PERIOD}`).then(setDetail).catch(e=>setError(e.message));const gp=new URLSearchParams({period:PERIOD,serviceId,maxNodes:"12",maxRelationships:"16"});if(selectedRisk?.riskFindingId)gp.set("riskFindingId",selectedRisk.riskFindingId);void read<Graph>(`/api/experience/graph?${gp}`).then(setGraph).catch(()=>setGraph(undefined))},[serviceId,selectedRisk?.riskFindingId]);
  const areaId=params.get("areaDomainId")||detail?.service?.areaDomainId||"";
  const selectedArea=overview?.areas.find(a=>a.areaDomainId===areaId);
  const areaServices=useMemo(()=>overview?.services.filter(s=>!areaId||s.areaDomainId===areaId)??[],[overview,areaId]);
  const risks=detail?.riskFindings??overview?.attentionFindings??[];
  const navigate=(path:string,ctx:Record<string,string>={})=>{const q=new URLSearchParams({period:PERIOD,...ctx});window.history.pushState({},"",`${path}?${q}`);window.dispatchEvent(new PopStateEvent("popstate"))};
  if(!host||!overview)return null;
  if(error)return createPortal(<div className="x-error">{error}</div>,host);

  const level=location.path==="risks"?"condition":location.path==="services"?"service":location.path==="areas"?"area":"ecosystem";
  const focus=selectedRisk?.condition||detail?.service?.name||selectedArea?.name||"Technology";
  const near=detail?.service?.name||selectedArea?.name||"Technology landscape";
  const global="VECTOR / Technology";
  const cardinality=overview.services.length>24?"aggregate":overview.services.length>8?"cluster":"individual";
  const universe=`${overview.areas.length} areas · ${overview.services.length} services · ${overview.attentionFindings.length} findings`;
  const contextTransform=`ecosystem → area → service → risk → evidence`;

  return createPortal(
    <main className="x-workspace" data-experience-space="investigation" data-focus-level={level} data-near-context={near} data-global-context={global} data-context-transform={contextTransform} data-semantic-level={level} data-cardinality-mode={cardinality} data-universe-context={universe} data-investigated-subset={focus}>
      <header className="x-head">
        <div><span className="x-kicker">VECTOR · RELIABILITY INTELLIGENCE</span><h1>{focus}</h1><p>{location.path==="overview"?"Where should Technology intervene, and what evidence explains it?":"Investigation preserves origin, context, evidence and outcome."}</p></div>
        <div className="x-meta"><Badge>{level.toUpperCase()}</Badge><span>{PERIOD}</span></div>
      </header>

      <nav className="x-context" aria-label="Investigation context">
        <button onClick={()=>navigate("/")}>Technology</button><span>›</span>
        <button disabled={!selectedArea} onClick={()=>selectedArea&&navigate("/areas",{areaDomainId:selectedArea.areaDomainId})}>{selectedArea?.name??"Area"}</button><span>›</span>
        <button disabled={!detail?.service} onClick={()=>detail?.service&&navigate(`/services/${encodeURIComponent(detail.service.serviceId)}`,{areaDomainId:detail.service.areaDomainId,serviceId:detail.service.serviceId})}>{detail?.service?.name??"Service"}</button><span>›</span>
        <strong>{selectedRisk?.condition??(location.path==="risks"?"Risk":"Focus")}</strong>
      </nav>

      <section className="x-space" data-space-role="focus-context">
        <aside className="x-universe" data-space-role="context">
          <div className="x-section-label">GLOBAL CONTEXT</div><strong>{universe}</strong>
          <p>Focus remains inside the observable technology universe.</p>
          <div className="x-area-map">
            {overview.areas.map(a=><button key={a.areaDomainId} className={a.areaDomainId===areaId?"selected":""} onClick={()=>navigate("/areas",{areaDomainId:a.areaDomainId})}><i/><span>{a.name}</span><small>{a.attentionState}</small></button>)}
          </div>
          <Quality quality={detail?.quality??overview.quality}/>
        </aside>

        <section className="x-focus" data-space-role="focus" data-investigated-subset={focus}>
          <div className="x-focus-top"><div><div className="x-section-label">CURRENT FOCUS · {level.toUpperCase()}</div><h2>{focus}</h2></div><span className="x-mode">Representation · {cardinality}</span></div>

          {level==="ecosystem"&&<div className="x-field">
            <div className="x-field-copy"><strong>Attention field</strong><p>Select an area. The universe stays visible while the focus narrows.</p></div>
            <div className="x-nodes">{overview.areas.map(a=><button key={a.areaDomainId} className="x-node area" onClick={()=>navigate("/areas",{areaDomainId:a.areaDomainId})}><span>{a.name}</span><small>{overview.services.filter(s=>s.areaDomainId===a.areaDomainId).length} services</small></button>)}</div>
          </div>}

          {level==="area"&&<div className="x-field">
            <div className="x-field-copy"><strong>{selectedArea?.name??"Area"} becomes the investigation space</strong><p>Services are the next semantic level; the selected area remains Near Context.</p></div>
            <div className="x-nodes">{areaServices.map(s=><button key={s.serviceId} className="x-node service" onClick={()=>navigate(`/services/${encodeURIComponent(s.serviceId)}`,{areaDomainId:s.areaDomainId,serviceId:s.serviceId})}><span>{s.name}</span><small>{s.conditionContext}</small></button>)}</div>
          </div>}

          {level==="service"&&<div className="x-field">
            <div className="x-field-copy"><strong>Service is the correlation anchor</strong><p>{detail?.service?.conditionContext}</p></div>
            <div className="x-nodes">{risks.map(r=><button key={r.riskFindingId} className="x-node risk" onClick={()=>navigate(`/risks/${encodeURIComponent(r.riskFindingId)}`,{areaDomainId:detail?.service?.areaDomainId??"",serviceId:r.serviceId,riskFindingId:r.riskFindingId})}><span>{r.condition}</span><small>{r.explanation}</small></button>)}</div>
            <div className="x-evidence-strip">{detail?.evidence.map(e=><article key={e.evidenceId}><b>{e.supportedClaim}</b><span>{evidenceTime(e)}</span><small>{e.sourceReferenceIds.map(label).join(", ")}</small></article>)}</div>
          </div>}

          {level==="condition"&&<div className="x-investigation">
            <section className="x-explanation"><div className="x-section-label">EXPLANATION</div><h3>{selectedRisk?.condition??detail?.riskFinding?.condition}</h3><p>{selectedRisk?.explanation??detail?.riskFinding?.explanation}</p></section>
            <section className="x-evidence" data-space-role="evidence"><div className="x-section-label">EVIDENCE · resolution: {evidenceResolution}</div>{detail?.evidence.map(e=><article key={e.evidenceId}><div><strong>{e.supportedClaim}</strong><span>{evidenceTime(e)}</span></div><small>Source · {e.sourceReferenceIds.map(label).join(", ")}</small></article>)}</section>
            <section className="x-lens" data-relationship-lens="bounded-evidence-backed"><div className="x-section-label">RELATIONSHIP LENS · bounded 12 / 16</div>{graph?.graph.relationships.length?graph.graph.relationships.map((r,i)=><div className="x-edge" key={i}><span>{label(r.source.canonicalId)}</span><b>{r.predicate.replaceAll("_"," ")}</b><span>{label(r.target.canonicalId)}</span></div>):<p>No additional supported relationship is available.</p>}</section>
            <section className="x-outcome"><div className="x-section-label">ACTION → VERIFIED OUTCOME</div>{detail?.commitments.map(c=><article key={c.commitmentId}><strong>{c.declaration}</strong><Badge>{c.statusContext??"UNKNOWN"}</Badge>{detail.improvementActions.filter(a=>a.commitmentId===c.commitmentId).map(a=><div key={a.actionId}><span>{a.action}</span><Badge>{a.executionStatusContext}</Badge>{detail.outcomeVerifications.filter(o=>o.actionId===a.actionId).map(o=><p key={o.verificationId}>OutcomeVerification · <b>{o.outcome}</b></p>)}</div>)}</article>)}{!detail?.commitments.length&&<p>No associated Commitment is present.</p>}<small>Execution is not outcome proof. COMPLETED does not imply IMPROVED.</small></section>
          </div>}
        </section>

        <aside className="x-near" data-space-role="context">
          <div className="x-section-label">NEAR CONTEXT</div><h3>{near}</h3>
          <p>Semantic zoom changes represented granularity, not camera scale.</p>
          <ol><li className={level==="ecosystem"?"active":""}>Ecosystem</li><li className={level==="area"?"active":""}>Area</li><li className={level==="service"?"active":""}>Service</li><li className={level==="condition"?"active":""}>Condition</li><li>Evidence</li></ol>
          <div className="x-temporal" data-evidence-resolution={evidenceResolution}><b>Temporal projection</b><div><span>BEFORE</span><span>DURING</span><span>AFTER</span></div><small>Available only when source evidence supplies compatible periods. Correlation ≠ Causation.</small></div>
        </aside>
      </section>
    </main>,host);
}
