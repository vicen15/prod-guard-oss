---
title: Architecture
layout: default
permalink: /architecture/
---

← [Back to index](index.md)

<h1>Architecture Overview</h1>

<p>
This document describes the internal architecture of <strong>prod-guard</strong>,
its core components, execution flow, and design decisions.
</p>

<p>
The goal of prod-guard’s architecture is to provide:
</p>

<ul>
  <li>Zero-runtime overhead after startup</li>
  <li>Clear separation between FREE and PREMIUM functionality</li>
  <li>Deterministic, auditable production checks</li>
  <li>Strong license enforcement without external dependencies</li>
</ul>

<hr/>

<h2>High-Level Architecture</h2>

<p>
prod-guard is a <strong>startup-time validation framework</strong> for Spring Boot applications.
All checks are executed <strong>once during application startup</strong>.
</p>

<p>
There are no agents, background threads, schedulers, or network calls.
</p>

<pre><code>
Application Startup
        |
        v
ProdGuardRunner
        |
        +-- License Verification
        |
        +-- Check Discovery
        |
        +-- License Gate (FREE / PREMIUM)
        |
        +-- Check Execution
        |
        +-- Result Aggregation
        |
        +-- Optional Startup Blocking
</code></pre>

<hr/>

<h2>Core Components</h2>

<h3>ProdGuardRunner</h3>

<p>
<strong>ProdGuardRunner</strong> is the orchestration engine.
It is executed during application startup and controls the entire lifecycle.
</p>

<p>Responsibilities:</p>

<ul>
  <li>Detect active Spring profiles</li>
  <li>Honor <code>prodguard.force</code> and <code>prodguard.enabled</code></li>
  <li>Trigger license verification</li>
  <li>Discover available checks</li>
  <li>Apply license gating</li>
  <li>Execute allowed checks</li>
  <li>Aggregate and log results</li>
  <li>Optionally block application startup</li>
</ul>

<hr/>

<h3>ProdCheck</h3>

<p>
Each production rule is implemented as a <strong>ProdCheck</strong>.
</p>

<p>
A check is:
</p>

<ul>
  <li>Stateless</li>
  <li>Deterministic</li>
  <li>Self-describing</li>
</ul>

<p>
Each check provides:
</p>

<ul>
  <li>A unique code (e.g. <code>PG-011</code>)</li>
  <li>A default severity</li>
  <li>A human-readable message</li>
  <li>A remediation hint</li>
</ul>

<p>
Checks are discovered automatically via Spring’s component scanning.
</p>

<hr/>

<h3>SeverityResolver</h3>

<p>
The <strong>SeverityResolver</strong> determines the effective severity of each finding.
</p>

<p>
Resolution order:
</p>

<ol>
  <li>Explicit configuration override (<code>prodguard.severity.*</code>)</li>
  <li>Check default severity</li>
</ol>

<p>
This allows teams to:
</p>

<ul>
  <li>Disable specific checks</li>
  <li>Downgrade blocking errors to warnings</li>
  <li>Gradually adopt stricter policies</li>
</ul>

<hr/>

<h2>Licensing Architecture</h2>

<h3>LicenseVerifier</h3>

<p>
License validation is handled by the <strong>LicenseVerifier</strong> abstraction.
</p>

<p>
Two implementations exist:
</p>

<ul>
  <li><strong>NoopLicenseVerifier</strong> – FREE mode</li>
  <li><strong>SignedFileLicenseVerifier</strong> – PREMIUM mode</li>
</ul>

<p>
Selection is automatic based on configuration.
</p>

<hr/>

<h3>Signed License Model</h3>

<p>
prod-guard uses <strong>offline cryptographic licenses</strong>.
</p>

<ul>
  <li>Ed25519 asymmetric signatures</li>
  <li>Private key used only in license generation (CLI)</li>
  <li>Public key embedded in runtime</li>
  <li>No network calls</li>
  <li>No callbacks</li>
</ul>

<p>
A license contains:
</p>

<ul>
  <li>Licensee identifier</li>
  <li>License tier (FREE / PREMIUM)</li>
  <li>Issue date</li>
  <li>Expiration date</li>
  <li>Detached signature</li>
</ul>

<hr/>

<h3>LicenseContext</h3>

<p>
After verification, a <strong>LicenseContext</strong> is produced.
</p>

<p>
It represents the immutable result of license validation:
</p>

<ul>
  <li>Validity (valid / invalid)</li>
  <li>Licensee name</li>
  <li>Expiration date</li>
</ul>

<p>
This context is evaluated exactly once at startup.
</p>

<hr/>

<h3>LicenseGate</h3>

<p>
<strong>LicenseGate</strong> enforces licensing at the check level.
</p>

<p>
By convention:
</p>

<ul>
  <li>FREE checks → codes <strong>not</strong> starting with <code>PG-2</code></li>
  <li>PREMIUM checks → codes starting with <code>PG-2</code></li>
</ul>

<p>
If a PREMIUM check is encountered without a valid license:
</p>

<ul>
  <li>The check is skipped</li>
  <li>A warning is logged</li>
</ul>

<hr/>

<h2>Execution Flow</h2>

<pre><code>
1. Application starts
2. ProdGuardRunner invoked
3. Environment & profile detection
4. License verification
5. License expiration diagnostics
6. Check discovery
7. License gate filtering
8. Check execution
9. Result aggregation
10. Optional startup failure
</code></pre>

<hr/>

<h2>Design Principles</h2>

<h3>Fail Fast</h3>

<p>
All validation happens before the application begins serving traffic.
</p>

<h3>Zero Runtime Cost</h3>

<p>
No background tasks, no polling, no memory retention after startup.
</p>

<h3>Offline First</h3>

<p>
prod-guard never requires network access.
</p>

<h3>Transparent Enforcement</h3>

<p>
Every decision is logged explicitly and auditable.
</p>

<h3>Enterprise-Ready</h3>

<ul>
  <li>No vendor lock-in</li>
  <li>No phone-home behavior</li>
  <li>No hidden feature flags</li>
</ul>

<hr/>

## Execution Model

prod-guard executes validation as part of the **startup lifecycle**, but not all checks run
at the same moment.

To reflect this accurately, prod-guard uses a **two-phase execution model**.

---

## Phase 1 — Pre-Start Validation (Static Checks)

**When it runs**

- During application startup
- Before the application is considered ready to serve traffic

**What runs**

- Configuration-based checks
- Environment validation
- JVM, datasource, and framework defaults

**Examples**

- JPA Open Session In View
- Graceful shutdown configuration
- Datasource pool sizing
- Logging configuration
- HTTP timeouts

**Characteristics**

- No network calls
- No runtime dependencies
- Extremely fast
- Safe in all environments

This phase applies to **both FREE and PREMIUM editions**.

---

## Phase 2 — Post-Start Runtime Validation (Effective Checks)

Some production and security guarantees **cannot be validated statically**.

For example:

- Are HTTPS redirects effective?
- Are security headers actually present after proxies and filters?
- Is CSP enforced or report-only?
- Are cookies effectively marked Secure / HttpOnly?

These require observing the **effective runtime behavior**.

**When it runs**

- Immediately after the applicattion is started
- As part of the same startup lifecycle
- Before prod-guard completes execution

**What runs**

- Runtime HTTP/HTTPS checks
- Effective security header validation
- TLS and redirect enforcement

**Examples**

- Effective HTTPS enforcement
- HSTS configuration
- CSP enforcement
- Clickjacking protection
- Cookie security flags

**Characteristics**

- Performs real HTTP/HTTPS requests against localhost
- Requires the server to be running
- Executes synchronously
- No background threads

This phase is **PREMIUM-only**.

---

<h2>Comparison with Traditional Monitoring</h2>

<table>
  <thead>
    <tr>
      <th>Aspect</th>
      <th>Traditional Monitoring</th>
      <th>prod-guard</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>Execution time</td>
      <td>Runtime</td>
      <td>Startup only</td>
    </tr>
    <tr>
      <td>Purpose</td>
      <td>Observation</td>
      <td>Prevention</td>
    </tr>
    <tr>
      <td>Failure handling</td>
      <td>Alerts</td>
      <td>Startup blocking</td>
    </tr>
    <tr>
      <td>Licensing</td>
      <td>Subscription / SaaS</td>
      <td>Offline signed license</td>
    </tr>
  </tbody>
</table>

<hr/>

<h2>Next Steps</h2>

← [Back to index](index.md)

- [Checks Reference – full list of checks and tiers](/checks/)
- [Licensing – Free vs Premium model](/licensing)
- [Configuration – YAML / properties reference](/configuration)
