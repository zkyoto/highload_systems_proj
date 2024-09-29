package ru.ifmo.cs.misc;

import java.util.Objects;

public final class UserId {
    private final long uid;

    public static UserId of(long uid) {
        return new UserId(uid);
    }

    public static UserId of(String uid) {
        return new UserId(Long.parseLong(uid));
    }

    public long getUid() {
        return uid;
    }

    private UserId(long uid) {
        this.uid = uid;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (UserId) obj;
        return this.uid == that.uid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid);
    }

    @Override
    public String toString() {
        return "UserId[" +
                "uid=" + uid + ']';
    }

}
