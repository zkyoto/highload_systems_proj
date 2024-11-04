package ru.ifmo.cs.api_gateway.user.domain;

import java.util.Collection;
import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.ifmo.cs.api_gateway.user.domain.value.Password;
import ru.ifmo.cs.api_gateway.user.domain.value.Role;
import ru.ifmo.cs.api_gateway.user.domain.value.UserId;
import ru.ifmo.cs.api_gateway.user.domain.value.Username;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class User implements UserDetails {
    private final UserId id;
    private final Username username;
    @NonNull Password password;
    private final Role role;

    public static User create(Username username, Password password, Role role) {
        return new User(UserId.generate(), username, password, role);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.value()));
    }

    @Override
    public String getPassword() {
        return password.getValue();
    }

    @Override
    public String getUsername() {
        return username.getValue();
    }

    public void encodePassword(PasswordEncoder encoder) {
        password = Password.of(encoder.encode(password.getValue()));
    }

}
