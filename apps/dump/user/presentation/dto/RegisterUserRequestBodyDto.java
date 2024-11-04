package ru.ifmo.cs.api_gateway.user.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RegisterUserRequestBodyDto(
        @JsonProperty("username") String username,
        @JsonProperty("password") String password,
        @JsonProperty("role") String role
) {}
