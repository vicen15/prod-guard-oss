<h1>Security</h1>

<p>
Security is a core design principle of <strong>prod-guard</strong>, not an afterthought.
</p>

<p>
prod-guard is built to operate in <strong>high-security production environments</strong>,
including air-gapped systems, regulated industries, and restricted networks.
</p>

<hr/>

<h2>Security Design Principles</h2>

<ul>
  <li>Offline-first</li>
  <li>No external communication</li>
  <li>Minimal attack surface</li>
  <li>Deterministic behavior</li>
</ul>

<p>
prod-guard never requires network access to operate.
</p>

<hr/>

<h2>No Telemetry, No Data Collection</h2>

<p>
prod-guard does <strong>not</strong>:
</p>

<ul>
  <li>Send metrics</li>
  <li>Upload configuration</li>
  <li>Phone home</li>
  <li>Collect usage data</li>
</ul>

<p>
All analysis happens locally, in-process, at application startup.
</p>

<hr/>

<h2>License Security</h2>

<h3>Cryptographic Signatures</h3>

<p>
Premium features are protected using <strong>Ed25519 digital signatures</strong>.
</p>

<ul>
  <li>Licenses are signed with a private key</li>
  <li>Applications embed the corresponding public key</li>
  <li>License verification is deterministic and offline</li>
</ul>

<p>
This prevents:
</p>

<ul>
  <li>License forgery</li>
  <li>License tampering</li>
  <li>Unauthorized feature unlocking</li>
</ul>

<hr/>

<h3>Public Key Embedding</h3>

<p>
The public verification key is embedded directly in the prod-guard runtime.
</p>

<ul>
  <li>No runtime key loading</li>
  <li>No environment variables</li>
  <li>No remote key retrieval</li>
</ul>

<p>
This eliminates:
</p>

<ul>
  <li>Key injection attacks</li>
  <li>Configuration-based bypasses</li>
</ul>

<hr/>

<h3>Graceful Degradation</h3>

<p>
If a license is:
</p>

<ul>
  <li>Missing</li>
  <li>Invalid</li>
  <li>Expired</li>
</ul>

<p>
prod-guard:
</p>

<ul>
  <li>Logs the reason</li>
  <li>Disables premium checks</li>
  <li>Continues application startup</li>
</ul>

<p>
There is no forced shutdown or denial-of-service behavior.
</p>

<hr/>

<h2>Runtime Isolation</h2>

<p>
prod-guard executes:
</p>

<ul>
  <li>Before application traffic</li>
  <li>Inside the JVM</li>
  <li>Without reflective access to business code</li>
</ul>

<p>
Checks operate through controlled interfaces only.
</p>

<hr/>

<h2>Dependency Safety</h2>

<p>
prod-guard has a deliberately small dependency footprint.
</p>

<ul>
  <li>No native libraries</li>
  <li>No bytecode manipulation</li>
  <li>No agents</li>
</ul>

<p>
This reduces:
</p>

<ul>
  <li>Supply chain risk</li>
  <li>Upgrade friction</li>
  <li>Attack vectors</li>
</ul>

<hr/>

<h2>Failure Modes</h2>

<p>
Security-critical failure scenarios are explicitly handled:
</p>

<ul>
  <li>Malformed license files</li>
  <li>Invalid signatures</li>
  <li>Parsing errors</li>
</ul>

<p>
In all cases:
</p>

<ul>
  <li>Premium features are disabled</li>
  <li>Free features remain available</li>
</ul>

<p>
There is no undefined behavior.
</p>

<hr/>

<h2>Auditability</h2>

<p>
prod-guard logs:
</p>

<ul>
  <li>License validation status</li>
  <li>Expiration warnings</li>
  <li>Premium feature activation</li>
</ul>

<p>
All logs are emitted through standard logging frameworks and can be audited.
</p>

<hr/>

<h2>Compliance Alignment</h2>

<p>
prod-guard design aligns with requirements commonly found in:
</p>

<ul>
  <li>ISO 27001</li>
  <li>SOC 2</li>
  <li>PCI-DSS</li>
</ul>

<p>
Specifically:
</p>

<ul>
  <li>No external data flow</li>
  <li>Deterministic startup behavior</li>
  <li>Explicit failure handling</li>
</ul>

<hr/>

<h2>Security Updates</h2>

<p>
Security-related improvements are delivered via standard version updates.
</p>

<p>
There is no remote kill switch or forced update mechanism.
</p>

<hr/>

<h2>Responsible Disclosure</h2>

<p>
Security issues can be reported privately.
</p>

<p>
Confirmed vulnerabilities will:
</p>

<ul>
  <li>Be acknowledged</li>
  <li>Be prioritized</li>
  <li>Result in a patched release</li>
</ul>

<hr/>

<h2>Summary</h2>

<p>
prod-guard is designed to:
</p>

<ul>
  <li>Improve production safety without adding risk</li>
  <li>Operate in restricted environments</li>
  <li>Respect organizational security boundaries</li>
</ul>

<p>
It protects production environments without becoming another security liability.
</p>

<hr/>

<h2>Next Steps</h2>

<ul>
  <li><a href="architecture.md">Architecture Overview</a></li>
  <li><a href="licensing.md">Licensing Model</a></li>
  <li><a href="faq.md">FAQ</a></li>
</ul>
