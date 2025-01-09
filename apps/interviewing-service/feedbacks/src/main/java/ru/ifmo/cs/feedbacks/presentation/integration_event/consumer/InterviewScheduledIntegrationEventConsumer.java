package ru.ifmo.cs.feedbacks.presentation.integration_event.consumer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ifmo.cs.contracts.interviewing_service.interviews.integration_event.InterviewScheduledIntegrationEvent;
import ru.ifmo.cs.integration_event.IntegrationEventConsumer;
import ru.ifmo.cs.feedbacks.application.command.CreateFeedbackForInterviewCommand;
import ru.itmo.cs.command_bus.CommandBus;

@Component
@AllArgsConstructor
public class InterviewScheduledIntegrationEventConsumer
        implements IntegrationEventConsumer<InterviewScheduledIntegrationEvent> {
    public final CommandBus commandBus;

    @Override
    public void consume(InterviewScheduledIntegrationEvent event) {
        commandBus.submit(new CreateFeedbackForInterviewCommand(event.getInterviewId()));
    }
}
