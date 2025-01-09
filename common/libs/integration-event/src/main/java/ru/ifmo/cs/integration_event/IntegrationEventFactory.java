package ru.ifmo.cs.integration_event;

import ru.ifmo.cs.domain_event.domain.DomainEvent;

public interface IntegrationEventFactory<T extends DomainEvent> {
    IntegrationEvent createFromDomainEvent(String deduplicationKey, T domainEvent);
}
