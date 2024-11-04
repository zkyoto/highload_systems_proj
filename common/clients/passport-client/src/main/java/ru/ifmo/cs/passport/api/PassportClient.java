package ru.ifmo.cs.passport.api;

import ru.ifmo.cs.misc.UserId;
import ru.ifmo.cs.passport.api.domain.PassportUser;

public interface PassportClient {
    PassportUser findPassportUser(UserId userId);
}
