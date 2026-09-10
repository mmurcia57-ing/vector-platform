import assert from 'node:assert/strict'
import { readFile } from 'node:fs/promises'
import test from 'node:test'

test('the SPA entry point follows the evidence-first investigation slice', async () => {
  const app = await readFile(new URL('../src/App.tsx', import.meta.url), 'utf8')
  assert.match(app, /Technology overview/)
  assert.match(app, /Attention.*context.*explanation.*evidence/s)
  assert.match(app, /api\/experience\/overview/)
  assert.match(app, /sourceReferenceIds/)
  assert.match(app, /Action → Outcome/)
  assert.match(app, /execution is not outcome proof/)
  assert.match(app, /Bounded graph context/)
  assert.match(app, /maxRelationships=16/)
  assert.doesNotMatch(app, /ServiceNow|Dynatrace|Neo4j|Cypher|credential/i)
})

test('frontend configuration examples contain public values only', async () => {
  const config = await readFile(new URL('../.env.example', import.meta.url), 'utf8')
  const assignments = config.split('\n').filter((line) => line.length > 0 && !line.startsWith('#')).join('\n')
  assert.match(assignments, /^VITE_VECTOR_BFF_BASE_URL=http:\/\/localhost:8080$/m)
  assert.doesNotMatch(assignments, /password|token|secret|credential|neo4j/i)
})

test('Area Intelligence preserves durable investigation context', async () => {
  const app = await readFile(new URL('../src/App.tsx', import.meta.url), 'utf8')
  assert.match(app, /path: 'areas'/)
  assert.match(app, /areaDomainId/)
  assert.match(app, /sessionStorage\.setItem\(CONTEXT_KEY/)
  assert.match(app, /overview\.services\.filter\(\(service\) => service\.areaDomainId === route\.areaDomainId\)/)
})

test('Service Intelligence consumes the existing BFF service projection', async () => {
  const app = await readFile(new URL('../src/App.tsx', import.meta.url), 'utf8')
  assert.match(app, /path: 'services'/)
  assert.match(app, /api\/experience\/services\//)
  assert.match(app, /Service Intelligence/)
  assert.match(app, /detail\.riskFindings/)
  assert.match(app, /detail\.evidence/)
})

test('Risk Investigation keeps Evidence, Timeline, Graph, and outcome context bounded', async () => {
  const app = await readFile(new URL('../src/App.tsx', import.meta.url), 'utf8')
  assert.match(app, /path: 'risks'/)
  assert.match(app, /api\/experience\/graph/)
  assert.match(app, /Bounded graph context/)
  assert.match(app, /observedAt/)
  assert.match(app, /ActionOutcomePanel detail={detail}/)
  assert.match(app, /execution is not outcome proof/)
})

test('J02 presentation keeps Change and Deployment association noncausal', async () => {
  const app = await readFile(new URL('../src/App.tsx', import.meta.url), 'utf8')
  assert.match(app, /J02 Change\/Deployment context/)
  assert.match(app, /temporal\/contextual/)
  assert.match(app, /Correlation != Causation/)
})
