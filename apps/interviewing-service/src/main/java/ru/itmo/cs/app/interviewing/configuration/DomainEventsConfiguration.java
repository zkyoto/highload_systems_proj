package ru.itmo.cs.app.interviewing.configuration;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import ru.ifmo.cs.domain_event.application.service.ConsumptionLogJournalService;
import ru.ifmo.cs.domain_event.application.service.DomainEventDelivererService;
import ru.ifmo.cs.domain_event.application.service.DomainEventFanoutDelivererService;
import ru.ifmo.cs.domain_event.application.service.DomainEventRecipientsResolverService;
import ru.ifmo.cs.domain_event.application.service.KnownEventProvider;
import ru.ifmo.cs.domain_event.domain.stored_event.StoredDomainEventRepository;
import ru.ifmo.cs.domain_event.infrastructure.event_delivery.IdempotentDomainEventDelivererService;
import ru.ifmo.cs.domain_event.infrastructure.event_delivery.SpringContextDomainEventRecipientsResolverService;
import ru.ifmo.cs.domain_event.infrastructure.event_delivery.SpringContextEventDelivererService;
import ru.ifmo.cs.domain_event.infrastructure.repository.KnownDomainEventTypeResolver;
import ru.ifmo.cs.domain_event.infrastructure.repository.in_memory.InMemoryStubConsumptionLogJournalService;
import ru.ifmo.cs.domain_event.infrastructure.repository.in_memory.InMemoryStubStoredDomainEventRepository;
import ru.ifmo.cs.domain_event.infrastructure.repository.pg.PgConsumptionLogJournalService;
import ru.ifmo.cs.domain_event.infrastructure.repository.pg.PgStoredDomainEventRepository;
import ru.ifmo.cs.domain_event.infrastructure.repository.pg.mapper.StoredDomainEventRowMapper;

@Configuration
public class DomainEventsConfiguration {

    @Bean
    public DomainEventFanoutDelivererService domainEventFanoutDelivererService(
            StoredDomainEventRepository storedDomainEventRepository,
            DomainEventRecipientsResolverService recipientsResolverService,
            DomainEventDelivererService idempotentDomainEventDelivererService
    ) {
        return new DomainEventFanoutDelivererService(
                storedDomainEventRepository,
                recipientsResolverService,
                idempotentDomainEventDelivererService
        );
    }

    @Bean
    public DomainEventDelivererService idempotentDomainEventDelivererService(
            DomainEventDelivererService springContextEventDelivererService,
            ConsumptionLogJournalService consumptionLogJournalService,
            KnownDomainEventTypeResolver knownDomainEventTypeResolver
    ) {
        return new IdempotentDomainEventDelivererService(
                springContextEventDelivererService,
                consumptionLogJournalService,
                knownDomainEventTypeResolver
        );
    }

    @Bean
    public KnownDomainEventTypeResolver knownDomainEventTypeResolver(
            KnownEventProvider candidateEventsKnownEventProvider,
            KnownEventProvider feedbackEventsKnownEventProvider,
            KnownEventProvider interviewEventsKnownEventProvider,
            KnownEventProvider interviewerEventsKnownEventProvider,
            KnownEventProvider interviewResultEventsKnownEventProvider
    ) {
        return new KnownDomainEventTypeResolver(
                List.of(candidateEventsKnownEventProvider,
                        feedbackEventsKnownEventProvider,
                        interviewEventsKnownEventProvider,
                        interviewerEventsKnownEventProvider,
                        interviewResultEventsKnownEventProvider));
    }

    @Bean
    public StoredDomainEventRowMapper storedDomainEventRowMapper(
            KnownDomainEventTypeResolver knownDomainEventTypeResolver,
            ObjectMapper objectMapper
    ) {
        return new StoredDomainEventRowMapper(knownDomainEventTypeResolver, objectMapper);
    }

    @Bean
    public DomainEventDelivererService springContextEventDelivererService(ApplicationContext applicationContext) {
        return new SpringContextEventDelivererService(applicationContext);
    }

    @Bean
    public StoredDomainEventRepository storedDomainEventRepository(
            NamedParameterJdbcOperations jdbcOperations,
            StoredDomainEventRowMapper rowMapper,
            KnownDomainEventTypeResolver knownDomainEventTypeResolver,
            ObjectMapper objectMapper
    ) {
        return new PgStoredDomainEventRepository(
                jdbcOperations,
                rowMapper,
                knownDomainEventTypeResolver,
                objectMapper
        );
    }

    @Bean
    public StoredDomainEventRepository inMemoryStubStoredDomainEventRepository() {
        return new InMemoryStubStoredDomainEventRepository();
    }

    @Bean
    public ConsumptionLogJournalService inMemoryStubConsumptionLogJournalService() {
        return new InMemoryStubConsumptionLogJournalService();
    }

    @Bean
    public DomainEventRecipientsResolverService recipientsResolverService() {
        return new SpringContextDomainEventRecipientsResolverService();
    }

    @Bean
    public ConsumptionLogJournalService consumptionLogJournalService(
            NamedParameterJdbcOperations jdbcOperations
    ) {
        return new PgConsumptionLogJournalService(jdbcOperations);
    }
}
