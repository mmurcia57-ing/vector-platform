package com.vector.bff.integration;

import com.vector.bff.canonical.MonitoringEvent;

/** Contract-compatible ARIA route; it does not establish corporate authority or write back. */
public final class AriaMonitoringEventRoute implements ProviderRoute<MonitoringEvent, MonitoringEvent> {
    private final LocalIntegrationContract contract;

    public AriaMonitoringEventRoute(LocalIntegrationContract contract) {
        this.contract = requireMapping(contract, "MonitoringEvent");
    }

    @Override
    public LocalIntegrationResult<MonitoringEvent> read(MonitoringEvent sourceRecord) {
        if (sourceRecord == null) throw new IllegalArgumentException("sourceRecord is required");
        return new LocalIntegrationResult<>(sourceRecord, contract);
    }

    private static LocalIntegrationContract requireMapping(LocalIntegrationContract value, String mapping) {
        if (value == null || !mapping.equals(value.canonicalMapping())) throw new IllegalArgumentException("MonitoringEvent mapping is required");
        return value;
    }
}
