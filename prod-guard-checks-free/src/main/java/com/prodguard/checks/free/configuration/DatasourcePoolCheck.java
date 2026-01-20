package com.prodguard.checks.free.configuration;

import java.util.Optional;

import com.prodguard.core.CheckDescriptor;
import com.prodguard.core.CheckResult;
import com.prodguard.core.LicenseLevel;
import com.prodguard.core.ProdCheck;
import com.prodguard.core.ProdGuardContext;
import com.prodguard.core.Severity;

public class DatasourcePoolCheck implements ProdCheck {

    public static final CheckDescriptor DESCRIPTOR =
            new CheckDescriptor(
                    "PG-009",
                    "Datasource pool size configuration",
                    Severity.WARN, null, LicenseLevel.FREE
            );
	
    @Override
    public Optional<CheckResult> check(ProdGuardContext ctx) {
        Optional<String> pool = ctx.getProperty("spring.datasource.hikari.maximum-pool-size");
        if (pool.isEmpty()) {
            return Optional.of(new CheckResult(
                    DESCRIPTOR,
                    "Datasource pool size not configured (spring.datasource.hikari.maximum-pool-size)",
                    "Explicitly configure connection pool size for production"
            ));
        }
        return Optional.empty();
    }
    
    @Override
    public CheckDescriptor descriptor() {
        return DESCRIPTOR;
    }    
}