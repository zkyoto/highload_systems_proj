package ru.ifmo.cs.jwt_auth.application;

public interface JwtValidator {
    boolean isValid(String token);
}
