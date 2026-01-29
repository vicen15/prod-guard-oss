# prod-guard

**Production readiness & security guardrails for Spring Boot**

---

## Overview

**prod-guard** is a lightweight, offline-first guardrail that validates whether a
**Spring Boot application is correctly configured to run in production**.

It performs a set of **deterministic checks at application startup**, detecting:

- Insecure defaults
- Missing production hardening
- Configuration mistakes

**Before the application starts serving traffic.**

prod-guard is designed to be:

- ✅ Deterministic
- 🔒 Offline
- 🧩 Non-intrusive
- ⚡ Easy to adopt
- 🏛️ Suitable for regulated environments

---

## 📚 Table of Contents

- What is prod-guard
- Why prod-guard exists
- What prod-guard is not
- How prod-guard works
- Editions: Free vs Premium
- Design principles
- Typical use cases
- Integration overview
- Documentation structure

---

## What is prod-guard

prod-guard is a **startup-time validation framework** for Spring Boot applications.

It inspects the runtime environment and configuration to ensure that the
application adheres to **production-grade standards** in terms of:

- Stability
- Performance
- Security

Checks are executed:

- At **application startup**
- **Inside the application JVM**
- With **no external dependencies**
- With **zero runtime overhead**

---

## Why prod-guard exists

Modern Spring Boot applications often reach production with:

- Default configurations unintentionally left enabled
- Missing HTTP security headers
- Unsafe persistence or thread-pool defaults
- Production-only concerns overlooked
- “Temporary” configurations becoming permanent

These issues are rarely detected by monitoring tools, because **monitoring focuses on runtime behavior**, not startup correctness.

**prod-guard addresses this gap.**

It answers a simple but critical question:

> **Is this application actually safe and ready to run in production?**

---

## What prod-guard is not

prod-guard intentionally does **not** attempt to replace existing security or observability tools.

It is **not**:

- A monitoring or observability platform
- A vulnerability scanner
- A DAST or SAST tool
- A runtime intrusion detection system
- A cloud security posture manager (CSPM)

prod-guard does **not** inspect traffic, analyze requests, or collect metrics.

It validates **configuration correctness**, not runtime behavior.

---

## How prod-guard works

At startup, prod-guard performs the following steps:

1. Discovers all available checks on the classpath
2. Evaluates each check against the current runtime
3. Aggregates results
4. Applies the configured enforcement mode
5. Logs findings or blocks application startup

Each check is:

- Stateless
- Fast
- Deterministic
- Side-effect free

There are **no background threads**, **no network calls**, and **no agents**.

---

## Editions: Free vs Premium

prod-guard is offered in two editions.

---

### Free Edition

The **Free edition** focuses on production hygiene and best practices.

Examples of checks include:

- Graceful shutdown configuration
- JPA Open Session In View
- Thread pool sizing
- HTTP request timeouts
- CSRF configuration sanity
- General Spring Boot production defaults

The Free edition is suitable for:

- Internal services
- Early-stage production environments
- Non-regulated workloads

---

### Premium Edition

The **Premium edition** focuses on **security hardening and enforcement**.

It introduces checks such as:

- HTTPS enforcement
- HSTS configuration
- Content Security Policy
- Clickjacking protection
- Missing or weak HTTP security headers
- Blocking-mode enforcement

Premium checks:

- Require a **valid offline license**
- Are **never executed without one**
- Do not introduce any network dependency

---

## Design principles

prod-guard is built around a small set of strict principles.

### Deterministic by design

The same configuration always produces the same outcome.  
No heuristics. No probabilistic checks.

### Offline-first

- No SaaS dependency
- No outbound connections
- License verification is fully offline

### Fail fast

Issues are detected **before** the application starts serving traffic.

### Explicitness over magic

- Every check has a unique code
- Every result is explicit
- Every behavior is configurable

---

## Typical use cases

### Preventing insecure production deployments

Detect missing HTTPS enforcement, absent security headers, and unsafe defaults
before traffic is accepted.

### CI/CD enforcement

Run prod-guard in a pipeline and fail builds when blocking issues are detected.

### Regulated environments

Offline verification makes prod-guard suitable for restricted or air-gapped environments.

### Large Spring Boot estates

Ensure consistent production standards across many services.

---

## Integration overview

prod-guard is delivered as a **Spring Boot starter**.

Integration requires:

- Adding a dependency
- (Optional) Providing a license file
- Minimal configuration

**No application code changes are required.**

---

## 📖 Documentation structure

- <a href="getting-started.md">**Getting Started** – quick adoption guide</a>
- <a href="checks.md">**Checks Reference** – full list of checks and tiers</a>
- <a href="licensing.md">**Licensing** – Free vs Premium model</a>
- <a href="configuration.md">**Configuration** – YAML / properties reference</a>
- <a href="architecture.md">**Architecture** – internal design and flow</a>
- <a href="faq.md">**FAQ** – common questions and troubleshooting</a>

---

➡ Start with **Getting Started** to integrate prod-guard in minutes.
