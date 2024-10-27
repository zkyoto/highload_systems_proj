package ru.itmo.cs.app.interviewing.candidate.infrastructure;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.itmo.cs.app.interviewing.candidate.application.query.CandidateByInterviewResultQueryService;
import ru.itmo.cs.app.interviewing.candidate.domain.Candidate;
import ru.itmo.cs.app.interviewing.candidate.domain.CandidateRepository;
import ru.itmo.cs.app.interviewing.feedback.domain.Feedback;
import ru.itmo.cs.app.interviewing.feedback.domain.FeedbackRepository;
import ru.itmo.cs.app.interviewing.interview.domain.Interview;
import ru.itmo.cs.app.interviewing.interview.domain.InterviewRepository;
import ru.itmo.cs.app.interviewing.interview_result.domain.InterviewResult;
import ru.itmo.cs.app.interviewing.interview_result.domain.InterviewResultRepository;
import ru.itmo.cs.app.interviewing.interview_result.domain.value.InterviewResultId;

@Primary
@Service
@AllArgsConstructor
public class CandidateByInterviewResultQueryServiceImpl implements CandidateByInterviewResultQueryService {
    private final InterviewResultRepository interviewResultRepository;
    private final FeedbackRepository feedbackRepository;
    private final InterviewRepository interviewRepository;
    private final CandidateRepository candidateRepository;

    @Override
    public Candidate findBy(InterviewResultId interviewResultId) {
        InterviewResult interviewResult = interviewResultRepository.findById(interviewResultId);
        Feedback feedback = feedbackRepository.findById(interviewResult.getFeedbackId());
        Interview interview = interviewRepository.findById(feedback.getInterviewId());
        return candidateRepository.findById(interview.getCandidateId());
    }

}
