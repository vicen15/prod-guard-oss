package com.prodguard.checks.free.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.prodguard.checks.free.support.MockProdGuardContext;


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