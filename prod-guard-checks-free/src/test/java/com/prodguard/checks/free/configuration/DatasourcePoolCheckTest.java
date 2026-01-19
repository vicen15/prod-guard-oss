package com.prodguard.checks.free.configuration;

import com.prodguard.checks.free.configuration.DatasourcePoolCheck;
import com.prodguard.checks.free.support.MockProdGuardContext;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DatasourcePoolCheckTest {

    private final DatasourcePoolCheck check = new DatasourcePoolCheck();

    @Test
    void warnsWhenPoolSizeIsNotConfigured() {
        var ctx = new MockProdGuardContext();

        var result = check.check(ctx);

        assertTrue(result.isPresent());
        assertEquals("PG-009", result.get().code());
    }

    @Test
    void passesWhenPoolSizeIsConfigured() {
        var ctx = new MockProdGuardContext()
                .withProperty("spring.datasource.hikari.maximum-pool-size", "20");

        assertTrue(check.check(ctx).isEmpty());
    }
}