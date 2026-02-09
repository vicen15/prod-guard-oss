---
title: checks
layout: default
permalink: /checks/
---

← [Back to index](index.md)

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

### PG-004 — Spring Boot Actuator endpoint exposure
**Category:** Observability / Security  
**Default Severity:** WARN  
**Tier:** FREE

#### What it checks
Actuator endpoints are exposed with permissive defaults.

#### Why it matters
Unrestricted actuator endpoints may leak sensitive runtime information.

#### Remediation
```properties
management.endpoints.web.exposure.include=health,info
management.endpoint.health.show-details=never
```

### PG-005 — HTTPS / TLS configuration
**Category:** Security  
**Default Severity:** ERROR  
**Tier:** FREE

#### What it checks
TLS/HTTPS is not configured or insecure TLS settings are detected.

#### Why it matters
Unencrypted traffic exposes credentials and data in transit.

#### Remediation
```properties
server.ssl.key-store=classpath:keystore.jks
server.ssl.key-store-password=secret
server.port=8443
```

### PG-006 — HTTP security headers infrastructure
**Category:** Security / HTTP  
**Default Severity:** WARN  
**Tier:** FREE

#### What it checks
Detects missing common security headers infrastructure.

#### Why it matters
Missing headers make the app more vulnerable to clickjacking, MIME sniffing, and referrer leakage.

#### Remediation
Configure strict security headers in framework or proxy.

### PG-007 — CSRF protection configuration review
**Category:** Security / Web  
**Default Severity:** WARN  
**Tier:** FREE

#### What it checks
CSRF protection is disabled globally or for endpoints that should be protected.

#### Why it matters
CSRF vulnerabilities allow attackers to execute actions on behalf of authenticated users.

#### Remediation
Enable CSRF or explicitly document safe exceptions.

### PG-008 — JVM maximum heap size configuration
**Category:** Runtime / JVM  
**Default Severity:** WARN  
**Tier:** FREE

#### What it checks
JVM maximum heap size not tuned in production.

#### Why it matters
Can cause OOM or poor GC behavior.

#### Remediation

``` bash
JAVA_OPTS="-Xms512m -Xmx2g -XX:+UseG1GC"
```

### PG-009 — Datasource pool size configuration
**Category:** Persistence / Database  
**Default Severity:** WARN  
**Tier:** FREE

#### What it checks
Connection pool size not explicitly configured.

#### Why it matters

Defaults may not fit workload.

#### Remediation

``` properties
spring.datasource.hikari.maximum-pool-size=20
```


### PG-010 — HTTP request timeout configuration
**Category:** Web / Performance  
**Default Severity:** WARN  
**Tier:** FREE

#### What it checks
No configured HTTP request timeout.

#### Why it matters
Stuck requests exhaust resources.

#### Remediation

``` properties
spring.mvc.async.request-timeout=30s
server.connection-timeout=30s
```

### PG-011 — JPA Open Session In View configuration
**Category:** Persistence  
**Default Severity:** WARN  
**Tier:** FREE

#### What it checks
spring.jpa.open-in-view is enabled.

#### Why it matters

Masks inefficient queries.

#### Remediation

``` properties
spring.jpa.open-in-view=false
```


### PG-012 — Graceful shutdown configuration
**Category:** Runtime / Lifecycle  
**Default Severity:** WARN  
**Tier:** FREE

#### What it checks
Graceful shutdown is disabled.

#### Why it matters

In-flight requests terminated abruptly.

#### Remediation

``` properties
server.shutdown=graceful
spring.lifecycle.timeout-per-shutdown-phase=30s
```

### PG-013 — CORS configuration scope
**Category:** Web / Security  
**Default Severity:** WARN  
**Tier:** FREE

#### What it checks
CORS policy is overly permissive.

#### Why it matters

Enables cross-origin attacks.

#### Remediation

Restrict origins, methods, and headers.


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

- ← [Back to index](index.md)
- → [Configuration](/configuration) 
- → [Licensing](/licensing)