package ru.ifmo.cs.authorizator.presentation.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthorizedUserTokenResponseBodyDto(
        @JsonProperty("token") String token
) {}
