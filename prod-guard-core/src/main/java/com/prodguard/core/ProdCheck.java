
package com.prodguard.core;

import java.util.Optional;

/**
 * A single production check. Implementations should be lightweight and side-effect free.
 */
public interface ProdCheck {
	
    CheckDescriptor descriptor();
    
    Optional<CheckResult> check(ProdGuardContext ctx);
}
