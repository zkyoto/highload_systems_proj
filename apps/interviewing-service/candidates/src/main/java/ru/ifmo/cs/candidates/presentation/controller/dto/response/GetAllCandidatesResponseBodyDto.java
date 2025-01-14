package ru.ifmo.cs.candidates.presentation.controller.dto.response;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Ответ, содержащий список всех кандидатов")
public record GetAllCandidatesResponseBodyDto(

        @JsonValue
        @ArraySchema(
                schema = @Schema(description = "DTO информации о кандидате", implementation = CandidateResponseDto.class),
                arraySchema = @Schema(description = "Список всех кандидатов")
        )
        List<CandidateResponseDto> list
) {}