package com.neel.imageUploader.controller;

import com.neel.imageUploader.model.ImageEntity;
import com.neel.imageUploader.service.ImageService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public ResponseEntity<byte[]> download(@PathVariable Long id) {
        return service.findById(id).map(img -> {
            HttpHeaders headers = new HttpHeaders();
            String ct = img.getContentType() == null ? "application/octet-stream" : img.getContentType();
            headers.setContentType(MediaType.parseMediaType(ct));
            headers.setContentDisposition(ContentDisposition.inline().filename(img.getFilename()).build());
            return new ResponseEntity<>(img.getData(), headers, HttpStatus.OK);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}