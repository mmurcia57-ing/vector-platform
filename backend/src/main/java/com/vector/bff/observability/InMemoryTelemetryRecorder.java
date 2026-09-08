package com.vector.bff.observability;
import java.util.ArrayList;
import java.util.List;
public final class InMemoryTelemetryRecorder implements TelemetryRecorder {
    private final List<TelemetryEvent> events = new ArrayList<>();
    public void record(TelemetryEvent event) { events.add(event); }
    public List<TelemetryEvent> events() { return List.copyOf(events); }
}
