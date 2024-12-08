package ru.ifmo.cs.passport.infrastructure.r2dbc;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.core.ReactiveSelectOperation;
import org.springframework.data.relational.core.query.Query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.ifmo.cs.misc.Name;
import ru.ifmo.cs.misc.UserId;
import ru.ifmo.cs.passport.domain.PassportUser;
import ru.ifmo.cs.passport.domain.event.PassportUserCreatedEvent;
import ru.ifmo.cs.passport.domain.value.PassportUserId;
import ru.ifmo.cs.passport.domain.value.Role;
import ru.ifmo.cs.passport.infrastructure.r2dbc.entity.R2dbcPassportUserEntity;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class R2dbcPassportUserRepositoryTest {

    @Mock
    private R2dbcEntityTemplate template;

    @InjectMocks
    private R2dbcPassportUserRepository repository;

    private PassportUserId passportUserId;
    private UserId userId;
    private R2dbcPassportUserEntity entity;
    private PassportUser user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        passportUserId = new PassportUserId(UUID.randomUUID());
        userId = UserId.of(1);
        entity = new R2dbcPassportUserEntity(
                passportUserId.value(),
                userId.getUid(),
                "John Doe",
                Instant.now(),
                Instant.now(),
                List.of("hr")
        );
        user = PassportUser.create(userId, Name.of("John Doe"), List.of(Role.HR));
    }

    @Test
    public void testFindById() {
        when(template.select(any(Query.class), eq(R2dbcPassportUserEntity.class)))
                .thenReturn(Flux.just(entity));

        Mono<PassportUser> result = repository.findById(passportUserId);

        StepVerifier.create(result)
                .expectNextMatches(foundUser -> foundUser.getId().equals(passportUserId))
                .verifyComplete();

        ArgumentCaptor<Query> queryCaptor = ArgumentCaptor.forClass(Query.class);
        verify(template).select(queryCaptor.capture(), eq(R2dbcPassportUserEntity.class));
        Query capturedQuery = queryCaptor.getValue();
        assertEquals(passportUserId.value(), capturedQuery.getCriteria().get().getValue());
    }

    @Test
    public void testFindByUserId() {
        when(template.select(any(Query.class), eq(R2dbcPassportUserEntity.class)))
                .thenReturn(Flux.just(entity));

        Mono<PassportUser> result = repository.findByUserId(userId);

        StepVerifier.create(result)
                .expectNextMatches(foundUser -> foundUser.getUid().equals(userId))
                .verifyComplete();

        ArgumentCaptor<Query> queryCaptor = ArgumentCaptor.forClass(Query.class);
        verify(template).select(queryCaptor.capture(), eq(R2dbcPassportUserEntity.class));
        Query capturedQuery = queryCaptor.getValue();
//        assertEquals(userId.getUid(), capturedQuery.getCriteria().getGroup().get(0).getValue());
    }

    @Test
    public void testSaveNewUser() {
        when(template.insert(any(R2dbcPassportUserEntity.class)))
                .thenReturn(Mono.just(entity));

        Mono<PassportUser> result = repository.save(user);

        StepVerifier.create(result)
                .expectNextMatches(savedUser -> savedUser.getId().equals(passportUserId))
                .verifyComplete();

        verify(template).insert(any(R2dbcPassportUserEntity.class));
        verify(template, never()).update(any(R2dbcPassportUserEntity.class));
    }

    @Test
    public void testUpdateExistingUser() {
        when(template.update(any(R2dbcPassportUserEntity.class)))
                .thenReturn(Mono.just(entity));

        // Assume the user is not new by clearing events
        user.releaseEvents().clear();
        Mono<PassportUser> result = repository.save(user);

        StepVerifier.create(result)
                .expectNextMatches(updatedUser -> updatedUser.getId().equals(passportUserId))
                .verifyComplete();

        verify(template).update(any(R2dbcPassportUserEntity.class));
        verify(template, never()).insert(any(R2dbcPassportUserEntity.class));
    }
}