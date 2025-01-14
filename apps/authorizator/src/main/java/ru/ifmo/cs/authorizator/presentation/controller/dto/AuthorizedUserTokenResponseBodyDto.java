package ru.ifmo.cs.authorizator.presentation.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthorizedUserTokenResponseBodyDto(
        @JsonProperty("token") String token
) {}
