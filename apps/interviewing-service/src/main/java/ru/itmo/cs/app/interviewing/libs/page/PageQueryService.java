package ru.itmo.cs.app.interviewing.libs.page;

public interface PageQueryService<T extends Page<?>> {
    T findFor(int page, int size);
}
