package ru.ifmo.cs.interviewers.configuration;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.ifmo.cs.domain_event.configuration.DomainEventsConfiguration;
import ru.ifmo.cs.domain_event.infrastructure.repository.KnownDomainEventTypeResolver;
import ru.ifmo.cs.interviewers.application.service.event.InterviewerEventsKnownEventProvider;

@Configuration
@Import(DomainEventsConfiguration.class)
public class EventsConfiguration {
    @Bean
    public KnownDomainEventTypeResolver knownDomainEventTypeResolver(
            InterviewerEventsKnownEventProvider interviewerEventsKnownEventProvider
    ) {
        return new KnownDomainEventTypeResolver(
                List.of(interviewerEventsKnownEventProvider));
    }
}