package ru.ifmo.cs.passport.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.ifmo.cs.misc.UserId;
import ru.ifmo.cs.passport_contracts.PassportUserResponseDto;

@FeignClient(
        name = "passportFeignClient",
        url="http://passport:81",
        configuration = FeignConfig.class
)
public interface PassportFeignClient {

    @PostMapping("/api/v1/users/create")
    UserId create(@RequestBody String role);

    @GetMapping("/api/v1/users/{userId}")
    PassportUserResponseDto getUser(@PathVariable("userId") long userId);

}
