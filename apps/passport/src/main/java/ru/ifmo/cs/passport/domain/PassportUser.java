package ru.ifmo.cs.passport.domain;

import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.ifmo.cs.misc.Name;
import ru.ifmo.cs.misc.UserId;
import ru.ifmo.cs.passport.domain.event.PassportUserCreatedEvent;
import ru.ifmo.cs.passport.domain.event.PassportUserEvent;
import ru.ifmo.cs.passport.domain.value.PassportUserId;
import ru.ifmo.cs.passport.domain.value.Role;
import ru.ifmo.cs.passport.infrastructure.r2dbc.entity.R2dbcPassportUserEntity;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PassportUser {
    private final PassportUserId id;
    private final Instant createdAt;
    private final Instant updatedAt;
    private final UserId uid;
    private final Name name;
    private final List<Role> roles;
    private List<PassportUserEvent> events = new LinkedList<>();

    public static PassportUser create(UserId uid, Name name, List<Role> roles) {
        Instant now = Instant.now();
        PassportUser passportUser = new PassportUser(
                PassportUserId.generate(),
                now,
                now,
                uid,
                name,
                roles
        );
        passportUser.events.add(new PassportUserCreatedEvent());
        return passportUser;
    }

    public static PassportUser hydrate(R2dbcPassportUserEntity r2dbcPassportUserEntity) {
        return new PassportUser(
                new PassportUserId(r2dbcPassportUserEntity.id()),
                r2dbcPassportUserEntity.createdAt(),
                r2dbcPassportUserEntity.updatedAt(),
                UserId.of(r2dbcPassportUserEntity.uid()),
                Name.of(r2dbcPassportUserEntity.fullName()),
                r2dbcPassportUserEntity.roles().stream().map(Role.R::fromValue).toList()
        );
    }

    public List<PassportUserEvent> releaseEvents() {
        List<PassportUserEvent> releasedEvents = events;
        events = new LinkedList<>();
        return releasedEvents;
    }
}
