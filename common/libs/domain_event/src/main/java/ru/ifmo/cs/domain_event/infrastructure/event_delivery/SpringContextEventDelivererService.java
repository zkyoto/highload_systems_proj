package ru.ifmo.cs.domain_event.infrastructure.event_delivery;

import java.util.UUID;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ifmo.cs.domain_event.application.service.DomainEventConsumer;
import ru.ifmo.cs.domain_event.application.service.DomainEventDelivererService;
import ru.ifmo.cs.domain_event.domain.DomainEvent;
import ru.ifmo.cs.domain_event.domain.SubscriberReferenceId;

public class SpringContextEventDelivererService implements DomainEventDelivererService {

    private final ApplicationContext applicationContext;

    public SpringContextEventDelivererService(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Transactional
    public void deliver(SubscriberReferenceId referenceId, UUID eventId, DomainEvent domainEvent) {
        DomainEventConsumer<DomainEvent> domainEventConsumer =
                (DomainEventConsumer<DomainEvent>) applicationContext.getBean(referenceId.id());

        domainEventConsumer.consume(eventId.toString(), domainEvent);
    }
}
