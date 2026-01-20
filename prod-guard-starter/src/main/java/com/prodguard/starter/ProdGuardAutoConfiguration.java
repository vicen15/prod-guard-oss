package com.prodguard.starter;

import java.util.List;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.prodguard.core.ProdCheck;
import com.prodguard.licensing.LicenseContext;
import com.prodguard.licensing.LicenseGate;
import com.prodguard.licensing.LicenseVerifier;
import com.prodguard.licensing.NoopLicenseVerifier;

@AutoConfiguration
@EnableConfigurationProperties(ProdGuardProperties.class)
public class ProdGuardAutoConfiguration {

	
	final String PREMIUM_CHECKS_PREFIX = "PG-2";

    @Bean
    @ConditionalOnMissingBean
    SeverityResolver severityResolver(ProdGuardProperties properties) {
        return new SeverityResolver(properties);
    }	
	
    @Bean
    ProdGuardRunner prodGuardRunner(
            List<ProdCheck> checks,
            ApplicationContext applicationContext,
            SeverityResolver severityResolver,
            ProdGuardProperties properties,
            LicenseGate licenseGate
    ) {
        return new ProdGuardRunner(
            checks,
            applicationContext,
            severityResolver,
            properties,
            licenseGate
        );
    }
    
    @Bean
    @ConditionalOnMissingBean
    LicenseVerifier licenseVerifier() {
        return new NoopLicenseVerifier();
    }

//    @Bean
//    LicenseGate licenseGate(LicenseVerifier verifier) {
//        LicenseContext ctx = verifier.verify();
//        return level -> ctx.valid() && ctx.level().ordinal() >= level.ordinal();
//    }    
    
    
    @Bean
    LicenseGate licenseGate(LicenseVerifier verifier) {

        LicenseContext ctx = verifier.verify();

        return checkCode -> {
            if (!checkCode.startsWith(PREMIUM_CHECKS_PREFIX)) {
                return true; // FREE check
            }

            return ctx.valid(); // PREMIUM → license required
        };
    }
    
}
