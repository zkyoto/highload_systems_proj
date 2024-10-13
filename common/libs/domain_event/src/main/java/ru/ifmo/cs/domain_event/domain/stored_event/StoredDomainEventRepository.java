package ru.ifmo.cs.domain_event.domain.stored_event;

public interface StoredDomainEventRepository {

    StoredDomainEvent nextWaitedForDelivery();

    void save(StoredDomainEvent event);
}
