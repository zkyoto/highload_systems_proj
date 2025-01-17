package ru.ifmo.cs.candidates.presentation.integration_event.consumer;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.ifmo.cs.contracts.interviewing_service.candidates.integration_event.CandidateStatusTransferToWaitingForInterviewFailIntegrationEvent;
import ru.ifmo.cs.contracts.interviewing_service.interviews.integration_event.InterviewScheduledIntegrationEvent;
import ru.ifmo.cs.integration_event.IntegrationEventConsumer;
import ru.ifmo.cs.candidates.application.command.ChageStatusToScheduledCommand;
import ru.ifmo.cs.candidates.domain.value.CandidateId;
import ru.ifmo.cs.integration_event.IntegrationEventPublisher;
import ru.itmo.cs.command_bus.CommandBus;

@Slf4j
@Component
@AllArgsConstructor
public class InterviewScheduledIntegrationEventConsumer
        implements IntegrationEventConsumer<InterviewScheduledIntegrationEvent> {
    private final IntegrationEventPublisher integrationEventPublisher;
    private final CommandBus commandBus;

    @Override
    public void consume(InterviewScheduledIntegrationEvent event) {
        try {
            CandidateId candidateId = CandidateId.hydrate(event.getCandidateId());
            commandBus.submit(new ChageStatusToScheduledCommand(candidateId));
        } catch (Exception exception) {
            log.error(exception.getMessage(), exception);
            integrationEventPublisher.publish(
                    CandidateStatusTransferToWaitingForInterviewFailIntegrationEvent.createFrom(event)
            );
        }
    }
}

