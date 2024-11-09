package ru.ifmo.cs.authorizator.presentation.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.cs.authorizator.application.command.RegisterUserCommand;
import ru.ifmo.cs.authorizator.application.service.AuthorizationProcessor;
import ru.ifmo.cs.authorizator.presentation.controller.dto.request.AuthorizeUserRequestDto;
import ru.ifmo.cs.authorizator.presentation.controller.dto.request.RegisterUserRequestBodyDto;
import ru.ifmo.cs.authorizator.presentation.controller.dto.response.AuthorizedUserTokenResponseBodyDto;
import ru.itmo.cs.command_bus.CommandBus;

@RestController
@AllArgsConstructor
public class AuthorizatorApiController {
    private final CommandBus commandBus;
    private final AuthorizationProcessor authorizationProcessor;

    @PostMapping("/api/v1/users/register")
    public ResponseEntity<?> registerUser(
            @RequestBody RegisterUserRequestBodyDto registerUserRequestBodyDto
    ) {
        commandBus.submit(new RegisterUserCommand(
                registerUserRequestBodyDto.username(),
                registerUserRequestBodyDto.password(),
                registerUserRequestBodyDto.roleSlug()
        ));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/v1/users/authorized-token")
    public ResponseEntity<?> authorize(@RequestParam("authorization_credentials") AuthorizeUserRequestDto authorizeUserRequestDto) {
        String jwt = authorizationProcessor.authorize(
                authorizeUserRequestDto.username(),
                authorizeUserRequestDto.password()
        );
        return ResponseEntity.ok().body(new AuthorizedUserTokenResponseBodyDto(jwt));
    }
}
