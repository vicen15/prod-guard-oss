package com.prodguard.checks.free.security;

import java.util.Optional;

import com.prodguard.core.CheckDescriptor;
import com.prodguard.core.CheckResult;
import com.prodguard.core.LicenseLevel;
import com.prodguard.core.ProdCheck;
import com.prodguard.core.ProdGuardContext;
import com.prodguard.core.Severity;

public class SecurityHeadersCheck implements ProdCheck {

    public static final CheckDescriptor DESCRIPTOR =
            new CheckDescriptor(
                    "PG-006",
                    "HTTP security headers infrastructure",
                    Severity.WARN, null, LicenseLevel.FREE
            );

    @Override
    public Optional<CheckResult> check(ProdGuardContext ctx) {
        boolean hasSecurity =
                ctx.hasBean("org.springframework.security.web.SecurityFilterChain");

        if (!hasSecurity) {
            return Optional.of(new CheckResult(
                    DESCRIPTOR,
                    "Spring Security was not detected (no SecurityFilterChain bean found)",
                    "Add Spring Security or ensure equivalent security headers are configured"
            ));
        }

        return Optional.empty();
    }
    
    @Override
    public CheckDescriptor descriptor() {
        return DESCRIPTOR;
    }    
}
