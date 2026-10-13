package com.ahujanursinghome.hospital_management.about;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AboutJourneyRepo extends JpaRepository<AboutJourneyEntity,Long> {
}
