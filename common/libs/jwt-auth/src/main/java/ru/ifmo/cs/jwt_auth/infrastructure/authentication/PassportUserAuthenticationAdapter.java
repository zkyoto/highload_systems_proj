package ru.ifmo.cs.jwt_auth.infrastructure.authentication;

import java.util.Collection;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.ifmo.cs.passport_contracts.PassportUserResponseDto;

@Slf4j
public class PassportUserAuthenticationAdapter implements Authentication {
    private final List<SimpleGrantedAuthority> authorities;
    private final String name;
    private boolean isAuthenticated;

    private PassportUserAuthenticationAdapter(List<SimpleGrantedAuthority> authorities, String name) {
        this.authorities = authorities;
        this.name = name;
        this.isAuthenticated = true;
    }

    public static PassportUserAuthenticationAdapter of(PassportUserResponseDto user) {
        return new PassportUserAuthenticationAdapter(
                user.roles()
                        .stream()
                        .map(SimpleGrantedAuthority::new)
                        .toList(),
                user.name()
                        .fullName());
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        log.info("Get authorities: {}", authorities);
        return authorities;
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
        return null;
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
        return name;
    }
}
