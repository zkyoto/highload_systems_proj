package ru.ifmo.cs.domain_event.domain.stored_event;

import ru.ifmo.cs.string_enum.StringEnum;
import ru.ifmo.cs.string_enum.StringEnumResolver;

public enum StoredDomainEventStatus implements StringEnum {
    STORED("stored"),
    DELIVERED("delivered"),
    ;

    public static final StringEnumResolver<StoredDomainEventStatus> R =
            StringEnumResolver.r(StoredDomainEventStatus.class);
    private final String value;

    StoredDomainEventStatus(String value) {
        this.value = value;
    }

    @Override
    public String value() {
        return value;
    }
}
