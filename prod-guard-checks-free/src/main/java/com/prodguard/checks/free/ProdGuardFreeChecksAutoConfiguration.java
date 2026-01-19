package com.prodguard.checks.free;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.prodguard.checks.free.configuration.DatasourcePoolCheck;
import com.prodguard.checks.free.configuration.DebugLoggingCheck;
import com.prodguard.checks.free.configuration.OpenInViewCheck;
import com.prodguard.checks.free.configuration.ShowSqlCheck;
import com.prodguard.checks.free.configuration.TimeoutDefaultsCheck;
import com.prodguard.checks.free.runtime.GracefulShutdownCheck;
import com.prodguard.checks.free.runtime.HeapSizeCheck;
import com.prodguard.checks.free.security.ActuatorExposureCheck;
import com.prodguard.checks.free.security.CorsWildcardCheck;
import com.prodguard.checks.free.security.CsrfDisabledCheck;
import com.prodguard.checks.free.security.HttpsEnabledCheck;
import com.prodguard.checks.free.security.SecurityHeadersCheck;
import com.prodguard.checks.free.security.StacktraceExposureCheck;
import com.prodguard.core.ProdCheck;

@Configuration
class ProdGuardFreeChecksAutoConfiguration {

    @Bean ProdCheck gracefulShutdownCheck() {
        return new GracefulShutdownCheck();
    }

    @Bean ProdCheck openInViewCheck() {
        return new OpenInViewCheck();
    }

    @Bean ProdCheck corsWildcardCheck() {
        return new CorsWildcardCheck();
    }

    @Bean ProdCheck httpsEnabledCheck() {
        return new HttpsEnabledCheck();
    }

    @Bean ProdCheck securityHeadersCheck() {
        return new SecurityHeadersCheck();
    }

    @Bean ProdCheck heapSizeCheck() {
        return new HeapSizeCheck();
    }

    @Bean ProdCheck datasourcePoolCheck() {
        return new DatasourcePoolCheck();
    }

    @Bean ProdCheck debugLoggingCheck() {
        return new DebugLoggingCheck();
    }

    @Bean ProdCheck showSqlCheck() {
        return new ShowSqlCheck();
    }

    @Bean ProdCheck timeoutDefaultsCheck() {
        return new TimeoutDefaultsCheck();
    }

    @Bean ProdCheck stacktraceExposureCheck() {
        return new StacktraceExposureCheck();
    }

    @Bean ProdCheck csrfDisabledCheck() {
        return new CsrfDisabledCheck();
    }

    @Bean ProdCheck actuatorExposureCheck() {
        return new ActuatorExposureCheck();
    }
}
