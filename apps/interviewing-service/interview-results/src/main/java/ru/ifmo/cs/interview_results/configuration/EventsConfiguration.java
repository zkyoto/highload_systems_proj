package ru.ifmo.cs.interview_results.configuration;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.ifmo.cs.domain_event.configuration.DomainEventsConfiguration;
import ru.ifmo.cs.domain_event.infrastructure.repository.KnownDomainEventTypeResolver;
import ru.ifmo.cs.integration_event.configuration.IntegrationEventPublishingConfiguration;
import ru.ifmo.cs.interview_results.application.service.event.InterviewResultEventsKnownEventProvider;

@Configuration
@Import({
        DomainEventsConfiguration.class,
        IntegrationEventPublishingConfiguration.class,
})
public class EventsConfiguration {
    @Bean
    public KnownDomainEventTypeResolver knownDomainEventTypeResolver(
            InterviewResultEventsKnownEventProvider interviewResultEventsKnownEventProvider
    ) {
        return new KnownDomainEventTypeResolver(
                List.of(interviewResultEventsKnownEventProvider));
    }
}