package budkevych.dcsapi.service.impl;

import budkevych.dcsapi.service.FileService;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.codec.binary.Base64;
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

    @Override
    public String readImage(String path) {
        byte[] fileContent;
        try {
            fileContent = FileUtils.readFileToByteArray(new File(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Base64.encodeBase64String(fileContent);
    }

    @Override
    public void writeJpg(String path) {
        byte[] decodedBytes = Base64.decodeBase64(path);
        try {
            FileUtils.writeByteArrayToFile(new File(path), decodedBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
