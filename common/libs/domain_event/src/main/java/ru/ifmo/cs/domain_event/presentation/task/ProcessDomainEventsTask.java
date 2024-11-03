package ru.ifmo.cs.domain_event.presentation.task;

import lombok.AllArgsConstructor;
import ru.ifmo.cs.domain_event.application.service.DomainEventFanoutDelivererService;

@AllArgsConstructor
public class ProcessDomainEventsTask {
    private final DomainEventFanoutDelivererService domainEventFanoutDelivererService;

    public void execute(){
        while (true) {
            boolean hasNext = domainEventFanoutDelivererService.deliverNext();

            if (!hasNext) {
                return;
            }
        }
    }
}
