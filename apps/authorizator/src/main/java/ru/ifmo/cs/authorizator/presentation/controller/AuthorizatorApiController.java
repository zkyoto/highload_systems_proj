package ru.ifmo.cs.authorizator.presentation.controller;

import java.time.Instant;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.cs.authorizator.application.command.RegisterUserCommand;
import ru.ifmo.cs.authorizator.application.service.AuthorizationProcessor;
import ru.ifmo.cs.authorizator.presentation.controller.dto.AuthorizedUserTokenResponseBodyDto;
import ru.ifmo.cs.authorizator.presentation.controller.dto.RegisterUserRequestBodyDto;
import ru.itmo.cs.command_bus.CommandBus;

@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)

@OpenAPIDefinition(
        servers = {
                @Server(url = "/", description = "Gateway server")
        }
)
@Slf4j
@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class AuthorizatorApiController {
    private final CommandBus commandBus;
    private final AuthorizationProcessor authorizationProcessor;

    @Operation(summary = "Register a new user", description = "Register a new user with username, password, and role.")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping("/api/v1/auth/register")
    public ResponseEntity<?> registerUser(
            @RequestBody RegisterUserRequestBodyDto registerUserRequestBodyDto
    ) {
        log.info("Register user request: {}", registerUserRequestBodyDto);
        commandBus.submit(new RegisterUserCommand(
                registerUserRequestBodyDto.username(),
                registerUserRequestBodyDto.password(),
                registerUserRequestBodyDto.roleSlug()
        ));
        log.info(Instant.now().toString());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Authorize a user", description = "Authorize a user and return a JWT token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User authorized successfully", content = @Content(schema = @Schema(implementation = AuthorizedUserTokenResponseBodyDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/api/v1/auth/authorized-token")
    public ResponseEntity<?> authorize(
            @RequestParam("username") String username,
            @RequestParam("password") String password
    ) {
        String jwt = authorizationProcessor.authorize(username, password);
        return ResponseEntity.ok().body(new AuthorizedUserTokenResponseBodyDto(jwt));
    }
}