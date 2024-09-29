package ru.itmo.cs.app.interviewing.interviewer.domain;

import java.time.Instant;
import java.util.LinkedList;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.ifmo.cs.misc.UserId;
import ru.ifmo.cs.misc.Name;
import ru.itmo.cs.app.interviewing.interviewer.domain.event.InterviewerActivatedEvent;
import ru.itmo.cs.app.interviewing.interviewer.domain.event.InterviewerCreatedEvent;
import ru.itmo.cs.app.interviewing.interviewer.domain.event.InterviewerDemotedEvent;
import ru.itmo.cs.app.interviewing.interviewer.domain.event.InterviewerEvent;
import ru.itmo.cs.app.interviewing.interviewer.domain.value.InterviewerId;
import ru.itmo.cs.app.interviewing.interviewer.domain.value.InterviewerStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Interviewer {
    private final InterviewerId interviewerId;
    private final UserId userId;
    private final Name name;
    private final Instant createdAt;
    private Instant updatedAt;
    private InterviewerStatus interviewerStatus;
    @Getter(AccessLevel.NONE)
    private List<InterviewerEvent> events;

    public static Interviewer create(UserId userId, Name name, InterviewerStatus status) {
        Interviewer createdEntity = new Interviewer(InterviewerId.generate(),
                                                    userId,
                                                    name,
                                                    Instant.now(),
                                                    Instant.now(),
                                                    status,
                                                    new LinkedList<>());
        createdEntity.events.add(InterviewerCreatedEvent.fromCreatedEntity(createdEntity));
        return createdEntity;
    }

    public static Interviewer create(UserId userId, Name name) {
        Interviewer createdEntity = new Interviewer(InterviewerId.generate(),
                                                    userId,
                                                    name,
                                                    Instant.now(),
                                                    Instant.now(),
                                                    InterviewerStatus.PENDING_ACTIVATION,
                                                    new LinkedList<>());
        createdEntity.events.add(InterviewerCreatedEvent.fromCreatedEntity(createdEntity));
        return createdEntity;
    }

    public void activate() {
        validateCanBeActivated();
        interviewerStatus = InterviewerStatus.ACTIVE;
        updatedAt = Instant.now();
        events.add(InterviewerActivatedEvent.fromActivatedEntity(this));
    }

    public void demote() {
        validateCanBeDemoted();
        interviewerStatus = InterviewerStatus.DEMOTED;
        updatedAt = Instant.now();
        events.add(InterviewerDemotedEvent.fromDemotedEntity(this));
    }

    private void validateCanBeDemoted() {
        if (interviewerStatus != InterviewerStatus.ACTIVE) {
            throw new IllegalStateException("Interviewer " + interviewerId + "can not be demoted!");
        }
    }

    private void validateCanBeActivated() {
        if (interviewerStatus != InterviewerStatus.PENDING_ACTIVATION
                && interviewerStatus != InterviewerStatus.DEMOTED) {
            throw new IllegalStateException("Interviewer " + interviewerId + "can not be activated!");
        }
    }

    public List<InterviewerEvent> releaseEvents() {
        List<InterviewerEvent> releasedEvents = events;
        events = new LinkedList<>();
        return releasedEvents;
    }

}
