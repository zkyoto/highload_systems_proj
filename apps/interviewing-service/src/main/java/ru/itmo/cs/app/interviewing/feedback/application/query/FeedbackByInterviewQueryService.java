package ru.itmo.cs.app.interviewing.feedback.application.query;

import java.util.Optional;

import ru.itmo.cs.app.interviewing.feedback.domain.Feedback;
import ru.itmo.cs.app.interviewing.interview.domain.value.InterviewId;

public interface FeedbackByInterviewQueryService {
    Optional<Feedback> findByInterviewId(InterviewId interviewId);
}
