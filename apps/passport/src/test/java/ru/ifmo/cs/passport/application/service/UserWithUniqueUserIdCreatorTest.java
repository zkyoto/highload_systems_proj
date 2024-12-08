package ru.ifmo.cs.passport.application.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.ifmo.cs.misc.UserId;
import ru.ifmo.cs.passport.domain.PassportUser;
import ru.ifmo.cs.passport.domain.PassportUserRepository;
import ru.ifmo.cs.passport.domain.value.Role;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserWithUniqueUserIdCreatorTest {

    @Mock
    private PassportUserRepository passportUserRepository;

    @InjectMocks
    private UserWithUniqueUserIdCreator userWithUniqueUserIdCreator;

    @Captor
    private ArgumentCaptor<PassportUser> userCaptor;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUserWithUniqueUserIdGuarantee_Success() {
        // Arrange
        PassportUser dummyUser = mock(PassportUser.class);
        when(dummyUser.getUid()).thenReturn(UserId.of(1));
        when(passportUserRepository.save(any(PassportUser.class)))
                .thenReturn(Mono.just(dummyUser));

        Role userRole = Role.OUT_OF_SCOPE;

        // Act
        Mono<UserId> result = userWithUniqueUserIdCreator.createUserWithUniqueUserIdGuarantee(userRole);

        // Assert
        StepVerifier.create(result)
                .expectNext(UserId.of(1))
                .verifyComplete();

        verify(passportUserRepository, times(1)).save(userCaptor.capture());
        assertEquals(userRole, userCaptor.getValue().getRoles().get(0));
    }

    @Test
    public void testCreateUserWithUniqueUserIdGuarantee_RetryOnDataIntegrityViolationException() {
        // Arrange
        PassportUser dummyUser = mock(PassportUser.class);
        when(dummyUser.getUid()).thenReturn(UserId.of(1));

        when(passportUserRepository.save(any(PassportUser.class)))
                .thenThrow(new DataIntegrityViolationException("Duplicate key"))
                .thenReturn(Mono.just(dummyUser));

        Role userRole = Role.OUT_OF_SCOPE;

        // Act
        Mono<UserId> result = userWithUniqueUserIdCreator.createUserWithUniqueUserIdGuarantee(userRole);

        // Assert
        StepVerifier.create(result)
                .expectNext(UserId.of(1))
                .verifyComplete();

        verify(passportUserRepository, times(2)).save(any(PassportUser.class));
    }
}