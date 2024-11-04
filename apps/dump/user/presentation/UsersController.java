package ru.ifmo.cs.api_gateway.user.presentation;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.cs.api_gateway.user.application.JwtService;
import ru.ifmo.cs.api_gateway.user.domain.User;
import ru.ifmo.cs.api_gateway.user.domain.UserRepository;
import ru.ifmo.cs.api_gateway.user.domain.value.Password;
import ru.ifmo.cs.api_gateway.user.domain.value.Role;
import ru.ifmo.cs.api_gateway.user.domain.value.Username;
import ru.ifmo.cs.api_gateway.user.presentation.dto.RegisterUserRequestBodyDto;

@RestController
@RequestMapping(path = "/api/user")
@AllArgsConstructor
public class UsersController {
    private final InMemoryUserDetailsManager userDetailsService;
    private final JwtService jwtService;

    @PostMapping("/register")
    @PreAuthorize("hasRole('supervisor')")
    public ResponseEntity<?> register(@RequestBody RegisterUserRequestBodyDto registerUserRequestBodyDto) {
        userDetailsService.createUser(User.create(
                Username.of(registerUserRequestBodyDto.username()),
                Password.of(registerUserRequestBodyDto.password()),
                Role.R.fromValue(registerUserRequestBodyDto.role())
        ));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/auth-token")
    public ResponseEntity<?> getAuthToken(
            @RequestParam String username,
            @RequestParam String password
    ) {
        try {
            if (userDetailsService.loadUserByUsername(username).getPassword().equals(password)) {
                return ResponseEntity.ok(jwtService.generateTokenForUsername(username));
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

