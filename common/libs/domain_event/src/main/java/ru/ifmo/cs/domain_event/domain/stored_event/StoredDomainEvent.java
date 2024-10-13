package ru.ifmo.cs.domain_event.domain.stored_event;

import java.time.Instant;
import java.util.UUID;

import lombok.Getter;
import ru.ifmo.cs.domain_event.domain.DomainEvent;

@Getter
public class StoredDomainEvent {

    private final UUID eventId;

    private final String eventTypeId;

    private final Instant occurredOn;

    private final DomainEvent event;

    private Instant deliveredAt;

    private StoredDomainEventStatus status;

    private final Long eventSequenceNumber;

    private final Integer retryCounter;

    public static StoredDomainEvent of(DomainEvent event) {
        return new StoredDomainEvent(
                UUID.randomUUID(),
                event.eventType(),
                event.occurredOn(),
                event,
                null,
                StoredDomainEventStatus.STORED,
                0L,
                0
        );
    }

    public StoredDomainEvent(
            UUID eventId,
            String eventTypeId,
            Instant occurredOn,
            DomainEvent event,
            Instant publishedAt,
            StoredDomainEventStatus status,
            Long eventSequenceNumber,
            Integer retryCounter
    ) {
        this.eventId = eventId;
        this.eventTypeId = eventTypeId;
        this.occurredOn = occurredOn;
        this.event = event;
        this.deliveredAt = publishedAt;
        this.status = status;
        this.eventSequenceNumber = eventSequenceNumber;
        this.retryCounter = retryCounter;
    }

    public void markEventAsDelivered() {
        status = StoredDomainEventStatus.DELIVERED;
        deliveredAt = Instant.now();
    }
}
