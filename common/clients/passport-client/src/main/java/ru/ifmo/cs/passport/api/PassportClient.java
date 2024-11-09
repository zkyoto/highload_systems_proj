package ru.ifmo.cs.passport.api;

import ru.ifmo.cs.misc.Name;
import ru.ifmo.cs.misc.UserId;
import ru.ifmo.cs.passport.api.domain.PassportUser;

public interface PassportClient {
    PassportUser findPassportUser(UserId userId);
    UserId createUser(Name name, String roleSlug);
    UserId createUser(String fullName, String roleSlug);
    UserId createUser(String firstName, String lastName, String role);
    UserId createUser(String roleSlug);
}
