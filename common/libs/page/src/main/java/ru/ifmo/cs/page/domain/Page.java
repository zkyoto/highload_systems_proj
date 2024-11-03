package ru.ifmo.cs.page.domain;

import java.util.List;

public interface Page<T>{
    List<T> content();
    int pageNumber();
    int pageSize();
    long totalElements();
}
