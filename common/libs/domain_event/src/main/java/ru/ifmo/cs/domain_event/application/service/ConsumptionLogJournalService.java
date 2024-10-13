package ru.ifmo.cs.domain_event.application.service;

import ru.ifmo.cs.domain_event.domain.consumed_event.ConsumedDomainEvent;

public interface ConsumptionLogJournalService {

    boolean acquireFor(ConsumedDomainEvent consumedDomainEvent);

}
