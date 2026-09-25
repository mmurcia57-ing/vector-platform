import assert from 'node:assert/strict'
import { readFile } from 'node:fs/promises'
import test from 'node:test'

const source = (path) => readFile(new URL(path, import.meta.url), 'utf8')

test('EXT-005 declares one persistent investigation-space contract', async () => {
  const viewport = await source('../src/ExperienceViewport.tsx')
  assert.match(viewport, /data-experience-space=["']investigation["']/)
  assert.match(viewport, /data-focus-level=/)
  assert.match(viewport, /data-near-context=/)
  assert.match(viewport, /data-global-context=/)
})

test('EL-03 and EL-16 transform focus inside the investigation space', async () => {
  const viewport = await source('../src/ExperienceViewport.tsx')
  assert.match(viewport, /data-context-transform=/)
  assert.match(viewport, /area.*service.*risk/s)
  assert.doesNotMatch(viewport, /const panorama = \([\s\S]*const areaView = \([\s\S]*const serviceView = \([\s\S]*const riskView = \(/)
})

test('EL-04 semantic zoom is an explicit granularity transition', async () => {
  const viewport = await source('../src/ExperienceViewport.tsx')
  assert.match(viewport, /data-semantic-level=/)
  assert.match(viewport, /ecosystem|area|service|condition|evidence/i)
  assert.doesNotMatch(viewport, /semanticZoom\s*=\s*["'](?:scale|camera|transform)["']/i)
})

test('EL-08 declares a bounded cardinality representation strategy', async () => {
  const viewport = await source('../src/ExperienceViewport.tsx')
  assert.match(viewport, /data-cardinality-mode=/)
  assert.match(viewport, /individual|aggregate|cluster|density/i)
})

test('EL-09 keeps investigated subset connected to its universe', async () => {
  const viewport = await source('../src/ExperienceViewport.tsx')
  assert.match(viewport, /data-universe-context=/)
  assert.match(viewport, /data-investigated-subset=/)
})

test('EL-10 keeps graph as a bounded relationship lens', async () => {
  const viewport = await source('../src/ExperienceViewport.tsx')
  const entry = await source('../src/App.tsx')
  assert.match(viewport, /maxNodes=12/)
  assert.match(viewport, /maxRelationships=16/)
  assert.match(viewport, /data-relationship-lens=/)
  assert.match(entry, /Bounded graph context/)
})

test('EL-11 spatial structure carries explicit informational semantics', async () => {
  const viewport = await source('../src/ExperienceViewport.tsx')
  assert.match(viewport, /data-space-role=/)
  assert.match(viewport, /focus|context|evidence/i)
})

test('EL-14 prevents dashboard primitives from being the primary investigation architecture', async () => {
  const viewport = await source('../src/ExperienceViewport.tsx')
  const primaryDashboardPrimitives = [
    ...viewport.matchAll(/className=["'][^"']*gold-metrics[^"']*["']/g),
    ...viewport.matchAll(/className=["'][^"']*gold-main-grid[^"']*["']/g),
  ]
  assert.equal(primaryDashboardPrimitives.length, 0, 'gold-metrics/gold-main-grid still define primary route structure')
  assert.match(viewport, /data-experience-space=["']investigation["']/)
})

test('EL-17 preserves execution state independently from verified outcome', async () => {
  const entry = await source('../src/App.tsx')
  const shell = await source('../src/AppRemediated.tsx')
  assert.match(entry, /execution is not outcome proof/)
  assert.match(shell, /Execution is tracked separately from structural outcome verification/)
  assert.doesNotMatch(shell, /COMPLETED\s*(?:=>|⇒|means|implies)\s*IMPROVED/i)
})

test('EL-18 requires presentation precision to be explicit before temporal placement', async () => {
  const viewport = await source('../src/ExperienceViewport.tsx')
  assert.match(viewport, /evidenceResolution|evidence-resolution|temporalResolution/)
  assert.match(viewport, /period|timestamp|time-series/i)
  assert.doesNotMatch(viewport, /observedAt\.slice\(0,\s*10\)/)
})

test('J02 temporal projection carries a non-causation boundary in the runtime experience', async () => {
  const viewport = await source('../src/ExperienceViewport.tsx')
  assert.match(viewport, /BEFORE/)
  assert.match(viewport, /DURING/)
  assert.match(viewport, /AFTER/)
  assert.match(viewport, /Correlation.*Causation|Correlación.*causalidad/i)
})

test('experiment-specific aesthetics are not required by the conformance harness', async () => {
  const harness = await source('./experience-conformance.test.mjs')
  for (const aesthetic of ['dark mode', 'neon', 'glow', 'isometry', '3d']) {
    assert.doesNotMatch(harness, new RegExp(`assert\\.match\\([^\\n]*${aesthetic}`, 'i'))
  }
})
