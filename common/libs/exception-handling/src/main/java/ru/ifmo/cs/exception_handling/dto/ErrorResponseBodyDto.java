package ru.ifmo.cs.exception_handling.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;

@Schema(description = "DTO representing an error response")
public record ErrorResponseBodyDto(
        @JsonProperty("status")
        @Schema(description = "HTTP status code of the error", example = "404")
        HttpStatus status,

        @JsonProperty("exception")
        @Schema(description = "Class type of the exception thrown", example = "java.lang.IllegalArgumentException")
        Class<? extends Exception> exception,

        @JsonProperty("message")
        @Schema(description = "Detailed error message", example = "Resource not found")
        String message,

        @JsonProperty("stack_trace")
        @Schema(description = "Stack trace of the exception", example = "java.lang.NullPointerException: ...")
        String stackTrace
) {}