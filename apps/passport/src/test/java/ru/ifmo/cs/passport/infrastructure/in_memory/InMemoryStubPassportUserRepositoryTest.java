package ru.ifmo.cs.passport.infrastructure.in_memory;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.ifmo.cs.misc.Name;
import ru.ifmo.cs.misc.UserId;
import ru.ifmo.cs.passport.domain.PassportUser;
import ru.ifmo.cs.passport.domain.exception.DuplicateUserIdException;
import ru.ifmo.cs.passport.domain.value.PassportUserId;
import ru.ifmo.cs.passport.domain.value.Role;
import ru.ifmo.cs.passport.infrastructure.in_memory.InMemoryStubPassportUserRepository;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class InMemoryStubPassportUserRepositoryTest {

    private InMemoryStubPassportUserRepository repository;
    private PassportUser passportUser;

    @BeforeEach
    public void setUp() {
        repository = new InMemoryStubPassportUserRepository();
        passportUser = PassportUser.create(UserId.of(1), Name.of("t t"), List.of(Role.OUT_OF_SCOPE));

        // Pre-populate repository with one user
        repository.save(passportUser).subscribe();
    }

    @Test
    public void testFindById() {
        Mono<PassportUser> foundUserMono = repository.findById(passportUser.getId());

        StepVerifier.create(foundUserMono)
                .expectNextMatches(user -> user.getId().equals(passportUser.getId()))
                .verifyComplete();
    }

    @Test
    public void testFindByUserId() {
        Mono<PassportUser> foundUserMono = repository.findByUserId(passportUser.getUid());

        StepVerifier.create(foundUserMono)
                .expectNextMatches(user -> user.getUid().equals(passportUser.getUid()))
                .verifyComplete();
    }

    @Test
    public void testFindAll() {
        StepVerifier.create(repository.findAll())
                .expectNext(passportUser)
                .verifyComplete();
    }

    @Test
    public void testSave() {
        PassportUser newUser = PassportUser.create(UserId.of(2), Name.of("test test"), List.of(Role.OUT_OF_SCOPE));

        StepVerifier.create(repository.save(newUser))
                .verifyComplete();

        Mono<PassportUser> foundUserMono = repository.findById(newUser.getId());
        StepVerifier.create(foundUserMono)
                .expectNextMatches(user -> user.getUid().equals(newUser.getUid()))
                .verifyComplete();
    }

    @Test
    public void testSaveDuplicateUserId() {
        PassportUser duplicateUser = PassportUser.create(UserId.of(1), Name.of("test test"), List.of(Role.OUT_OF_SCOPE));

        assertThrows(DuplicateUserIdException.class, () ->
                repository.save(duplicateUser).block());
    }

}