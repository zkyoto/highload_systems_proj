package ru.itmo.cs.app.interviewing.feedback.infrastructure.in_memory;

import java.util.Optional;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.itmo.cs.app.interviewing.feedback.application.query.FeedbackByInterviewQueryService;
import ru.itmo.cs.app.interviewing.feedback.domain.Feedback;
import ru.itmo.cs.app.interviewing.feedback.domain.FeedbackRepository;
import ru.itmo.cs.app.interviewing.interview.domain.value.InterviewId;

@Service
public class InMemoryStubFeedbackByInterviewQueryService implements FeedbackByInterviewQueryService {
    private final FeedbackRepository feedbackRepository;

    public InMemoryStubFeedbackByInterviewQueryService(
            @Qualifier("inMemoryStubFeedbackRepository") FeedbackRepository feedbackRepository
    ) {
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    public Optional<Feedback> findByInterviewId(InterviewId interviewId) {
        return feedbackRepository.findAll()
                                 .stream()
                                 .filter(feedback -> feedback.getInterviewId().equals(interviewId))
                                 .findAny();
    }
}
