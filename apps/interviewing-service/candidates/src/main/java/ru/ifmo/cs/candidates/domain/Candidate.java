package ru.ifmo.cs.candidates.domain;

import java.time.Instant;
import java.util.LinkedList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.ifmo.cs.misc.Name;
import ru.ifmo.cs.candidates.domain.event.CandidateCancelledEvent;
import ru.ifmo.cs.candidates.domain.event.CandidateCreatedEvent;
import ru.ifmo.cs.candidates.domain.event.CandidateEvent;
import ru.ifmo.cs.candidates.domain.event.CandidateProcessedEvent;
import ru.ifmo.cs.candidates.domain.event.CandidateScheduledForInterviewEvent;
import ru.ifmo.cs.candidates.domain.value.CandidateId;
import ru.ifmo.cs.candidates.domain.value.CandidateStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Candidate {
    private final CandidateId id;
    private final Instant createdAt;
    @NonNull private Instant updatedAt;
    @NonNull private CandidateStatus status;
    private final Name name;
    private List<CandidateEvent> events = new LinkedList<>();

    public static Candidate create(Name name) {
        Instant now = Instant.now();
        Candidate createdCandidate = new Candidate(CandidateId.generate(),
                                                   now,
                                                   now,
                                                   CandidateStatus.WAITING_FOR_APPOINTMENT_AN_INTERVIEW,
                                                   name);

        createdCandidate.events.add(CandidateCreatedEvent.fromEntity(createdCandidate));
        return createdCandidate;
    }

    public static Candidate hydrate(
            String id,
            Instant createdAt,
            Instant updatedAt,
            String status,
            String full_name
    ) {
        return new Candidate(
                CandidateId.hydrate(id),
                createdAt,
                updatedAt,
                CandidateStatus.R.fromValue(status),
                Name.of(full_name)
        );
    }

    public void changeStatusToScheduled() {
        validateState();
        this.status = CandidateStatus.WAITING_FOR_INTERVIEW;
        this.updatedAt = Instant.now();
        events.add(CandidateScheduledForInterviewEvent.fromEntity(this));
    }

    public void changeStatusToProcessed() {
        this.status = CandidateStatus.PROCESSED;
        this.updatedAt = Instant.now();
        events.add(CandidateProcessedEvent.fromEntity(this));
    }

    public void changeStatusToCancelled() {
        this.status = CandidateStatus.CANCELLED;
        this.updatedAt = Instant.now();
        events.add(CandidateCancelledEvent.fromEntity(this));
    }

    private void validateState() {
        if (this.status != CandidateStatus.WAITING_FOR_APPOINTMENT_AN_INTERVIEW) {
            throw new IllegalStateException();
        }
    }

    public List<CandidateEvent> releaseEvents() {
        List<CandidateEvent> releasedEvents = events;
        events = new LinkedList<>();
        return releasedEvents;
    }
}
