package ru.itmo.cs.app.interviewing.candidate.presentation.task;

import java.time.Instant;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.ifmo.cs.domain_event.domain.stored_event.StoredDomainEvent;
import ru.ifmo.cs.domain_event.domain.stored_event.StoredDomainEventRepository;
import ru.itmo.cs.app.interviewing.AbstractIntegrationTest;
import ru.itmo.cs.app.interviewing.candidate.domain.event.CandidateCreatedEvent;
import ru.itmo.cs.app.interviewing.candidate.domain.event.CandidateEvent;
import ru.itmo.cs.app.interviewing.candidate.domain.value.CandidateId;

class ProcessCandidateEventsTaskTest extends AbstractIntegrationTest {
    @Autowired
    private ProcessCandidateEventsTask processCandidateEventsTask;
    @Autowired
    private StoredDomainEventRepository storedDomainEventRepository;
    CandidateEvent stubCandidateEvent = new CandidateCreatedEvent(CandidateId.generate(), Instant.now());

    @Test
    void testProcessCandidateEventsWhenThereAreEventsToDeliver() {
        storedDomainEventRepository.save(StoredDomainEvent.of(stubCandidateEvent));
        Assertions.assertNotNull(storedDomainEventRepository.nextWaitedForDelivery());
        processCandidateEventsTask.execute();
        Assertions.assertNull(storedDomainEventRepository.nextWaitedForDelivery());
    }

    @Test
    void testStopProcessingCandidateEventsWhenNoEventsAreAvailable() {
        Assertions.assertNull(storedDomainEventRepository.nextWaitedForDelivery());
        processCandidateEventsTask.execute();
        Assertions.assertNull(storedDomainEventRepository.nextWaitedForDelivery());
    }

    @Test
    void testHandleMultipleCandidateEventsInSequence() {
        storedDomainEventRepository.save(StoredDomainEvent.of(stubCandidateEvent));
        storedDomainEventRepository.save(StoredDomainEvent.of(stubCandidateEvent));
        Assertions.assertNotNull(storedDomainEventRepository.nextWaitedForDelivery());

        processCandidateEventsTask.execute();
        Assertions.assertNull(storedDomainEventRepository.nextWaitedForDelivery());
    }

}
