package ru.ifmo.cs.integration_event.event_delivery;

import java.util.List;

public interface KnownIntegrationEventProvider {

    List<KnownIntegrationEvent> provide();
}
