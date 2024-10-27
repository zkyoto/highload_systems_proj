package ru.itmo.cs.app.interviewing.candidate.presentation.consumer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.ifmo.cs.domain_event.domain.stored_event.StoredDomainEventRepository;
import ru.itmo.cs.app.interviewing.AbstractIntegrationTest;
import ru.itmo.cs.app.interviewing.candidate.application.query.CandidateByInterviewResultQueryService;
import ru.itmo.cs.app.interviewing.candidate.domain.event.CandidateProcessedEvent;
import ru.itmo.cs.app.interviewing.candidate.domain.value.CandidateStatus;
import ru.itmo.cs.app.interviewing.interview_result.domain.InterviewResult;
import ru.itmo.cs.app.interviewing.interview_result.domain.event.InterviewResultCreatedEvent;
import ru.itmo.cs.app.interviewing.utils.InterviewingServiceStubFactory;


class InterviewResultCreatedEventCandidateDomainEventConsumerTest extends AbstractIntegrationTest {
    @Autowired
    private InterviewResultCreatedEventCandidateDomainEventConsumer consumer;
    @Autowired
    private StoredDomainEventRepository storedDomainEventRepository;
    @Autowired
    private InterviewingServiceStubFactory stubFactory;
    @Autowired
    private CandidateByInterviewResultQueryService candidateByInterviewResultQueryService;
    private InterviewResult stubInterviewResult;
    private InterviewResultCreatedEvent stubEvent;

    @BeforeEach
    void setup() {
        stubInterviewResult = stubFactory.createInterviewResult();
        stubEvent = InterviewResultCreatedEvent.fromEntity(stubInterviewResult);
        deliverAllSavedDomainEvents();
    }

    @Test
    void consumerTest() {
        consumer.consume(stubEvent);

        Assertions.assertEquals(candidateByInterviewResultQueryService.findBy(stubInterviewResult.getId()).getStatus(), CandidateStatus.PROCESSED);
        Assertions.assertTrue(storedDomainEventRepository.nextWaitedForDelivery().getEvent() instanceof CandidateProcessedEvent);
    }

}