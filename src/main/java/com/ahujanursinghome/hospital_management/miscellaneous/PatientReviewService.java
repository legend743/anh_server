package com.ahujanursinghome.hospital_management.miscellaneous;

import com.ahujanursinghome.hospital_management.gallery.GalleryCategory;
import com.ahujanursinghome.hospital_management.gallery.GalleryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class PatientReviewService {
    @Autowired
    private PatientReviewRepo patientReviewRepo;
    @Autowired
    private  SaveImageHelperMethod saveImageHelperMethod;
    @Autowired
    private HeroImageRepo heroImageRepo;



    public ResponseEntity<String>postReview(PatientReviewEntity patientReviewEntity){

        if (patientReviewEntity==null){
            return ResponseEntity.badRequest().body("please write some review");
        }
        try{
             patientReviewRepo.save(patientReviewEntity);
             return ResponseEntity.accepted().body("Review accepted!!");

        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }


    }
    public ResponseEntity<List<PatientReviewEntity>>getAllPatientReview(){
        List<PatientReviewEntity> allPatientReview=patientReviewRepo.findAll();
        if(allPatientReview.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        try{
            return ResponseEntity.ok().body(allPatientReview);
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }

    }
    public String uploadHeroImage(String prefix,MultipartFile file) throws Exception{
        if (file.isEmpty()){
            return "file not found!!";
        }
        String fileName=saveImageHelperMethod.saveFile(prefix,file);
        HeroImageEntity heroImageEntity=new HeroImageEntity();
        heroImageEntity.setImagepath(fileName);
        heroImageRepo.save(heroImageEntity);
        return "Image updated successfully!!";

    }

    public boolean deleteHeroImage(Long id) {
        if (!heroImageRepo.existsById(id)) {
            return false;
        }

        try {
            heroImageRepo.deleteById(id);
            return true;
        } catch (Exception e) {
            // Optional: log the exception (recommended in real apps)
            throw new RuntimeException("Error occurred while deleting the image with id: " + id, e);
        }
    }
public boolean removePatientReview(Long id){

        try {
            patientReviewRepo.deleteById(id);
            return true;
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
}

}
