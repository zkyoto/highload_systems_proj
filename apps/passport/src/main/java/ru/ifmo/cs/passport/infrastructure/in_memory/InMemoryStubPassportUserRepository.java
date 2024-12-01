package ru.ifmo.cs.passport.infrastructure.in_memory;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.ifmo.cs.misc.UserId;
import ru.ifmo.cs.passport.domain.PassportUserRepository;
import ru.ifmo.cs.passport.domain.PassportUser;
import ru.ifmo.cs.passport.domain.exception.DuplicateUserIdException;
import ru.ifmo.cs.passport.domain.value.PassportUserId;

@Repository
public class InMemoryStubPassportUserRepository implements PassportUserRepository {
    private final List<PassportUser> users = new LinkedList<>();

    @Override
    public Mono<PassportUser> findById(PassportUserId userId) {
        return Mono.just(users
                .stream()
                .filter(user -> user.getId().equals(userId))
                .findFirst()
                .orElseThrow());
    }

    @Override
    public Mono<PassportUser> findByUserId(UserId userId) {
        return Mono.just(users
                .stream()
                .filter(user -> user.getUid().equals(userId))
                .findFirst()
                .orElseThrow());
    }

    @Override
    public Flux<PassportUser> findAll() {
        return Flux.fromStream(users.stream());
    }

    @Override
    public Mono<PassportUser> save(PassportUser user) {
        boolean update = users.removeIf(passportUser -> passportUser.getId().equals(user.getId()));
        if (update) {
            users.add(user);
            return Mono.empty();
        } else {
            boolean isDuplicateUid = users
                    .stream()
                    .anyMatch(passportUser -> passportUser.getUid().equals(user.getUid()));
            if (isDuplicateUid) {
                throw new DuplicateUserIdException();
            } else {
                users.add(user);
                return Mono.empty();
            }
        }
    }
}
