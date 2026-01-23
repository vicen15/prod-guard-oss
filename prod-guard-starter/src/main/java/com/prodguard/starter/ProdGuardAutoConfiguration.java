package com.prodguard.starter;

import java.nio.file.Path;
import java.util.List;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.prodguard.core.ProdCheck;
import com.prodguard.licensing.Ed25519SignatureVerifier;
import com.prodguard.licensing.LicenseContext;
import com.prodguard.licensing.LicenseGate;
import com.prodguard.licensing.LicenseVerifier;
import com.prodguard.licensing.NoopLicenseVerifier;
import com.prodguard.licensing.SignatureVerifier;
import com.prodguard.licensing.SignedFileLicenseVerifier;

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
    @ConditionalOnProperty(
        prefix = "prodguard.license",
        name = "path"
    )
    LicenseVerifier signedFileLicenseVerifier(
            ProdGuardProperties properties,
            SignatureVerifier signatureVerifier
    ) {
        return new SignedFileLicenseVerifier(
            Path.of(properties.getLicense().getPath()),
            signatureVerifier
        );
    }
    
    
    @Bean
    @ConditionalOnMissingBean
    LicenseVerifier noopLicenseVerifier() {
        return new NoopLicenseVerifier();
    }

    @Bean
    @ConditionalOnMissingBean
    SignatureVerifier signatureVerifier() {
        return new Ed25519SignatureVerifier();
    }
    

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
