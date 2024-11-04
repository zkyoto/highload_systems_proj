package ru.ifmo.cs.api_gateway.user.domain.value;

import java.util.UUID;

public record UserId(UUID value) {
    public static UserId generate() {
        return new UserId(UUID.randomUUID());
    }

    public static UserId hydrate(String value) {
        return new UserId(UUID.fromString(value));
    }
}
