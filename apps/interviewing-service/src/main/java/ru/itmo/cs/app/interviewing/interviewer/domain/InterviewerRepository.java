package ru.itmo.cs.app.interviewing.interviewer.domain;

import ru.itmo.cs.app.interviewing.interviewer.domain.value.InterviewerId;

public interface InterviewerRepository {
    Interviewer findById(InterviewerId id);
    void save(Interviewer interviewer);
}
