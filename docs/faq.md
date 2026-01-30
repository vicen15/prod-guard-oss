---
title: Faq
layout: default
permalink: /faq/
---

← [Back to index](index.md)


<h1>Frequently Asked Questions (FAQ)</h1>

<p>
This document answers the most common questions about <strong>prod-guard</strong>,
its purpose, behavior, licensing model, and how it compares to existing solutions.
</p>

<hr/>

<h2>General</h2>

<h3>What is prod-guard?</h3>

<p>
prod-guard is a <strong>startup-time production readiness validation framework</strong>
for Spring Boot applications.
</p>

<p>
It detects common misconfigurations, risky defaults, and production anti-patterns
<strong>before</strong> the application begins serving traffic.
</p>

<p>
Unlike monitoring tools, prod-guard focuses on <strong>prevention</strong>, not observation.
</p>

<hr/>

<h3>Is prod-guard a monitoring or security tool?</h3>

<p>
No.
</p>

<p>
prod-guard is <strong>not</strong>:
</p>

<ul>
  <li>A monitoring platform</li>
  <li>An APM</li>
  <li>A vulnerability scanner</li>
  <li>A runtime security agent</li>
</ul>

<p>
It complements those tools by ensuring the application is
<strong>correctly configured before startup</strong>.
</p>

<hr/>

<h3>When does prod-guard run?</h3>

<p>
Exactly once, during application startup.
</p>

<p>
After startup completes, prod-guard has <strong>zero runtime overhead</strong>.
</p>

<hr/>

<h2>Adoption & Usage</h2>

<h3>Does prod-guard block application startup?</h3>

<p>
It can, but only if you want it to.
</p>

<p>
Blocking behavior depends on:
</p>

<ul>
  <li>Check severity</li>
  <li>Severity overrides</li>
  <li><code>prodguard.report-only</code> configuration</li>
</ul>

<p>
This allows gradual adoption without breaking existing systems.
</p>

<hr/>

<h3>Can prod-guard run outside production?</h3>

<p>
Yes.
</p>

<p>
By default, prod-guard runs only when a production profile is active
(<code>prod</code> or <code>production</code>).
</p>

<p>
To force execution in other environments:
</p>

<pre><code>prodguard:
  force: true
</code></pre>

<p>
This is commonly used in CI pipelines and staging environments.
</p>

<hr/>

<h3>Can we disable specific checks?</h3>

<p>
Yes.
</p>

<p>
Any check can be:
</p>

<ul>
  <li>Disabled</li>
  <li>Downgraded</li>
  <li>Upgraded</li>
</ul>

<p>
Example:
</p>

<pre><code>prodguard:
  severity:
    PG-011: DISABLED
    PG-012: WARN
</code></pre>

<hr/>

<h2>Licensing</h2>

<h3>Is prod-guard free?</h3>

<p>
prod-guard offers two tiers:
</p>

<ul>
  <li><strong>FREE</strong> – Core production readiness checks</li>
  <li><strong>PREMIUM</strong> – Advanced checks requiring a license</li>
</ul>

<p>
FREE checks work without any license.
</p>

<hr/>

<h3>How does licensing work?</h3>

<p>
prod-guard uses <strong>offline, cryptographically signed licenses</strong>.
</p>

<ul>
  <li>No network calls</li>
  <li>No SaaS dependency</li>
  <li>No callbacks</li>
</ul>

<p>
A license is verified locally at startup using an embedded public key.
</p>

<hr/>

<h3>What happens if no license is present?</h3>

<ul>
  <li>FREE checks are executed</li>
  <li>PREMIUM checks are skipped</li>
  <li>Explicit warnings are logged</li>
</ul>

<p>
The application continues to start normally.
</p>

<hr/>

<h3>What happens if the license is expired?</h3>

<p>
An expired license is treated as <strong>invalid</strong>.
</p>

<ul>
  <li>PREMIUM checks are disabled</li>
  <li>A warning is logged indicating expiration</li>
</ul>

<p>
No application functionality is blocked.
</p>

<hr/>

<h3>Does prod-guard “phone home”?</h3>

<p>
No. Never.
</p>

<p>
prod-guard does not:
</p>

<ul>
  <li>Send telemetry</li>
  <li>Contact license servers</li>
  <li>Collect usage data</li>
</ul>

<p>
This is a deliberate architectural decision.
</p>

<hr/>

<h2>Security</h2>

<h3>How secure is the licensing mechanism?</h3>

<p>
Licenses are signed using <strong>Ed25519 asymmetric cryptography</strong>.
</p>

<ul>
  <li>Private key is used only for license generation</li>
  <li>Public key is embedded in the runtime</li>
  <li>Licenses cannot be forged without the private key</li>
</ul>

<hr/>

<h2>Comparison</h2>

<h3>How is prod-guard different from monitoring tools?</h3>

<p>
Monitoring tools:
</p>

<ul>
  <li>Detect issues after deployment</li>
  <li>Generate alerts</li>
  <li>Require operational effort</li>
</ul>

<p>
prod-guard:
</p>

<ul>
  <li>Prevents issues before deployment</li>
  <li>Fails fast</li>
  <li>Requires no runtime operation</li>
</ul>

<hr/>

<h3>How does prod-guard compare to static analysis?</h3>

<p>
Static analysis:
</p>

<ul>
  <li>Analyzes code</li>
  <li>Cannot see runtime configuration</li>
</ul>

<p>
prod-guard:
</p>

<ul>
  <li>Analyzes runtime configuration</li>
  <li>Detects environment-specific issues</li>
</ul>

<hr/>

<h2>Support & Roadmap</h2>

<h3>Is prod-guard production ready?</h3>

<p>
Yes.
</p>

<p>
prod-guard is designed for:
</p>

<ul>
  <li>Enterprise Spring Boot applications</li>
  <li>Regulated environments</li>
  <li>Offline deployments</li>
</ul>

<hr/>

<h3>Will more checks be added?</h3>

<p>
Yes.
</p>

<p>
The check catalog is expected to grow, especially in:
</p>

<ul>
  <li>Security hardening</li>
  <li>Cloud-native best practices</li>
  <li>Compliance-oriented checks</li>
</ul>

<hr/>

<h2>Next Steps</h2>

← [Back to index](index.md)

- [Getting Started– quick adoption guide](/prod-guard-oss/getting-started)
- [Checks Reference – full list of checks and tiers](/prod-guard-oss/checks)
- [Licensing – Free vs Premium model](/prod-guard-oss/licensing)
