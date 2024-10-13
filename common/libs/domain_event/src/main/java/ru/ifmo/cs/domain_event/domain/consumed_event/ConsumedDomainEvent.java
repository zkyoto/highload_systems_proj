package ru.ifmo.cs.domain_event.domain.consumed_event;

import java.util.UUID;

public record ConsumedDomainEvent(UUID eventId, String beanId) {}
