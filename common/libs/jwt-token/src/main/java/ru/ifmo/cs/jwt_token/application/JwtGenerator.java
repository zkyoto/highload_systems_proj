package ru.ifmo.cs.jwt_token.application;

import ru.ifmo.cs.misc.UserId;

public interface JwtGenerator {
    String generateFor(UserId userId);
}
