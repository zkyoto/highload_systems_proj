package ru.itmo.cs.app.interviewing.feedback.presentation.task;

import java.time.Instant;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.ifmo.cs.domain_event.domain.stored_event.StoredDomainEvent;
import ru.ifmo.cs.domain_event.domain.stored_event.StoredDomainEventRepository;
import ru.itmo.cs.app.interviewing.AbstractIntegrationTest;
import ru.itmo.cs.app.interviewing.feedback.domain.event.FeedbackCreatedEvent;
import ru.itmo.cs.app.interviewing.feedback.domain.event.FeedbackEvent;
import ru.itmo.cs.app.interviewing.feedback.domain.value.FeedbackId;

class ProcessFeedbackEventsTaskTest extends AbstractIntegrationTest {

    @Autowired
    private ProcessFeedbackEventsTask processFeedbackEventsTask;
    @Autowired
    private StoredDomainEventRepository storedDomainEventRepository;
    FeedbackEvent stubFeedbackEvent = new FeedbackCreatedEvent(FeedbackId.generate(), Instant.now());

    @Test
    void testProcessFeedbackEventsWhenThereAreEventsToDeliver() {
        storedDomainEventRepository.save(StoredDomainEvent.of(stubFeedbackEvent));
        Assertions.assertNotNull(storedDomainEventRepository.nextWaitedForDelivery());
        processFeedbackEventsTask.execute();
        Assertions.assertNull(storedDomainEventRepository.nextWaitedForDelivery());
    }

    @Test
    void testStopProcessingFeedbackEventsWhenThereAreNoMoreEventsToDeliver() {
        StoredDomainEvent actual = storedDomainEventRepository.nextWaitedForDelivery();
        Assertions.assertNull(actual);
        processFeedbackEventsTask.execute();
        Assertions.assertNull(storedDomainEventRepository.nextWaitedForDelivery());
    }

    @Test
    void testHandleMultipleFeedbackEventsInTheDeliveryQueue() {
        storedDomainEventRepository.save(StoredDomainEvent.of(stubFeedbackEvent));
        storedDomainEventRepository.save(StoredDomainEvent.of(stubFeedbackEvent));
        Assertions.assertNotNull(storedDomainEventRepository.nextWaitedForDelivery());

        processFeedbackEventsTask.execute();
        Assertions.assertNull(storedDomainEventRepository.nextWaitedForDelivery());
    }

}