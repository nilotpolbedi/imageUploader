package com.neel.imageUploader.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "images")
public class ImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filename;

    @Column(name = "content_type")
    private String contentType;

    @Lob
    @Column(name = "data", columnDefinition = "bytea")
    private byte[] data;

    @Column(name = "created_at")
    private Instant createdAt = Instant.now();

    // Getters / setters (or use Lombok @Getter @Setter)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFilename() { return filename; }
    public void setFilename(String filename) { this.filename = filename; }
    public String getContentType() { return contentType; }
    public void setContentType(String contentType) { this.contentType = contentType; }
    public byte[] getData() { return data; }
    public void setData(byte[] data) { this.data = data; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}