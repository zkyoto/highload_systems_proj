package ru.itmo.cs.app.interviewing.feedback.infrastructure.in_memory;

import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.cs.app.interviewing.feedback.application.query.FeedbackPageQueryService;
import ru.itmo.cs.app.interviewing.feedback.application.query.dto.FeedbackPage;
import ru.itmo.cs.app.interviewing.feedback.domain.Feedback;
import ru.itmo.cs.app.interviewing.feedback.domain.FeedbackRepository;

@Service
@AllArgsConstructor
public class InMemoryStubFeedbackPageQueryService implements FeedbackPageQueryService {
    private final FeedbackRepository feedbackRepository;

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
