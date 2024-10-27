package ru.itmo.cs.app.interviewing.interview_result.presentation.task;

import java.time.Instant;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ContextConfiguration;
import ru.ifmo.cs.domain_event.domain.stored_event.StoredDomainEvent;
import ru.ifmo.cs.domain_event.domain.stored_event.StoredDomainEventRepository;
import ru.itmo.cs.app.interviewing.AbstractIntegrationTest;
import ru.itmo.cs.app.interviewing.candidate.presentation.consumer.InterviewResultCreatedEventCandidateDomainEventConsumer;
import ru.itmo.cs.app.interviewing.interview_result.domain.event.InterviewResultCreatedEvent;
import ru.itmo.cs.app.interviewing.interview_result.domain.event.InterviewResultEvent;
import ru.itmo.cs.app.interviewing.interview_result.domain.value.InterviewResultId;

@ContextConfiguration(classes = {ProcessInterviewResultEventsTaskTest.TestContextConfiguration.class})
class ProcessInterviewResultEventsTaskTest extends AbstractIntegrationTest {
    @Autowired
    private ProcessInterviewResultEventsTask processInterviewResultEventsTask;
    @Autowired
    private StoredDomainEventRepository storedDomainEventRepository;
    InterviewResultEvent stubInterviewResultEvent =
            new InterviewResultCreatedEvent(InterviewResultId.generate(), Instant.now());

    @Test
    void testProcessCandidateEventsWhenThereAreEventsToDeliver() {
        storedDomainEventRepository.save(StoredDomainEvent.of(stubInterviewResultEvent));
        Assertions.assertNotNull(storedDomainEventRepository.nextWaitedForDelivery());
        processInterviewResultEventsTask.execute();
        Assertions.assertNull(storedDomainEventRepository.nextWaitedForDelivery());
    }

    @Test
    void testStopProcessingCandidateEventsWhenNoEventsAreAvailable() {
        Assertions.assertNull(storedDomainEventRepository.nextWaitedForDelivery());
        processInterviewResultEventsTask.execute();
        Assertions.assertNull(storedDomainEventRepository.nextWaitedForDelivery());
    }

    @Test
    void testHandleMultipleCandidateEventsInSequence() {
        storedDomainEventRepository.save(StoredDomainEvent.of(stubInterviewResultEvent));
        storedDomainEventRepository.save(StoredDomainEvent.of(stubInterviewResultEvent));
        Assertions.assertNotNull(storedDomainEventRepository.nextWaitedForDelivery());

        processInterviewResultEventsTask.execute();
        Assertions.assertNull(storedDomainEventRepository.nextWaitedForDelivery());
    }

    @TestConfiguration
    static class TestContextConfiguration {

        @Primary
        @Bean
        public InterviewResultCreatedEventCandidateDomainEventConsumer interviewResultCreatedEventCandidateDomainEventConsumer() {
            return Mockito.mock(InterviewResultCreatedEventCandidateDomainEventConsumer.class);
        }
    }
}