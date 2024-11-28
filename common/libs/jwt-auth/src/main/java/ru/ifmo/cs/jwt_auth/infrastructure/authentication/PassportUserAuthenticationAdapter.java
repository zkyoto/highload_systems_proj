package ru.ifmo.cs.jwt_auth.infrastructure.authentication;

import java.util.Collection;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.ifmo.cs.passport.api.domain.PassportUser;
import ru.ifmo.cs.passport.domain.value.Role;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PassportUserAuthenticationAdapter implements Authentication {
    private final PassportUser user;
    private boolean isAuthenticated = false;

    public static PassportUserAuthenticationAdapter of(PassportUser user) {
        return new PassportUserAuthenticationAdapter(user);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.roles().stream()
                .map(Role::value)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return user.uid();
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.isAuthenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return user.name().fullName();
    }
}
