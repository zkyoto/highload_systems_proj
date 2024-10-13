package ru.itmo.cs.app.interviewing.interview_result.infrastructure.in_memory;

import java.util.Optional;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.cs.app.interviewing.feedback.domain.value.FeedbackId;
import ru.itmo.cs.app.interviewing.interview_result.application.query.InterviewResultByFeedbackQueryService;
import ru.itmo.cs.app.interviewing.interview_result.domain.InterviewResult;
import ru.itmo.cs.app.interviewing.interview_result.domain.InterviewResultRepository;

@Service
@AllArgsConstructor
public class InMemoryStubInterviewResultByFeedbackQueryService implements InterviewResultByFeedbackQueryService {
    private final InterviewResultRepository interviewResultRepository;

    @Override
    public Optional<InterviewResult> findByFeedbackId(FeedbackId feedbackId) {
        return interviewResultRepository.findAll()
                                        .stream()
                                        .filter(interviewResult -> interviewResult.getFeedbackId().equals(feedbackId))
                                        .findAny();
    }

}
