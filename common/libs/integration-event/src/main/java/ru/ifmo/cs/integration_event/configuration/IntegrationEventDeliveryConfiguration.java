package ru.ifmo.cs.integration_event.configuration;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.ifmo.cs.integration_event.event_delivery.IntegrationEventDelivererService;
import ru.ifmo.cs.integration_event.event_delivery.IntegrationEventFanoutDelivererService;
import ru.ifmo.cs.integration_event.event_delivery.IntegrationEventRecipientsResolverService;
import ru.ifmo.cs.integration_event.event_delivery.SpringContextIntegrationEventDelivererService;
import ru.ifmo.cs.integration_event.event_delivery.SpringContextIntegrationEventRecipientsResolverService;

@Configuration
public class IntegrationEventDeliveryConfiguration {
    @Bean
    public IntegrationEventDelivererService integrationEventDelivererService(
            ApplicationContext applicationContext
    ) {
        return new SpringContextIntegrationEventDelivererService(applicationContext);
    }

    @Bean
    public IntegrationEventFanoutDelivererService integrationEventFanoutDelivererService(
            IntegrationEventRecipientsResolverService integrationEventRecipientsResolverService,
            IntegrationEventDelivererService integrationEventDelivererService
    ) {
        return new IntegrationEventFanoutDelivererService(
                integrationEventRecipientsResolverService,
                integrationEventDelivererService
        );
    }

    @Bean
    public IntegrationEventRecipientsResolverService integrationEventRecipientsResolverService() {
        return new SpringContextIntegrationEventRecipientsResolverService();
    }

}
