---
title: Licensing
layout: default
permalink: /licensing/
---

← [Back to index](index.md)


<h1>Licensing</h1>

<p>
This document explains the <strong>prod-guard licensing model</strong>,
how licenses work, how they are validated, and what happens when a license
is missing or expired.
</p>

<hr/>

<h2>Overview</h2>

<p>
prod-guard uses a <strong>signed offline license model</strong>.
No network calls, no SaaS dependency, and no runtime activation are required.
</p>

<p>
Licensing is designed to be:
</p>

<ul>
  <li><strong>Offline-first</strong> – no external connectivity required</li>
  <li><strong>Secure</strong> – cryptographically signed licenses</li>
  <li><strong>Predictable</strong> – behavior is deterministic at startup</li>
  <li><strong>Production-safe</strong> – no license server failures</li>
</ul>

<hr/>

<h2>License Types</h2>

<p>
prod-guard currently supports the following license types:
</p>

<ul>
  <li><strong>FREE</strong> – default, no license required</li>
  <li><strong>PREMIUM</strong> – enables advanced production checks</li>
</ul>

<p>
The license tier controls which checks are allowed to execute.
</p>

<hr/>

<h2>What a License Enables</h2>

<table>
  <thead>
    <tr>
      <th>Capability</th>
      <th>FREE</th>
      <th>PREMIUM</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>FREE checks (PG-0xx / PG-1xx)</td>
      <td>✔</td>
      <td>✔</td>
    </tr>
    <tr>
      <td>PREMIUM checks (PG-2xx)</td>
      <td>✘</td>
      <td>✔</td>
    </tr>
    <tr>
      <td>Offline usage</td>
      <td>✔</td>
      <td>✔</td>
    </tr>
    <tr>
      <td>Startup validation</td>
      <td>✔</td>
      <td>✔</td>
    </tr>
  </tbody>
</table>

<hr/>

<h2>License File</h2>

<p>
A prod-guard license is distributed as a single file, typically named:
</p>

<pre><code>prodguard.lic
</code></pre>

<p>
The license file contains:
</p>

<ul>
  <li>License payload (JSON)</li>
  <li>Cryptographic signature (Ed25519)</li>
</ul>

<p>
The payload includes:
</p>

<ul>
  <li>Licensee name</li>
  <li>License tier</li>
  <li>Issue date</li>
  <li>Expiration date</li>
</ul>

<hr/>

<h2>Cryptographic Model</h2>

<p>
prod-guard uses <strong>Ed25519</strong> for license signing and verification.
</p>

<ul>
  <li>Licenses are signed using a private key (offline, CLI-only)</li>
  <li>Applications embed the public key for verification</li>
</ul>

<p>
At runtime:
</p>

<ul>
  <li>The license file is read</li>
  <li>The payload is verified against the signature</li>
  <li>No private key is ever shipped with applications</li>
</ul>

<hr/>

<h2>License Validation Lifecycle</h2>

<p>
License validation happens once at application startup.
</p>

<ol>
  <li>Read license file</li>
  <li>Parse payload and signature</li>
  <li>Verify cryptographic signature</li>
  <li>Check expiration date</li>
  <li>Create a runtime <code>LicenseContext</code></li>
</ol>

<p>
The result is cached and reused during the entire application lifecycle.
</p>

<hr/>

<h2>Behavior Without a License</h2>

<p>
If no license is present:
</p>

<ul>
  <li>FREE checks are executed</li>
  <li>PREMIUM checks are skipped</li>
  <li>Warnings are logged for skipped PREMIUM checks</li>
</ul>

<p>
Example log:
</p>

<pre><code>[prod-guard] premium check PG-203 present but no valid license found
</code></pre>

<hr/>

<h2>Expired Licenses</h2>

<p>
Expired licenses behave the same as missing licenses, with explicit logging.
</p>

<ul>
  <li>PREMIUM checks are skipped</li>
  <li>FREE checks continue to run</li>
  <li>A warning is logged indicating expiration</li>
</ul>

<p>
Example log:
</p>

<pre><code>[prod-guard] license expired on 2026-01-23 (licensee: Vicente_lopez)
</code></pre>

<hr/>

<h2>Expiration Warnings</h2>

<p>
prod-guard emits proactive warnings before license expiration.
</p>

<p>
By default:
</p>

<ul>
  <li>A warning is logged when the license expires within a configurable number of days</li>
</ul>

<p>
Example:
</p>

<pre><code>[prod-guard] License expires in 14 days (licensee: Vicente_lopez)
</code></pre>

<hr/>

<h2>Configuration</h2>

<p>
The license file location is configured via properties or YAML:
</p>

<pre><code>prodguard:
  license:
    path: ./prodguard.lic
</code></pre>

<p>
If the property is not set, prod-guard runs in FREE mode.
</p>

<hr/>

<h2>Operational Guarantees</h2>

<ul>
  <li>No license calls during runtime</li>
  <li>No remote dependencies</li>
  <li>No startup delays due to licensing</li>
  <li>No application shutdown on license failure</li>
</ul>

<p>
Licensing never blocks application startup by itself.
</p>

<hr/>

<h2>Why This Model</h2>

<p>
This licensing approach was chosen to align with production constraints:
</p>

<ul>
  <li>Air-gapped environments</li>
  <li>Regulated industries</li>
  <li>CI/CD and containerized deployments</li>
  <li>Predictable startup behavior</li>
</ul>

<hr/>

<h2>Next Steps</h2>

← [Back to index](index.md)

- [Configuration – YAML / properties reference](configuration)
- [Checks Reference – full list of checks and tiers](checks)
- [Architecture – internal design and flow](architecture)


<ul>
  <li><a href="configuration.md">Configuration</a></li>
  <li><a href="checks.md">Checks Reference</a></li>
  <li><a href="architecture.md">Architecture</a></li>
</ul>
