package ru.ifmo.cs.domain_event.application.service;

import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ifmo.cs.domain_event.domain.SubscriberReferenceId;
import ru.ifmo.cs.domain_event.domain.consumed_event.exception.DomainEventAlreadyConsumedException;
import ru.ifmo.cs.domain_event.domain.stored_event.StoredDomainEvent;
import ru.ifmo.cs.domain_event.domain.stored_event.StoredDomainEventRepository;

@AllArgsConstructor
public class DomainEventFanoutDelivererService {
    private final StoredDomainEventRepository storedDomainEventRepository;
    private final DomainEventRecipientsResolverService recipientsResolverService;
    private final DomainEventDelivererService domainEventDelivererService;

    @Transactional
    public boolean deliverNext() {
        StoredDomainEvent storedDomainEvent = storedDomainEventRepository.nextWaitedForDelivery();

        if (null == storedDomainEvent) {
            return false;
        }

        List<SubscriberReferenceId> referenceIds = recipientsResolverService.subscribersFor(
                storedDomainEvent.getEvent().getClass()
        );

        for (SubscriberReferenceId referenceId : referenceIds) {
            try {
                domainEventDelivererService.deliver(
                        referenceId,
                        storedDomainEvent.getEventId(),
                        storedDomainEvent.getEvent()
                );
            } catch (DomainEventAlreadyConsumedException e) {
                //log e.message();
            }
        }

        storedDomainEvent.markEventAsDelivered();
        storedDomainEventRepository.save(storedDomainEvent);

        return true;
    }
}
