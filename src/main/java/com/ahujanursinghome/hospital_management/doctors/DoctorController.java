package com.ahujanursinghome.hospital_management.doctors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/doctor")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;
    @PostMapping(value = "/uploadDoctorsDetails", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> uploadDoctorsDetails(
            @RequestParam("name") String name,
            @RequestParam("qualifications") String qualifications,
            @RequestParam("specialization") String specialization,
            @RequestParam("experienceYears") int experienceYears,
            @RequestParam("department") String department,
            @RequestParam("bio") String bio,
            @RequestParam("contactNumber") String contactNumber,
            @RequestParam("email") String email, // Use @ModelAttribute here
            @RequestParam("file") MultipartFile file) throws IOException {
        DoctorRequestBody doctorRequestBody = new DoctorRequestBody();
        doctorRequestBody.setName(name);
        doctorRequestBody.setQualifications(qualifications);
        doctorRequestBody.setSpecialization(specialization);
        doctorRequestBody.setExperienceYears(experienceYears);
        doctorRequestBody.setDepartment(department);
        doctorRequestBody.setBio(bio);
        doctorRequestBody.setContactNumber(contactNumber);
        doctorRequestBody.setEmail(email);

        return doctorService.uploadDoctorsDetails(doctorRequestBody, file);
    }

    @GetMapping("/getAllDoctorsDetails")
    public ResponseEntity<List<DoctorEntity>>getALlDoctorsDetails(){
        List<DoctorEntity> doctorEntity= doctorService.getAllDoctorsDetails();
        if (doctorEntity.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(doctorEntity);
    }
    @PatchMapping("/remove-doctor/{id}")
    ResponseEntity<List<DoctorEntity>>removeDoctorById(@PathVariable Long id){
        try{
           List<DoctorEntity> doctorEntity= doctorService.removeDoctorDetails(id);
           if (doctorEntity.isEmpty()){
               return ResponseEntity.notFound().build();
           }
           return ResponseEntity.ok(doctorEntity);


        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

}
