package ru.ifmo.cs.interviews.application.query;

import ru.ifmo.cs.interviews.application.query.dto.InterviewsByInterviewerDto;

public interface InterviewsByInterviewerQueryService {
    InterviewsByInterviewerDto findFor(String interviewerId);
}
