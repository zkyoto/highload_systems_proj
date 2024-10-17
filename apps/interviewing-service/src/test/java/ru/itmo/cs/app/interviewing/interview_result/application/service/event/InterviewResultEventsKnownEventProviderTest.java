package ru.itmo.cs.app.interviewing.interview_result.application.service.event;

import org.junit.jupiter.api.Test;
import ru.ifmo.cs.domain_event.domain.KnownDomainEvent;
import ru.itmo.cs.app.interviewing.interview_result.domain.event.InterviewResultCreatedEvent;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InterviewResultEventsKnownEventProviderTest {

    @Test
    void testProvideReturnsListOfKnownDomainEvents() {
        InterviewResultEventsKnownEventProvider provider = new InterviewResultEventsKnownEventProvider();

        List<KnownDomainEvent> events = provider.provide();

        assertEquals(1, events.size(), "Expected exactly one known domain event");

        KnownDomainEvent knownEvent = events.get(0);
        assertEquals(InterviewResultCreatedEvent.EVENT_TYPE, knownEvent.eventTypeId(),
                "Expected event type to match InterviewResultCreatedEvent.EVENT_TYPE");
        assertEquals(InterviewResultCreatedEvent.class, knownEvent.eventClass(),
                "Expected event class to match InterviewResultCreatedEvent.class");
    }
}