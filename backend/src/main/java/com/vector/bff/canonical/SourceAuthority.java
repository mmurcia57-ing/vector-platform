package com.vector.bff.canonical;

public record SourceAuthority(String authorityReference, boolean explicitlyDeclared) {
	public SourceAuthority {
		if (authorityReference != null && authorityReference.isBlank()) {
			throw new IllegalArgumentException("authorityReference cannot be blank");
		}
	}

	public static SourceAuthority unknown() {
		return new SourceAuthority(null, false);
	}
}
