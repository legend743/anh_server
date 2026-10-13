package com.ahujanursinghome.hospital_management.miscellaneous;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientReviewRepo extends JpaRepository<PatientReviewEntity,Long> {
}
