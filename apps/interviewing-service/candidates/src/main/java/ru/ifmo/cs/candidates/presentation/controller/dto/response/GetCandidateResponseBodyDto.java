package ru.ifmo.cs.candidates.presentation.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonValue;

public record GetCandidateResponseBodyDto(
        @JsonValue CandidateResponseDto candidateResponseDto
) {}
