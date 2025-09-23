package com.neel.imageUploader.repository;

import com.neel.imageUploader.model.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ImageEntity, Long> {
}