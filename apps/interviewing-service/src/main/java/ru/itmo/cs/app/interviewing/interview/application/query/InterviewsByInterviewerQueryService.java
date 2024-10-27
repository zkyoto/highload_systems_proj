package ru.itmo.cs.app.interviewing.interview.application.query;

import ru.itmo.cs.app.interviewing.interview.application.query.dto.InterviewsByInterviewerDto;
import ru.itmo.cs.app.interviewing.interviewer.domain.value.InterviewerId;

public interface InterviewsByInterviewerQueryService {
    InterviewsByInterviewerDto findBy(InterviewerId interviewerId);
}
