package ru.ifmo.cs.candidates.presentation.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import ru.ifmo.cs.misc.Name;
import ru.ifmo.cs.candidates.domain.Candidate;
import ru.ifmo.cs.candidates.domain.value.CandidateId;

@Schema(description = "DTO ответа, содержащий информацию о кандидате")
public record CandidateResponseDto(

        @JsonProperty("candidate_id")
        @Schema(description = "Уникальный идентификатор кандидата", example = "123e4567-e89b-12d3-a456-426614174000")
        CandidateId id,

        @JsonProperty("status")
        @Schema(description = "Статус кандидата", example = "active")
        String status,

        @JsonProperty("candidate_name")
        @Schema(description = "Полное имя кандидата", example = "Иван Иванов")
        Name name
) {
    public static CandidateResponseDto from(Candidate candidate) {
        return new CandidateResponseDto(candidate.getId(), candidate.getStatus().value(), candidate.getName());
    }
}