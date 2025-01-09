package ru.ifmo.cs.interview_results.domain;

import java.util.List;

import ru.ifmo.cs.interview_results.domain.InterviewResult;
import ru.ifmo.cs.interview_results.domain.value.InterviewResultId;

public interface InterviewResultRepository {
    InterviewResult findById(InterviewResultId interviewResultId);
    List<InterviewResult> findAll();
    void save(InterviewResult interviewResult);
}
