package ru.ifmo.cs.integration_event;

import lombok.RequiredArgsConstructor;
import ru.ifmo.cs.domain_event.application.service.DomainEventConsumer;
import ru.ifmo.cs.domain_event.domain.DomainEvent;

@RequiredArgsConstructor
public abstract class IntegrationEventPublisherConsumer<T extends DomainEvent> implements DomainEventConsumer<T> {
    private final IntegrationEventFactory<T> integrationEventFactory;
    private final IntegrationEventPublisher integrationEventPublisher;

    @Override
    public void consume(
            String deduplicationKey,
            T event
    ) {
        IntegrationEvent integrationEvent = integrationEventFactory.createFromDomainEvent(deduplicationKey, event);
        integrationEventPublisher.publish(integrationEvent);
    }
}
