package ru.ifmo.cs.passport.presentation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.ifmo.cs.misc.Name;
import ru.ifmo.cs.misc.UserId;
import ru.ifmo.cs.passport.application.service.UserWithUniqueUserIdCreator;
import ru.ifmo.cs.passport.domain.PassportUser;
import ru.ifmo.cs.passport.domain.PassportUserRepository;
import ru.ifmo.cs.passport.domain.value.PassportUserId;
import ru.ifmo.cs.passport.domain.value.Role;
import ru.ifmo.cs.passport_contracts.PassportUserResponseDto;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebFluxTest(PassportUsersApiController.class)
public class PassportUsersApiControllerTest {

    @MockBean
    private PassportUserRepository passportUserRepository;

    @MockBean
    private UserWithUniqueUserIdCreator userWithUniqueUserIdCreator;

    @InjectMocks
    private PassportUsersApiController controller;

    @Autowired
    private WebTestClient webTestClient;

    private PassportUser passportUser;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        passportUser = PassportUser.create(
                UserId.of(1),
                Name.of("John Doe"),
                List.of(Role.INTERVIEWER));

        when(passportUserRepository.findByUserId(any(UserId.class)))
                .thenReturn(Mono.just(passportUser));
        when(passportUserRepository.findAll())
                .thenReturn(Flux.just(passportUser));
        when(userWithUniqueUserIdCreator.createUserWithUniqueUserIdGuarantee(any(Role.class)))
                .thenReturn(Mono.just(passportUser.getUid()));
    }

    @Test
    public void getUser() {
        webTestClient.get()
                .uri("/api/v1/users/{userId}", passportUser.getUid().getUid())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(PassportUserResponseDto.class)
                .value(user -> {
                    assertNotNull(user);
                    assertEquals(passportUser.getUid(), user.uid());
                    assertEquals(passportUser.getName(), user.name());
                });
    }

    @Test
    public void createUser() {
        webTestClient.post()
                .uri("/api/v1/users/create")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(Role.INTERVIEWER.value())
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserId.class)
                .value(id -> {
                    assertNotNull(id);
                    assertEquals(passportUser.getUid(), id);
                });
    }

    @Test
    public void getAll() {
        webTestClient.get()
                .uri("/api/v1/users")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(PassportUserResponseDto.class)
                .hasSize(1)
                .value(users -> {
                    assertEquals(1, users.size());
                    PassportUserResponseDto user = users.get(0);
                    assertEquals(passportUser.getUid(), user.uid());
                    assertEquals(passportUser.getName(), user.name());
                });
    }
}