package ru.ifmo.cs.candidates.presentation.consumer;

import java.time.Instant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.ifmo.cs.candidates.AbstractIntegrationTest;
import ru.ifmo.cs.candidates.application.query.CandidateByInterviewResultQueryService;
import ru.ifmo.cs.candidates.domain.Candidate;
import ru.ifmo.cs.candidates.presentation.integration_event.consumer.InterviewResultCreatedIntegrationEventConsumer;
import ru.ifmo.cs.consumer.KafkaConsumerProperties;
import ru.ifmo.cs.consumer.KafkaEventsConsumer;
import ru.ifmo.cs.contracts.interviewing_service.interview_results.integration_event.InterviewResultCreatedIntegrationEvent;
import ru.itmo.cs.command_bus.CommandBus;

@MockBean(classes = {KafkaConsumerProperties.class, KafkaEventsConsumer.class})
class InterviewResultCreatedIntegrationEventConsumerTest extends AbstractIntegrationTest {
    @Autowired
    private CandidateByInterviewResultQueryService candidateByInterviewResultQueryService;
    @Autowired
    private CommandBus commandBus;

    private InterviewResultCreatedIntegrationEventConsumer consumer;
    private InterviewResultCreatedIntegrationEvent stubEvent;

    @BeforeEach
    void setup() {
        consumer = new InterviewResultCreatedIntegrationEventConsumer(
                candidateByInterviewResultQueryService,
                commandBus
        );
        Candidate stubCandidate = createCandidate();
        stubEvent = new InterviewResultCreatedIntegrationEvent(
                "z",
                Instant.now(),
                "z",
                "z"
        );
        deliverAllSavedDomainEvents();
    }

    @Test
    void consumerTest() {
        consumer.consume(stubEvent);

//        Assertions.assertEquals(candidateByInterviewResultQueryService.findFor("z").getStatus(), CandidateStatus.PROCESSED);
//        Assertions.assertTrue(storedDomainEventRepository.nextWaitedForDelivery().getEvent() instanceof CandidateProcessedEvent);
    }

}