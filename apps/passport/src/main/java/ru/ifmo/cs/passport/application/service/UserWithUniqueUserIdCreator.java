package ru.ifmo.cs.passport.application.service;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.ifmo.cs.misc.UserId;
import ru.ifmo.cs.misc.util.RandomNameGenerator;
import ru.ifmo.cs.misc.util.RandomUserIdGenerator;
import ru.ifmo.cs.passport.domain.PassportUser;
import ru.ifmo.cs.passport.domain.PassportUserRepository;
import ru.ifmo.cs.passport.domain.value.Role;

@Service
@AllArgsConstructor
public class UserWithUniqueUserIdCreator {
    private final PassportUserRepository passportUserRepository;

    public Mono<UserId> createUserWithUniqueUserIdGuarantee(Role userRole) {
        while (true) {
            try {
                PassportUser user = PassportUser.create(
                        RandomUserIdGenerator.generateRandomUserId(),
                        RandomNameGenerator.generateRandomName(),
                        Arrays.asList(userRole));
                return passportUserRepository.save(user).map(PassportUser::getUid);
            } catch (DataIntegrityViolationException e) {
                //just try again
            }
        }
    }
}
