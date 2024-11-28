package ru.ifmo.cs.passport.domain.value;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonValue;

public record PassportUserId(@JsonValue UUID value) {
    public static PassportUserId generate() {
        return new PassportUserId(UUID.randomUUID());
    }

    public static PassportUserId hydrate(String uuid) {
        return new PassportUserId(UUID.fromString(uuid));
    }
}
