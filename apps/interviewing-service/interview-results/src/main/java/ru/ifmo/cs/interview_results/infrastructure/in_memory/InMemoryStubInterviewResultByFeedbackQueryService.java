package ru.ifmo.cs.interview_results.infrastructure.in_memory;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.ifmo.cs.interview_results.application.query.InterviewResultByFeedbackQueryService;
import ru.ifmo.cs.interview_results.domain.InterviewResult;
import ru.ifmo.cs.interview_results.domain.InterviewResultRepository;

@Service
public class InMemoryStubInterviewResultByFeedbackQueryService implements InterviewResultByFeedbackQueryService {
    private final InterviewResultRepository interviewResultRepository;

    public InMemoryStubInterviewResultByFeedbackQueryService(
            @Qualifier("inMemoryStubInterviewResultRepository") InterviewResultRepository interviewResultRepository
    ) {
        this.interviewResultRepository = interviewResultRepository;
    }

    @Override
    public Optional<InterviewResult> findByFeedbackId(String feedbackId) {
        return interviewResultRepository.findAll()
                                        .stream()
                                        .filter(interviewResult -> interviewResult.getFeedbackId().equals(feedbackId))
                                        .findAny();
    }

}
