package ru.ifmo.cs.jwt_token.application;

public interface JwtValidator {
    boolean isValid(String token);
}
