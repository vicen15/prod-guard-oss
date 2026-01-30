---
title: Getting-started
layout: default
permalink: /getting-started/
---

← [Back to index](index.md)


# Getting Started

**Start protecting your Spring Boot application in minutes**

---

## 🎯 Objective

This guide walks you through the minimal steps required to:

- Add **prod-guard** to a Spring Boot application
- Run the **Free edition**
- Understand what happens at startup
- Prepare for enabling **Premium checks**

No prior knowledge of prod-guard is required.

---

## 📋 Prerequisites

Before starting, ensure you have:

- **Java 17+** (Java 21+ recommended)
- **Spring Boot 3.x**
- **Maven or Gradle**
- A runnable Spring Boot application

> prod-guard operates at framework level and does **not** require application code changes.

---

## 🚀 Step 1 — Add the dependency

### Maven

Add the prod-guard starter to your `pom.xml`:

```xml
<dependency>
    <groupId>com.prodguard</groupId>
    <artifactId>prod-guard-starter</artifactId>
    <version>0.0.1</version>
</dependency>
```

Gradle
implementation "com.prodguard:prod-guard-starter:0.0.1"


That’s it.
No additional configuration is required for the Free edition.

## ▶️ Step 2 — Start your application

Run your application normally:

mvn spring-boot:run


or

java -jar app.jar

## 📊 Step 3 — Observe the startup logs

During startup, prod-guard automatically activates.

Typical output:

[prod-guard] discovered 13 checks
[prod-guard] executing 13 checks
[prod-guard] WARN PG-012 - Graceful shutdown is not enabled
[prod-guard] WARN PG-011 - JPA Open Session In View is enabled
[prod-guard] 2 issues detected (blocking: false)

What this means

discovered: checks found on the classpath

executing: checks actually run

WARN / ERROR: production issues detected

blocking: whether startup enforcement is active

Your application continues to start normally.

🧠 **Understanding check results**

Each check produces:

A unique code (e.g. PG-012)

A severity (INFO, WARN, ERROR)

A human-readable explanation

A clear remediation hint

**Example**:

WARN PG-012 - Graceful shutdown is not enabled
→ Set server.shutdown=graceful for production deployments


This is intentional: prod-guard is designed to be actionable, not noisy.

## ⚙️ Step 4 — Basic configuration (optional)

You can fine-tune prod-guard via application.yml or application.properties.

Enable / disable prod-guard
prodguard:
  enabled: true

Report-only vs blocking mode
prodguard:
  enforcement: report-only   # default


or

prodguard:
  enforcement: blocking


In blocking mode, startup fails if blocking issues are detected.

## 🔒 Step 5 — Understanding Premium checks

Premium checks:

Are distributed separately

Are never executed without a valid license

Remain fully offline

If Premium checks are present without a license, you will see:

[prod-guard] premium check PG-203 present but no valid license found


This is expected behavior.

👉 The application still starts, but Premium checks are skipped.

## 🧾 Step 6 — Enabling Premium (preview)

To enable Premium checks, you will need:

A signed prodguard.lic file

A configuration entry pointing to it

**Example:**

prodguard:
  license:
    path: ./prodguard.lic


Once a valid license is detected:

[prod-guard] premium license validated for Vicente_Lopez
[prod-guard] executing 22 checks


➡ Full details are covered in the Licensing Guide.

## 🧪 Step 7 — Typical first fixes

New users usually fix:

Graceful shutdown

HTTP timeouts

JPA Open Session In View

Connection pool sizing

prod-guard then becomes a regression guard:
if a bad configuration is reintroduced, startup warns or blocks immediately.

✅ **What you have now**

At this point, you have:

Integrated prod-guard

Seen production issues detected automatically

Understood the execution model

Prepared for Premium activation

All without modifying application code.

<h2>Next Steps</h2>

← [Back to index](index.md)
- [Checks Reference – full list of checks and tiers](checks)
- [Licensing – Free vs Premium model](licensing)
- [Configuration – YAML / properties reference](configuration)
