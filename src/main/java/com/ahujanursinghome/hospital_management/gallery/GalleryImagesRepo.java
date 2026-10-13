package com.ahujanursinghome.hospital_management.gallery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GalleryImagesRepo extends JpaRepository<GalleryImage,Long> {
}
