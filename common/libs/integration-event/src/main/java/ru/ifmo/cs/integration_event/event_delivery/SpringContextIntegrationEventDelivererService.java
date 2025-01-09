package ru.ifmo.cs.integration_event.event_delivery;

import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;
import ru.ifmo.cs.integration_event.IntegrationEvent;
import ru.ifmo.cs.integration_event.IntegrationEventConsumer;

public class SpringContextIntegrationEventDelivererService implements IntegrationEventDelivererService {

    private final ApplicationContext applicationContext;

    public SpringContextIntegrationEventDelivererService(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Transactional
    public void deliver(
            IntegrationEventSubscriberReferenceId referenceId,
            IntegrationEvent integrationEvent
    ) {
        IntegrationEventConsumer<IntegrationEvent> integrationEventConsumer =
                (IntegrationEventConsumer<IntegrationEvent>) applicationContext.getBean(referenceId.id());

        integrationEventConsumer.consume(integrationEvent);
    }
}
