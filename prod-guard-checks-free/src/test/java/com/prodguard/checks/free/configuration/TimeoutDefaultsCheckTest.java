package com.prodguard.checks.free.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.prodguard.checks.free.support.MockProdGuardContext;

class TimeoutDefaultsCheckTest {

    private final TimeoutDefaultsCheck check = new TimeoutDefaultsCheck();

    @Test
    void warnsWhenTimeoutIsNotConfigured() {
        var ctx = new MockProdGuardContext();

        var result = check.check(ctx);

        assertTrue(result.isPresent());
        assertEquals("PG-010", result.get().code());
    }

    @Test
    void passesWhenTimeoutIsConfigured() {
        var ctx = new MockProdGuardContext()
                .withProperty("spring.mvc.async.request-timeout", "30s");

        assertTrue(check.check(ctx).isEmpty());
    }
}
