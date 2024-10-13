package ru.itmo.cs.app.interviewing.feedback.infrastructure.in_memory;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.cs.app.interviewing.feedback.application.query.FeedbacksPendingResultQueryService;
import ru.itmo.cs.app.interviewing.feedback.domain.Feedback;
import ru.itmo.cs.app.interviewing.feedback.domain.FeedbackRepository;
import ru.itmo.cs.app.interviewing.feedback.domain.value.FeedbackId;
import ru.itmo.cs.app.interviewing.feedback.domain.value.FeedbackStatus;
import ru.itmo.cs.app.interviewing.interview_result.domain.InterviewResult;
import ru.itmo.cs.app.interviewing.interview_result.domain.InterviewResultRepository;

@Service
@AllArgsConstructor
public class InMemoryStubFeedbacksPendingResultQueryService implements FeedbacksPendingResultQueryService {
    private final FeedbackRepository feedbackRepository;
    private final InterviewResultRepository interviewResultRepository;

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
