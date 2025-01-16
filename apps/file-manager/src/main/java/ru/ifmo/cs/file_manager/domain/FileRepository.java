package ru.ifmo.cs.file_manager.domain;

import ru.ifmo.cs.file_manager.domain.value.FileExternalId;
import ru.ifmo.cs.file_manager.domain.value.FileId;

public interface FileRepository {
    File findById(FileId fileId);
    File findByExternalId(FileExternalId externalId);
    void save(File file);
}
