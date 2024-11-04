package ru.ifmo.cs.jwt_auth.infrastructure.request_filter;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.ifmo.cs.jwt_auth.application.JwtResolver;
import ru.ifmo.cs.jwt_auth.application.JwtValidator;
import ru.ifmo.cs.jwt_auth.infrastructure.authentication.PassportUserAuthenticationAdapter;
import ru.ifmo.cs.misc.UserId;
import ru.ifmo.cs.passport.api.PassportClient;

@AllArgsConstructor
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {
    private final JwtResolver jwtResolver;
    private final JwtValidator jwtValidator;
    private final PassportClient passportClient;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String jwt = extractJwtTokenFromHeaders(request);
        if (jwtValidator.isValid(jwt)) {
            UserId userId = jwtResolver.resolveFor(jwt);
            SecurityContextHolder.getContext()
                    .setAuthentication(PassportUserAuthenticationAdapter.of(passportClient.findPassportUser(userId)));
        }

        filterChain.doFilter(request, response);
    }

    private String extractJwtTokenFromHeaders(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth)) {
            return headerAuth;
        }
        return null;
    }
}