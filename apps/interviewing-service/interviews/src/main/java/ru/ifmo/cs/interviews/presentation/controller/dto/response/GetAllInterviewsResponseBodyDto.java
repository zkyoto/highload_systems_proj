package ru.ifmo.cs.interviews.presentation.controller.dto.response;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response DTO containing a list of interviews")
public record GetAllInterviewsResponseBodyDto(
        @JsonValue
        @ArraySchema(schema = @Schema(implementation = InterviewResponseDto.class),
                arraySchema = @Schema(description = "List of interview response DTOs"))
        List<InterviewResponseDto> list
) {}