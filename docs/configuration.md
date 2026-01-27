<h1>Configuration</h1>

<p>
This document describes all configuration options available in <strong>prod-guard</strong>,
how they affect runtime behavior, and recommended values for production environments.
</p>

<p>
prod-guard is configured using standard <strong>Spring Boot properties</strong>, either via:
</p>

<ul>
  <li><code>application.yml</code></li>
  <li><code>application.properties</code></li>
  <li>Environment variables</li>
</ul>

<hr/>

<h2>Configuration Overview</h2>

<p>
All prod-guard settings are namespaced under:
</p>

<pre><code>prodguard:
</code></pre>

<p>
Configuration is divided into the following areas:
</p>

<ul>
  <li>Core behavior</li>
  <li>License management</li>
  <li>Severity and enforcement</li>
  <li>Execution control</li>
</ul>

<hr/>

<h2>Core Configuration</h2>

<h3>Enable / Disable prod-guard</h3>

<pre><code>prodguard:
  enabled: true
</code></pre>

<p><strong>Property:</strong> <code>prodguard.enabled</code></p>
<p><strong>Default:</strong> <code>true</code></p>

<p>
Enables or disables prod-guard entirely.  
If disabled, no checks will be executed.
</p>

<hr/>

<h3>Force execution outside production</h3>

<p>
By default, prod-guard only runs when a <strong>production profile</strong> is active.
</p>

<p>Recognized production profiles:</p>

<ul>
  <li><code>prod</code></li>
  <li><code>production</code></li>
</ul>

<p>
To force execution in other environments:
</p>

<pre><code>prodguard:
  force: true
</code></pre>

<p><strong>Property:</strong> <code>prodguard.force</code></p>
<p><strong>Default:</strong> <code>false</code></p>

<p>
Recommended for CI pipelines, staging environments, or pre-production validation.
</p>

<hr/>

<h2>License Configuration</h2>

<p>
prod-guard supports <strong>signed license files</strong> for enabling premium checks.
</p>

<h3>License file path</h3>

<pre><code>prodguard:
  license:
    path: ./prodguard.lic
</code></pre>

<p><strong>Property:</strong> <code>prodguard.license.path</code></p>
<p><strong>Required:</strong> Yes (for PREMIUM checks)</p>

<p>
If this property is not present, prod-guard runs in <strong>FREE mode</strong>.
</p>

<hr/>

<h3>License behavior</h3>

<ul>
  <li>No license file → Premium checks disabled</li>
  <li>Invalid signature → Premium checks disabled</li>
  <li>Expired license → Premium checks disabled</li>
  <li>Valid license → Premium checks enabled</li>
</ul>

<p>
All invalid scenarios emit explicit diagnostic log messages.
</p>

<hr/>

<h3>License expiration warnings</h3>


<p>
prod-guard emits warnings when the license is approaching expiration.
</p>

<pre><code>[prod-guard] License expires in 14 days (licensee: Vicente_Lopez)
</code></pre>

<hr/>

<h2>Severity Configuration</h2>

<p>
Each prod-guard check has a default severity which can be overridden.
</p>

<pre><code>prodguard:
  severity:
    PG-011: WARN
    PG-012: DISABLED
</code></pre>

<p>Available severities:</p>

<ul>
  <li><strong>ERROR</strong> – Blocks application startup</li>
  <li><strong>WARN</strong> – Logs a warning</li>
  <li><strong>INFO</strong> – Informational only</li>
  <li><strong>DISABLED</strong> – Check is skipped</li>
</ul>

<hr/>

<h2>Execution Behavior</h2>

<h3>Report-only mode</h3>

<pre><code>prodguard:
  report-only: true
</code></pre>

<p><strong>Property:</strong> <code>prodguard.report-only</code></p>
<p><strong>Default:</strong> <code>false</code></p>

<p>
When enabled, blocking issues are reported but the application continues to start.
</p>

<hr/>

<h2>Logging Behavior</h2>

<p>
Typical startup logs:
</p>

<pre><code>[prod-guard] discovered 22 checks
[prod-guard] premium license validated for Vicente_Lopez
[prod-guard] executing 13 checks
[prod-guard] 6 issues detected (blocking: false)
</code></pre>

<hr/>

<h2>Recommended Production Configuration</h2>

<pre><code>spring:
  profiles:
    active: prod

prodguard:
  enabled: true
  force: false
  report-only: false

  license:
    path: /etc/prodguard/prodguard.lic

  severity:
    PG-011: WARN
    PG-012: ERROR
</code></pre>

<hr/>

<h2>Next Steps</h2>

<ul>
  <li><a href="checks.md">Checks Reference</a></li>
  <li><a href="licensing.md">Licensing Model</a></li>
  <li><a href="architecture.md">Architecture Overview</a></li>
</ul>
