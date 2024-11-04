package ru.ifmo.cs.api_gateway.user.infrastructure.in_memory;

import java.util.LinkedList;
import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import ru.ifmo.cs.api_gateway.user.domain.User;
import ru.ifmo.cs.api_gateway.user.domain.UserRepository;
import ru.ifmo.cs.api_gateway.user.domain.value.Username;

@Repository
@AllArgsConstructor
public class InMemoryStubUserRepository implements UserRepository {
    private final PasswordEncoder passwordEncoder;
    private final List<User> stubStorage = new LinkedList<>();

    @Override
    public User findByUsername(Username username) {
        return stubStorage
                .stream()
                .filter(user -> user.getUsername().equals(username))
                .findAny()
                .orElseThrow();
    }

    @Override
    public void save(User user) {
        user.encodePassword(passwordEncoder);
        insertOrUpdate(user);
    }

    private void insertOrUpdate(User user) {
        stubStorage.removeIf(u -> u.getId().equals(user.getId()));
        stubStorage.add(user);
    }
}
