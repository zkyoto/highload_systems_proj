package ru.itmo.cs.app.interviewing.feedback.application.service.event;

import java.util.List;

import org.springframework.stereotype.Service;
import ru.ifmo.cs.domain_event.application.service.KnownEventProvider;
import ru.ifmo.cs.domain_event.domain.KnownDomainEvent;
import ru.itmo.cs.app.interviewing.feedback.domain.event.FeedbackCreatedEvent;
import ru.itmo.cs.app.interviewing.feedback.domain.event.FeedbackRewrittenEvent;
import ru.itmo.cs.app.interviewing.feedback.domain.event.FeedbackSubmittedEvent;

@Service
public class FeedbackEventsKnownEventProvider implements KnownEventProvider {

    @Override
    public List<KnownDomainEvent> provide() {
        return List.of(new KnownDomainEvent(FeedbackCreatedEvent.EVENT_TYPE, FeedbackCreatedEvent.class),
                       new KnownDomainEvent(FeedbackRewrittenEvent.EVENT_TYPE, FeedbackRewrittenEvent.class),
                       new KnownDomainEvent(FeedbackSubmittedEvent.EVENT_TYPE, FeedbackSubmittedEvent.class));
    }

}
