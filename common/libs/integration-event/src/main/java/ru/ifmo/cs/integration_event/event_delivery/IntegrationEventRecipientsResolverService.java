package ru.ifmo.cs.integration_event.event_delivery;

import java.util.List;
import java.util.Map;

import ru.ifmo.cs.integration_event.IntegrationEvent;

public interface IntegrationEventRecipientsResolverService {

    List<IntegrationEventSubscriberReferenceId> subscribersFor(Class<? extends IntegrationEvent> className);
    Map<Class<?>, List<IntegrationEventSubscriberReferenceId>> registeredConsumers();
}