package ru.ifmo.cs.passport.presentation;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.ifmo.cs.misc.UserId;
import ru.ifmo.cs.passport.application.service.UserWithUniqueUserIdCreator;
import ru.ifmo.cs.passport.domain.PassportUser;
import ru.ifmo.cs.passport.domain.PassportUserRepository;
import ru.ifmo.cs.passport.domain.value.Role;
import ru.ifmo.cs.passport_contracts.PassportUserResponseDto;

@Slf4j
@RestController
@AllArgsConstructor
public class PassportUsersApiController {
    private final UserWithUniqueUserIdCreator userWithUniqueUserIdCreator;
    private final PassportUserRepository passportUserRepository;

    @GetMapping("/api/v1/users/{userId}")
    public Mono<PassportUserResponseDto> getUser(@PathVariable("userId") Long userId) {
        return passportUserRepository.findByUserId(UserId.of(userId)).map(this::toResponseDto);
    }

    @PostMapping("/api/v1/users/create")
    public Mono<UserId> create(
            @RequestBody String role
    ) {
        log.info("Create user request with role: {}", role);
        return userWithUniqueUserIdCreator.createUserWithUniqueUserIdGuarantee(Role.R.fromValue(role));
    }

    @GetMapping("/api/v1/users")
    public Flux<PassportUserResponseDto> getAll() {
        return passportUserRepository.findAll().map(this::toResponseDto);
    }

    private PassportUserResponseDto toResponseDto(PassportUser passportUser) {
        return new PassportUserResponseDto(
                passportUser.getUid(),
                passportUser.getName(),
                passportUser.getRoles()
                        .stream()
                        .map(Role::value)
                        .toList()
        );
    }

}
