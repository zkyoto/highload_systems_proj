package ru.ifmo.cs.file_manager.domain.value;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonValue;

public record FileExternalId(@JsonValue String value) {
    public static FileExternalId generate() {
        return new FileExternalId(UUID.randomUUID().toString());
    }
}
