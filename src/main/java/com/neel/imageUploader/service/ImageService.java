package com.neel.imageUploader.service;

import com.neel.imageUploader.model.ImageEntity;
import com.neel.imageUploader.repository.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class ImageService {
    private final ImageRepository repo;
    public ImageService(ImageRepository repo) { this.repo = repo; }

    @Transactional
    public ImageEntity save(MultipartFile file) throws IOException {
        ImageEntity img = new ImageEntity();
        img.setFilename(file.getOriginalFilename());
        img.setContentType(file.getContentType());
        img.setData(file.getBytes());
        return repo.save(img);
    }

    public Optional<ImageEntity> findById(Long id) {
        return repo.findById(id);
    }
}