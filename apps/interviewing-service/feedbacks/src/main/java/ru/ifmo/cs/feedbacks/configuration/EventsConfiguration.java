package ru.ifmo.cs.feedbacks.configuration;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.ifmo.cs.contracts.interviewing_service.interviews.integration_event.InterviewScheduledIntegrationEvent;
import ru.ifmo.cs.domain_event.configuration.DomainEventsConfiguration;
import ru.ifmo.cs.domain_event.infrastructure.repository.KnownDomainEventTypeResolver;
import ru.ifmo.cs.feedbacks.application.service.event.FeedbackEventsKnownEventProvider;
import ru.ifmo.cs.integration_event.configuration.IntegrationEventDeliveryConfiguration;
import ru.ifmo.cs.integration_event.event_delivery.KnownIntegrationEvent;
import ru.ifmo.cs.integration_event.event_delivery.KnownIntegrationEventTypeResolver;

@Configuration
@Import({
        DomainEventsConfiguration.class,
        IntegrationEventDeliveryConfiguration.class,
})
public class EventsConfiguration {
    @Bean
    public KnownDomainEventTypeResolver knownDomainEventTypeResolver(
            FeedbackEventsKnownEventProvider feedbackEventsKnownEventProvider
    ) {
        return new KnownDomainEventTypeResolver(
                List.of(feedbackEventsKnownEventProvider));
    }

    @Bean
    public KnownIntegrationEventTypeResolver knownIntegrationEventTypeResolver() {
        return new KnownIntegrationEventTypeResolver(
                List.of(() -> List.of(
                        new KnownIntegrationEvent(
                                InterviewScheduledIntegrationEvent.EVENT_TYPE,
                                InterviewScheduledIntegrationEvent.class
                        )))
        );
    }
}