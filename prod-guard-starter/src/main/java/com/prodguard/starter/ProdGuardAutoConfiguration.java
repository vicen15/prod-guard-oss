package com.prodguard.starter;

import java.util.List;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.prodguard.core.LicenseLevel;
import com.prodguard.core.ProdCheck;
import com.prodguard.core.licensing.LicenseGate;

@AutoConfiguration
@EnableConfigurationProperties(ProdGuardProperties.class)
public class ProdGuardAutoConfiguration {


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
    LicenseGate licenseGate() {
        return level -> level == LicenseLevel.FREE;
    }
}
