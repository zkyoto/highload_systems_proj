package ru.ifmo.cs.api_gateway.auth.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import ru.ifmo.cs.jwt_auth.infrastructure.request_filter.JwtTokenAuthenticationFilter;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

//    @Bean
//    public SecurityFilterChain securityFilterChain(ServerHttpSecurity http, JwtTokenAuthenticationFilter authFilter) throws Exception {
//        return  http
//                .csrf(ServerHttpSecurity.CsrfSpec::disable)
//                .cors(ServerHttpSecurity.CorsSpec::disable)
//                .addFilterBefore(authxFilter, UsernamePasswordAuthenticationFilter.class)
//                .authorizeHttpRequests(auth -> auth.requestMatchers("/**").permitAll()
//                        )
//                .build();
//    }

    // ...
    @Bean
        public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http, JwtTokenAuthenticationFilter authFilter) throws Exception {
        http
                .addFilterAt(authFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .authorizeExchange((authorize) -> authorize
                        .pathMatchers("/**").permitAll()
//                        .pathMatchers("/authorizator/get-token").permitAll()
//                        .pathMatchers("/authorizator/create-user").hasRole("supervisor")
//                        .pathMatchers("/admin/**").hasRole("ADMIN")
//                        .pathMatchers("/db/**")
//                        .access((authentication, context) ->
//                                hasRole("ADMIN").check(authentication, context)
//                                        .filter(decision -> !decision.isGranted())
//                                        .switchIfEmpty(hasRole("DBA").check(authentication, context))
//                        )
//                        .anyExchange().denyAll()
                );
        return http.build();
    }

}
