package com.prodguard.core;

public record CheckDescriptor(String code, String name, Severity severity, String longDescription) {
	
	public CheckDescriptor(String code, String name, Severity severity) {
		this(code, name, severity, null);
	}

	
	public boolean hasLongDescription() {
		return longDescription != null && !longDescription.isBlank();
	}
	
}