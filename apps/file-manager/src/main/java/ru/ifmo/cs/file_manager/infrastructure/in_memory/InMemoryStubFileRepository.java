package ru.ifmo.cs.file_manager.infrastructure.in_memory;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Repository;
import ru.ifmo.cs.file_manager.domain.File;
import ru.ifmo.cs.file_manager.domain.FileRepository;
import ru.ifmo.cs.file_manager.domain.value.FileExternalId;
import ru.ifmo.cs.file_manager.domain.value.FileId;

@Repository
public class InMemoryStubFileRepository implements FileRepository {
    private final List<File> files = new LinkedList<>();

    @Override
    public File findById(FileId fileId) {
        return files
                .stream()
                .filter(file -> file.getId().equals(fileId))
                .findAny()
                .orElseThrow();
    }

    @Override
    public File findByExternalId(FileExternalId externalId) {
        return files
                .stream()
                .filter(file -> file.getExternalId().equals(externalId))
                .findAny()
                .orElseThrow();
    }

    @Override
    public void save(File file) {
        files.removeIf(f -> f.getId().equals(file.getId()));
        files.add(file);
    }
}
