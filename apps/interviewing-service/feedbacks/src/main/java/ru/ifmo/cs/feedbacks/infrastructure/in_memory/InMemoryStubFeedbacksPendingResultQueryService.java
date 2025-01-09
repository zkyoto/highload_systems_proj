package ru.ifmo.cs.feedbacks.infrastructure.in_memory;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.ifmo.cs.feedbacks.application.query.FeedbacksPendingResultQueryService;
import ru.ifmo.cs.feedbacks.domain.Feedback;
import ru.ifmo.cs.feedbacks.domain.FeedbackRepository;

@Service
public class InMemoryStubFeedbacksPendingResultQueryService implements FeedbacksPendingResultQueryService {
    private final FeedbackRepository feedbackRepository;

    public InMemoryStubFeedbacksPendingResultQueryService(
            @Qualifier("inMemoryStubFeedbackRepository") FeedbackRepository feedbackRepository
    ) {
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    public List<Feedback> findAll() {
        return List.of();
    }
}
