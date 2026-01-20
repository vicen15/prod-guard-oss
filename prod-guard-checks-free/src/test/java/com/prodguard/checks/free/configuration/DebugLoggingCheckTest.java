package com.prodguard.checks.free.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.prodguard.checks.free.support.MockProdGuardContext;
import com.prodguard.core.CheckResult;
import com.prodguard.core.Severity;

class DebugLoggingCheckTest {

    private final DebugLoggingCheck check = new DebugLoggingCheck();

    @Test
    void returnsErrorWhenRootLoggingIsDebug() {
        var ctx = new MockProdGuardContext()
                .withProperty("logging.level.root", "DEBUG");

        Optional<CheckResult> result = check.check(ctx);

        assertTrue(result.isPresent());
        assertEquals(Severity.ERROR, result.get().severity());
        assertEquals("PG-001", result.get().code());
    }

    @Test
    void returnsEmptyWhenLoggingIsInfo() {
        var ctx = new MockProdGuardContext()
                .withProperty("logging.level.root", "INFO");

        Optional<CheckResult> result = check.check(ctx);

        assertTrue(result.isEmpty());
    }
}
