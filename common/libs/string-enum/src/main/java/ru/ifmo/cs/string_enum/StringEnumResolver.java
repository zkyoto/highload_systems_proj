package ru.ifmo.cs.string_enum;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringEnumResolver<T extends Enum<T> & StringEnum> {
    private final Class<T> enumClass;
    private final Map<String, T> enumsByValue;

    private StringEnumResolver(Class<T> enumClass) {
        this.enumClass = enumClass;
        enumsByValue = Stream.of(enumClass.getEnumConstants()).collect(Collectors.toMap(StringEnum::value, e -> e));
    }

    public Optional<T> fromValueO(String value) {
        return Optional.ofNullable(enumsByValue.get(value));
    }

    public T fromValue(String value) {
        Objects.requireNonNull(value, "value must not be null; for enum of type " + enumClass.getName());
        return fromValueO(value)
                .orElseThrow(() -> new NoSuchElementException(
                        "no enum of type " + enumClass.getName() + " found with value " + value)
                );
    }

    public static <T extends Enum<T> & StringEnum> StringEnumResolver<T> r(Class<T> enumClass) {
        return new StringEnumResolver<>(enumClass);
    }
}
