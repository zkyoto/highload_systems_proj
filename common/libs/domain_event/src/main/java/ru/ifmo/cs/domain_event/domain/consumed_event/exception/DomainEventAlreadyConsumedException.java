package ru.ifmo.cs.domain_event.domain.consumed_event.exception;

import java.util.UUID;

import ru.ifmo.cs.domain_event.domain.DomainEvent;

public class DomainEventAlreadyConsumedException extends Exception {

    public DomainEventAlreadyConsumedException(Class<? extends DomainEvent> clazz, UUID uuid, String beanId) {
        super(String.format(
                "Domain event #%s of class '%s' has been already consumed by bean '%s'",
                uuid.toString(),
                clazz.getSimpleName(),
                beanId));
    }
}
