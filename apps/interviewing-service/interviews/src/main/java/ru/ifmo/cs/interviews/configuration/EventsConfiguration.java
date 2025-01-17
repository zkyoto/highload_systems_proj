package ru.ifmo.cs.interviews.configuration;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.ifmo.cs.contracts.interviewing_service.candidates.integration_event.CandidateStatusTransferToWaitingForInterviewFailIntegrationEvent;
import ru.ifmo.cs.domain_event.configuration.DomainEventsConfiguration;
import ru.ifmo.cs.domain_event.infrastructure.repository.KnownDomainEventTypeResolver;
import ru.ifmo.cs.integration_event.configuration.IntegrationEventDeliveryConfiguration;
import ru.ifmo.cs.integration_event.configuration.IntegrationEventPublishingConfiguration;
import ru.ifmo.cs.integration_event.event_delivery.KnownIntegrationEvent;
import ru.ifmo.cs.integration_event.event_delivery.KnownIntegrationEventTypeResolver;
import ru.ifmo.cs.interviews.application.service.event.InterviewEventsKnownEventProvider;

@Configuration
@Import({
        DomainEventsConfiguration.class,
        IntegrationEventPublishingConfiguration.class,
        IntegrationEventDeliveryConfiguration.class,
})
public class EventsConfiguration {
    @Bean
    public KnownDomainEventTypeResolver knownDomainEventTypeResolver(
            InterviewEventsKnownEventProvider interviewEventsKnownEventProvider
    ) {
        return new KnownDomainEventTypeResolver(
                List.of(interviewEventsKnownEventProvider));
    }

    @Bean
    public KnownIntegrationEventTypeResolver knownIntegrationEventTypeResolver() {
        return new KnownIntegrationEventTypeResolver(
                List.of(() -> List.of(
                        new KnownIntegrationEvent(
                                CandidateStatusTransferToWaitingForInterviewFailIntegrationEvent.EVENT_TYPE,
                                CandidateStatusTransferToWaitingForInterviewFailIntegrationEvent.class
                        )
                ))
        );
    }

}