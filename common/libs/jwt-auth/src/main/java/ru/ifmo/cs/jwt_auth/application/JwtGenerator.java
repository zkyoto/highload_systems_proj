package ru.ifmo.cs.jwt_auth.application;

import ru.ifmo.cs.misc.UserId;

public interface JwtGenerator {
    String generateFor(UserId userId);
}
