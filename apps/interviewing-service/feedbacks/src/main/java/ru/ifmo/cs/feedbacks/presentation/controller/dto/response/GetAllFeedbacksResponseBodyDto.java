package ru.ifmo.cs.feedbacks.presentation.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Ответ, содержащий все отзывы в виде списка")
public record GetAllFeedbacksResponseBodyDto(
        @JsonValue
        @ArraySchema(
                schema = @Schema(description = "Список отзывов", implementation = FeedbackResponseDto.class),
                arraySchema = @Schema(description = "JSON массив объектов отзывов"))
        List<FeedbackResponseDto> list
) {}