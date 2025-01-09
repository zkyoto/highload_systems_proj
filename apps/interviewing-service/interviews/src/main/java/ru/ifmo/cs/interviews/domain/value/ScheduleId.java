package ru.ifmo.cs.interviews.domain.value;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonValue;

public record ScheduleId(@JsonValue UUID value) {
    public static ScheduleId generate() {
        return new ScheduleId(UUID.randomUUID());
    }

    public static ScheduleId hydrate(String uuid) {
        return new ScheduleId(UUID.fromString(uuid));
    }
}
