package ru.itmo.cs.app.interviewing.interview_result.domain;

import java.util.List;

import ru.itmo.cs.app.interviewing.interview_result.domain.value.InterviewResultId;

public interface InterviewResultRepository {
    InterviewResult findById(InterviewResultId interviewResultId);
    List<InterviewResult> findAll();
    void save(InterviewResult interviewResult);
}
