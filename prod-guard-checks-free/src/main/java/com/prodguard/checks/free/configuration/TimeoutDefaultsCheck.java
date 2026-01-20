package com.prodguard.checks.free.configuration;

import java.util.Optional;

import com.prodguard.core.CheckDescriptor;
import com.prodguard.core.CheckResult;
import com.prodguard.core.LicenseLevel;
import com.prodguard.core.ProdCheck;
import com.prodguard.core.ProdGuardContext;
import com.prodguard.core.Severity;

public class TimeoutDefaultsCheck implements ProdCheck {

    public static final CheckDescriptor DESCRIPTOR =
            new CheckDescriptor(
                    "PG-010",
                    "HTTP request timeout configuration",
                    Severity.WARN, null, LicenseLevel.FREE
            );

    @Override
    public Optional<CheckResult> check(ProdGuardContext ctx) {
        Optional<String> timeout =
                ctx.getProperty("spring.mvc.async.request-timeout");

        if (timeout.isEmpty()) {
            return Optional.of(new CheckResult(
                    DESCRIPTOR,
                    "HTTP request timeout is not configured (spring.mvc.async.request-timeout)",
                    "Configure explicit request timeouts for production workloads"
            ));
        }

        return Optional.empty();
    }
    
    @Override
    public CheckDescriptor descriptor() {
        return DESCRIPTOR;
    }
}
