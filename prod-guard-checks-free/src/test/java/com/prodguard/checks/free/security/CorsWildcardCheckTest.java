package com.prodguard.checks.free.security;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.prodguard.checks.free.support.MockProdGuardContext;
import com.prodguard.core.CheckResult;

class CorsWildcardCheckTest {

    private final CorsWildcardCheck check = new CorsWildcardCheck();

    @Test
    void warnsWhenWildcardOriginIsConfigured() {

        MockProdGuardContext ctx =
                new MockProdGuardContext()
                        .withProperty(
                                "spring.web.cors.allowed-origins",
                                "*"
                        );

        Optional<CheckResult> result = check.check(ctx);

        assertThat(result).isPresent();
        assertThat(result.get().descriptor().code())
                .isEqualTo("PG-013");
        assertThat(result.get().message())
                .contains("CORS allows all origins");
    }

    @Test
    void passesWhenOriginsAreExplicit() {

        MockProdGuardContext ctx =
                new MockProdGuardContext()
                        .withProperty(
                                "spring.web.cors.allowed-origins",
                                "https://example.com,https://api.example.com"
                        );

        Optional<CheckResult> result = check.check(ctx);

        assertThat(result).isEmpty();
    }

    @Test
    void passesWhenPropertyIsMissing() {

        MockProdGuardContext ctx =
                new MockProdGuardContext();

        Optional<CheckResult> result = check.check(ctx);

        assertThat(result).isEmpty();
    }

    @Test
    void passesWhenPropertyIsEmpty() {

        MockProdGuardContext ctx =
                new MockProdGuardContext()
                        .withProperty(
                                "spring.web.cors.allowed-origins",
                                ""
                        );

        Optional<CheckResult> result = check.check(ctx);

        assertThat(result).isEmpty();
    }
}
