package ru.itmo.cs.app.interviewing.feedback.presentation.consumer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ifmo.cs.domain_event.application.service.DomainEventConsumer;
import ru.itmo.cs.app.interviewing.feedback.application.command.CreateFeedbackForInterviewCommand;
import ru.itmo.cs.app.interviewing.interview.domain.event.InterviewScheduledEvent;
import ru.itmo.cs.command_bus.CommandBus;

@Component
@AllArgsConstructor
public class InterviewScheduledEventFeedbackDomainEventConsumer implements DomainEventConsumer<InterviewScheduledEvent> {
    public final CommandBus commandBus;

    @Override
    public void consume(InterviewScheduledEvent event) {
        commandBus.submit(new CreateFeedbackForInterviewCommand(event.interviewId()));
    }

}
