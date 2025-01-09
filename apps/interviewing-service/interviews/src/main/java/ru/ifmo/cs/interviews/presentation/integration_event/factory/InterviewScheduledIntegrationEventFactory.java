package ru.ifmo.cs.interviews.presentation.integration_event.factory;

import org.springframework.stereotype.Component;
import ru.ifmo.cs.contracts.interviewing_service.interviews.integration_event.InterviewScheduledIntegrationEvent;
import ru.ifmo.cs.integration_event.IntegrationEvent;
import ru.ifmo.cs.integration_event.IntegrationEventFactory;
import ru.ifmo.cs.interviews.domain.event.InterviewScheduledEvent;

@Component
public class InterviewScheduledIntegrationEventFactory implements IntegrationEventFactory<InterviewScheduledEvent> {

    @Override
    public IntegrationEvent createFromDomainEvent(String deduplicationKey, InterviewScheduledEvent domainEvent) {
        return new InterviewScheduledIntegrationEvent(
                deduplicationKey,
                domainEvent.occurredOn(),
                domainEvent.interviewId().value().toString(),
                domainEvent.interviewerId().toString(),
                domainEvent.candidateId().toString(),
                domainEvent.scheduledFor()
        );
    }
}
