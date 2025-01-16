package ru.ifmo.cs.file_manager.domain;

import java.time.Instant;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.ifmo.cs.file_manager.domain.value.FileContent;
import ru.ifmo.cs.file_manager.domain.value.FileExternalId;
import ru.ifmo.cs.file_manager.domain.value.FileId;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class File {
    private final FileId id;
    private final Instant createdAt;
    @NonNull private Instant updatedAt;
    private final String fileName;
    private final FileContent fileContent;
    private final FileExternalId externalId;

    public static File create(String fileName, FileContent fileContent, FileExternalId externalId) {
        Instant now = Instant.now();
        return new File(FileId.generate(), now, now, fileName, fileContent, externalId);
    }

}
