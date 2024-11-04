package ru.ifmo.cs.passport.api.domain;

import java.util.List;

import ru.ifmo.cs.misc.Name;
import ru.ifmo.cs.misc.UserId;
import ru.ifmo.cs.passport.api.domain.value.Role;

public record PassportUser(
        UserId uid,
        Name name,
        List<Role> roles
) {}
