package com.vector.bff.graph;

public record GraphViewProjection(String period, String areaDomainId, String serviceId, String riskFindingId,
        BoundedGraphProjection graph, String state) {
    public GraphViewProjection {
        if (graph == null) throw new IllegalArgumentException("graph is required");
    }
}
