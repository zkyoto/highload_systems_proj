package ru.itmo.cs.app.interviewing.libs.page.application.query;

import ru.itmo.cs.app.interviewing.libs.page.domain.Page;

public interface PageQueryService<T extends Page<?>> {
    T findFor(int page, int size);
}
