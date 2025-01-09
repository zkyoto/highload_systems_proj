package ru.ifmo.cs.integration_event;

public interface IntegrationEventConsumer<T extends IntegrationEvent> {
    void consume(T event);
}