package com.prodguard.checks.free.configuration;

import java.util.Optional;

import com.prodguard.core.CheckDescriptor;
import com.prodguard.core.CheckResult;
import com.prodguard.core.LicenseLevel;
import com.prodguard.core.ProdCheck;
import com.prodguard.core.ProdGuardContext;
import com.prodguard.core.Severity;

public class DebugLoggingCheck implements ProdCheck {

    public static final CheckDescriptor DESCRIPTOR =
            new CheckDescriptor(
                    "PG-001",
                    "Root logging level configuration",
                    Severity.ERROR, null, LicenseLevel.FREE
            );
	
    @Override
    public Optional<CheckResult> check(ProdGuardContext ctx) {
        String level = ctx.getProperty("logging.level.root").orElse("INFO");
        if ("DEBUG".equalsIgnoreCase(level)) {
            return Optional.of(new CheckResult(
            		DESCRIPTOR,
                    "Root logging level is DEBUG",
                    "Set logging.level.root=INFO or WARN"
            ));
        }
        return Optional.empty();
    }
    
    @Override
    public CheckDescriptor descriptor() {
        return DESCRIPTOR;
    }
}