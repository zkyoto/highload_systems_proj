package ru.ifmo.cs.file_manager.domain.value;

import java.util.Base64;

import com.fasterxml.jackson.annotation.JsonValue;

public record FileContent(@JsonValue byte[] data) {

    public static FileContent fromBase64(String base64Content) {
        return new FileContent(Base64.getDecoder().decode(base64Content));
    }

    public String toBase64() {
        return Base64.getEncoder().encodeToString(data);
    }
}
