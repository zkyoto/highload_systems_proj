package ru.ifmo.cs.feedbacks.application.query;

import java.util.Optional;

import ru.ifmo.cs.feedbacks.domain.Feedback;

public interface FeedbackByInterviewQueryService {
    Optional<Feedback> findByInterviewId(String interviewId);
}
