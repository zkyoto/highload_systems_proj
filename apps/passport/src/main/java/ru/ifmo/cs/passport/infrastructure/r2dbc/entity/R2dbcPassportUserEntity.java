package ru.ifmo.cs.passport.infrastructure.r2dbc.entity;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import ru.ifmo.cs.passport.domain.PassportUser;
import ru.ifmo.cs.passport.domain.value.Role;

public record R2dbcPassportUserEntity(
        UUID id,
        long userId,
        String fullName,
        Instant createdAt,
        Instant updatedAt,
        List<String> roles
) {
    public static R2dbcPassportUserEntity from(PassportUser user) {
        return new R2dbcPassportUserEntity(
                user.getId().value(),
                user.getUid().getUid(),
                user.getName().fullName(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getRoles().stream().map(Role::value).toList()
        );
    }
}
