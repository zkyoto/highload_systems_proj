package ru.ifmo.cs.candidates.presentation.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;

@Schema(description = "DTO для добавления нового кандидата")
public record AddCandidateRequestBodyDto(
        @JsonProperty("candidate_name")
        @Nullable
        @Schema(description = "Полное имя кандидата", example = "Иван Иванов")
        String candidateFullName
) {}