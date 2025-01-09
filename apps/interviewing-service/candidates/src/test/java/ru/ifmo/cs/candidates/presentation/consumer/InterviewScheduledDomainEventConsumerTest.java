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
import ru.ifmo.cs.candidates.domain.event.CandidateScheduledForInterviewEvent;
import ru.ifmo.cs.candidates.domain.value.CandidateStatus;
import ru.ifmo.cs.candidates.presentation.integration_event.consumer.InterviewScheduledIntegrationEventConsumer;
import ru.ifmo.cs.configuration.KafkaConsumerConfig;
import ru.ifmo.cs.consumer.KafkaConsumerProperties;
import ru.ifmo.cs.consumer.KafkaEventsConsumer;
import ru.ifmo.cs.contracts.interviewing_service.interviews.integration_event.InterviewScheduledIntegrationEvent;
import ru.ifmo.cs.domain_event.domain.stored_event.StoredDomainEventRepository;

@MockBean(classes = {KafkaConsumerProperties.class, KafkaEventsConsumer.class})
class InterviewScheduledIntegrationEventConsumerTest extends AbstractIntegrationTest {
    @Autowired
    private InterviewScheduledIntegrationEventConsumer consumer;
    @Autowired
    private StoredDomainEventRepository storedDomainEventRepository;
    @Autowired
    private CandidateRepository candidateRepository;
    private InterviewScheduledIntegrationEvent stubEvent;
    private Candidate stubCandidate;

    @BeforeEach
    void setup() {
        stubCandidate = createCandidate();
        stubEvent = new InterviewScheduledIntegrationEvent(
                "z",
                Instant.now(),
                "z",
                "z",
                stubCandidate.getId().value().toString(),
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