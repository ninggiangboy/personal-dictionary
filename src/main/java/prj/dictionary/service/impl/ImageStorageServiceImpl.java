package prj.dictionary.service.impl;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;
import prj.dictionary.constant.IConstant;
import prj.dictionary.service.StorageService;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.UUID;

@Service
public class ImageStorageServiceImpl implements StorageService {
    private final Path storageFolder = Paths.get(IConstant.UPLOAD_IMAGE_PATH);

    public ImageStorageServiceImpl() {
        try {
            Files.createDirectories(storageFolder);
        } catch (IOException exception) {
            throw new RuntimeException("Cannot initialize storage", exception);
        }
    }

    private boolean isImageFile(MultipartFile file) {
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        return Arrays.asList(IConstant.IMAGE_FILE_EXTENSIONS)
                .contains(fileExtension.trim().toLowerCase());
    }

    @Override
    public String save(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Failed to store empty file.");
            }
            if (!isImageFile(file)) {
                throw new RuntimeException("You can only upload image file");
            }
            if (file.getSize() > IConstant.MAX_FILE_SIZE_MEGABYTES) {
                throw new RuntimeException("File must be <= 5Mb");
            }
            String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
            String generatedFileName = UUID.randomUUID().toString().replace("-", "");
            generatedFileName = generatedFileName + "." + fileExtension;
            Path destinationFilePath = this.storageFolder.resolve(
                            Paths.get(generatedFileName))
                    .normalize().toAbsolutePath();
            if (!destinationFilePath.getParent().equals(this.storageFolder.toAbsolutePath())) {
                throw new RuntimeException(
                        "Cannot store file outside current directory.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
            }
            return generatedFileName;
        } catch (IOException exception) {
            throw new RuntimeException("Failed to store file.", exception);
        }
    }

    public byte[] load(String fileName) {
        if (fileName == null) {
            return null;
        }
        try {
            Path file = storageFolder.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                try (InputStream inputStream = resource.getInputStream()) {
                    return StreamUtils.copyToByteArray(inputStream);
                }
            } else {
                throw new RuntimeException("Could not read file: " + fileName);
            }
        } catch (IOException exception) {
            throw new RuntimeException("Could not read file: " + fileName, exception);
        }
    }

    @Override
    public void delete(String fileName) {
        try {
            Path file = storageFolder.resolve(fileName);
            Files.deleteIfExists(file);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
