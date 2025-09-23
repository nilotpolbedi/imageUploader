package com.neel.imageUploader.controller;

import com.neel.imageUploader.model.ImageEntity;
import com.neel.imageUploader.service.ImageService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    private final ImageService service;
    public ImageController(ImageService service) { this.service = service; }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> upload(@RequestParam("file") MultipartFile file) throws IOException {
        ImageEntity saved = service.save(file);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("id", saved.getId(), "filename", saved.getFilename()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) throws IOException {
        ImageEntity img = service.findById(id)
                .orElseThrow(() -> new RuntimeException("Image not found"));

        // Read file from disk
        Path path = Paths.get(img.getFilePath());
        byte[] fileBytes = Files.readAllBytes(path);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + img.getFileName() + "\"")
                .contentType(MediaType.parseMediaType(img.getContentType()))
                .body(fileBytes);
    }

}