package ru.ifmo.cs.page.query;

import ru.ifmo.cs.page.domain.Page;

public interface PageQueryService<T extends Page<?>> {
    T findFor(int page, int size);
}
