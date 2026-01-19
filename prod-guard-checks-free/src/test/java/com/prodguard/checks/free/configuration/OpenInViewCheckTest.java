package com.prodguard.checks.free.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.prodguard.checks.free.support.MockProdGuardContext;
import com.prodguard.core.CheckResult;


class OpenInViewCheckTest {

    @Test
    void reportsWarningWhenOpenInViewIsEnabled() {

        MockProdGuardContext ctx =
                new MockProdGuardContext()
                        .withProperty("spring.jpa.open-in-view", "true");

        OpenInViewCheck check = new OpenInViewCheck();

        Optional<CheckResult> result = check.check(ctx);

        assertThat(result).isPresent();
        assertThat(result.get().code())
                .isEqualTo("PG-011");
        assertThat(result.get().message())
                .contains("Open Session In View is enabled");
        assertThat(result.get().remediation())
                .contains("spring.jpa.open-in-view=false");
    }

    @Test
    void reportsWarningWhenOpenInViewPropertyIsMissing() {

        MockProdGuardContext ctx = new MockProdGuardContext();
        // property absent → defaults to true

        OpenInViewCheck check = new OpenInViewCheck();

        Optional<CheckResult> result = check.check(ctx);

        assertThat(result).isPresent();
        assertThat(result.get().message())
                .contains("Open Session In View is enabled");
    }

    @Test
    void passesWhenOpenInViewIsDisabled() {

        MockProdGuardContext ctx =
                new MockProdGuardContext()
                        .withProperty("spring.jpa.open-in-view", "false");

        OpenInViewCheck check = new OpenInViewCheck();

        Optional<CheckResult> result = check.check(ctx);

        assertThat(result).isEmpty();
    }

    @Test
    void acceptsCaseInsensitiveBooleanValues() {

        MockProdGuardContext ctx =
                new MockProdGuardContext()
                        .withProperty("spring.jpa.open-in-view", "FALSE");

        OpenInViewCheck check = new OpenInViewCheck();

        Optional<CheckResult> result = check.check(ctx);

        assertThat(result).isEmpty();
    }
}
