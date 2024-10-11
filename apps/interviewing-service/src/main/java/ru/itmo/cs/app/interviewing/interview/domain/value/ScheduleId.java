package ru.itmo.cs.app.interviewing.interview.domain.value;

import java.util.UUID;

public record ScheduleId(UUID id) {
    public static ScheduleId generate() {
        return new ScheduleId(UUID.randomUUID());
    }

    public static ScheduleId hydrate(String uuid) {
        return new ScheduleId(UUID.fromString(uuid));
    }
}
