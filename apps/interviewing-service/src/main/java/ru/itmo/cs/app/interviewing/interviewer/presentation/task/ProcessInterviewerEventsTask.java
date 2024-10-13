package ru.itmo.cs.app.interviewing.interviewer.presentation.task;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ifmo.cs.domain_event.application.service.DomainEventFanoutDelivererService;

@Component
@AllArgsConstructor
public class ProcessInterviewerEventsTask {
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
