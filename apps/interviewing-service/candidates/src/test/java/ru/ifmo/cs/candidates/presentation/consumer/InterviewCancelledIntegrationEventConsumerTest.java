package ru.ifmo.cs.candidates.presentation.consumer;

import java.time.Instant;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.ifmo.cs.candidates.AbstractIntegrationTest;
import ru.ifmo.cs.candidates.domain.Candidate;
import ru.ifmo.cs.candidates.domain.CandidateRepository;
import ru.ifmo.cs.candidates.domain.event.CandidateCancelledEvent;
import ru.ifmo.cs.candidates.domain.value.CandidateStatus;
import ru.ifmo.cs.candidates.presentation.integration_event.consumer.InterviewCancelledIntegrationEventConsumer;
import ru.ifmo.cs.consumer.KafkaConsumerProperties;
import ru.ifmo.cs.consumer.KafkaEventsConsumer;
import ru.ifmo.cs.contracts.interviewing_service.interviews.integration_event.InterviewCancelledIntegrationEvent;
import ru.ifmo.cs.domain_event.domain.stored_event.StoredDomainEventRepository;
import ru.ifmo.cs.misc.Name;

@MockBean(classes = {KafkaConsumerProperties.class, KafkaEventsConsumer.class})
class InterviewCancelledIntegrationEventConsumerTest extends AbstractIntegrationTest {
    @Autowired
    private InterviewCancelledIntegrationEventConsumer consumer;
    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private StoredDomainEventRepository storedDomainEventRepository;

    private Candidate stubCandidate;
    private InterviewCancelledIntegrationEvent stubEvent;

    @BeforeEach
    void setup() {
        stubCandidate = Candidate.create(Name.of("my name"));
        candidateRepository.save(stubCandidate);
        stubEvent = new InterviewCancelledIntegrationEvent(
                "z",
                Instant.now(),
                "z",
                "z",
                stubCandidate.getId().value().toString()
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