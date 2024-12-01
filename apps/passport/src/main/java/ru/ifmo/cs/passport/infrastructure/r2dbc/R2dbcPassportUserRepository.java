package ru.ifmo.cs.passport.infrastructure.r2dbc;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.ifmo.cs.misc.UserId;
import ru.ifmo.cs.passport.domain.PassportUser;
import ru.ifmo.cs.passport.domain.PassportUserRepository;
import ru.ifmo.cs.passport.domain.event.PassportUserCreatedEvent;
import ru.ifmo.cs.passport.domain.value.PassportUserId;
import ru.ifmo.cs.passport.infrastructure.r2dbc.entity.R2dbcPassportUserEntity;

@Primary
@Repository
@AllArgsConstructor
@Slf4j
public class R2dbcPassportUserRepository implements PassportUserRepository {
    private final R2dbcEntityTemplate template;

    @Override
    public Mono<PassportUser> findById(PassportUserId userId) {
        return template
                .select(Query.query(Criteria.where("id").is(userId.value())), R2dbcPassportUserEntity.class)
                .next()
                .map(PassportUser::hydrate);
    }

    @Override
    public Mono<PassportUser> findByUserId(UserId userId) {
        return template
                .select(Query.query(Criteria.where("uid").is(userId.getUid())), R2dbcPassportUserEntity.class)
                .next()
                .map(PassportUser::hydrate);
    }

    @Override
    public Flux<PassportUser> findAll() {
        return template.select(R2dbcPassportUserEntity.class).all().map(PassportUser::hydrate);
    }

    @Override
    public Mono<PassportUser> save(PassportUser user) {
        boolean isNew = user.releaseEvents().stream().anyMatch(event -> event instanceof PassportUserCreatedEvent);
        if (isNew) {
            return insert(user);
        } else {
            return update(user);
        }
    }

    private Mono<PassportUser> insert(PassportUser user) {
        return template.insert(R2dbcPassportUserEntity.from(user)).map(PassportUser::hydrate);
    }

    private Mono<PassportUser> update(PassportUser user) {
        return template.update(R2dbcPassportUserEntity.from(user)).map(PassportUser::hydrate);
    }
}
