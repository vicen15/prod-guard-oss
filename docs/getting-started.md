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


← [Back to index](index.md)