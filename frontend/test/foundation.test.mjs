import assert from 'node:assert/strict'
import { readFile } from 'node:fs/promises'
import test from 'node:test'

const source=(path)=>readFile(new URL(path,import.meta.url),'utf8')

test('SPA keeps the evidence-first canonical boundary',async()=>{
 const app=await source('../src/App.tsx')
 assert.match(app,/Technology overview/)
 assert.match(app,/Attention.*context.*explanation.*evidence/s)
 assert.match(app,/api\/experience\/overview/)
 assert.match(app,/sourceReferenceIds/)
 assert.match(app,/execution is not outcome proof/)
 assert.match(app,/Bounded graph context/)
 assert.match(app,/maxRelationships=16/)
 assert.doesNotMatch(app,/ServiceNow|Dynatrace|Neo4j|Cypher|credential/i)
})

test('frontend configuration examples contain public values only',async()=>{
 const config=await source('../.env.example')
 const assignments=config.split('\n').filter(line=>line.length>0&&!line.startsWith('#')).join('\n')
 assert.match(assignments,/^VITE_VECTOR_BFF_BASE_URL=http:\/\/localhost:8080$/m)
 assert.doesNotMatch(assignments,/password|token|secret|credential|neo4j/i)
})

test('persistent workspace consumes existing BFF projections',async()=>{
 const viewport=await source('../src/ExperienceViewport.tsx')
 assert.match(viewport,/api\/experience\/overview/)
 assert.match(viewport,/api\/experience\/services\//)
 assert.match(viewport,/api\/experience\/graph/)
 assert.match(viewport,/maxNodes:"12",maxRelationships:"16"/)
 assert.match(viewport,/sourceReferenceIds/)
})

test('Area to Service to Risk navigation preserves canonical identifiers',async()=>{
 const viewport=await source('../src/ExperienceViewport.tsx')
 assert.match(viewport,/areaDomainId/)
 assert.match(viewport,/serviceId/)
 assert.match(viewport,/riskFindingId/)
 assert.match(viewport,/navigate\("\/areas"/)
 assert.match(viewport,/navigate\(\`\/services\//)
 assert.match(viewport,/navigate\(\`\/risks\//)
})

test('Risk Investigation keeps evidence graph action and outcome bounded',async()=>{
 const viewport=await source('../src/ExperienceViewport.tsx')
 assert.match(viewport,/data-relationship-lens="bounded-evidence-backed"/)
 assert.match(viewport,/ACTION → VERIFIED OUTCOME/)
 assert.match(viewport,/Execution is not outcome proof/)
 assert.match(viewport,/OutcomeVerification/)
})

test('J02 presentation boundary remains explicitly noncausal and resolution bounded',async()=>{
 const viewport=await source('../src/ExperienceViewport.tsx')
 assert.match(viewport,/BEFORE/)
 assert.match(viewport,/DURING/)
 assert.match(viewport,/AFTER/)
 assert.match(viewport,/Correlation ≠ Causation/)
 assert.match(viewport,/evidenceResolution="period"/)
})

test('all experience levels render inside one persistent application shell',async()=>{
 const entry=await source('../src/App.tsx')
 const viewport=await source('../src/ExperienceViewport.tsx')
 assert.match(entry,/window\.history\.pushState = function/)
 assert.match(entry,/new PopStateEvent\('popstate'\)/)
 assert.match(entry,/<ExperienceApp \/><ExperienceViewport \/>/)
 assert.match(viewport,/createPortal\(/)
 assert.match(viewport,/document\.querySelector\("\.content-shell"\)/)
 assert.match(viewport,/new MutationObserver/)
 assert.match(viewport,/data-experience-space="investigation"/)
 for(const level of ['ecosystem','area','service','condition','evidence'])assert.match(viewport,new RegExp(level))
})

test('root CSS no longer constrains the investigation workspace to legacy document width',async()=>{
 const css=await source('../src/index.css')
 assert.match(css,/#root \{[\s\S]*max-width: none/)
})

test('desktop shell remains stable and viewport vertically reachable',async()=>{
 const css=await source('../src/App.css')
 assert.match(css,/\.app-shell \{ height: 100vh; .*overflow: hidden/)
 assert.match(css,/\.content-shell \{ .*height: 100vh; overflow: hidden/)
 assert.match(css,/\.x-workspace\{height:calc\(100vh - 58px\);overflow:auto/)
 assert.match(css,/@media\(max-width:760px\)/)
})
