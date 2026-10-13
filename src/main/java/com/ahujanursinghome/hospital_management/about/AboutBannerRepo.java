package com.ahujanursinghome.hospital_management.about;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AboutBannerRepo extends JpaRepository<AboutBannerImageEntity,Long> {
}
