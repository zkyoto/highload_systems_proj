package ru.itmo.cs.app.interviewing.candidate.presentation.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.ifmo.cs.misc.Name;
import ru.itmo.cs.app.interviewing.candidate.domain.Candidate;
import ru.itmo.cs.app.interviewing.candidate.domain.value.CandidateId;
import ru.itmo.cs.app.interviewing.candidate.domain.value.CandidateStatus;

public record CandidateResponseDto(
        @JsonProperty("candidate_id") CandidateId id,
        @JsonProperty("status") String status,
        @JsonProperty("candidate_name") Name name
) {
    public static CandidateResponseDto from(Candidate candidate) {
        return new CandidateResponseDto(candidate.getId(), candidate.getStatus().value(), candidate.getName());
    }
}
