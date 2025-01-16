package ru.ifmo.cs.file_manager.application;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.ifmo.cs.file_manager.domain.value.FileContent;
import ru.ifmo.cs.file_manager.domain.value.FileExternalId;
import ru.itmo.cs.command_bus.Command;

@AllArgsConstructor
@FieldDefaults(makeFinal = true)
public class SaveFileCommand implements Command {
    String filename;
    FileContent content;
    FileExternalId externalId;
}
