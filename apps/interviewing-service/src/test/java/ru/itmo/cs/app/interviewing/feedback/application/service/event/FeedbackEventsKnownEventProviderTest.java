package ru.itmo.cs.app.interviewing.feedback.application.service.event;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.ifmo.cs.domain_event.domain.KnownDomainEvent;
import ru.itmo.cs.app.interviewing.feedback.domain.event.FeedbackCreatedEvent;
import ru.itmo.cs.app.interviewing.feedback.domain.event.FeedbackRewrittenEvent;
import ru.itmo.cs.app.interviewing.feedback.domain.event.FeedbackSubmittedEvent;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class FeedbackEventsKnownEventProviderTest {

    @Test
    void testProvide() {
        FeedbackEventsKnownEventProvider provider = new FeedbackEventsKnownEventProvider();

        List<KnownDomainEvent> events = provider.provide();

        assertEquals(3, events.size(), "Expected three known domain events");

        assertTrue(events.stream().anyMatch(event ->
                        event.eventTypeId().equals(FeedbackCreatedEvent.EVENT_TYPE) &&
                                event.eventClass().equals(FeedbackCreatedEvent.class)),
                "Expected list to contain FeedbackCreatedEvent");

        assertTrue(events.stream().anyMatch(event ->
                        event.eventTypeId().equals(FeedbackRewrittenEvent.EVENT_TYPE) &&
                                event.eventClass().equals(FeedbackRewrittenEvent.class)),
                "Expected list to contain FeedbackRewrittenEvent");

        assertTrue(events.stream().anyMatch(event ->
                        event.eventTypeId().equals(FeedbackSubmittedEvent.EVENT_TYPE) &&
                                event.eventClass().equals(FeedbackSubmittedEvent.class)),
                "Expected list to contain FeedbackSubmittedEvent");
    }
}