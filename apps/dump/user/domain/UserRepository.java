package ru.ifmo.cs.api_gateway.user.domain;

import ru.ifmo.cs.api_gateway.user.domain.value.Username;

public interface UserRepository {
    User findByUsername(Username username);
    void save(User user);
}
