<h1>Why prod-guard?</h1>

<p>
Modern production systems fail far more often due to
<strong>misconfiguration</strong> than due to code defects.
</p>

<p>
prod-guard exists to eliminate an entire class of avoidable production incidents
<strong>before they happen</strong>.
</p>

<hr/>

<h2>The Problem prod-guard Solves</h2>

<h3>Production incidents are rarely caused by bugs</h3>

<p>
In mature teams, most outages are caused by:
</p>

<ul>
  <li>Wrong configuration defaults</li>
  <li>Forgotten production hardening</li>
  <li>Environment drift between dev and prod</li>
  <li>Silent security misconfigurations</li>
</ul>

<p>
These issues often:
</p>

<ul>
  <li>Pass all tests</li>
  <li>Pass code reviews</li>
  <li>Are only discovered under real traffic</li>
</ul>

<hr/>

<h3>Monitoring detects problems too late</h3>

<p>
Monitoring, APMs, and alerts are essential — but they are reactive.
</p>

<p>
They tell you something is wrong <strong>after</strong> users are affected.
</p>

<p>
prod-guard shifts this left:
</p>

<ul>
  <li>Before the first request</li>
  <li>Before the first customer</li>
  <li>Before the first incident</li>
</ul>

<hr/>

<h2>What prod-guard Is (and Is Not)</h2>

<h3>prod-guard is NOT</h3>

<ul>
  <li>A monitoring platform</li>
  <li>A security scanner</li>
  <li>An APM</li>
  <li>A SaaS dependency</li>
</ul>

<hr/>

<h3>prod-guard IS</h3>

<ul>
  <li>A <strong>production readiness gate</strong></li>
  <li>A <strong>startup-time validator</strong></li>
  <li>A <strong>best-practice enforcement layer</strong></li>
</ul>

<p>
It ensures that your application does not start in an unsafe state.
</p>

<hr/>

<h2>Key Differentiators</h2>

<h3>1. Runs before traffic</h3>

<p>
prod-guard executes during application startup.
</p>

<p>
If something is wrong, you find out immediately —
not hours later via an alert.
</p>

<hr/>

<h3>2. Zero runtime overhead</h3>

<p>
Once startup completes, prod-guard is done.
</p>

<ul>
  <li>No background threads</li>
  <li>No agents</li>
  <li>No performance impact</li>
</ul>

<hr/>

<h3>3. Configuration-aware (not just code-aware)</h3>

<p>
Static analysis tools inspect code.
</p>

<p>
prod-guard inspects:
</p>

<ul>
  <li>Active Spring profiles</li>
  <li>Resolved configuration properties</li>
  <li>Effective runtime behavior</li>
</ul>

<hr/>

<h3>4. Opinionated by design</h3>

<p>
prod-guard encodes hard-earned production experience.
</p>

<p>
It is intentionally opinionated about:
</p>

<ul>
  <li>Security defaults</li>
  <li>Operational safety</li>
  <li>Production best practices</li>
</ul>

<p>
Every check exists because someone was paged at 3am.
</p>

<hr/>

<h3>5. Offline, enterprise-friendly licensing</h3>

<p>
prod-guard works in:
</p>

<ul>
  <li>Air-gapped environments</li>
  <li>Regulated industries</li>
  <li>On-prem deployments</li>
</ul>

<p>
No outbound calls. Ever.
</p>

<hr/>

<h2>How prod-guard Fits in Your Stack</h2>

<h3>Complement, not replacement</h3>

<p>
prod-guard does not replace:
</p>

<ul>
  <li>Monitoring</li>
  <li>Logging</li>
  <li>Security tooling</li>
</ul>

<p>
It complements them by eliminating preventable issues early.
</p>

<hr/>

<h3>Works with existing workflows</h3>

<p>
prod-guard integrates naturally with:
</p>

<ul>
  <li>Spring Boot auto-configuration</li>
  <li>CI pipelines</li>
  <li>Infrastructure-as-code</li>
</ul>

<p>
No new operational model required.
</p>

<hr/>

<h2>Why Teams Adopt prod-guard</h2>

<ul>
  <li>Reduce production incidents</li>
  <li>Enforce standards across teams</li>
  <li>Fail fast instead of fail silently</li>
  <li>Improve confidence in deployments</li>
</ul>

<hr/>

<h2>Who prod-guard Is For</h2>

<ul>
  <li>Backend teams running Spring Boot in production</li>
  <li>Platform and DevOps teams</li>
  <li>Organizations with compliance requirements</li>
  <li>Teams scaling across many services</li>
</ul>

<hr/>

<h2>Typical prod-guard Moment</h2>

<blockquote>
"Why is prod-guard failing startup?"
</blockquote>

<blockquote>
"Oh. That would have been a production incident."
</blockquote>

<hr/>

<h2>Summary</h2>

<p>
prod-guard is about <strong>preventing failure</strong>, not reacting to it.
</p>

<p>
It makes production safer by refusing to start in unsafe conditions.
</p>

<p>
If you care about reliability, prod-guard belongs in your startup path.
</p>

<hr/>

<h2>Next Steps</h2>

<ul>
  <li><a href="getting-started.md">Getting Started</a></li>
  <li><a href="checks.md">Checks Reference</a></li>
  <li><a href="licensing.md">Licensing Model</a></li>
</ul>
