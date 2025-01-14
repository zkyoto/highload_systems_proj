package ru.ifmo.cs.authorizator.presentation.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public record RegisterUserRequestBodyDto(
        @JsonProperty("username")
        @Schema(description = "Username of the user", example = "john_doe")
        String username,

        @JsonProperty("password")
        @Schema(description = "Password for the account", example = "P@ssw0rd")
        String password,

        @JsonProperty("roleSlug")
        @Schema(description = "Role of the user", example = "staff", allowableValues = {"supervisor", "staff", "interviewer", "hr", "out-of-scope"})
        String roleSlug
) {}