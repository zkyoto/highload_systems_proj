package ru.ifmo.cs.file_manager;

public record FileMessage(
        String fileName,
        String fileContentBase64
) {
}
