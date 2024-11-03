package ru.ifmo.cs.service_token.domain;

public record RequestData(
        ServiceId src,
        ServiceId dst
) {}
