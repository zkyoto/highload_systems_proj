package ru.ifmo.cs.misc;

public record Name(String firstName, String lastName, String fullName) {
    public static Name of(String firstName, String lastName) {
        return new Name(firstName, lastName, firstName + ' ' + lastName);
    }

    public static Name of(String fullName) {
        String[] splittedFullName = fullName.split(" ", 2);
        return new Name(splittedFullName[0], splittedFullName[1], fullName);
    }
}