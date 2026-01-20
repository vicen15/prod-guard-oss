package com.prodguard.checks.free.security;

import java.util.Optional;

import com.prodguard.core.CheckDescriptor;
import com.prodguard.core.CheckResult;
import com.prodguard.core.LicenseLevel;
import com.prodguard.core.ProdCheck;
import com.prodguard.core.ProdGuardContext;
import com.prodguard.core.Severity;

public class HttpsEnabledCheck implements ProdCheck {

    public static final CheckDescriptor DESCRIPTOR =
            new CheckDescriptor(
                    "PG-005",
                    "HTTPS / TLS configuration",
                    Severity.ERROR, null, LicenseLevel.FREE
            );

    @Override
    public Optional<CheckResult> check(ProdGuardContext ctx) {
        String v = ctx.getProperty("server.ssl.enabled").orElse("false");

        if (!"true".equalsIgnoreCase(v)) {
            return Optional.of(new CheckResult(
                    DESCRIPTOR,
                    "HTTPS is not enabled (server.ssl.enabled is not set to true)",
                    "Enable TLS/SSL for production by configuring server.ssl.* properties"
            ));
        }

        return Optional.empty();
    }

    @Override
    public CheckDescriptor descriptor() {
        return DESCRIPTOR;
    }
}
