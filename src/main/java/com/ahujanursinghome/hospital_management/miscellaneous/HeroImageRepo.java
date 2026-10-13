package com.ahujanursinghome.hospital_management.miscellaneous;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface HeroImageRepo extends JpaRepository<HeroImageEntity,Long> {
    @Query(value = "SELECT id,imagepath FROM heroimage ORDER BY id DESC LIMIT 1", nativeQuery = true)
    Optional<HeroImageDto> findLatestImagePath();
}
