package ru.itmo.cs.app.interviewing.feedback.domain.value;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonValue;

public record FeedbackId(@JsonValue UUID value) {

    public static FeedbackId generate() {
        return new FeedbackId(UUID.randomUUID());
    }

    public static FeedbackId hydrate(String uuid) {
        return new FeedbackId(UUID.fromString(uuid));
    }

}
