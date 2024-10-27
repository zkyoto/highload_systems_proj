package ru.itmo.cs.app.interviewing.interview.presentation.task;

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
import ru.itmo.cs.app.interviewing.candidate.domain.value.CandidateId;
import ru.itmo.cs.app.interviewing.candidate.presentation.consumer.InterviewScheduledEventCandidateDomainEventConsumer;
import ru.itmo.cs.app.interviewing.feedback.presentation.consumer.InterviewScheduledEventFeedbackDomainEventConsumer;
import ru.itmo.cs.app.interviewing.interview.domain.event.InterviewEvent;
import ru.itmo.cs.app.interviewing.interview.domain.event.InterviewScheduledEvent;
import ru.itmo.cs.app.interviewing.interview.domain.value.InterviewId;
import ru.itmo.cs.app.interviewing.interviewer.domain.value.InterviewerId;

@ContextConfiguration(classes = {ProcessInterviewEventsTaskTest.TestContextConfiguration.class})
class ProcessInterviewEventsTaskTest extends AbstractIntegrationTest {
    @Autowired
    private ProcessInterviewEventsTask processInterviewEventsTask;
    @Autowired
    private StoredDomainEventRepository storedDomainEventRepository;
    InterviewEvent stubInterviewEvent =
            new InterviewScheduledEvent(InterviewId.generate(), Instant.now(), InterviewerId.generate(),
                    CandidateId.generate(), Instant.now());

    @Test
    void testProcessCandidateEventsWhenThereAreEventsToDeliver() {
        storedDomainEventRepository.save(StoredDomainEvent.of(stubInterviewEvent));
        Assertions.assertNotNull(storedDomainEventRepository.nextWaitedForDelivery());
        processInterviewEventsTask.execute();
        Assertions.assertNull(storedDomainEventRepository.nextWaitedForDelivery());
    }

    @Test
    void testStopProcessingCandidateEventsWhenNoEventsAreAvailable() {
        Assertions.assertNull(storedDomainEventRepository.nextWaitedForDelivery());
        processInterviewEventsTask.execute();
        Assertions.assertNull(storedDomainEventRepository.nextWaitedForDelivery());
    }

    @Test
    void testHandleMultipleCandidateEventsInSequence() {
        storedDomainEventRepository.save(StoredDomainEvent.of(stubInterviewEvent));
        storedDomainEventRepository.save(StoredDomainEvent.of(stubInterviewEvent));
        Assertions.assertNotNull(storedDomainEventRepository.nextWaitedForDelivery());

        processInterviewEventsTask.execute();
        Assertions.assertNull(storedDomainEventRepository.nextWaitedForDelivery());
    }

    @TestConfiguration
    static class TestContextConfiguration {

        @Primary
        @Bean
        public InterviewScheduledEventCandidateDomainEventConsumer interviewScheduledEventCandidateDomainEventConsumer() {
            return Mockito.mock(InterviewScheduledEventCandidateDomainEventConsumer.class);
        }

        @Primary
        @Bean
        public InterviewScheduledEventFeedbackDomainEventConsumer interviewScheduledEventFeedbackDomainEventConsumer() {
            return Mockito.mock(InterviewScheduledEventFeedbackDomainEventConsumer.class);
        }

    }
}