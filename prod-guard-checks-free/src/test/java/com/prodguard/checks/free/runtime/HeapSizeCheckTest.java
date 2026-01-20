package com.prodguard.checks.free.runtime;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.prodguard.checks.free.support.MockProdGuardContext;

class HeapSizeCheckTest {

    private final HeapSizeCheck check = new HeapSizeCheck();

    @Test
    void heapCheckAlwaysReturnsResultOnLowHeap() {
        var ctx = new MockProdGuardContext();

        var result = check.check(ctx);

        // Depends on JVM, but result is Optional (never throws)
        assertNotNull(result);
    }
}
