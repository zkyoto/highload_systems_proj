package ru.ifmo.cs.authorizator.domain.value;

import java.util.UUID;

public record UserInfoId(UUID value) {
    public static UserInfoId generate() {
        return new UserInfoId(UUID.randomUUID());
    }

    public static UserInfoId hydrate(String uuid) {
        return new UserInfoId(UUID.fromString(uuid));
    }
}
