package ru.itmo.cs.app.interviewing.interview.domain.value;

import java.util.UUID;

public record InterviewId(UUID id) {

    public static InterviewId hydrate(String uuid) {
        return new InterviewId(UUID.fromString(uuid));
    }

    public static InterviewId generate() {
        return new InterviewId(UUID.randomUUID());
    }

}
