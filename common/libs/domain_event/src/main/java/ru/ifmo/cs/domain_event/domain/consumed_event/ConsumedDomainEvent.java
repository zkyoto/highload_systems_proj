package ru.ifmo.cs.domain_event.domain.consumed_event;

import java.time.Instant;
import java.util.UUID;

import ru.ifmo.cs.domain_event.domain.SubscriberReferenceId;

public record ConsumedDomainEvent(
        UUID eventId,
        String eventTypeId,
        SubscriberReferenceId referenceId,
        Instant consumedAt
) {}
