package com.vector.bff.graph;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/experience/graph")
public class GraphViewController {
    private final BoundedGraphQueryService queryService;

    public GraphViewController(BoundedGraphQueryService queryService) { this.queryService = queryService; }

    @GetMapping
    GraphViewProjection graph(@RequestParam(required = false) String period,
            @RequestParam(required = false) String areaDomainId, @RequestParam(required = false) String serviceId,
            @RequestParam(required = false) String riskFindingId, @RequestParam(defaultValue = "12") int maxNodes,
            @RequestParam(defaultValue = "16") int maxRelationships) {
        var seed = riskFindingId == null || riskFindingId.isBlank()
            ? new GraphNode("Service", serviceId == null ? "unresolved-service" : serviceId)
            : new GraphNode("RiskFinding", riskFindingId);
        var graph = queryService.query(new BoundedGraphQuery(seed, maxNodes, maxRelationships));
        return new GraphViewProjection(period, areaDomainId, serviceId, riskFindingId, graph,
            graph.nodes().size() <= 1 ? "partial" : "available");
    }
}
