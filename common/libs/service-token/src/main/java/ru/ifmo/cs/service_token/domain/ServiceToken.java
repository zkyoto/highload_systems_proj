package ru.ifmo.cs.service_token.domain;

public record ServiceToken(String value) {
    public static ServiceToken of(String token) {
        return new ServiceToken(token);
    }
}
