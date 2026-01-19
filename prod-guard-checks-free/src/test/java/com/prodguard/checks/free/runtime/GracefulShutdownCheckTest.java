package com.prodguard.checks.free.runtime;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.prodguard.core.CheckResult;
import com.prodguard.checks.free.support.MockProdGuardContext;

class GracefulShutdownCheckTest {

    @Test
    void reportsWarningWhenGracefulShutdownIsNotEnabled() {

        MockProdGuardContext ctx =
                new MockProdGuardContext()
                        .withProperty("server.shutdown", "immediate");

        GracefulShutdownCheck check = new GracefulShutdownCheck();

        Optional<CheckResult> result = check.check(ctx);

        assertThat(result).isPresent();
        assertThat(result.get().code())
                .isEqualTo("PG-012");
        assertThat(result.get().message())
                .contains("Graceful shutdown is not enabled");
        assertThat(result.get().remediation())
                .contains("server.shutdown=graceful");
    }

    @Test
    void reportsWarningWhenShutdownPropertyIsMissing() {

        MockProdGuardContext ctx = new MockProdGuardContext();
        // no server.shutdown property → defaults to "immediate"

        GracefulShutdownCheck check = new GracefulShutdownCheck();

        Optional<CheckResult> result = check.check(ctx);

        assertThat(result).isPresent();
        assertThat(result.get().message())
                .contains("Graceful shutdown is not enabled");
    }

    @Test
    void passesWhenGracefulShutdownIsEnabled() {

        MockProdGuardContext ctx =
                new MockProdGuardContext()
                        .withProperty("server.shutdown", "graceful");

        GracefulShutdownCheck check = new GracefulShutdownCheck();

        Optional<CheckResult> result = check.check(ctx);

        assertThat(result).isEmpty();
    }

    @Test
    void acceptsCaseInsensitiveGracefulValue() {

        MockProdGuardContext ctx =
                new MockProdGuardContext()
                        .withProperty("server.shutdown", "GrAcEfUl");

        GracefulShutdownCheck check = new GracefulShutdownCheck();

        Optional<CheckResult> result = check.check(ctx);

        assertThat(result).isEmpty();
    }
}
