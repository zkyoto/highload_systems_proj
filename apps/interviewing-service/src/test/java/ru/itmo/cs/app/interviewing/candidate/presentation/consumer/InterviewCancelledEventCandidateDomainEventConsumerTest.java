package ru.itmo.cs.app.interviewing.candidate.presentation.consumer;

import java.time.Instant;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.ifmo.cs.domain_event.domain.stored_event.StoredDomainEventRepository;
import ru.ifmo.cs.misc.Name;
import ru.itmo.cs.app.interviewing.AbstractIntegrationTest;
import ru.itmo.cs.app.interviewing.candidate.domain.Candidate;
import ru.itmo.cs.app.interviewing.candidate.domain.CandidateRepository;
import ru.itmo.cs.app.interviewing.candidate.domain.event.CandidateCancelledEvent;
import ru.itmo.cs.app.interviewing.candidate.domain.value.CandidateStatus;
import ru.itmo.cs.app.interviewing.interview.domain.event.InterviewCancelledEvent;
import ru.itmo.cs.app.interviewing.interview.domain.value.InterviewId;
import ru.itmo.cs.app.interviewing.interviewer.domain.value.InterviewerId;

class InterviewCancelledEventCandidateDomainEventConsumerTest extends AbstractIntegrationTest {
    @Autowired
    private InterviewCancelledEventCandidateDomainEventConsumer consumer;
    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private StoredDomainEventRepository storedDomainEventRepository;

    private Candidate stubCandidate;
    private InterviewCancelledEvent stubEvent;

    @BeforeEach
    void setup() {
        stubCandidate = Candidate.create(Name.of("my name"));
        candidateRepository.save(stubCandidate);
        stubEvent = new InterviewCancelledEvent(
                InterviewId.generate(),
                Instant.now(),
                InterviewerId.generate(),
                stubCandidate.getId()
        );
        deliverAllSavedDomainEvents();
    }

    @Test
    void testHandleUpdatesCandidateStatusToCancelled() {
        consumer.consume(stubEvent);
        Assertions.assertEquals(candidateRepository.findById(stubCandidate.getId()).getStatus(), CandidateStatus.CANCELLED);
        Assertions.assertTrue(storedDomainEventRepository.nextWaitedForDelivery().getEvent() instanceof CandidateCancelledEvent);
    }

}