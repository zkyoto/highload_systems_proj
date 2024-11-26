package ru.ifmo.cs.authorizator_client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.ifmo.cs.authorizator.contracts.request.AuthorizeUserRequestDto;
import ru.ifmo.cs.authorizator.contracts.request.RegisterUserRequestBodyDto;
import ru.ifmo.cs.authorizator.contracts.response.AuthorizedUserTokenResponseBodyDto;

@FeignClient(
        name = "authorizatorFeignClient",
        url="lb://authorizator",
        configuration = FeignConfig.class
)
public interface AuthorizatorFeignClient {

    @PostMapping("/api/v1/users/register")
    ResponseEntity<?> registerUser(
            @RequestBody RegisterUserRequestBodyDto registerUserRequestBodyDto
    );

    @GetMapping("/api/v1/users/authorized-token")
    ResponseEntity<AuthorizedUserTokenResponseBodyDto> authorize(
            @RequestParam("authorization_credentials") AuthorizeUserRequestDto authorizeUserRequestDto
    );
}
