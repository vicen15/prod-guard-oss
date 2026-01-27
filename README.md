# prod-guard

**prod-guard** is a lightweight, production-focused guardrail for Spring Boot applications.

It detects common **production misconfigurations**, **unsafe defaults**, and **operational risks** *at startup time*, before they turn into incidents.

prod-guard is not a monitoring tool and not an APM. It is a **preventive control layer** that runs once, early, and tells you:

> "You are about to run this in production — are you sure this is safe?"

---

## ✨ Key Features

* 🧠 **Production-aware checks** (datasource, timeouts, shutdown, JPA, security, etc.)
* 🔌 **Zero runtime overhead** (runs only at startup)
* 🔐 **Signed license model** for premium checks (Ed25519)
* ⚙️ **Fully configurable** via `application.yml` / `properties`
* 🚫 **Fail-fast or report-only** execution modes
* 📦 **Auto-configuration** for Spring Boot

---

## 📦 Modules

This repository contains the **open-source core** of prod-guard.

Related repositories:

* **prod-guard-oss** (this repo)

  * Core checks
  * Starter / auto-configuration
  * Free tier

* **prod-guard-premium**

  * Premium-only checks

* **prod-guard-licensing**

  * License model
  * Signature verification

* **prod-guard-license-cli**

  * CLI tool to generate keys and licenses

---

## 🚀 Getting Started

The fastest way to understand and adopt prod-guard is through the documentation.

👉 **Start here:**

📘 **Documentation index**
[index](docs/index.md)

---

## 📚 Documentation

All documentation lives in the [`docs/`](./docs) directory and is written in Markdown.

### Core Docs

* 📖 [Getting Started](docs/getting-started.md)
* ⚙️ [Configuration Reference](docs/configuration.md)
* 🔐 [Licensing Model](docs/licensing.md)
* 🧪 [Checks Reference](docs/checks.md)
* 🏗️ [Architecture](docs/architecture.md)

### Product & Business

* ❓ [FAQ](docs/faq.md)
* 💡 [Why prod-guard](docs/why-prod-guard.md)
* 💰 [Pricing](docs/pricing.md)
* 🔒 [Security Model](docs/security.md)

---

## 🧩 Typical Usage

Add prod-guard to a Spring Boot application:

```xml
<dependency>
  <groupId>com.prodguard</groupId>
  <artifactId>prod-guard-starter</artifactId>
  <version>${prodguard.version}</version>
</dependency>
```

Configure:

```yaml
spring:
  profiles:
    active: prod

prodguard:
  enabled: true
  license:
    path: ./prodguard.lic
```

On startup, prod-guard will:

1. Detect the environment
2. Validate the license (if present)
3. Execute allowed checks
4. Log findings or block startup if configured

---

## 🧠 Design Philosophy

prod-guard is built around a few strong principles:

* **Fail early, not later**
* **Visibility before observability**
* **Production should be explicit**
* **Licensing must be cryptographically enforced**

More details in [architecture.md](docs/architecture.md).

---

## 📄 License

The **open-source core** is released under a permissive license.

Premium features require a valid signed license file.

See:

* [Licensing Model](docs/licensing.md)
* [Pricing](docs/pricing.md)

---

## 🤝 Contributing

Contributions, discussions, and feedback are welcome.

Before contributing:

* Read the documentation
* Understand the FREE vs PREMIUM boundary
* Open an issue for significant changes

---

## 📬 Contact

For commercial inquiries, licensing, or support:

📧 **[contact@prodguard.io](mailto:contact@prodguard.io)** (placeholder)

---

> prod-guard — *production should never be an afterthought.*
