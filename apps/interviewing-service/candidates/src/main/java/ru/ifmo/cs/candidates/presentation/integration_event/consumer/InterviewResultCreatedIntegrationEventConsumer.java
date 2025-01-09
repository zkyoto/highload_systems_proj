package ru.ifmo.cs.candidates.presentation.integration_event.consumer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ifmo.cs.integration_event.IntegrationEventConsumer;
import ru.ifmo.cs.candidates.application.command.ChageStatusToProcessedCommand;
import ru.ifmo.cs.candidates.application.query.CandidateByInterviewResultQueryService;
import ru.ifmo.cs.candidates.domain.Candidate;
import ru.ifmo.cs.contracts.interviewing_service.interview_results.integration_event.InterviewResultCreatedIntegrationEvent;
import ru.itmo.cs.command_bus.CommandBus;

@Component
@AllArgsConstructor
public class InterviewResultCreatedIntegrationEventConsumer implements IntegrationEventConsumer<InterviewResultCreatedIntegrationEvent> {
    private final CandidateByInterviewResultQueryService candidateByInterviewResultQueryService;
    private final CommandBus commandBus;

    @Override
    public void consume(InterviewResultCreatedIntegrationEvent event) {
//        Candidate candidate = candidateByInterviewResultQueryService.findFor(event.getInterviewResultId());
//        commandBus.submit(new ChageStatusToProcessedCommand(candidate.getId()));
    }
}
