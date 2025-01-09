package ru.ifmo.cs.integration_event.event_delivery;

import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import ru.ifmo.cs.domain_event.application.service.DomainEventDelivererService;
import ru.ifmo.cs.domain_event.application.service.DomainEventRecipientsResolverService;
import ru.ifmo.cs.domain_event.domain.SubscriberReferenceId;
import ru.ifmo.cs.domain_event.domain.consumed_event.exception.DomainEventAlreadyConsumedException;
import ru.ifmo.cs.domain_event.domain.stored_event.StoredDomainEvent;
import ru.ifmo.cs.domain_event.domain.stored_event.StoredDomainEventRepository;
import ru.ifmo.cs.integration_event.IntegrationEvent;

@AllArgsConstructor
public class IntegrationEventFanoutDelivererService {
    private final IntegrationEventRecipientsResolverService recipientsResolverService;
    private final IntegrationEventDelivererService delivererService;

    @Transactional
    public void deliverEvent(
            IntegrationEvent event
    ) {

        List<IntegrationEventSubscriberReferenceId> referenceIds = recipientsResolverService.subscribersFor(
                event.getClass()
        );

        for (IntegrationEventSubscriberReferenceId referenceId : referenceIds) {
            delivererService.deliver(
                    referenceId,
                    event
            );
        }
    }
}
