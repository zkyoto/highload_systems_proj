package ru.ifmo.cs.feedbacks.infrastructure.in_memory;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.ifmo.cs.feedbacks.application.query.FeedbackPageQueryService;
import ru.ifmo.cs.feedbacks.application.query.dto.FeedbackPage;
import ru.ifmo.cs.feedbacks.domain.Feedback;
import ru.ifmo.cs.feedbacks.domain.FeedbackRepository;

@Service
public class InMemoryStubFeedbackPageQueryService implements FeedbackPageQueryService {
    private final FeedbackRepository feedbackRepository;

    public InMemoryStubFeedbackPageQueryService(
            @Qualifier("inMemoryStubFeedbackRepository") FeedbackRepository feedbackRepository
    ) {
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    public FeedbackPage findFor(int page, int size) {
        List<Feedback> allFeedbacks = feedbackRepository.findAll();
        int from = page * size;
        int to = Math.min(from + size, allFeedbacks.size());
        List<Feedback> content = allFeedbacks.subList(from, to);
        return new FeedbackPage(content,
                                page,
                                size,
                                allFeedbacks.size());
    }

}
