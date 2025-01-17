package ru.ifmo.cs.domain_event.presentation.task;

import com.hazelcast.core.HazelcastInstance;
import ru.ifmo.cs.domain_event.application.service.DomainEventFanoutDelivererService;
import ru.ifmo.cs.synchronization.lock.LockedBetweenInstancesAbstractTask;

public class ProcessDomainEventsTask extends LockedBetweenInstancesAbstractTask {
    private final DomainEventFanoutDelivererService domainEventFanoutDelivererService;

    public ProcessDomainEventsTask(
            HazelcastInstance hazelcastInstance,
            String lockSlug,
            DomainEventFanoutDelivererService domainEventFanoutDelivererService
    ) {
        super(hazelcastInstance, lockSlug);
        this.domainEventFanoutDelivererService = domainEventFanoutDelivererService;
    }

    protected void execute() {
        while (true) {
            boolean hasNext = domainEventFanoutDelivererService.deliverNext();
            if (!hasNext) {
                return;
            }
        }
    }
}
