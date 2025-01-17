package ru.ifmo.cs.interviews.presentation.integration_event.consumer;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.ifmo.cs.contracts.interviewing_service.candidates.integration_event.CandidateStatusTransferToWaitingForInterviewFailIntegrationEvent;
import ru.ifmo.cs.integration_event.IntegrationEventConsumer;
import ru.ifmo.cs.interviews.application.command.CancelInterviewCommand;
import ru.ifmo.cs.interviews.application.command.FailInterviewCommand;
import ru.ifmo.cs.interviews.domain.value.InterviewId;
import ru.itmo.cs.command_bus.CommandBus;

@Slf4j
@Component
@AllArgsConstructor
public class CandidateStatusTransferToWaitingForInterviewFailIntegrationEventConsumer
        implements IntegrationEventConsumer<CandidateStatusTransferToWaitingForInterviewFailIntegrationEvent> {
    private final CommandBus commandBus;

    @Override
    public void consume(CandidateStatusTransferToWaitingForInterviewFailIntegrationEvent event) {
        log.info("Cancelling interview {} because candidate {} status transfer to wait for interview fail",
                event.getInterviewId(), event.getCandidateId());
        commandBus.submit(new FailInterviewCommand(InterviewId.hydrate(event.getInterviewId())));
    }

}
