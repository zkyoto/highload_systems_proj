package ru.ifmo.cs.domain_event.infrastructure.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.ifmo.cs.domain_event.application.service.KnownEventProvider;
import ru.ifmo.cs.domain_event.domain.KnownDomainEvent;

public class KnownDomainEventTypeResolver {

    private final Map<String, Class<?>> eventTypeToClassMap = new HashMap<>();
    private final Map<Class<?>, String> eventClassToTypeMap = new HashMap<>();

    public KnownDomainEventTypeResolver(List<KnownEventProvider> knownEventProviders) {
        for (KnownEventProvider provider : knownEventProviders) {
            for (KnownDomainEvent knownDomainEvent : provider.provide()) {
                eventTypeToClassMap.put(knownDomainEvent.eventTypeId(), knownDomainEvent.eventClass());
                eventClassToTypeMap.put(knownDomainEvent.eventClass(), knownDomainEvent.eventTypeId());
            }
        }
    }

    public String typeByClass(Class<?> className) {
        return eventClassToTypeMap.get(className);
    }

    public Class<?> classByType(String typeId) {
        return eventTypeToClassMap.get(typeId);
    }
}
