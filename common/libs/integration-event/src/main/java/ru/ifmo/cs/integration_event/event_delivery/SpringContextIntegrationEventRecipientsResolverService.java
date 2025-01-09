package ru.ifmo.cs.integration_event.event_delivery;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.aop.support.AopUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.ResolvableType;
import ru.ifmo.cs.integration_event.IntegrationEvent;
import ru.ifmo.cs.integration_event.IntegrationEventConsumer;

public class SpringContextIntegrationEventRecipientsResolverService implements IntegrationEventRecipientsResolverService,
        ApplicationListener<ContextRefreshedEvent> {
    private Map<Class<?>, List<IntegrationEventSubscriberReferenceId>> integrationEventSubscribers;

    public SpringContextIntegrationEventRecipientsResolverService() {

    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext context = event.getApplicationContext();
        this.integrationEventSubscribers = Arrays.stream(context.getBeanNamesForType(IntegrationEventConsumer.class))
                .map(beanId -> getEventSubscriberPair(beanId, context))
                .collect(Collectors.groupingBy(
                        IntegrationEventTypeSubscriberReferenceIdPair::eventType,
                        Collectors.mapping(IntegrationEventTypeSubscriberReferenceIdPair::subscriber, Collectors.toList())
                ));
    }

    public List<IntegrationEventSubscriberReferenceId> subscribersFor(Class<? extends IntegrationEvent> className) {
        if (integrationEventSubscribers.containsKey(className)) {
            return integrationEventSubscribers.get(className);
        }
        return List.of();
    }

    private IntegrationEventTypeSubscriberReferenceIdPair getEventSubscriberPair(
            String consumerBeanId,
            ApplicationContext context
    ) {
        Object consumerBean = context.getBean(consumerBeanId);
        Class<?> consumerBeanType = AopUtils.getTargetClass(consumerBean);

        Class<?> integrationEventType = ResolvableType.forClass(consumerBeanType)
                .as(IntegrationEventConsumer.class)
                .getGeneric(0)
                .resolve();

        return new IntegrationEventTypeSubscriberReferenceIdPair(
                integrationEventType,
                new IntegrationEventSubscriberReferenceId(consumerBeanId)
        );
    }

    @Override
    public Map<Class<?>, List<IntegrationEventSubscriberReferenceId>> registeredConsumers() {
        Map<Class<?>, List<IntegrationEventSubscriberReferenceId>> shallowCopy = new HashMap<>(integrationEventSubscribers);
        return shallowCopy;
    }

    private record IntegrationEventTypeSubscriberReferenceIdPair(Class<?> eventType, IntegrationEventSubscriberReferenceId subscriber) {
    }
}
