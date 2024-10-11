package ru.itmo.cs.app.interviewing.candidate.domain;

import java.time.Instant;
import java.util.LinkedList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.ifmo.cs.misc.Name;
import ru.itmo.cs.app.interviewing.candidate.domain.event.CandidateCreatedEvent;
import ru.itmo.cs.app.interviewing.candidate.domain.event.CandidateEvent;
import ru.itmo.cs.app.interviewing.candidate.domain.event.CandidateScheduledForInterviewEvent;
import ru.itmo.cs.app.interviewing.candidate.domain.value.CandidateId;
import ru.itmo.cs.app.interviewing.candidate.domain.value.CandidateStatus;

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
        Candidate createdCandidate = new Candidate(CandidateId.generate(),
                                                   Instant.now(),
                                                   Instant.now(),
                                                   CandidateStatus.WAITING_FOR_APPOINTMENT_AN_INTERVIEW,
                                                   name);

        createdCandidate.events.add(CandidateCreatedEvent.fromCreatedEntity(createdCandidate));
        return createdCandidate;
    }

    public void changeStatusToScheduled() {
        validateState();
        this.status = CandidateStatus.WAITING_FOR_INTERVIEW;
        this.updatedAt = Instant.now();
        events.add(CandidateScheduledForInterviewEvent.fromEntity(this));
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
