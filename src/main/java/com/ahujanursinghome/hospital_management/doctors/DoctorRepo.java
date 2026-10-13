package com.ahujanursinghome.hospital_management.doctors;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepo extends JpaRepository<DoctorEntity,Long> {
    @Query(value = "SELECT * FROM doctordetails WHERE active_doctor = true", nativeQuery = true)
    List<DoctorEntity> findActiveDoctors();


}
