package ru.ifmo.cs.integration_event.event_delivery;

import ru.ifmo.cs.integration_event.IntegrationEvent;

public record KnownIntegrationEvent(String eventTypeId, Class<? extends IntegrationEvent> eventClass) {}
