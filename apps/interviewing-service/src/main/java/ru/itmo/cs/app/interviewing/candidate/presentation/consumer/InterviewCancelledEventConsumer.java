package ru.itmo.cs.app.interviewing.candidate.presentation.consumer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ifmo.cs.domain_event.application.service.DomainEventConsumer;
import ru.itmo.cs.app.interviewing.candidate.application.command.ChageStatusToCancelledCommand;
import ru.itmo.cs.app.interviewing.interview.domain.event.InterviewCancelledEvent;
import ru.itmo.cs.command_bus.CommandBus;

@Component
@AllArgsConstructor
public class InterviewCancelledEventConsumer implements DomainEventConsumer<InterviewCancelledEvent> {
    private final CommandBus commandBus;

    @Override
    public void consume(InterviewCancelledEvent event) {
        commandBus.submit(new ChageStatusToCancelledCommand(event.candidateId()));
    }

}
