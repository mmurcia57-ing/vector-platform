param(
  [switch]$SkipInstall
)

$ErrorActionPreference = "Stop"
$repoRoot = Split-Path -Parent $PSScriptRoot
Set-Location $repoRoot

Write-Host "VECTOR EXT-005 local launcher" -ForegroundColor Cyan
Write-Host "Branch expected: feat/vector-experience-v0.3-conformance"

if (-not $SkipInstall) {
  npm --prefix frontend install
  if ($LASTEXITCODE -ne 0) { throw "npm install failed" }
}

Write-Host "Running frontend quality gates..."
npm --prefix frontend test
if ($LASTEXITCODE -ne 0) { throw "Frontend tests failed. Runtime not started." }

npm --prefix frontend run build
if ($LASTEXITCODE -ne 0) { throw "Frontend build failed. Runtime not started." }

Write-Host "Running backend tests..."
& .\backend\mvnw.cmd test
if ($LASTEXITCODE -ne 0) { throw "Backend tests failed. Runtime not started." }

Write-Host "Starting VECTOR BFF on http://localhost:8080 ..."
$backend = Start-Process powershell -PassThru -ArgumentList "-NoExit","-Command","cd '$repoRoot'; .\backend\mvnw.cmd spring-boot:run"

Write-Host "Waiting for BFF health..."
$healthy = $false
for ($i=0; $i -lt 60; $i++) {
  Start-Sleep -Seconds 2
  try {
    $health = Invoke-RestMethod "http://localhost:8080/actuator/health"
    if ($health.status -eq "UP") { $healthy = $true; break }
  } catch {}
}
if (-not $healthy) {
  Write-Warning "BFF did not become healthy within 120 seconds. Check backend window."
  exit 1
}

Write-Host "Starting VECTOR SPA on http://localhost:5173 ..."
$frontend = Start-Process powershell -PassThru -ArgumentList "-NoExit","-Command","cd '$repoRoot'; npm --prefix frontend run dev -- --host 0.0.0.0"

Write-Host ""
Write-Host "VECTOR is starting locally." -ForegroundColor Green
Write-Host "Open: http://localhost:5173"
Write-Host "BFF:  http://localhost:8080"
Write-Host "Backend PID: $($backend.Id) | Frontend PID: $($frontend.Id)"
