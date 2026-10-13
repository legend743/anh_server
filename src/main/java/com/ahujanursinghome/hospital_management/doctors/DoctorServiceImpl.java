package com.ahujanursinghome.hospital_management.doctors;

import com.ahujanursinghome.hospital_management.config.ImageUrlBuilder;
import com.ahujanursinghome.hospital_management.miscellaneous.SaveImageHelperMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorServiceImpl implements DoctorService {

@Value("${image.upload.dir}")
private String uploadDir;

@Autowired
    private DoctorRepo doctorRepo;
@Autowired
private SaveImageHelperMethod imageHelperMethod;

@Autowired
private ImageUrlBuilder imageUrlBuilder;

    @Override
    public ResponseEntity<String> uploadDoctorsDetails(DoctorRequestBody doctorRequestBody, MultipartFile file) throws IOException {

        DoctorEntity doctorEntity=new DoctorEntity();
        if (doctorRequestBody == null || file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body("Doctor details  or image file is missing.");
        }
        File directory = new File(uploadDir);
        if (!directory.exists() && !directory.mkdirs()) {
            throw new IOException("Failed to create directory: " + uploadDir);
        }

        try {
            String filePath= imageHelperMethod.saveFile("doctor",file);
            doctorEntity.setName(doctorRequestBody.getName());
            doctorEntity.setEmail(doctorRequestBody.getEmail());
            doctorEntity.setImageUrl(filePath);
            doctorEntity.setDepartment(doctorRequestBody.getDepartment());
            doctorEntity.setContactNumber(doctorRequestBody.getContactNumber());
            doctorEntity.setExperienceYears(doctorRequestBody.getExperienceYears());
            doctorEntity.setQualifications(doctorRequestBody.getQualifications());
            doctorEntity.setBio(doctorRequestBody.getBio());
            doctorEntity.setSpecialization(doctorRequestBody.getSpecialization());
            doctorRepo.save(doctorEntity);

            return ResponseEntity.ok("Doctor details saved successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error saving doctor details.");
        }
    }


    @Override
    public List<DoctorEntity> getAllDoctorsDetails() {
        List<DoctorEntity> doctorDetails = doctorRepo.findActiveDoctors();

//        for (DoctorEntity doctor : doctorDetails) {
//            if (doctor.getImageUrl() != null) {
//                String filename = Paths.get(doctor.getImageUrl()).getFileName().toString();
//                doctor.setImageUrl(imageUrlBuilder.buildUrl(filename));
//            }
//        }

        return doctorDetails;
    }


    @Override
    public List<DoctorEntity> removeDoctorDetails(Long id) {
        Optional<DoctorEntity> doctorOptional = doctorRepo.findById(id);

        if (doctorOptional.isPresent()) {
            DoctorEntity doctorEntity = doctorOptional.get();
            doctorEntity.setActiveDoctor(false); // Set status to inactive
            doctorRepo.save(doctorEntity); // Save the updated entity
            return doctorRepo.findActiveDoctors(); // Return updated list (or only active ones if needed)
        }

        throw new RuntimeException("Doctor with ID " + id + " not found.");
    }


}
