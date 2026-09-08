package com.vector.bff.graph;

import java.util.List;

public interface GraphOutbox {
    void append(GraphProjectionEvent event);
    List<GraphProjectionEvent> pending();
    void markProjected(String eventId);
}
