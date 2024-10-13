package ru.ifmo.cs.domain_event.infrastructure.event_delivery;

import java.util.UUID;

import lombok.AllArgsConstructor;
import ru.ifmo.cs.domain_event.application.service.ConsumptionLogJournalService;
import ru.ifmo.cs.domain_event.application.service.DomainEventDelivererService;
import ru.ifmo.cs.domain_event.domain.DomainEvent;
import ru.ifmo.cs.domain_event.domain.SubscriberReferenceId;
import ru.ifmo.cs.domain_event.domain.consumed_event.ConsumedDomainEvent;
import ru.ifmo.cs.domain_event.domain.consumed_event.exception.DomainEventAlreadyConsumedException;

@AllArgsConstructor
public class IdempotentDomainEventDelivererService implements DomainEventDelivererService {

    private final DomainEventDelivererService domainEventDelivererService;
    private final ConsumptionLogJournalService consumptionLogJournalService;

    //    @Transactional
    public void deliver(
            SubscriberReferenceId referenceId,
            UUID eventId,
            DomainEvent domainEvent
    ) throws DomainEventAlreadyConsumedException {
        ConsumedDomainEvent consumedDomainEvent = new ConsumedDomainEvent(eventId, referenceId.id());

        boolean isEventAlreadyConsumed = consumptionLogJournalService.acquireFor(consumedDomainEvent);
        if (isEventAlreadyConsumed) {
            throw new DomainEventAlreadyConsumedException(
                    domainEvent.getClass(),
                    eventId,
                    referenceId.toString()
            );
        }

        domainEventDelivererService.deliver(referenceId, eventId, domainEvent);
    }
}
