package ru.ifmo.cs.candidates.presentation.integration_event.consumer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ifmo.cs.contracts.interviewing_service.interviews.integration_event.InterviewCancelledIntegrationEvent;
import ru.ifmo.cs.integration_event.IntegrationEventConsumer;
import ru.ifmo.cs.candidates.application.command.ChageStatusToCancelledCommand;
import ru.ifmo.cs.candidates.domain.value.CandidateId;
import ru.itmo.cs.command_bus.CommandBus;

@Component
@AllArgsConstructor
public class InterviewCancelledIntegrationEventConsumer implements IntegrationEventConsumer<InterviewCancelledIntegrationEvent> {
    private final CommandBus commandBus;

    @Override
    public void consume(InterviewCancelledIntegrationEvent event) {
        commandBus.submit(new ChageStatusToCancelledCommand(CandidateId.hydrate(event.getCandidateId())));
    }
}
