package ru.ifmo.cs.domain_event.infrastructure.event_delivery;

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
import ru.ifmo.cs.domain_event.application.service.DomainEventConsumer;
import ru.ifmo.cs.domain_event.application.service.DomainEventRecipientsResolverService;
import ru.ifmo.cs.domain_event.domain.DomainEvent;
import ru.ifmo.cs.domain_event.domain.SubscriberReferenceId;

public class SpringContextDomainEventRecipientsResolverService implements DomainEventRecipientsResolverService,
        ApplicationListener<ContextRefreshedEvent> {
    private Map<Class<?>, List<SubscriberReferenceId>> domainEventSubscribers;

    public SpringContextDomainEventRecipientsResolverService() {

    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext context = event.getApplicationContext();
        this.domainEventSubscribers = Arrays.stream(context.getBeanNamesForType(DomainEventConsumer.class))
                .map(beanId -> getEventSubscriberPair(beanId, context))
                .collect(Collectors.groupingBy(
                        DomainEventTypeSubscriberReferenceIdPair::eventType,
                        Collectors.mapping(DomainEventTypeSubscriberReferenceIdPair::subscriber, Collectors.toList())
                ));
    }

    public List<SubscriberReferenceId> subscribersFor(Class<? extends DomainEvent> className) {
        if (domainEventSubscribers.containsKey(className)) {
            return domainEventSubscribers.get(className);
        }
        return List.of();
    }

    private DomainEventTypeSubscriberReferenceIdPair getEventSubscriberPair(
            String consumerBeanId,
            ApplicationContext context
    ) {
        Object consumerBean = context.getBean(consumerBeanId);
        Class<?> consumerBeanType = AopUtils.getTargetClass(consumerBean);

        Class<?> domainEventType = ResolvableType.forClass(consumerBeanType)
                .as(DomainEventConsumer.class)
                .getGeneric(0)
                .resolve();

        return new DomainEventTypeSubscriberReferenceIdPair(
                domainEventType,
                new SubscriberReferenceId(consumerBeanId)
        );
    }

    @Override
    public Map<Class<?>, List<SubscriberReferenceId>> registeredConsumers() {
        Map<Class<?>, List<SubscriberReferenceId>> shallowCopy = new HashMap<>(domainEventSubscribers);
        return shallowCopy;
    }

    private record DomainEventTypeSubscriberReferenceIdPair(Class<?> eventType, SubscriberReferenceId subscriber) {
    }
}
