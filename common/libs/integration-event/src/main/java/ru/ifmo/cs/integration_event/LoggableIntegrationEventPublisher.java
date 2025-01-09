package ru.ifmo.cs.integration_event;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class LoggableIntegrationEventPublisher implements IntegrationEventPublisher {
    private final IntegrationEventPublisher publisher;

    @Override
    public void publish(IntegrationEvent integrationEvent) {
        log.info("Sending event {}", integrationEvent);
        publisher.publish(integrationEvent);
    }
}
