package ru.itmo.cs.app.interviewing.candidate.application.query;

import ru.itmo.cs.app.interviewing.candidate.domain.Candidate;
import ru.itmo.cs.app.interviewing.interview_result.domain.value.InterviewResultId;

public interface CandidateByInterviewResultQueryService {
    Candidate findBy(InterviewResultId interviewResultId);
}
