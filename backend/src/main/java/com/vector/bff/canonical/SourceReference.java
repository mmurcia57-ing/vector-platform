package com.vector.bff.canonical;

import java.util.Map;

public record SourceReference(CanonicalMetadata metadata, String sourceSystem, String sourceNativeIdentifier, String referencedObjectOrClaim, String authorityContext) implements CanonicalEntity {
	public SourceReference {
		if (sourceSystem == null || sourceSystem.isBlank()) throw new IllegalArgumentException("sourceSystem is required");
		if (sourceNativeIdentifier == null || sourceNativeIdentifier.isBlank()) throw new IllegalArgumentException("sourceNativeIdentifier is required");
		if (referencedObjectOrClaim == null || referencedObjectOrClaim.isBlank()) throw new IllegalArgumentException("referencedObjectOrClaim is required");
	}
	public String canonicalType() { return "SourceReference"; }
	public Map<String, String> semanticAttributes() { return CanonicalEntitySupport.attributes("sourceSystem", sourceSystem, "sourceNativeIdentifier", sourceNativeIdentifier, "referencedObjectOrClaim", referencedObjectOrClaim, "authorityContext", authorityContext); }
}
