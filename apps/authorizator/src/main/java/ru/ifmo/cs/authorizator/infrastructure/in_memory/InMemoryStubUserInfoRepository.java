package ru.ifmo.cs.authorizator.infrastructure.in_memory;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Repository;
import ru.ifmo.cs.authorizator.domain.UserInfoAggregate;
import ru.ifmo.cs.authorizator.domain.UserInfoRepository;
import ru.ifmo.cs.authorizator.domain.value.UserInfoId;

@Repository
public class InMemoryStubUserInfoRepository implements UserInfoRepository {
    private final List<UserInfoAggregate> stubStorage = new LinkedList<>();

    @Override
    public UserInfoAggregate findById(UserInfoId id) {
        return stubStorage.stream()
                .filter(u -> u.getId().equals(id))
                .findAny()
                .orElseThrow();
    }

    @Override
    public UserInfoAggregate findByUsername(String username) {
        return stubStorage.stream()
                .filter(userInfoAggregate -> userInfoAggregate.getUsername().equals(username))
                .findAny()
                .orElseThrow();
    }

    @Override
    public List<UserInfoAggregate> findAll() {
        return List.copyOf(stubStorage);
    }

    @Override
    public void save(UserInfoAggregate userInfoAggregate) {
        insertOrUpdate(userInfoAggregate);
    }

    private void insertOrUpdate(UserInfoAggregate userInfoAggregate) {
        stubStorage.removeIf(userInfo -> userInfo.getId().equals(userInfoAggregate.getId()));
        stubStorage.add(userInfoAggregate);
    }
}
