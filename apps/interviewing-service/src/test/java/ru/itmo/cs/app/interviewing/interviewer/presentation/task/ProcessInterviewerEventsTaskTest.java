package ru.itmo.cs.app.interviewing.interviewer.presentation.task;

import java.time.Instant;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.ifmo.cs.domain_event.domain.stored_event.StoredDomainEvent;
import ru.ifmo.cs.domain_event.domain.stored_event.StoredDomainEventRepository;
import ru.itmo.cs.app.interviewing.AbstractIntegrationTest;
import ru.itmo.cs.app.interviewing.interviewer.domain.event.InterviewerActivatedEvent;
import ru.itmo.cs.app.interviewing.interviewer.domain.event.InterviewerEvent;
import ru.itmo.cs.app.interviewing.interviewer.domain.value.InterviewerId;

class ProcessInterviewerEventsTaskTest extends AbstractIntegrationTest {
    @Autowired
    private ProcessInterviewerEventsTask processInterviewerEventsTask;
    @Autowired
    private StoredDomainEventRepository storedDomainEventRepository;
    InterviewerEvent stubInterviewerEvent = new InterviewerActivatedEvent(InterviewerId.generate(), Instant.now());

    @Test
    void testProcessCandidateEventsWhenThereAreEventsToDeliver() {
        storedDomainEventRepository.save(StoredDomainEvent.of(stubInterviewerEvent));
        Assertions.assertNotNull(storedDomainEventRepository.nextWaitedForDelivery());
        processInterviewerEventsTask.execute();
        Assertions.assertNull(storedDomainEventRepository.nextWaitedForDelivery());
    }

    @Test
    void testStopProcessingCandidateEventsWhenNoEventsAreAvailable() {
        Assertions.assertNull(storedDomainEventRepository.nextWaitedForDelivery());
        processInterviewerEventsTask.execute();
        Assertions.assertNull(storedDomainEventRepository.nextWaitedForDelivery());
    }

    @Test
    void testHandleMultipleCandidateEventsInSequence() {
        storedDomainEventRepository.save(StoredDomainEvent.of(stubInterviewerEvent));
        storedDomainEventRepository.save(StoredDomainEvent.of(stubInterviewerEvent));
        Assertions.assertNotNull(storedDomainEventRepository.nextWaitedForDelivery());

        processInterviewerEventsTask.execute();
        Assertions.assertNull(storedDomainEventRepository.nextWaitedForDelivery());
    }
}