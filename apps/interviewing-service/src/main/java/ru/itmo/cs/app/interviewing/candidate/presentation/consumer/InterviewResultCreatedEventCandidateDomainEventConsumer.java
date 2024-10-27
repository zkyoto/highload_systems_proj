package ru.itmo.cs.app.interviewing.candidate.presentation.consumer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ifmo.cs.domain_event.application.service.DomainEventConsumer;
import ru.itmo.cs.app.interviewing.candidate.application.command.ChageStatusToProcessedCommand;
import ru.itmo.cs.app.interviewing.candidate.application.query.CandidateByInterviewResultQueryService;
import ru.itmo.cs.app.interviewing.candidate.domain.Candidate;
import ru.itmo.cs.app.interviewing.feedback.domain.Feedback;
import ru.itmo.cs.app.interviewing.feedback.domain.FeedbackRepository;
import ru.itmo.cs.app.interviewing.interview.domain.Interview;
import ru.itmo.cs.app.interviewing.interview.domain.InterviewRepository;
import ru.itmo.cs.app.interviewing.interview_result.domain.InterviewResult;
import ru.itmo.cs.app.interviewing.interview_result.domain.InterviewResultRepository;
import ru.itmo.cs.app.interviewing.interview_result.domain.event.InterviewResultCreatedEvent;
import ru.itmo.cs.command_bus.CommandBus;

@Component
@AllArgsConstructor
public class InterviewResultCreatedEventCandidateDomainEventConsumer implements DomainEventConsumer<InterviewResultCreatedEvent> {
    private final CandidateByInterviewResultQueryService candidateByInterviewResultQueryService;
    private final CommandBus commandBus;

    @Override
    public void consume(InterviewResultCreatedEvent event) {
        Candidate candidate = candidateByInterviewResultQueryService.findBy(event.interviewResultId());
        commandBus.submit(new ChageStatusToProcessedCommand(candidate.getId()));
    }

}
