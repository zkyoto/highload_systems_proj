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

    @Bean
        public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http, JwtTokenAuthenticationFilter authFilter) throws Exception {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .cors(ServerHttpSecurity.CorsSpec::disable)
                .addFilterAt(authFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .authorizeExchange((authorize) -> authorize
//                                .pathMatchers("**").permitAll()
                        .pathMatchers("/actuator/**").permitAll()
                        .pathMatchers("/api/v*/users/authorized-token").permitAll()
                        .pathMatchers("/api/v*/users/register").hasRole("supervisor")
                        .pathMatchers("/api/v*/interview-results, /api/v*/interview-results/**").hasRole("staff")
                        .pathMatchers("/api/v*/candidates/add").hasRole("hr")
                        .pathMatchers("/api/v*/candidates, /api/v*/candidates/by-id").hasAnyRole("hr", "interviewer")
                        .pathMatchers("/api/v*/feedbacks/pending-result").hasRole("staff")
                        .pathMatchers("/api/v*/feedbacks, /api/v*/feedbacks/**").hasRole("interviewer")
                        .pathMatchers("/api/v*/interviews/cancel").hasAnyRole("hr", "interviewer")
                        .pathMatchers("/api/v*/interviews, /api/v*/interviews/**").hasRole("interviewer")
                        .pathMatchers("/api/v*/interviewers, /api/v1/interviewers/**").hasAnyRole("hr", "staff")
                        .anyExchange().denyAll()
                );
        return http.build();
    }

}
