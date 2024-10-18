package ru.itmo.cs.app.interviewing.candidate.presentation.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonValue;

public record GetCandidateResponseBodyDto(
        @JsonValue CandidateResponseDto candidateResponseDto
) {}
