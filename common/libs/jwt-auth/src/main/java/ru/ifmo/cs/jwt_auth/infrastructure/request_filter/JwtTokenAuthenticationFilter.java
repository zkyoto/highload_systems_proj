package ru.ifmo.cs.jwt_auth.infrastructure.request_filter;

import java.util.List;
import java.util.function.Consumer;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import ru.ifmo.cs.jwt_auth.application.JwtResolver;
import ru.ifmo.cs.jwt_auth.application.JwtValidator;
import ru.ifmo.cs.jwt_auth.infrastructure.authentication.PassportUserAuthenticationAdapter;
import ru.ifmo.cs.misc.UserId;
import ru.ifmo.cs.passport.api.PassportClient;
import ru.ifmo.cs.passport.api.domain.PassportUser;

@Slf4j
@AllArgsConstructor
public class JwtTokenAuthenticationFilter implements WebFilter {
    private final JwtResolver jwtResolver;
    private final JwtValidator jwtValidator;
    private final PassportClient passportClient;


    private String extractJwtTokenFromHeaders(ServerHttpRequest request) {
        String headerAuth = request.getHeaders().getFirst("Authorization");
        if (StringUtils.hasText(headerAuth)) {
            return headerAuth;
        }
        return null;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        if (!ServerWebExchangeUtils.isAlreadyRouted(exchange)) {
            String jwt = extractJwtTokenFromHeaders(exchange.getRequest());
            if (jwtValidator.isValid(jwt)) {
                UserId userId = jwtResolver.resolveFor(jwt);
                log.info("Token resolved for uid: {}", userId);
                PassportUser passportUser = passportClient.findPassportUser(userId);
                log.info("Passport user: {}", passportUser);
                PassportUserAuthenticationAdapter authentication = PassportUserAuthenticationAdapter.of(passportUser);
                log.info("Authorities: {}", authentication.getAuthorities());
                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);
            }
        }

//        ServerHttpRequest origin = exchange.getRequest().mutate().headers( headers -> headers.setOrigin((String) null)).build();
//        exchange = exchange.mutate().request(origin).build();
//        return chain.filter(exchange.mutate()
//                .request(exchange.getRequest().mutate()
//                        .header("Origin", "HeaderValue")
//                        .build())
//                .build());
//    }

        return chain.filter(exchange);
    }

    class HttpHeadersFromReadOnlyToWritableConverter implements Consumer<HttpHeaders> {

        @Override
        public void accept(HttpHeaders httpHeaders) {
            HttpHeaders writableHttpHeaders = HttpHeaders.writableHttpHeaders(HttpHeaders.EMPTY);
            writableHttpHeaders.put("Origin", List.of("*"));
            httpHeaders = writableHttpHeaders;
        }
    }
}