package com.prodguard.core.licensing;

import com.prodguard.core.LicenseLevel;

public interface LicenseGate {

    boolean isAllowed(LicenseLevel level);
}
