package budkevych.dcsapi.service;

public interface FileService {
    String readAll(String path);

    void writeString(String content, String path);
}
