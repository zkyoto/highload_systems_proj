package ru.itmo.cs.app.interviewing.candidate.presentation.consumer;

import java.time.Instant;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.ifmo.cs.domain_event.domain.stored_event.StoredDomainEventRepository;
import ru.itmo.cs.app.interviewing.AbstractIntegrationTest;
import ru.itmo.cs.app.interviewing.candidate.application.query.CandidateByInterviewResultQueryService;
import ru.itmo.cs.app.interviewing.candidate.domain.Candidate;
import ru.itmo.cs.app.interviewing.candidate.domain.CandidateRepository;
import ru.itmo.cs.app.interviewing.candidate.domain.event.CandidateProcessedEvent;
import ru.itmo.cs.app.interviewing.candidate.domain.event.CandidateScheduledForInterviewEvent;
import ru.itmo.cs.app.interviewing.candidate.domain.value.CandidateStatus;
import ru.itmo.cs.app.interviewing.interview.domain.Interview;
import ru.itmo.cs.app.interviewing.interview.domain.event.InterviewScheduledEvent;
import ru.itmo.cs.app.interviewing.interview.domain.value.InterviewId;
import ru.itmo.cs.app.interviewing.interview_result.domain.InterviewResult;
import ru.itmo.cs.app.interviewing.interview_result.domain.event.InterviewResultCreatedEvent;
import ru.itmo.cs.app.interviewing.interviewer.domain.value.InterviewerId;
import ru.itmo.cs.app.interviewing.utils.InterviewingServiceStubFactory;

class InterviewScheduledEventCandidateDomainEventConsumerTest extends AbstractIntegrationTest {
    @Autowired
    private InterviewScheduledEventCandidateDomainEventConsumer consumer;
    @Autowired
    private StoredDomainEventRepository storedDomainEventRepository;
    @Autowired
    private InterviewingServiceStubFactory stubFactory;
    @Autowired
    private CandidateRepository candidateRepository;
    private InterviewScheduledEvent stubEvent;
    private Candidate stubCandidate;

    @BeforeEach
    void setup() {
        stubCandidate = stubFactory.createCandidate();
        stubEvent = new InterviewScheduledEvent(
                InterviewId.generate(),
                Instant.now(),
                InterviewerId.generate(),
                stubCandidate.getId(),
                Instant.now()
        );
        deliverAllSavedDomainEvents();
    }

    @Test
    void consumerTest() {
        consumer.consume(stubEvent);

        Assertions.assertEquals(candidateRepository.findById(stubCandidate.getId()).getStatus(), CandidateStatus.WAITING_FOR_INTERVIEW);
        Assertions.assertTrue(storedDomainEventRepository.nextWaitedForDelivery().getEvent() instanceof CandidateScheduledForInterviewEvent);
    }


}