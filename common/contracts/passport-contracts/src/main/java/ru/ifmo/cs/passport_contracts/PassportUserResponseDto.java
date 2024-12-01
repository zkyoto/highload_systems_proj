package ru.ifmo.cs.passport_contracts;

import java.util.List;

import ru.ifmo.cs.misc.Name;
import ru.ifmo.cs.misc.UserId;

public record PassportUserResponseDto(
        UserId uid,
        Name name,
        List<String> roles
) {}
