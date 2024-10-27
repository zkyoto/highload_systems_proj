package ru.itmo.cs.app.interviewing.utils;

import java.time.Instant;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.ifmo.cs.misc.Name;
import ru.ifmo.cs.misc.UserId;
import ru.itmo.cs.app.interviewing.candidate.domain.Candidate;
import ru.itmo.cs.app.interviewing.candidate.domain.CandidateRepository;
import ru.itmo.cs.app.interviewing.feedback.domain.Feedback;
import ru.itmo.cs.app.interviewing.feedback.domain.FeedbackRepository;
import ru.itmo.cs.app.interviewing.interview.domain.Interview;
import ru.itmo.cs.app.interviewing.interview.domain.InterviewRepository;
import ru.itmo.cs.app.interviewing.interview_result.domain.InterviewResult;
import ru.itmo.cs.app.interviewing.interview_result.domain.InterviewResultRepository;
import ru.itmo.cs.app.interviewing.interview_result.domain.value.Verdict;
import ru.itmo.cs.app.interviewing.interviewer.domain.Interviewer;
import ru.itmo.cs.app.interviewing.interviewer.domain.InterviewerRepository;

@Component
public class InterviewingServiceStubFactory {
    @Autowired
    private InterviewerRepository interviewerRepository;
    @Autowired
    private InterviewRepository interviewRepository;
    @Autowired
    private FeedbackRepository feedbackRepository;
    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private InterviewResultRepository interviewResultRepository;

    public Interviewer createInterviewer() {
        Interviewer stubInterviewer = Interviewer.create(UserId.of(new Random().nextLong()), Name.of("test name"));
        interviewerRepository.save(stubInterviewer);

        return stubInterviewer;
    }

    public Candidate createCandidate() {
        Candidate stubCandidate = Candidate.create(Name.of("test name"));
        candidateRepository.save(stubCandidate);
        return stubCandidate;
    }

    public Interview createInterview() {
        Interview interview = Interview.create(createInterviewer().getId(), createCandidate().getId(), Instant.now().plusSeconds(100));
        interviewRepository.save(interview);
        return interview;
    }

    public Feedback createFeedback() {
        Feedback stubFeedback = Feedback.create(createInterview().getId());
        feedbackRepository.save(stubFeedback);
        return stubFeedback;
    }

    public InterviewResult createInterviewResult() {
        InterviewResult stubInterviewResult = InterviewResult.create(createFeedback().getId(), Verdict.HIRE);
        interviewResultRepository.save(stubInterviewResult);
        return stubInterviewResult;
    }
}
