package ru.ifmo.cs.interviewers.application.service.event;

import org.junit.jupiter.api.Test;
import ru.ifmo.cs.domain_event.domain.KnownDomainEvent;
import ru.ifmo.cs.interviewers.domain.event.InterviewerActivatedEvent;
import ru.ifmo.cs.interviewers.domain.event.InterviewerCreatedEvent;
import ru.ifmo.cs.interviewers.domain.event.InterviewerDemotedEvent;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InterviewerEventsKnownEventProviderTest {

    private final InterviewerEventsKnownEventProvider eventProvider = new InterviewerEventsKnownEventProvider();

    @Test
    void testProvideReturnsCorrectKnownDomainEvents() {
        List<KnownDomainEvent> knownEvents = eventProvider.provide();

        assertEquals(3, knownEvents.size());

        assertEquals(InterviewerActivatedEvent.EVENT_TYPE, knownEvents.get(0).eventTypeId());
        assertEquals(InterviewerActivatedEvent.class, knownEvents.get(0).eventClass());

        assertEquals(InterviewerCreatedEvent.EVENT_TYPE, knownEvents.get(1).eventTypeId());
        assertEquals(InterviewerCreatedEvent.class, knownEvents.get(1).eventClass());

        assertEquals(InterviewerDemotedEvent.EVENT_TYPE, knownEvents.get(2).eventTypeId());
        assertEquals(InterviewerDemotedEvent.class, knownEvents.get(2).eventClass());
    }
}