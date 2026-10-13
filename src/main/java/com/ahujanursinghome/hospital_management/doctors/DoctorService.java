package com.ahujanursinghome.hospital_management.doctors;

import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface DoctorService {
     ResponseEntity<String>uploadDoctorsDetails(DoctorRequestBody doctorRequestBody, MultipartFile file) throws IOException;
    List<DoctorEntity> getAllDoctorsDetails();
    List<DoctorEntity>removeDoctorDetails(Long id);





}
