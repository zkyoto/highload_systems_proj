package ru.ifmo.cs.jwt_auth.infrastructure.request_filter;

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
import ru.ifmo.cs.jwt_auth.infrastructure.authentication.PassportUserAuthenticationAdapter;
import ru.ifmo.cs.jwt_token.application.JwtResolver;
import ru.ifmo.cs.jwt_token.application.JwtValidator;
import ru.ifmo.cs.misc.UserId;
import ru.ifmo.cs.passport.api.PassportFeignClient;
import ru.ifmo.cs.passport_contracts.PassportUserResponseDto;

@Slf4j
@AllArgsConstructor
public class JwtTokenAuthenticationFilter implements WebFilter {
    private final JwtResolver jwtResolver;
    private final JwtValidator jwtValidator;
    private final PassportFeignClient passportClient;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        logRequestUrl(exchange.getRequest()); // Логирование URL запроса

        return chain.filter(exchange)
                .contextWrite(context -> resolveAuthorityForContext(context, exchange));
    }

    private void logRequestUrl(ServerHttpRequest request) {
        String method = request.getMethod().name();
        String url = request.getURI().toString();
        log.info("Incoming request: {} {}", method, url);
    }

    private Mono<String> extractJwtTokenFromHeaders(ServerHttpRequest request) {
        String headerAuth = request.getHeaders().getFirst("Authorization");
        log.info("Authorization header: {}", headerAuth);
        if (headerAuth == null || !headerAuth.startsWith("Bearer ")) {
            log.info("Authorization failed.");
            return Mono.empty();
        }
        headerAuth = headerAuth.substring(7);
        return Mono.justOrEmpty(jwtValidator.isValid(headerAuth) ? headerAuth : null);
    }

    private Context resolveAuthorityForContext(Context mainContext, ServerWebExchange exchange) {
        return mainContext.putAll(
                compileAuthForContext(exchange)
                        .as(ReactiveSecurityContextHolder::withSecurityContext)
                        .readOnly());
    }

    private Mono<SecurityContextImpl> compileAuthForContext(ServerWebExchange exchange) {
        log.info("Try to authorize request.");
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