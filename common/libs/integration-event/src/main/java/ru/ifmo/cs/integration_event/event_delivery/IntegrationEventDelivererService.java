package ru.ifmo.cs.integration_event.event_delivery;

import ru.ifmo.cs.integration_event.IntegrationEvent;

public interface IntegrationEventDelivererService {

    void deliver(IntegrationEventSubscriberReferenceId referenceId, IntegrationEvent integrationEvent);
}
