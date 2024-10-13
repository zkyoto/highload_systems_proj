package ru.ifmo.cs.domain_event.application.service;

import java.util.List;
import java.util.Map;

import ru.ifmo.cs.domain_event.domain.DomainEvent;
import ru.ifmo.cs.domain_event.domain.SubscriberReferenceId;

public interface DomainEventRecipientsResolverService {

    List<SubscriberReferenceId> subscribersFor(Class<? extends DomainEvent> className);
    Map<Class<?>, List<SubscriberReferenceId>> registeredConsumers();
}