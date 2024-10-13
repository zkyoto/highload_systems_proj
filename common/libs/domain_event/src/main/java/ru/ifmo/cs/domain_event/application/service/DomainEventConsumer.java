package ru.ifmo.cs.domain_event.application.service;

import ru.ifmo.cs.domain_event.domain.DomainEvent;

public interface DomainEventConsumer<T extends DomainEvent> {

    void consume(T event);
}
