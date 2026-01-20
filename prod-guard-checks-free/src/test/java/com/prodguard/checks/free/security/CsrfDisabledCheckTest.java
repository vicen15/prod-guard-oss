package com.prodguard.checks.free.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.prodguard.checks.free.support.MockProdGuardContext;

class CsrfDisabledCheckTest {

    private final CsrfDisabledCheck check = new CsrfDisabledCheck();

    @Test
    void alwaysReturnsWarning() {
        var ctx = new MockProdGuardContext();

        var result = check.check(ctx);

        assertTrue(result.isPresent());
        assertEquals("PG-007", result.get().code());
    }
}
