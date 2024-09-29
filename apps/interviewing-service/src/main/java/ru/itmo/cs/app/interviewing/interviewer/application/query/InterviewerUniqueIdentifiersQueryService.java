package ru.itmo.cs.app.interviewing.interviewer.application.query;

import ru.ifmo.cs.misc.UserId;
import ru.itmo.cs.app.interviewing.interviewer.application.query.dto.InterviewerUniqueIdentifiersDto;
import ru.itmo.cs.app.interviewing.interviewer.domain.value.InterviewerId;

public interface InterviewerUniqueIdentifiersQueryService {
    InterviewerUniqueIdentifiersDto findBy(UserId userId);
    InterviewerUniqueIdentifiersDto findBy(InterviewerId interviewerId);
}
