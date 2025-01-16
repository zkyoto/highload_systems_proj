package ru.ifmo.cs.file_manager.application;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ifmo.cs.file_manager.domain.File;
import ru.ifmo.cs.file_manager.domain.FileRepository;
import ru.itmo.cs.command_bus.CommandHandler;

@Service
@AllArgsConstructor
public class SaveFileCommandHandler implements CommandHandler<SaveFileCommand> {
    private final FileRepository fileRepository;

    @Override
    public void handle(SaveFileCommand command) {
        File file = File.create(command.filename, command.content, command.externalId);
        fileRepository.save(file);
    }
}
