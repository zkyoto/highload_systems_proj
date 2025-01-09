package ru.ifmo.cs.feedbacks.infrastructure.in_memory;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.ifmo.cs.feedbacks.application.query.FeedbackByInterviewQueryService;
import ru.ifmo.cs.feedbacks.domain.Feedback;
import ru.ifmo.cs.feedbacks.domain.FeedbackRepository;

@Service
public class InMemoryStubFeedbackByInterviewQueryService implements FeedbackByInterviewQueryService {
    private final FeedbackRepository feedbackRepository;

    public InMemoryStubFeedbackByInterviewQueryService(
            @Qualifier("inMemoryStubFeedbackRepository") FeedbackRepository feedbackRepository
    ) {
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    public Optional<Feedback> findByInterviewId(String interviewId) {
        return feedbackRepository.findAll()
                                 .stream()
                                 .filter(feedback -> feedback.getInterviewId().equals(interviewId))
                                 .findAny();
    }
}
