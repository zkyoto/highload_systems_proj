package ru.ifmo.cs.exception_handling.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;

public record ErrorResponseBodyDto(
        @JsonProperty("status") HttpStatus status,
        @JsonProperty("exception") Class<? extends Exception> exception,
        @JsonProperty("message") String message,
        @JsonProperty("stack_trace") String stackTrace
) {}
