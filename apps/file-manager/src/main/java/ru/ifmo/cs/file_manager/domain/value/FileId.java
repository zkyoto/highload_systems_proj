package ru.ifmo.cs.file_manager.domain.value;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonValue;

public record FileId(@JsonValue UUID value) {
    public static FileId generate() {
        return new FileId(UUID.randomUUID());
    }

    public static FileId hydrate(String id) {
        return new FileId(UUID.fromString(id));
    }
}
