package ru.ifmo.cs.interviewers.application.query;

import ru.ifmo.cs.misc.UserId;
import ru.ifmo.cs.interviewers.application.query.dto.InterviewerUniqueIdentifiersDto;
import ru.ifmo.cs.interviewers.domain.value.InterviewerId;

public interface InterviewerUniqueIdentifiersQueryService {
    InterviewerUniqueIdentifiersDto findBy(UserId userId);
    InterviewerUniqueIdentifiersDto findBy(InterviewerId interviewerId);
}
