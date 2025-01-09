package ru.ifmo.cs.candidates.application.service.event;

import org.junit.jupiter.api.Test;
import ru.ifmo.cs.candidates.domain.event.CandidateCancelledEvent;
import ru.ifmo.cs.candidates.domain.event.CandidateCreatedEvent;
import ru.ifmo.cs.candidates.domain.event.CandidateProcessedEvent;
import ru.ifmo.cs.candidates.domain.event.CandidateScheduledForInterviewEvent;
import ru.ifmo.cs.domain_event.domain.KnownDomainEvent;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CandidateEventsKnownEventProviderTest {

    @Test
    public void testProvide() {
        CandidateEventsKnownEventProvider provider = new CandidateEventsKnownEventProvider();

        List<KnownDomainEvent> knownEvents = provider.provide();

        assertEquals(4, knownEvents.size());

        KnownDomainEvent candidateCreatedEvent = new KnownDomainEvent(
                CandidateCreatedEvent.EVENT_TYPE,
                CandidateCreatedEvent.class
        );
        KnownDomainEvent candidateScheduledForInterviewEvent = new KnownDomainEvent(
                CandidateScheduledForInterviewEvent.EVENT_TYPE,
                CandidateScheduledForInterviewEvent.class
        );
        KnownDomainEvent candidateProcessedEvent = new KnownDomainEvent(
                CandidateProcessedEvent.EVENT_TYPE,
                CandidateProcessedEvent.class
        );
        KnownDomainEvent candidateCancelledEvent = new KnownDomainEvent(
                CandidateCancelledEvent.EVENT_TYPE,
                CandidateCancelledEvent.class
        );

        assertTrue(knownEvents.contains(candidateCreatedEvent));
        assertTrue(knownEvents.contains(candidateScheduledForInterviewEvent));
        assertTrue(knownEvents.contains(candidateProcessedEvent));
        assertTrue(knownEvents.contains(candidateCancelledEvent));
    }
}