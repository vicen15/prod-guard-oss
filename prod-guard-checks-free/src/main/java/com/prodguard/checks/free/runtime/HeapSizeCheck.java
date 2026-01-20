package com.prodguard.checks.free.runtime;

import java.util.Optional;

import com.prodguard.core.CheckDescriptor;
import com.prodguard.core.CheckResult;
import com.prodguard.core.ProdCheck;
import com.prodguard.core.ProdGuardContext;
import com.prodguard.core.Severity;

public class HeapSizeCheck implements ProdCheck {

	public static final CheckDescriptor DESCRIPTOR =
	        new CheckDescriptor(
	                "PG-008",
	                "JVM maximum heap size configuration",
	                Severity.WARN, null
	        );

	
    @Override
    public Optional<CheckResult> check(ProdGuardContext ctx) {
        long maxHeapMb = Runtime.getRuntime().maxMemory() / (1024L * 1024L);
        if (maxHeapMb < 512) {
            return Optional.of(new CheckResult(
            		DESCRIPTOR,
            		 "Maximum JVM heap size is below 512 MB (" + maxHeapMb + " MB detected)",
            	     "Increase the JVM -Xmx setting for production workloads"
            ));
        }
        return Optional.empty();
    }
    
    @Override
    public CheckDescriptor descriptor() {
        return DESCRIPTOR;
    }    
}