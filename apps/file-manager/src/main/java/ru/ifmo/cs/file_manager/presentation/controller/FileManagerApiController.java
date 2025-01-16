package ru.ifmo.cs.file_manager.presentation.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ru.ifmo.cs.file_manager.application.SaveFileCommand;
import ru.ifmo.cs.file_manager.domain.File;
import ru.ifmo.cs.file_manager.domain.FileRepository;
import ru.ifmo.cs.file_manager.domain.value.FileContent;
import ru.ifmo.cs.file_manager.domain.value.FileExternalId;
import ru.ifmo.cs.file_manager.presentation.controller.dto.FileByExternalidResponseBodyDto;
import ru.ifmo.cs.file_manager.presentation.controller.dto.FilePayload;
import ru.itmo.cs.command_bus.CommandBus;

@Slf4j
@Controller
@AllArgsConstructor
public class FileManagerApiController {
    private final CommandBus commandBus;
    private final FileRepository fileRepository;

    @MessageMapping("/upload-file")
    @SendTo("/topic/ids")
    public String receiveFile(@Payload FilePayload filePayload) {
        FileExternalId externalId = FileExternalId.generate();
        FileContent content = FileContent.fromBase64(filePayload.fileContentBase64());
        commandBus.submit(
                new SaveFileCommand(
                        filePayload.fileName(),
                        content,
                        externalId
                )
        );

        return externalId.value();
    }

    @MessageMapping("/download-file")
    @SendTo("/topic/file-content")
    public FileByExternalidResponseBodyDto downloadFile(@Payload String fileExternalId) {
        FileExternalId externalId = new FileExternalId(fileExternalId);
        File file = fileRepository.findByExternalId(externalId);

        if (file == null) {
            throw new RuntimeException("File not found for ID: " + fileExternalId);
        }

        return FileByExternalidResponseBodyDto.fromEntity(file);
    }
}