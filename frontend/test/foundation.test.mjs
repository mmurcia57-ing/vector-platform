import assert from 'node:assert/strict'
import { readFile } from 'node:fs/promises'
import test from 'node:test'

test('the SPA entry point remains an explicitly technical foundation shell', async () => {
  const app = await readFile(new URL('../src/App.tsx', import.meta.url), 'utf8')

  assert.match(app, /VECTOR · technical foundation/)
  assert.match(app, /product experiences and journeys are intentionally not implemented here/)
  assert.doesNotMatch(app, /ServiceNow|Dynatrace|Neo4j|Cypher|credential/i)
})

test('frontend configuration examples contain public values only', async () => {
  const config = await readFile(new URL('../.env.example', import.meta.url), 'utf8')
  const assignments = config
    .split('\n')
    .filter((line) => line.length > 0 && !line.startsWith('#'))
    .join('\n')

  assert.match(assignments, /^VITE_VECTOR_BFF_BASE_URL=http:\/\/localhost:8080$/m)
  assert.doesNotMatch(assignments, /password|token|secret|credential|neo4j/i)
})
