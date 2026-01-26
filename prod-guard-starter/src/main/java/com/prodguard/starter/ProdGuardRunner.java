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
import com.prodguard.licensing.LicenseContext;
import com.prodguard.licensing.LicenseGate;
import com.prodguard.spring.SpringProdGuardContext;

public class ProdGuardRunner implements ApplicationRunner {

    private static final Logger log =
        LoggerFactory.getLogger(ProdGuardRunner.class);

    private final boolean reportOnly;
    private final List<ProdCheck> checks;
    private final ApplicationContext applicationContext;
    private final SeverityResolver severityResolver;
    private final LicenseGate licenseGate;
    
    public ProdGuardRunner(
            List<ProdCheck> checks,
            ApplicationContext applicationContext,
            SeverityResolver severityResolver,
            ProdGuardProperties properties,
            LicenseGate licenseGate
    ) {
        this.checks = checks;
        this.applicationContext = applicationContext;
        this.severityResolver = severityResolver;
        this.reportOnly = properties.isReportOnly();
        this.licenseGate = licenseGate;
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

        LicenseContext licenseContext = licenseGate.context();

        if (licenseContext.valid()) {
            log.info(
                "[prod-guard] premium license validated for {}",
                licenseContext.licensee()
            );
        } else {
            log.info("[prod-guard] running with FREE license");
        }        
        
        // ---------------------------------------------------------
        // Start logging
        // ---------------------------------------------------------
        log.info(
            "[prod-guard] discovered {} checks",
            checks.size()
        );

     // ---------------------------------------------------------
     // Allowed checks
     // ---------------------------------------------------------
     var allowedChecks =
         checks.stream()
             .filter(check -> {

                 String code = check.descriptor().code();
                 boolean allowed = licenseGate.isAllowed(code);

                 if (!allowed) {
                     log.warn(
                    		 "[prod-guard] premium check {} present but no valid license found",
                         code
                     );
                 }

                 return allowed;
             })
             .toList();

        if (allowedChecks.isEmpty()) {
            log.info(
                "[prod-guard] no allowed checks {}",
                checks.size()
            );
            return;
        }        
        
        log.info(
        	    "[prod-guard] skipped checks {}",
        	    checks.size() - allowedChecks.size()
        	);
        
        log.info(
        	    "[prod-guard] executing {} checks",
        	    allowedChecks.size()
        	);
        
         
        
        // ---------------------------------------------------------
        // Execute checks
        // ---------------------------------------------------------
        var results =
        	    allowedChecks.stream()
        	        .map(check -> check.check(ctx))
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
