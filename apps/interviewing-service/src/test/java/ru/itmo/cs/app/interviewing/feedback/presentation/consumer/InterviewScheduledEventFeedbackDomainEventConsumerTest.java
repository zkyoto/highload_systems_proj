package ru.itmo.cs.app.interviewing.feedback.presentation.consumer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.ifmo.cs.domain_event.domain.stored_event.StoredDomainEventRepository;
import ru.itmo.cs.app.interviewing.AbstractIntegrationTest;
import ru.itmo.cs.app.interviewing.feedback.application.query.FeedbackByInterviewQueryService;
import ru.itmo.cs.app.interviewing.feedback.domain.event.FeedbackCreatedEvent;
import ru.itmo.cs.app.interviewing.interview.domain.Interview;
import ru.itmo.cs.app.interviewing.interview.domain.event.InterviewScheduledEvent;
import ru.itmo.cs.app.interviewing.utils.InterviewingServiceStubFactory;

class InterviewScheduledEventFeedbackDomainEventConsumerTest extends AbstractIntegrationTest {

    @Autowired
    private InterviewScheduledEventFeedbackDomainEventConsumer consumer;
    @Autowired
    private FeedbackByInterviewQueryService feedbackByInterviewQueryService;
    @Autowired
    private StoredDomainEventRepository storedDomainEventRepository;
    @Autowired
    private InterviewingServiceStubFactory stubFactory;
    private Interview stubInterview;
    private InterviewScheduledEvent stubEvent;

    @BeforeEach
    void setup() {
        stubInterview = stubFactory.createInterview();
        stubEvent = InterviewScheduledEvent.fromEntity(stubInterview);
        deliverAllSavedDomainEvents();
    }

    @Test
    void consumerTest() {
        consumer.consume(stubEvent);
        Assertions.assertTrue(feedbackByInterviewQueryService.findByInterviewId(stubInterview.getId()).isPresent());
        Assertions.assertTrue(storedDomainEventRepository.nextWaitedForDelivery().getEvent() instanceof FeedbackCreatedEvent);
    }
}