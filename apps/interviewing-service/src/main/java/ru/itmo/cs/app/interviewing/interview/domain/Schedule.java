package ru.itmo.cs.app.interviewing.interview.domain;

import java.time.Instant;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.itmo.cs.app.interviewing.interview.domain.value.ScheduleId;
import ru.itmo.cs.app.interviewing.interview.domain.value.ScheduleStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Schedule {
    private final ScheduleId id;
    private final Instant createdAt;
    @NonNull private Instant updatedAt;
    @NonNull private Instant scheduledFor;
    @NonNull private ScheduleStatus status;

    public static Schedule create(Instant scheduledFor) {
        Instant now = Instant.now();
        return new Schedule(ScheduleId.generate(),
                            now,
                            now,
                            scheduledFor,
                            ScheduleStatus.ACTUAL);
    }

    public void cancel() {
        this.status = ScheduleStatus.CANCELLED;
        this.updatedAt = Instant.now();
    }

    public boolean isActual() {
        return status == ScheduleStatus.ACTUAL;
    }

    public boolean isCancelled() {
        return status == ScheduleStatus.CANCELLED;
    }
}
