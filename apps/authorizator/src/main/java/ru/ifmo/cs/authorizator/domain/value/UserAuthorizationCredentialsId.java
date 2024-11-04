package ru.ifmo.cs.authorizator.domain.value;

import java.util.UUID;

public record UserAuthorizationCredentialsId(UUID value) {

    public static UserAuthorizationCredentialsId generate() {
        return new UserAuthorizationCredentialsId(UUID.randomUUID());
    }

    public static UserAuthorizationCredentialsId hydrate(String uuid) {
        return new UserAuthorizationCredentialsId(UUID.fromString(uuid));
    }
}
