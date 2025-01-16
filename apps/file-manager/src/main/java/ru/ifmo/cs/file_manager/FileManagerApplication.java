package ru.ifmo.cs.file_manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import ru.ifmo.cs.file_manager.configuration.WebSocketConfiguration;

@SpringBootApplication
@Import(WebSocketConfiguration.class)
public class FileManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileManagerApplication.class, args);
    }

}
