# ProdGuard Checks Catalog

## FREE checks (PG-001 … PG-013)

FREE checks focus on baseline production readiness and hygiene. They are safe to run in all environments and do not require a license.

---

### PG-001 — Root logging level configuration

**Category:** Logging / Observability  
**Default Severity:** ERROR  
**Tier:** FREE  

#### What it checks
Root or global logging level is set to a verbose level (e.g. DEBUG/TRACE) in a production-like run.

#### Why it matters
Verbose logging in production can degrade performance and may leak sensitive data.

#### Remediation
```properties
logging.level.root=INFO
logging.level.com.myapp=DEBUG
```

---

### PG-002 — JPA SQL logging configuration

**Category:** Persistence / Observability  
**Default Severity:** WARN  
**Tier:** FREE  

#### What it checks
Detects if SQL logging is enabled in production.

#### Why it matters
SQL logging can produce very large logs and may reveal sensitive query details.

#### Remediation
```properties
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=false
```

---

### PG-003 — Error response stacktrace exposure

**Category:** Security / Web  
**Default Severity:** ERROR  
**Tier:** FREE  

#### What it checks
Application returns stack traces in HTTP error responses.

#### Why it matters
Stack traces leak implementation details.

#### Remediation
```properties
server.error.include-stacktrace=never
```

---

## PREMIUM checks (PG-201 … PG-209)

PREMIUM checks validate effective runtime behavior by performing real HTTP/HTTPS requests against the running application.  
They account for filters, reverse proxies, and load balancers.  
These checks require a valid license.

---

### PG-201 — Effective HTTP security headers

**Severity:** ERROR  
**Tier:** PREMIUM  

#### What it checks
Validates the effective HTTP security headers returned by the application at runtime.

#### Why it matters
Missing headers expose users to browser-based attacks.

#### Remediation
Ensure security headers are effectively applied at runtime.

---

### PG-202 — Effective HTTPS enforcement

**Severity:** ERROR  
**Tier:** PREMIUM  

#### What it checks
Verifies that HTTP traffic is redirected to HTTPS or explicitly rejected.

#### Why it matters
Allowing HTTP exposes credentials and sessions.

#### Remediation
Enforce HTTPS via application or infrastructure.

---

### PG-203 — Effective HSTS configuration

**Severity:** ERROR  
**Tier:** PREMIUM  

#### What it checks
Validates the Strict-Transport-Security header returned at runtime.

#### Why it matters
Without HSTS, browsers may downgrade connections.

#### Remediation
```http
Strict-Transport-Security: max-age=31536000; includeSubDomains
```

---

### PG-204 — Effective Content Security Policy

**Severity:** ERROR  
**Tier:** PREMIUM  

#### What it checks
Validates the effective Content-Security-Policy header.

#### Why it matters
Weak CSP allows XSS and script injection attacks.

#### Remediation
Define a strict CSP without unsafe directives.

---

### PG-205 — Effective cookie security flags

**Severity:** ERROR  
**Tier:** PREMIUM  

#### What it checks
Inspects cookies returned at runtime for Secure, HttpOnly, and SameSite flags.

#### Why it matters
Weak cookie flags enable session hijacking.

#### Remediation
Configure secure cookie attributes.

---

### PG-206 — Effective clickjacking protection

**Severity:** ERROR  
**Tier:** PREMIUM  

#### What it checks
Validates iframe protection via X-Frame-Options or CSP frame-ancestors.

#### Why it matters
Clickjacking can trick users into unintended actions.

#### Remediation
Deny framing or define strict frame-ancestors.

---

### PG-207 — Effective Referrer-Policy header

**Severity:** WARN  
**Tier:** PREMIUM  

#### What it checks
Validates the effective Referrer-Policy header.

#### Why it matters
Weak policies may leak sensitive URLs.

#### Remediation
Set a restrictive referrer policy.

---

### PG-208 — Effective Permissions-Policy header

**Severity:** WARN  
**Tier:** PREMIUM  

#### What it checks
Validates the Permissions-Policy header at runtime.

#### Why it matters
Permissive policies allow unintended browser feature access.

#### Remediation
Explicitly restrict unused browser features.

---

### PG-209 — Effective Cross-Origin-Opener-Policy

**Severity:** WARN  
**Tier:** PREMIUM  

#### What it checks
Validates the Cross-Origin-Opener-Policy header.

#### Why it matters
Weak COOP enables XS-Leaks and side-channel attacks.

#### Remediation
```http
Cross-Origin-Opener-Policy: same-origin
```
