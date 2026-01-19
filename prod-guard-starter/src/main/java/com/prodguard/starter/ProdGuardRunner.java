package com.prodguard.starter;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;

import com.prodguard.core.EffectiveSeverity;
import com.prodguard.core.ProdCheck;
import com.prodguard.core.ProdGuardContext;
import com.prodguard.spring.SpringProdGuardContext;

public class ProdGuardRunner implements ApplicationRunner {

    private static final Logger log =
        LoggerFactory.getLogger(ProdGuardRunner.class);

    private final boolean reportOnly;
    private final List<ProdCheck> checks;
    private final ApplicationContext applicationContext;
    private final SeverityResolver severityResolver;

    public ProdGuardRunner(
            List<ProdCheck> checks,
            ApplicationContext applicationContext,
            SeverityResolver severityResolver,
            ProdGuardProperties properties
    ) {
        this.checks = checks;
        this.applicationContext = applicationContext;
        this.severityResolver = severityResolver;
        this.reportOnly = properties.isReportOnly();
    }

    @Override
    public void run(ApplicationArguments args) {

        ProdGuardContext ctx =
            new SpringProdGuardContext(applicationContext);

        // ---------------------------------------------------------
        // Profile / force detection
        // ---------------------------------------------------------
        boolean isProd = false;
        for (String p : ctx.getActiveProfiles()) {
            if ("prod".equalsIgnoreCase(p)
                || "production".equalsIgnoreCase(p)) {
                isProd = true;
                break;
            }
        }

        boolean force =
            Boolean.parseBoolean(
                ctx.getProperty("prodguard.force").orElse("false")
            );

        if (!isProd && !force) {
            log.info(
                "[prod-guard] prod profile not active and prodguard.force not true -> skipping checks"
            );
            return;
        }

        // ---------------------------------------------------------
        // Start logging
        // ---------------------------------------------------------
        log.info(
            "[prod-guard] executing {} checks",
            checks.size()
        );

        // ---------------------------------------------------------
        // Execute checks
        // ---------------------------------------------------------
        var results =
            checks.stream()
                .map(c -> c.check(ctx))
                .flatMap(Optional::stream)
                .toList();

        // ---------------------------------------------------------
        // No findings
        // ---------------------------------------------------------
        if (results.isEmpty()) {
            log.info(
                "[prod-guard] all checks passed ({} executed)",
                checks.size()
            );
            return;
        }

        // ---------------------------------------------------------
        // Log findings
        // ---------------------------------------------------------
        results.forEach(result -> {
            var effective = severityResolver.resolve(result);

            if (effective == EffectiveSeverity.DISABLED) {
                return;
            }

            log.warn(
                "[prod-guard] {} {} - {} | {}",
                effective,
                result.code(),
                result.message(),
                result.remediation()
            );
        });

        boolean hasBlockingError =
            results.stream()
                .map(severityResolver::resolve)
                .anyMatch(s -> s == EffectiveSeverity.ERROR);

        log.warn(
            "[prod-guard] {} issues detected (blocking: {})",
            results.size(),
            hasBlockingError
        );

        // ---------------------------------------------------------
        // Fail startup if needed
        // ---------------------------------------------------------
        if (hasBlockingError) {
            if (reportOnly) {
                log.warn(
                    "[prod-guard] report-only mode enabled — application will continue to start"
                );
            } else {
                throw new IllegalStateException(
                    "prod-guard detected blocking issues"
                );
            }
        }
    }
}
