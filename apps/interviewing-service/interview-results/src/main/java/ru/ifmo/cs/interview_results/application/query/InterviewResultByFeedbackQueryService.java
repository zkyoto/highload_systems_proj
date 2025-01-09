package ru.ifmo.cs.interview_results.application.query;

import java.util.Optional;

import ru.ifmo.cs.interview_results.domain.InterviewResult;

public interface InterviewResultByFeedbackQueryService {
    Optional<InterviewResult> findByFeedbackId(String feedbackId);
}
