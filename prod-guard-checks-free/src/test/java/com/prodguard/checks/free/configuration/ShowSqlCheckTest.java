package com.prodguard.checks.free.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.prodguard.checks.free.support.MockProdGuardContext;

class ShowSqlCheckTest {

    private final ShowSqlCheck check = new ShowSqlCheck();

    @Test
    void warnsWhenShowSqlIsEnabled() {
        var ctx = new MockProdGuardContext()
                .withProperty("spring.jpa.show-sql", "true");

        var result = check.check(ctx);

        assertTrue(result.isPresent());
        assertEquals("PG-002", result.get().code());
    }

    @Test
    void passesWhenShowSqlIsDisabled() {
        var ctx = new MockProdGuardContext()
                .withProperty("spring.jpa.show-sql", "false");

        assertTrue(check.check(ctx).isEmpty());
    }
}
