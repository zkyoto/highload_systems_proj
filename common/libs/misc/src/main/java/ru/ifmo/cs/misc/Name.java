package ru.ifmo.cs.misc;

import java.util.Objects;

public record Name(String firstName, String lastName, String fullName) {
    public static Name of(String firstName, String lastName) {
        Objects.requireNonNull(firstName);
        Objects.requireNonNull(lastName);
        if (firstName.isEmpty()) {
            throw new IllegalArgumentException("Empty first name");
        }
        if (lastName.isEmpty()) {
            throw new IllegalArgumentException("Empty second name");
        }
        return new Name(firstName, lastName, firstName + ' ' + lastName);
    }

    public static Name of(String fullName) {
        Objects.requireNonNull(fullName);
        if (fullName.isEmpty()) {
            throw new IllegalArgumentException("Empty full name");
        }
        String[] splittedFullName = fullName.split(" ", 2);
        return new Name(splittedFullName[0], splittedFullName[1], fullName);
    }
}