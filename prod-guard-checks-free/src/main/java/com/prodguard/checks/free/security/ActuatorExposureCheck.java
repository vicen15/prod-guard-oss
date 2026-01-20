package com.prodguard.checks.free.security;

import java.util.Optional;

import com.prodguard.core.CheckDescriptor;
import com.prodguard.core.CheckResult;
import com.prodguard.core.ProdCheck;
import com.prodguard.core.ProdGuardContext;
import com.prodguard.core.Severity;

public class ActuatorExposureCheck implements ProdCheck {

    public static final CheckDescriptor DESCRIPTOR =
            new CheckDescriptor(
                    "PG-004",
                    "Spring Boot Actuator endpoint exposure",
                    Severity.WARN, null);	

	
    @Override
    public Optional<CheckResult> check(ProdGuardContext ctx) {
        String exposed = ctx.getProperty("management.endpoints.web.exposure.include").orElse("");
        if (exposed.contains("*")) {
            return Optional.of(new CheckResult(
            		DESCRIPTOR,
                    "All actuator endpoints are publicly exposed ('*')",
                    "Restrict actuator exposure (management.endpoints.web.exposure.include)"
            ));
        }
        return Optional.empty();
    }
    
    @Override
    public CheckDescriptor descriptor() {
        return DESCRIPTOR;
    }    
}