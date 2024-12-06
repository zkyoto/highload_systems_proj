package ru.ifmo.cs.passport.api.stub;

import java.util.List;
import java.util.Random;

import ru.ifmo.cs.misc.Name;
import ru.ifmo.cs.misc.UserId;
import ru.ifmo.cs.passport.api.PassportFeignClient;
import ru.ifmo.cs.passport_contracts.PassportUserResponseDto;

public class PassportFeignClientStub implements PassportFeignClient {

    @Override
    public UserId create(String role) {
        return UserId.of(new Random().nextLong());
    }

    @Override
    public PassportUserResponseDto getUser(long userId) {
        return new PassportUserResponseDto(UserId.of(userId), Name.of("test name"), List.of());
    }
}

