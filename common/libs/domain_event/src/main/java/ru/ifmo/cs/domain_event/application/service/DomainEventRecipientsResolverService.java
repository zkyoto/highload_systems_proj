package ru.ifmo.cs.domain_event.application.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import ru.ifmo.cs.domain_event.domain.DomainEvent;
import ru.ifmo.cs.domain_event.domain.SubscriberReferenceId;

@Service
public interface DomainEventRecipientsResolverService {

    List<SubscriberReferenceId> subscribersFor(Class<? extends DomainEvent> className);
    Map<Class<?>, List<SubscriberReferenceId>> registeredConsumers();
}