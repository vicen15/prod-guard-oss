package com.prodguard.checks.free.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.prodguard.checks.free.support.MockProdGuardContext;

class ActuatorExposureCheckTest {

    private final ActuatorExposureCheck check = new ActuatorExposureCheck();

    @Test
    void failsWhenAllEndpointsAreExposed() {
        var ctx = new MockProdGuardContext()
                .withProperty("management.endpoints.web.exposure.include", "*");

        var result = check.check(ctx);

        assertTrue(result.isPresent());
        assertEquals("PG-004", result.get().code());
    }

    @Test
    void passesWhenEndpointsAreRestricted() {
        var ctx = new MockProdGuardContext()
                .withProperty("management.endpoints.web.exposure.include", "health,info");

        assertTrue(check.check(ctx).isEmpty());
    }
}
