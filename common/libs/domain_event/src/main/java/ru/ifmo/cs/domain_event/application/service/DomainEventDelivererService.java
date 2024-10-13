package ru.ifmo.cs.domain_event.application.service;

import java.util.UUID;

import ru.ifmo.cs.domain_event.domain.DomainEvent;
import ru.ifmo.cs.domain_event.domain.SubscriberReferenceId;
import ru.ifmo.cs.domain_event.domain.consumed_event.exception.DomainEventAlreadyConsumedException;

public interface DomainEventDelivererService {

    void deliver(SubscriberReferenceId referenceId, UUID eventId, DomainEvent domainEvent) throws DomainEventAlreadyConsumedException;
}
