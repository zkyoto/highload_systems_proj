package ru.ifmo.cs.interview_results.presentation.integration_event.factory;

import org.springframework.stereotype.Component;
import ru.ifmo.cs.contracts.interviewing_service.interview_results.integration_event.InterviewResultCreatedIntegrationEvent;
import ru.ifmo.cs.integration_event.IntegrationEvent;
import ru.ifmo.cs.integration_event.IntegrationEventFactory;
import ru.ifmo.cs.interview_results.domain.event.InterviewResultCreatedEvent;

@Component
public class InterviewResultCreatedIntegrationEventFactory implements IntegrationEventFactory<InterviewResultCreatedEvent> {
    @Override
    public IntegrationEvent createFromDomainEvent(String deduplicationKey, InterviewResultCreatedEvent domainEvent) {
        return new InterviewResultCreatedIntegrationEvent(
                deduplicationKey,
                domainEvent.occurredOn(),
                domainEvent.interviewResultId().value().toString(),
                domainEvent.feedbackId()
        );
    }
}
