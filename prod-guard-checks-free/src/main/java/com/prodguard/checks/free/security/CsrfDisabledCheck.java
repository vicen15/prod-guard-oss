package com.prodguard.checks.free.security;

import java.util.Optional;

import com.prodguard.core.CheckDescriptor;
import com.prodguard.core.CheckResult;
import com.prodguard.core.LicenseLevel;
import com.prodguard.core.ProdCheck;
import com.prodguard.core.ProdGuardContext;
import com.prodguard.core.Severity;

public class CsrfDisabledCheck implements ProdCheck {

    public static final CheckDescriptor DESCRIPTOR =
            new CheckDescriptor(
                    "PG-007",
                    "CSRF protection configuration review",
                    Severity.WARN, null, LicenseLevel.FREE);	
                    
	
    @Override
    public Optional<CheckResult> check(ProdGuardContext ctx) {
        // We conservatively warn: detecting CSRF programmatically is framework-specific.
        return Optional.of(new CheckResult(
        		DESCRIPTOR,
        		"CSRF protection should be reviewed for publicly exposed endpoints",
                "Verify CSRF is enabled where applicable (APIs may disable it with care)"
        ));
    }
    
    @Override
    public CheckDescriptor descriptor() {
        return DESCRIPTOR;
    }    
}