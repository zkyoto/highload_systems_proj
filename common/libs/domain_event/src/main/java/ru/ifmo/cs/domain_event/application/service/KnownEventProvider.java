package ru.ifmo.cs.domain_event.application.service;

import java.util.List;

import ru.ifmo.cs.domain_event.domain.KnownDomainEvent;

public interface KnownEventProvider {

    List<KnownDomainEvent> provide();
}
