package ru.ifmo.cs.interviews.presentation.domain_event.consumer;

import org.springframework.stereotype.Component;
import ru.ifmo.cs.integration_event.IntegrationEventFactory;
import ru.ifmo.cs.integration_event.IntegrationEventPublisher;
import ru.ifmo.cs.integration_event.IntegrationEventPublisherConsumer;
import ru.ifmo.cs.interviews.domain.event.InterviewCancelledEvent;

@Component
public class InterviewCancelledDomainEventConsumer extends IntegrationEventPublisherConsumer<InterviewCancelledEvent> {
    public InterviewCancelledDomainEventConsumer(
            IntegrationEventFactory<InterviewCancelledEvent> integrationEventFactory,
            IntegrationEventPublisher integrationEventPublisher
    ) {
        super(integrationEventFactory, integrationEventPublisher);
    }
}
