package ru.ifmo.cs.integration_event;

public interface IntegrationEventPublisher {
    void publish(IntegrationEvent integrationEvent);
}
