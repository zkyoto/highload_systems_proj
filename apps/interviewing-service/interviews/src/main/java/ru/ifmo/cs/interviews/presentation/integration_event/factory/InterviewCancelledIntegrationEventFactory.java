package ru.ifmo.cs.interviews.presentation.integration_event.factory;

import org.springframework.stereotype.Component;
import ru.ifmo.cs.contracts.interviewing_service.interviews.integration_event.InterviewCancelledIntegrationEvent;
import ru.ifmo.cs.integration_event.IntegrationEvent;
import ru.ifmo.cs.integration_event.IntegrationEventFactory;
import ru.ifmo.cs.interviews.domain.event.InterviewCancelledEvent;

@Component
public class InterviewCancelledIntegrationEventFactory implements IntegrationEventFactory<InterviewCancelledEvent> {

    @Override
    public IntegrationEvent createFromDomainEvent(String deduplicationKey, InterviewCancelledEvent domainEvent) {
        return new InterviewCancelledIntegrationEvent(
                deduplicationKey,
                domainEvent.occurredOn(),
                domainEvent.interviewId().value().toString(),
                domainEvent.interviewerId().toString(),
                domainEvent.candidateId().toString()
        );
    }
}
