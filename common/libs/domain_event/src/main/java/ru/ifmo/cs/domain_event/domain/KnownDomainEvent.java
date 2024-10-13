package ru.ifmo.cs.domain_event.domain;

public record KnownDomainEvent(String eventTypeId, Class<?> eventClass) {}
