package ru.ifmo.cs.passport.api;

import ru.ifmo.cs.misc.Name;
import ru.ifmo.cs.misc.UserId;

public interface PassportClient {
    Name getNameByUserId(UserId userId);
}
