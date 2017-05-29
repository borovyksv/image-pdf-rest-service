package hello.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class FileService {
    @Value("${upload.file.storage}")
    private String uploadFileStorage;

    public void store(MultipartFile file) {
        try {
            if (Files.exists(Paths.get(uploadFileStorage + file.getOriginalFilename()))) {
                System.out.println("File: " + file.getOriginalFilename() + " exists");
            } else {
                Files.copy(file.getInputStream(), Paths.get(uploadFileStorage).resolve(file.getOriginalFilename()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
