package ru.itmo.cs.app.interviewing.interviewer.domain;

import java.time.Instant;
import java.util.LinkedList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ru.itmo.cs.app.interviewing.interviewer.domain.event.InterviewerCreatedEvent;
import ru.itmo.cs.app.interviewing.interviewer.domain.event.InterviewerEvent;
import ru.itmo.cs.app.interviewing.interviewer.domain.value.InterviewerId;

@Data
public class Interviewer {
    private final InterviewerId interviewerId;
    private final Instant createdAt;
    private final Instant updatedAt;
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private List<InterviewerEvent> events = new LinkedList<>();

    public static Interviewer create() {
        Interviewer createdEntity = new Interviewer(InterviewerId.generate(), Instant.now(), Instant.now());
        createdEntity.events.add(new InterviewerCreatedEvent());

        return createdEntity;
    }

    public List<InterviewerEvent> releaseEvents() {
        List<InterviewerEvent> releasedEvents = events;
        events = new LinkedList<>();
        return releasedEvents;
    }
}
