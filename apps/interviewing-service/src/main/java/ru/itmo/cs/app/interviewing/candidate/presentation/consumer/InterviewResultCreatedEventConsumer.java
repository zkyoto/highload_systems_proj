package ru.itmo.cs.app.interviewing.candidate.presentation.consumer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ifmo.cs.domain_event.application.service.DomainEventConsumer;
import ru.itmo.cs.app.interviewing.candidate.application.command.ChageStatusToProcessedCommand;
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
public class InterviewResultCreatedEventConsumer implements DomainEventConsumer<InterviewResultCreatedEvent> {
    private final InterviewResultRepository interviewResultRepository;
    private final FeedbackRepository feedbackRepository;
    private final InterviewRepository interviewRepository;
    private final CommandBus commandBus;

    @Override
    public void consume(InterviewResultCreatedEvent event) {
        InterviewResult interviewResult = interviewResultRepository.findById(event.interviewResultId());
        Feedback feedback = feedbackRepository.findById(interviewResult.getFeedbackId());
        Interview interview = interviewRepository.findById(feedback.getInterviewId());
        //то, что выше надо вынести в отделньый query service, но сейчас нет времени на это

        commandBus.submit(new ChageStatusToProcessedCommand(interview.getCandidateId()));
    }

}
