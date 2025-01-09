package ru.ifmo.cs.interviews.configuration;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.ifmo.cs.contracts.interviewing_service.interview_results.integration_event.InterviewResultCreatedIntegrationEvent;
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
})
public class EventsConfiguration {
    @Bean
    public KnownDomainEventTypeResolver knownDomainEventTypeResolver(
            InterviewEventsKnownEventProvider interviewEventsKnownEventProvider
    ) {
        return new KnownDomainEventTypeResolver(
                List.of(interviewEventsKnownEventProvider));
    }

}