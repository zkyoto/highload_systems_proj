package ru.ifmo.cs.integration_event.event_delivery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.ifmo.cs.integration_event.IntegrationEvent;

public class KnownIntegrationEventTypeResolver {

    private final Map<String, Class<? extends IntegrationEvent>> eventTypeToClassMap = new HashMap<>();
    private final Map<Class<? extends IntegrationEvent>, String> eventClassToTypeMap = new HashMap<>();

    public KnownIntegrationEventTypeResolver(List<KnownIntegrationEventProvider> knownEventProviders) {
        for (KnownIntegrationEventProvider provider : knownEventProviders) {
            for (KnownIntegrationEvent knownEvent : provider.provide()) {
                eventTypeToClassMap.put(knownEvent.eventTypeId(), knownEvent.eventClass());
                eventClassToTypeMap.put(knownEvent.eventClass(), knownEvent.eventTypeId());
            }
        }
    }

    public String typeByClass(Class<? extends IntegrationEvent> className) {
        return eventClassToTypeMap.get(className);
    }

    public Class<? extends IntegrationEvent> classByType(String typeId) {
        return eventTypeToClassMap.get(typeId);
    }
}
