package ru.itmo.cs.app.interviewing.interviewer.domain.value;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonValue;

public record InterviewerId(@JsonValue UUID value) {

    public static InterviewerId generate() {
        return new InterviewerId(UUID.randomUUID());
    }

    public static InterviewerId hydrate(String uuid) {
        return new InterviewerId(UUID.fromString(uuid));
    }

}
