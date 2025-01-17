package ru.ifmo.cs.file_manager.presentation.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.ifmo.cs.file_manager.domain.File;

public record FileByExternalIdResponseBodyDto(

        @JsonProperty("file_external_id")
        String fileExternalId,

        @JsonProperty("filename")
        String filename,

        @JsonProperty("content")
        String base64FileContent
) {
    public static FileByExternalIdResponseBodyDto fromEntity(File file) {
        return new FileByExternalIdResponseBodyDto(
                file.getExternalId().value(),
                file.getFileName(),
                file.getFileContent().toBase64()
        );
    }
}