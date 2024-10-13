package ru.itmo.cs.app.interviewing.interview_result.application.query;

import java.util.Optional;

import ru.itmo.cs.app.interviewing.feedback.domain.value.FeedbackId;
import ru.itmo.cs.app.interviewing.interview_result.domain.InterviewResult;

public interface InterviewResultByFeedbackQueryService {
    Optional<InterviewResult> findByFeedbackId(FeedbackId feedbackId);
}
