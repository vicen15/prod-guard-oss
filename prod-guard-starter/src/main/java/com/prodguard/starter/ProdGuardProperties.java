package com.prodguard.starter;

import com.prodguard.core.EffectiveSeverity;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "prodguard")
public class ProdGuardProperties {

	private boolean reportOnly = false;
	
	private boolean trustSelfSigned = false;
	
	public boolean isReportOnly() {
        return reportOnly;
    }

    public void setReportOnly(boolean reportOnly) {
        this.reportOnly = reportOnly;
    }	
	
	public boolean isTrustSelfSigned() {
	    return trustSelfSigned;
	}

	public void setTrustSelfSigned(boolean trustSelfSigned) {
	    this.trustSelfSigned = trustSelfSigned;
	}    
    
    /**
     * Key: check code (e.g. PG-005)
     * Value: severity override
     */
    private Map<String, EffectiveSeverity> severities = new HashMap<>();

    public Map<String, EffectiveSeverity> getSeverities() {
        return severities;
    }

    public void setSeverities(Map<String, EffectiveSeverity> severities) {
        this.severities = severities;
    }
}
