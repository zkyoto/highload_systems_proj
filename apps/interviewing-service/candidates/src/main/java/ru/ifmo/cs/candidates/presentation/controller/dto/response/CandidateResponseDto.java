package ru.ifmo.cs.candidates.presentation.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.ifmo.cs.misc.Name;
import ru.ifmo.cs.candidates.domain.Candidate;
import ru.ifmo.cs.candidates.domain.value.CandidateId;

public record CandidateResponseDto(
        @JsonProperty("candidate_id") CandidateId id,
        @JsonProperty("status") String status,
        @JsonProperty("candidate_name") Name name
) {
    public static CandidateResponseDto from(Candidate candidate) {
        return new CandidateResponseDto(candidate.getId(), candidate.getStatus().value(), candidate.getName());
    }
}
