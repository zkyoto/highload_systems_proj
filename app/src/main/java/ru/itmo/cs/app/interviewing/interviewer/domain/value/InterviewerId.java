package ru.itmo.cs.app.interviewing.interviewer.domain.value;

import java.util.UUID;

public record InterviewerId(UUID id) {
    public static InterviewerId generate() {
        return new InterviewerId(UUID.randomUUID());
    }

    public static InterviewerId hydrate(String uuid) {
        return new InterviewerId(UUID.fromString(uuid));
    }
}
