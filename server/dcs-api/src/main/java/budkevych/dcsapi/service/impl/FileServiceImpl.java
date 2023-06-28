package budkevych.dcsapi.service.impl;

import budkevych.dcsapi.service.FileService;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String readAll(String path) {
        Path filePath = Path.of(path);
        try {
            List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
            return String.join("", lines);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file on " + path, e);
        }
    }

    @Override
    public void writeString(String content, String path) {
        Path filePath = Path.of(path);
        try {
            Files.writeString(filePath, content);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write content to file on " + path, e);
        }
    }
}
