package ru.ifmo.cs.integration_event;

import java.time.Instant;

public interface IntegrationEvent {
    String getDeduplicationKey();

    Instant getOccurredOn();

    String getEventType();
}
