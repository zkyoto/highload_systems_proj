package ru.ifmo.cs.file_manager;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.Base64;

@Slf4j
@Controller
public class FileController {
    private final static String DOWNLOADS_DIR = "uploads";

    @MessageMapping("/upload-file")
    public void receiveFile(@Payload FileMessage fileMessage) {
        saveFile(generateSalt() + "_" + fileMessage.fileName(), fileMessage.fileContentBase64());
    }

    private void saveFile(String fileName, String fileContentBase64) {
        byte[] data = Base64.getDecoder().decode(fileContentBase64);
        ensureForDirectoryExists(DOWNLOADS_DIR);
        try (FileOutputStream fos = new FileOutputStream(DOWNLOADS_DIR + "/" + fileName)) {
            fos.write(data);
            log.info("Saved file: {}", fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void ensureForDirectoryExists(String pathToDir) {
        new File(pathToDir).mkdirs();
    }

    private static String generateSalt() {
        return Instant.now().toString();
    }
}
