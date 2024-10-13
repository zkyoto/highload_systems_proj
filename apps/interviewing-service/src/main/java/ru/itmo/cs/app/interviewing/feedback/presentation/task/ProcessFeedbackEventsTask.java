package ru.itmo.cs.app.interviewing.feedback.presentation.task;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ifmo.cs.domain_event.application.service.DomainEventFanoutDelivererService;

@Component
@AllArgsConstructor
public class ProcessFeedbackEventsTask {
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
