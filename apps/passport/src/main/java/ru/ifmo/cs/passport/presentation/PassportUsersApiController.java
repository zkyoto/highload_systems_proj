package ru.ifmo.cs.passport.presentation;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.ifmo.cs.misc.UserId;
import ru.ifmo.cs.passport.application.CreatePassportUserCommand;
import ru.ifmo.cs.passport.domain.PassportUser;
import ru.ifmo.cs.passport.domain.PassportUserRepository;
import ru.ifmo.cs.passport.domain.value.Role;
import ru.itmo.cs.command_bus.CommandBus;

@RestController
@RequestMapping()
@AllArgsConstructor
public class PassportUsersApiController {
    private final CommandBus commandBus;
    private final PassportUserRepository passportUserRepository;

    @GetMapping("/api/v1/users/{userId}")
    public Mono<ResponseEntity> getUser(@PathVariable Long userId) {
        return Mono.just(this.passportUserRepository.findByUserId(UserId.of(userId)))
                .map(ResponseEntity::ok);
    }

    @PostMapping("/api/v1/users/create")
    public Mono<ResponseEntity> create(
            @RequestBody String role
    ) {
        commandBus.submit(new CreatePassportUserCommand(Role.R.fromValue(role)));
        return Mono.just(ResponseEntity.ok().build());
    }

    @GetMapping("/api/v1/users")
    public Flux<PassportUser> getAll() {
        return passportUserRepository.findAll();
    }

}
