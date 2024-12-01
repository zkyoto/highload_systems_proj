package ru.ifmo.cs.passport.domain;

import java.util.List;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.ifmo.cs.misc.UserId;
import ru.ifmo.cs.passport.domain.value.PassportUserId;

public interface PassportUserRepository {
    Mono<PassportUser> findById(PassportUserId userId);
    Mono<PassportUser> findByUserId(UserId userId);
    Flux<PassportUser> findAll();
    Mono<PassportUser> save(PassportUser user);
}
