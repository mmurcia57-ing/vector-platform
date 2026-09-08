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
