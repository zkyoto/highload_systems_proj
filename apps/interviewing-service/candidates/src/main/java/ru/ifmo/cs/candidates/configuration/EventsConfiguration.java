package ru.ifmo.cs.candidates.configuration;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.ifmo.cs.candidates.application.service.event.CandidateEventsKnownEventProvider;
import ru.ifmo.cs.contracts.interviewing_service.interviews.integration_event.InterviewCancelledIntegrationEvent;
import ru.ifmo.cs.contracts.interviewing_service.interview_results.integration_event.InterviewResultCreatedIntegrationEvent;
import ru.ifmo.cs.contracts.interviewing_service.interviews.integration_event.InterviewScheduledIntegrationEvent;
import ru.ifmo.cs.domain_event.configuration.DomainEventsConfiguration;
import ru.ifmo.cs.domain_event.infrastructure.repository.KnownDomainEventTypeResolver;
import ru.ifmo.cs.integration_event.configuration.IntegrationEventDeliveryConfiguration;
import ru.ifmo.cs.integration_event.event_delivery.KnownIntegrationEvent;
import ru.ifmo.cs.integration_event.event_delivery.KnownIntegrationEventTypeResolver;

@Configuration
@Import({
        DomainEventsConfiguration.class,
        IntegrationEventDeliveryConfiguration.class
})
public class EventsConfiguration {
    @Bean
    public KnownDomainEventTypeResolver knownDomainEventTypeResolver(
            CandidateEventsKnownEventProvider candidateEventsKnownEventProvider
    ) {
        return new KnownDomainEventTypeResolver(
                List.of(candidateEventsKnownEventProvider));
    }

    @Bean
    public KnownIntegrationEventTypeResolver knownIntegrationEventTypeResolver() {
        return new KnownIntegrationEventTypeResolver(
                List.of(() -> List.of(
                        new KnownIntegrationEvent(
                                InterviewScheduledIntegrationEvent.EVENT_TYPE,
                                InterviewScheduledIntegrationEvent.class
                        ),
                        new KnownIntegrationEvent(
                                InterviewResultCreatedIntegrationEvent.EVENT_TYPE,
                                InterviewResultCreatedIntegrationEvent.class
                        ),
                        new KnownIntegrationEvent(
                                InterviewCancelledIntegrationEvent.EVENT_TYPE,
                                InterviewCancelledIntegrationEvent.class
                        )))
        );
    }
}