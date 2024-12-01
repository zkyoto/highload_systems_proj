package ru.ifmo.cs.jwt_auth.infrastructure.request_filter;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;
import ru.ifmo.cs.jwt_auth.application.JwtResolver;
import ru.ifmo.cs.jwt_auth.application.JwtValidator;
import ru.ifmo.cs.jwt_auth.infrastructure.authentication.PassportUserAuthenticationAdapter;
import ru.ifmo.cs.misc.Name;
import ru.ifmo.cs.misc.UserId;
import ru.ifmo.cs.passport.api.PassportFeignClient;
import ru.ifmo.cs.passport_contracts.PassportUserResponseDto;

@Slf4j
@AllArgsConstructor
public class JwtTokenAuthenticationFilter implements WebFilter {
    private final JwtResolver jwtResolver;
    private final JwtValidator jwtValidator;
    private final PassportFeignClient passportClient;


    private Mono<String> extractJwtTokenFromHeaders(ServerHttpRequest request) {
        String headerAuth = request.getHeaders().getFirst("Authorization");
        return Mono.justOrEmpty(jwtValidator.isValid(headerAuth) ? headerAuth : null);
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return chain.filter(exchange)
                .contextWrite(context -> resolveAuthorityForContext(context, exchange));
    }

    private Context resolveAuthorityForContext(Context mainContext, ServerWebExchange exchange) {
        return mainContext.putAll(
                compileAuthForContext(exchange)
                        .as(ReactiveSecurityContextHolder::withSecurityContext)
                        .readOnly());
    }

    private Mono<SecurityContextImpl> compileAuthForContext(ServerWebExchange exchange) {
        return extractJwtTokenFromHeaders(exchange.getRequest())
                .map(this::createAuthFromToken)
                .map(SecurityContextImpl::new);
    }

    private Authentication createAuthFromToken(String token) {
        UserId userId = jwtResolver.resolveFor(token);
        log.info("Token resolved for uid: {}", userId);
        PassportUserResponseDto passportUser = passportClient.getUser(userId.getUid());
        log.info("Passport user: {}", passportUser);
        PassportUserAuthenticationAdapter authentication =
                PassportUserAuthenticationAdapter.of(passportUser);
        log.info("Authorities: {}", authentication.getAuthorities());
        return authentication;
    }
}