package prj.dictionary.service;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    String save(MultipartFile file);

    byte[] load(String fileName);

    void delete(String fileName);
}
