package ru.ifmo.cs.misc.util;

import java.util.Random;

import ru.ifmo.cs.misc.UserId;

public class RandomUserIdGenerator {
    private static final Random rand = new Random();

    public static UserId generateRandomUserId() {
        long uid = Math.abs(rand.nextLong());
        return UserId.of(uid);
    }
}
