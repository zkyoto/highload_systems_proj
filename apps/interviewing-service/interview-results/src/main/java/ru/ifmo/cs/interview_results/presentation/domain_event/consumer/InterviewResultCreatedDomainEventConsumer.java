package ru.ifmo.cs.interview_results.presentation.domain_event.consumer;

import org.springframework.stereotype.Component;
import ru.ifmo.cs.integration_event.IntegrationEventFactory;
import ru.ifmo.cs.integration_event.IntegrationEventPublisher;
import ru.ifmo.cs.integration_event.IntegrationEventPublisherConsumer;
import ru.ifmo.cs.interview_results.domain.event.InterviewResultCreatedEvent;

@Component
class InterviewResultCreatedDomainEventConsumer extends IntegrationEventPublisherConsumer<InterviewResultCreatedEvent> {
    public InterviewResultCreatedDomainEventConsumer(
            IntegrationEventFactory<InterviewResultCreatedEvent> integrationEventFactory,
            IntegrationEventPublisher integrationEventPublisher
    ) {
        super(integrationEventFactory, integrationEventPublisher);
    }
}
