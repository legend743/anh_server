package com.ahujanursinghome.hospital_management.gallery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface GalleryRepo extends JpaRepository<GalleryCategory,Long> {




}

