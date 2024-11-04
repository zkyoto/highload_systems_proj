//package ru.ifmo.cs.api_gateway.configuration;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import ru.ifmo.cs.api_gateway.user.domain.User;
//import ru.ifmo.cs.api_gateway.user.domain.value.Password;
//import ru.ifmo.cs.api_gateway.user.domain.value.Role;
//import ru.ifmo.cs.api_gateway.user.domain.value.Username;
//
//@Configuration
//public class UserDetailsConfiguration {
//
//    @Bean
//    public UserDetailsService userDetailsService(
//            InMemoryUserDetailsManager inMemoryUserDetailsManager
//    ) {
//        return inMemoryUserDetailsManager;
//    }
//
//    @Bean
//    public InMemoryUserDetailsManager InMemoryUserDetailsManager() {
//        return new InMemoryUserDetailsManager(
//                User.create(Username.of("z"), Password.of("z"), Role.SUPERVISOR)
//        );
//    }
//}
