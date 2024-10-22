package ru.itmo.cs.app.interviewing.feedback.infrastructure.in_memory;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.itmo.cs.app.interviewing.feedback.application.query.FeedbacksPendingResultQueryService;
import ru.itmo.cs.app.interviewing.feedback.domain.Feedback;
import ru.itmo.cs.app.interviewing.feedback.domain.FeedbackRepository;
import ru.itmo.cs.app.interviewing.feedback.domain.value.FeedbackId;
import ru.itmo.cs.app.interviewing.feedback.domain.value.FeedbackStatus;
import ru.itmo.cs.app.interviewing.interview_result.domain.InterviewResult;
import ru.itmo.cs.app.interviewing.interview_result.domain.InterviewResultRepository;

@Service
public class InMemoryStubFeedbacksPendingResultQueryService implements FeedbacksPendingResultQueryService {
    private final FeedbackRepository feedbackRepository;
    private final InterviewResultRepository interviewResultRepository;

    public InMemoryStubFeedbacksPendingResultQueryService(
            @Qualifier("inMemoryStubFeedbackRepository") FeedbackRepository feedbackRepository,
            @Qualifier("inMemoryStubInterviewResultRepository") InterviewResultRepository interviewResultRepository
    ) {
        this.feedbackRepository = feedbackRepository;
        this.interviewResultRepository = interviewResultRepository;
    }

    @Override
    public List<Feedback> findAll() {
        Set<FeedbackId> feedbacksWithResult = interviewResultRepository.findAll()
                                                                       .stream()
                                                                       .map(InterviewResult::getFeedbackId)
                                                                       .collect(Collectors.toSet());
        return feedbackRepository.findAll()
                                 .stream()
                                 .filter(feedback -> feedback.getStatus().equals(FeedbackStatus.SUBMITTED))
                                 .filter(feedback -> !feedbacksWithResult.contains(feedback.getId()))
                                 .toList();
    }
}
