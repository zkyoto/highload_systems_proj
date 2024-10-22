package ru.ifmo.cs.domain_event.infrastructure.repository.in_memory;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Repository;
import ru.ifmo.cs.domain_event.domain.stored_event.StoredDomainEvent;
import ru.ifmo.cs.domain_event.domain.stored_event.StoredDomainEventRepository;
import ru.ifmo.cs.domain_event.domain.stored_event.StoredDomainEventStatus;

public class InMemoryStubStoredDomainEventRepository implements StoredDomainEventRepository {
    private final List<StoredDomainEvent> stubRepository = new LinkedList<>();

    @Override
    public StoredDomainEvent nextWaitedForDelivery() {
        return stubRepository.stream()
                             .filter(event -> event.getStatus()
                             .equals(StoredDomainEventStatus.STORED))
                             .findFirst()
                             .orElse(null);
    }

    @Override
    public void save(StoredDomainEvent event) {
        if (event.getStatus().equals(StoredDomainEventStatus.DELIVERED)) {
            update(event);
        } else {
            insert(event);
        }
    }

    private void insert(StoredDomainEvent event) {
        stubRepository.add(event);
    }

    private void update(StoredDomainEvent event) {
        boolean removed = stubRepository.removeIf(e -> e.getEventId().equals(event.getEventId()));
        if (!removed) {
            throw new IllegalStateException();
        }
        stubRepository.add(event);
    }
}
