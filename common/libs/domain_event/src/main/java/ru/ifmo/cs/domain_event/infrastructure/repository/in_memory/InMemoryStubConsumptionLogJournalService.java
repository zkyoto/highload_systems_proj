package ru.ifmo.cs.domain_event.infrastructure.repository.in_memory;

import java.util.LinkedList;
import java.util.List;

import ru.ifmo.cs.domain_event.application.service.ConsumptionLogJournalService;
import ru.ifmo.cs.domain_event.domain.consumed_event.ConsumedDomainEvent;

public class InMemoryStubConsumptionLogJournalService implements ConsumptionLogJournalService {
    private final List<ConsumedDomainEvent> stubRepository = new LinkedList<>();

    @Override
    public boolean acquireFor(ConsumedDomainEvent consumedDomainEvent) {
        boolean domainEventAlreadyConsumed =
                stubRepository.stream()
                              .anyMatch(event -> event.eventId().equals(consumedDomainEvent.eventId()));
        if (domainEventAlreadyConsumed) {
            return true;
        } else {
            stubRepository.add(consumedDomainEvent);
            return false;
        }
    }
}
