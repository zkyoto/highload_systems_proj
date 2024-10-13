package ru.itmo.cs.app.interviewing.configuration;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.ifmo.cs.domain_event.application.service.ConsumptionLogJournalService;
import ru.ifmo.cs.domain_event.application.service.DomainEventDelivererService;
import ru.ifmo.cs.domain_event.application.service.DomainEventFanoutDelivererService;
import ru.ifmo.cs.domain_event.application.service.DomainEventRecipientsResolverService;
import ru.ifmo.cs.domain_event.domain.stored_event.StoredDomainEventRepository;
import ru.ifmo.cs.domain_event.infrastructure.event_delivery.IdempotentDomainEventDelivererService;
import ru.ifmo.cs.domain_event.infrastructure.event_delivery.SpringContextDomainEventRecipientsResolverService;
import ru.ifmo.cs.domain_event.infrastructure.event_delivery.SpringContextEventDelivererService;
import ru.ifmo.cs.domain_event.infrastructure.repository.InMemoryStubConsumptionLogJournalService;
import ru.ifmo.cs.domain_event.infrastructure.repository.InMemoryStubStoredDomainEventRepository;

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
            ConsumptionLogJournalService consumptionLogJournalService
    ) {
        return new IdempotentDomainEventDelivererService(
                springContextEventDelivererService,
                consumptionLogJournalService
        );
    }

    @Bean
    public DomainEventDelivererService springContextEventDelivererService(ApplicationContext applicationContext) {
        return new SpringContextEventDelivererService(applicationContext);
    }

    @Bean
    public StoredDomainEventRepository storedDomainEventRepository() {
        return new InMemoryStubStoredDomainEventRepository();
    }

    @Bean
    public DomainEventRecipientsResolverService recipientsResolverService() {
        return new SpringContextDomainEventRecipientsResolverService();
    }

    @Bean
    public ConsumptionLogJournalService consumptionLogJournalService() {
        return new InMemoryStubConsumptionLogJournalService();
    }
}
