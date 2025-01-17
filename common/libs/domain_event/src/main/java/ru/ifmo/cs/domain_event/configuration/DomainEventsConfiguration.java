package ru.ifmo.cs.domain_event.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import ru.ifmo.cs.domain_event.application.service.ConsumptionLogJournalService;
import ru.ifmo.cs.domain_event.application.service.DomainEventFanoutDelivererService;
import ru.ifmo.cs.domain_event.application.service.DomainEventRecipientsResolverService;
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
import ru.ifmo.cs.domain_event.presentation.job.ProcessDomainEventsTaskScheduler;
import ru.ifmo.cs.domain_event.presentation.task.ProcessDomainEventsTask;
import ru.ifmo.cs.synchronization.configuration.HazelCastConfiguration;

@Configuration
@Import(
        HazelCastConfiguration.class
)
public class DomainEventsConfiguration {
    @Value("${spring.application.name}")
    private String applicationName;

    @Bean
    public DomainEventFanoutDelivererService domainEventFanoutDelivererService(
            StoredDomainEventRepository storedDomainEventRepository,
            DomainEventRecipientsResolverService recipientsResolverService,
            IdempotentDomainEventDelivererService idempotentDomainEventDelivererService
    ) {
        return new DomainEventFanoutDelivererService(
                storedDomainEventRepository,
                recipientsResolverService,
                idempotentDomainEventDelivererService
        );
    }

    @Bean
    public IdempotentDomainEventDelivererService idempotentDomainEventDelivererService(
            SpringContextEventDelivererService springContextEventDelivererService,
            PgConsumptionLogJournalService consumptionLogJournalService,
            KnownDomainEventTypeResolver knownDomainEventTypeResolver
    ) {
        return new IdempotentDomainEventDelivererService(
                springContextEventDelivererService,
                consumptionLogJournalService,
                knownDomainEventTypeResolver
        );
    }

    @Bean
    public StoredDomainEventRowMapper storedDomainEventRowMapper(
            KnownDomainEventTypeResolver knownDomainEventTypeResolver,
            ObjectMapper objectMapper
    ) {
        return new StoredDomainEventRowMapper(knownDomainEventTypeResolver, objectMapper);
    }

    @Bean
    public SpringContextEventDelivererService springContextEventDelivererService(ApplicationContext applicationContext) {
        return new SpringContextEventDelivererService(applicationContext);
    }

    @Primary
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
    public PgConsumptionLogJournalService consumptionLogJournalService(
            NamedParameterJdbcOperations jdbcOperations
    ) {
        return new PgConsumptionLogJournalService(jdbcOperations);
    }

    @Bean
    public ProcessDomainEventsTask processDomainEventsTask(
            HazelcastInstance hazelcastInstance,
            DomainEventFanoutDelivererService domainEventFanoutDelivererService
    ) {
        return new ProcessDomainEventsTask(
                hazelcastInstance,
                applicationName,
                domainEventFanoutDelivererService
        );
    }

    @Bean
    public ProcessDomainEventsTaskScheduler processDomainEventsTaskScheduler(
            ProcessDomainEventsTask processDomainEventsTask
    ) {
        return new ProcessDomainEventsTaskScheduler(processDomainEventsTask);
    }

}
