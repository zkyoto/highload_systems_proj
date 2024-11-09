package ru.ifmo.cs.authorizator.domain;

import java.util.List;

import ru.ifmo.cs.authorizator.domain.value.UserInfoId;

public interface UserInfoRepository {
    UserInfoAggregate findById(UserInfoId id);
    UserInfoAggregate findByUsername(String username);
    List<UserInfoAggregate> findAll();
    void save(UserInfoAggregate userInfoAggregate);
}
