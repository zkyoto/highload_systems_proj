package ru.ifmo.cs.api_gateway.auth.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import ru.ifmo.cs.jwt_auth.infrastructure.request_filter.JwtTokenAuthenticationFilter;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http,
                                                      JwtTokenAuthenticationFilter authFilter) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .cors(ServerHttpSecurity.CorsSpec::disable)
                .addFilterAt(authFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .authorizeExchange((authorize) -> authorize
//                        .pathMatchers("**").permitAll()
                                .pathMatchers("/actuator/**")
                                    .permitAll()
                                .pathMatchers("/webjars/swagger-ui/**")
                                    .permitAll()
                                .pathMatchers("/v*/api-docs/**")
                                    .permitAll()
                                .pathMatchers("/api-docs/**")
                                    .permitAll()
                                .pathMatchers("/api/v*/auth/authorized-token")
                                    .permitAll()
                                .pathMatchers("/api/v*/auth/**")
                                    .hasAuthority("supervisor")
                                .pathMatchers("/api/v*/interview-results", "/api/v*/interview-results/**")
                                    .hasAnyAuthority("staff", "out-of-scope")
                                .pathMatchers("/api/v*/candidates", "/api/v*/candidates/by-id")
                                    .hasAnyAuthority("hr", "interviewer", "out-of-scope")
                                .pathMatchers("/api/v*/candidates/add")
                                    .hasAnyAuthority("hr", "out-of-scope")
                                .pathMatchers("/api/v*/feedbacks/pending-result")
                                    .hasAnyAuthority("staff", "out-of-scope")
                                .pathMatchers("/api/v*/feedbacks", "/api/v*/feedbacks/**")
                                    .hasAnyAuthority("interviewer", "out-of-scope")
                                .pathMatchers("/api/v*/interviews/cancel")
                                    .hasAnyAuthority("hr", "interviewer", "out-of-scope")
                                .pathMatchers("/api/v*/interviews", "/api/v*/interviews/**")
                                    .hasAnyAuthority("interviewer", "out-of-scope")
                                .pathMatchers("/api/v*/interviewers", "/api/v1/interviewers/**")
                                    .hasAnyAuthority("hr", "staff", "interviewer", "out-of-scope")
                );
        return http.build();
    }

}
