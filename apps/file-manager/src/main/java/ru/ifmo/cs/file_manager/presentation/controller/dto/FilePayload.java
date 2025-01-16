package ru.ifmo.cs.file_manager.presentation.controller.dto;

public record FilePayload(
        String fileName,
        String fileContentBase64
) {
}
