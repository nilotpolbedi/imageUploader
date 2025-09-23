package com.neel.imageUploader.service;

import com.neel.imageUploader.model.ImageEntity;
import com.neel.imageUploader.repository.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Service
public class ImageService {

    private final ImageRepository repo;

    // Folder to store uploaded files
    private static final String UPLOAD_DIR = "uploads/";

    public ImageService(ImageRepository repo) {
        this.repo = repo;

        // Ensure folder exists
        File uploadFolder = new File(UPLOAD_DIR);
        if (!uploadFolder.exists()) {
            uploadFolder.mkdirs();
        }
    }

    @Transactional
    public ImageEntity save(MultipartFile file) throws IOException {
        // 1. Resolution check
        if (file.getContentType() != null && file.getContentType().startsWith("image/")) {
            BufferedImage image = ImageIO.read(file.getInputStream());
            if (image != null) {
                int width = image.getWidth();
                int height = image.getHeight();

                if (width > 1920 || height > 1080) {
                    throw new RuntimeException("Image resolution exceeds 1920x1080");
                }
            }
        }

        // 2. Generate unique file name to avoid clashes
        String uniqueFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_DIR + uniqueFileName);

        // 3. Save file to folder
        Files.write(filePath, file.getBytes());

        // 4. Save metadata in DB
        ImageEntity img = new ImageEntity();
        img.setFileName(file.getOriginalFilename());
        img.setContentType(file.getContentType());
        img.setSize(file.getSize());
        img.setFilePath(filePath.toAbsolutePath().toString());  // ✅ store location in DB

        return repo.save(img);
    }

    public Optional<ImageEntity> findById(Long id) {
        return repo.findById(id);
    }
}
