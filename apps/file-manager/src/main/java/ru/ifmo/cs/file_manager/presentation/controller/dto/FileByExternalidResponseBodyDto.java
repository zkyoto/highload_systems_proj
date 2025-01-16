package ru.ifmo.cs.file_manager.presentation.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import ru.ifmo.cs.file_manager.domain.File;

@Schema(description = "Response body containing file details by external ID")
public record FileByExternalidResponseBodyDto(

        @JsonProperty("file_external_id")
        @Schema(description = "External identifier of the file", example = "abc123")
        String fileExternalId,

        @JsonProperty("filename")
        @Schema(description = "Name of the file", example = "document.txt")
        String filename,

        @JsonProperty("content")
        @Schema(description = "Base64 encoded content of the file", example = "SGVsbG8gV29ybGQ=")
        String base64FileContent
) {
    public static FileByExternalidResponseBodyDto fromEntity(File file) {
        return new FileByExternalidResponseBodyDto(
                file.getExternalId().value(),
                file.getFileName(),
                file.getFileContent().toBase64()
        );
    }
}