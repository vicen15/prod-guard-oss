package com.prodguard.checks.free.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.prodguard.checks.free.support.MockProdGuardContext;

class HttpsEnabledCheckTest {

    private final HttpsEnabledCheck check = new HttpsEnabledCheck();

    @Test
    void failsWhenHttpsIsDisabled() {
        var ctx = new MockProdGuardContext()
                .withProperty("server.ssl.enabled", "false");

        var result = check.check(ctx);

        assertTrue(result.isPresent());
        assertEquals("PG-005", result.get().code());
    }

    @Test
    void passesWhenHttpsIsEnabled() {
        var ctx = new MockProdGuardContext()
                .withProperty("server.ssl.enabled", "true");

        assertTrue(check.check(ctx).isEmpty());
    }
}
