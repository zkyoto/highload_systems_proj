package ru.ifmo.cs.candidates.presentation.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Ответ, содержащий данные одного кандидата")
public record GetCandidateResponseBodyDto(

        @JsonValue
        @Schema(description = "DTO, представляющий информацию о кандидате")
        CandidateResponseDto candidateResponseDto
) {}