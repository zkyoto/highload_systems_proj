package ru.ifmo.cs.candidates.presentation.integration_event.consumer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ifmo.cs.contracts.interviewing_service.interviews.integration_event.InterviewScheduledIntegrationEvent;
import ru.ifmo.cs.integration_event.IntegrationEventConsumer;
import ru.ifmo.cs.candidates.application.command.ChageStatusToScheduledCommand;
import ru.ifmo.cs.candidates.domain.value.CandidateId;
import ru.itmo.cs.command_bus.CommandBus;

@Component
@AllArgsConstructor
public class InterviewScheduledIntegrationEventConsumer implements IntegrationEventConsumer<InterviewScheduledIntegrationEvent> {
    public final CommandBus commandBus;

    @Override
    public void consume(InterviewScheduledIntegrationEvent event) {
        CandidateId candidateId = CandidateId.hydrate(event.getCandidateId());
        commandBus.submit(new ChageStatusToScheduledCommand(candidateId));
    }
}

