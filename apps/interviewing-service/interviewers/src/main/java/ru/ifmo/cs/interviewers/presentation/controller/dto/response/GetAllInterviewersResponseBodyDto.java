package ru.ifmo.cs.interviewers.presentation.controller.dto.response;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response body containing a list of interviewers")
public record GetAllInterviewersResponseBodyDto(

        @JsonValue
        @ArraySchema(schema = @Schema(description = "List of interviewer response details", implementation = InterviewerResponseDto.class))
        List<InterviewerResponseDto> list

) {}