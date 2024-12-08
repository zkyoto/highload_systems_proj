package ru.ifmo.cs.passport.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.ifmo.cs.misc.Name;
import ru.ifmo.cs.misc.UserId;
import ru.ifmo.cs.passport.domain.PassportUser;
import ru.ifmo.cs.passport.domain.event.PassportUserCreatedEvent;
import ru.ifmo.cs.passport.domain.event.PassportUserEvent;
import ru.ifmo.cs.passport.domain.value.PassportUserId;
import ru.ifmo.cs.passport.domain.value.Role;
import ru.ifmo.cs.passport.infrastructure.r2dbc.entity.R2dbcPassportUserEntity;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class PassportUserTest {

    private UserId userId;
    private Name name;
    private List<Role> roles;

    @BeforeEach
    public void setUp() {
        userId = UserId.of(1);
        name = Name.of("John Doe");
        roles = List.of(Role.HR, Role.STAFF);
    }

    @Test
    public void testCreate() {
        PassportUser passportUser = PassportUser.create(userId, name, roles);

        assertNotNull(passportUser.getId());
        assertEquals(userId, passportUser.getUid());
        assertEquals(name, passportUser.getName());
        assertEquals(roles, passportUser.getRoles());
        assertNotNull(passportUser.getCreatedAt());
        assertNotNull(passportUser.getUpdatedAt());
        List<PassportUserEvent> events = passportUser.releaseEvents();
        assertEquals(1, events.size());
        assertTrue(events.get(0) instanceof PassportUserCreatedEvent);
    }

    @Test
    public void testHydrate() {
        Instant now = Instant.now();
        R2dbcPassportUserEntity entity = new R2dbcPassportUserEntity(
                UUID.randomUUID(),
                userId.getUid(),
                name.toString(),
                now,
                now,
                roles.stream().map(Role::value).toList()
        );

        PassportUser passportUser = PassportUser.hydrate(entity);

        assertEquals(new PassportUserId(entity.id()), passportUser.getId());
        assertEquals(UserId.of(entity.uid()), passportUser.getUid());
        assertEquals(Name.of(entity.fullName()), passportUser.getName());
        assertEquals(roles, passportUser.getRoles());
        assertEquals(entity.createdAt(), passportUser.getCreatedAt());
        assertEquals(entity.updatedAt(), passportUser.getUpdatedAt());
        assertTrue(passportUser.releaseEvents().isEmpty());
    }

    @Test
    public void testReleaseEvents() {
        PassportUser passportUser = PassportUser.create(userId, name, roles);

        List<PassportUserEvent> events = passportUser.releaseEvents();
        assertEquals(1, events.size());
        assertTrue(events.get(0) instanceof PassportUserCreatedEvent);

        // After releasing, events should be empty
        List<PassportUserEvent> releasedAgain = passportUser.releaseEvents();
        assertTrue(releasedAgain.isEmpty());
    }
}