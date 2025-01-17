package ru.ifmo.cs.interviews.domain;

import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.ifmo.cs.interviews.domain.event.InterviewCancelledEvent;
import ru.ifmo.cs.interviews.domain.event.InterviewEvent;
import ru.ifmo.cs.interviews.domain.event.InterviewRescheduledEvent;
import ru.ifmo.cs.interviews.domain.event.InterviewScheduledEvent;
import ru.ifmo.cs.interviews.domain.specification.InterviewIsCancelledSpecification;
import ru.ifmo.cs.interviews.domain.specification.InterviewIsFailedSpecification;
import ru.ifmo.cs.interviews.domain.specification.InterviewIsWaitingForConductSpecification;
import ru.ifmo.cs.interviews.domain.value.InterviewId;
import ru.ifmo.cs.interviews.infrastructure.pg.entity.PgInterviewEntity;
import ru.ifmo.cs.interviews.infrastructure.pg.entity.PgScheduleEntity;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Interview {
    private final InterviewId id;
    private final Instant createdAt;
    private final Instant updatedAt;
    private final String interviewerId;
    private final String candidateId;
    private final List<Schedule> schedules;
    @Getter(AccessLevel.NONE)
    private List<InterviewEvent> events = new LinkedList<>();

    public static Interview create(String interviewerId, String candidateId, Instant scheduledFor) {
        Instant now = Instant.now();
        Interview createdInterview = new Interview(InterviewId.generate(),
                                                   now,
                                                   now,
                                                   interviewerId,
                                                   candidateId,
                                                   new LinkedList<>(List.of(Schedule.create(scheduledFor))));
        createdInterview.events.add(InterviewScheduledEvent.fromEntity(createdInterview));
        return createdInterview;
    }

    public static Interview hydrate(
            PgInterviewEntity pgInterviewEntity,
            List<PgScheduleEntity> pgScheduleEntities
    ) {
        return new Interview(InterviewId.hydrate(pgInterviewEntity.id()),
                             pgInterviewEntity.createdAt().toInstant(),
                             pgInterviewEntity.updated_at().toInstant(),
                             pgInterviewEntity.interviewerId(),
                             pgInterviewEntity.candidateId(),
                             new LinkedList<>(pgScheduleEntities.stream().map(Schedule::hydrate).toList()));
    }

    public Instant getScheduledFor() {
        return getActualSchedule().orElseThrow().getScheduledFor();
    }

    public Optional<Instant> getScheduledForO() {
        return getActualSchedule().map(Schedule::getScheduledFor);
    }

    public void reschedule(Instant newScheduledFor) {
        if (!InterviewIsWaitingForConductSpecification.isSatisfiedBy(this)) {
            throw new IllegalStateException("Interview is not waiting for conduct");
        }
        getActualSchedule().orElseThrow().cancel();
        this.schedules.add(Schedule.create(newScheduledFor));
        this.events.add(InterviewRescheduledEvent.fromEntity(this));
    }

    public void cancel() {
        if (InterviewIsCancelledSpecification.isSatisfiedBy(this)) {
            throw new IllegalStateException("Interview is already cancelled");
        }
        getActualSchedule().orElseThrow().cancel();
        this.events.add(InterviewCancelledEvent.fromEntity(this));
    }

    public void fail() {
        if (InterviewIsFailedSpecification.isSatisfiedBy(this)) {
            throw new IllegalStateException("Interview is already failed");
        }
        getActualSchedule().orElseThrow().fail();
        this.events.add(InterviewCancelledEvent.fromEntity(this));
    }

    private Optional<Schedule> getActualSchedule() {
        return schedules.stream()
                        .filter(Schedule::isActual)
                        .findAny();
    }

    public List<InterviewEvent> releaseEvents() {
        List<InterviewEvent> releasedEvents = events;
        events = new LinkedList<>();
        return releasedEvents;
    }
}
