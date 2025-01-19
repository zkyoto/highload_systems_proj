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
    private static final String SUPERVISOR_AUTHORITY = "supervisor";
    private static final String STAFF_AUTHORITY = "staff";
    private static final String INTERVIEWER_AUTHORITY = "interviewer";
    private static final String HR_AUTHORITY = "hr";
    private static final String OUT_OF_SCOPE_AUTHORITY = "out-of-scope";

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http,
                                                      JwtTokenAuthenticationFilter authFilter) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .cors(ServerHttpSecurity.CorsSpec::disable)
                .addFilterAt(authFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .authorizeExchange(authorize -> authorize
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
                                .pathMatchers("/availability")
                                    .permitAll()
                                .pathMatchers("/switch")
                                    .permitAll()
                                .pathMatchers("/api/v*/auth/**")
                                    .hasAuthority(SUPERVISOR_AUTHORITY)
                                .pathMatchers("/api/v*/interview-results", "/api/v*/interview-results/**")
                                    .hasAnyAuthority(STAFF_AUTHORITY, OUT_OF_SCOPE_AUTHORITY)
                                .pathMatchers("/api/v*/candidates", "/api/v*/candidates/by-id")
                                    .hasAnyAuthority(HR_AUTHORITY, INTERVIEWER_AUTHORITY, OUT_OF_SCOPE_AUTHORITY)
                                .pathMatchers("/api/v*/candidates/add")
                                    .hasAnyAuthority(HR_AUTHORITY, OUT_OF_SCOPE_AUTHORITY)
                                .pathMatchers("/api/v*/feedbacks/pending-result")
                                    .hasAnyAuthority(STAFF_AUTHORITY, OUT_OF_SCOPE_AUTHORITY)
                                .pathMatchers("/api/v*/feedbacks", "/api/v*/feedbacks/**")
                                    .hasAnyAuthority(INTERVIEWER_AUTHORITY, OUT_OF_SCOPE_AUTHORITY)
                                .pathMatchers("/api/v*/interviews/cancel")
                                    .hasAnyAuthority(HR_AUTHORITY, INTERVIEWER_AUTHORITY, OUT_OF_SCOPE_AUTHORITY)
                                .pathMatchers("/api/v*/interviews", "/api/v*/interviews/**")
                                    .hasAnyAuthority(INTERVIEWER_AUTHORITY, OUT_OF_SCOPE_AUTHORITY)
                                .pathMatchers("/api/v*/interviewers", "/api/v1/interviewers/**")
                                    .hasAnyAuthority(HR_AUTHORITY, STAFF_AUTHORITY, INTERVIEWER_AUTHORITY, OUT_OF_SCOPE_AUTHORITY)
                );
        return http.build();
    }

}
