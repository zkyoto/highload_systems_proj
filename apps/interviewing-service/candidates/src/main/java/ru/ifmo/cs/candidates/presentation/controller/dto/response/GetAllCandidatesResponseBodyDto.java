package ru.ifmo.cs.candidates.presentation.controller.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonValue;

public record GetAllCandidatesResponseBodyDto(
        @JsonValue List<CandidateResponseDto> list
) {}
