package com.prodguard.core;

public record CheckDescriptor(String code, String name, Severity severity, String longDescription, LicenseLevel licenseLevel) {
	
	public CheckDescriptor(String code, String name, Severity severity) {
		this(code, name, severity, null, null);
	}

	
	public boolean hasLongDescription() {
		return longDescription != null && !longDescription.isBlank();
	}
	
}