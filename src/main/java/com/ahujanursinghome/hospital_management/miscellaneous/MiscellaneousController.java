package com.ahujanursinghome.hospital_management.miscellaneous;

import com.ahujanursinghome.hospital_management.gallery.GalleryRepo;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;


//@CrossOrigin("*")
@RestController
@RequestMapping("/miscellaneous")
public class MiscellaneousController {
    @Autowired
    private PatientReviewService patientReviewService;
    @Autowired
    private ContactUsRepo contactUsRepo;
    @Autowired
    private HeroImageRepo heroImageRepo ;
    @Autowired
    private PatientReviewRepo patientReviewRepo;
    @Autowired
    private VideoReviewService videoReviewService;
    @Autowired
    SaveImageHelperMethod saveImageHelperMethod;

    @PostMapping(value = "/post-review", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> postReview(
            @RequestParam("name") String name,
            @RequestParam("feedback") String feedback,  // ✅ Fix casing
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        // Save file and generate URL
        String filename = saveImageHelperMethod.saveFile("review-image", file);


        // Prepare entity
        PatientReviewEntity review = new PatientReviewEntity();
        review.setName(name);
        review.setFeedback(feedback);
        review.setImageUrl(filename);

        // Save via service
        return patientReviewService.postReview(review);
    }
    @GetMapping("/getAllReview")
    public ResponseEntity<List<PatientReviewEntity>>getAllReview(){
        return patientReviewService.getAllPatientReview();
    }

    @PostMapping("/upload-all-addresses")
    public ResponseEntity<String>uploadAllAddresses(@RequestBody ContactUsEntity contactUsEntity){
        if(contactUsEntity==null){
            return ResponseEntity.noContent().build();
        }
        try{
            contactUsRepo.save(contactUsEntity);
            return ResponseEntity.created(new URI("/api/contact-us/" + contactUsEntity.getId()))
                    .body("Contact request submitted successfully!"); }

        catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/all-address")
    public ResponseEntity<List<ContactUsEntity>>getAllHospitalAddresses(){
        List<ContactUsEntity> allHospitalAddresses=contactUsRepo.findAll();
        if (allHospitalAddresses.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        try{
            return ResponseEntity.ok(allHospitalAddresses);
        } catch (Exception e) {
                throw new RuntimeException(e);
        }
    }
    @PostMapping(value = "/upload-hero-image",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String>upload_hero_image(@RequestParam MultipartFile file) throws Exception {
        if (file.isEmpty()){
            return ResponseEntity.badRequest().body("please upload image");
        }
        patientReviewService.uploadHeroImage("hero",file);
        return ResponseEntity.ok("Created");

    }
    @GetMapping("/get-hero")
    public ResponseEntity<Optional<HeroImageDto>>getHeroImage(){
        Optional<HeroImageDto> heroImageDto= heroImageRepo.findLatestImagePath();
        if (heroImageDto.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(heroImageDto);

    }
    @DeleteMapping("/delete-hero-image/{id}")
    public ResponseEntity<String> deleteHeroImageById(@PathVariable Long id) {
        try {
            boolean result = patientReviewService.deleteHeroImage(id);

            if (result) {
                return ResponseEntity.ok("Hero image deleted successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Hero image with ID " + id + " not found.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while deleting the hero image: " + e.getMessage());
        }

    }
    @DeleteMapping("delete-contact/{id}")
    public ResponseEntity<String>deleteContactById(@PathVariable Long id){
        if (id==null){
            return ResponseEntity.badRequest().body("please pass contact id!!");
        }
        try {
            boolean result=contactUsRepo.existsById(id);
            if (!result){
                return ResponseEntity.badRequest().body("contact is not found with this id");

            }
            contactUsRepo.deleteById(id);
            return ResponseEntity.ok().body("contact is deleted");
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

    }
    @DeleteMapping("/delete-review/{id}")
public ResponseEntity<String>deletePatientReview(@PathVariable Long  id) {

        if (!patientReviewRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        } else {
            if (id == null) {
                return ResponseEntity.badRequest().body("please provide valid id");
            }
            try {
                boolean result = patientReviewService.removePatientReview(id);
                if (result) {
                    return ResponseEntity.ok("review remove successfully");
                }
                return ResponseEntity.badRequest().build();
            } catch (RuntimeException e) {
                throw new RuntimeException(e);
            }


        }
    }
    @PostMapping("/upload-video-review")
    public ResponseEntity<String>uploadVideoReview(@RequestBody VideoReviewEntity videoReviewEntity){

       try {
           videoReviewService.uploadImageLink(videoReviewEntity);
           return ResponseEntity.ok("Video uploaded successfully!");
       } catch (RuntimeException e) {
           throw new RuntimeException(e);
       }
    }
    @GetMapping("get-allvideo-review")
    public ResponseEntity<List<VideoReviewEntity>>getAllVideoReview(){
       try {
           return videoReviewService.getAllVideoReview();
       } catch (RuntimeException e) {
           throw new RuntimeException(e);
       }
    }
    @DeleteMapping("/delete-video-review/{id}")
    public ResponseEntity<String>deleteVideoReview(@PathVariable Long id){
        if (id==null) {
            return ResponseEntity.badRequest().body("please pass id");
        }
        if (!patientReviewRepo.existsById(id)) {
            return ResponseEntity.badRequest().body("video is not found");
        }
        else {
            try {
                videoReviewService.deleteReviewVideo(id);
                return ResponseEntity.ok("video deleted successfully!");
            } catch (RuntimeException e) {
                throw new RuntimeException(e);
            }}


    }
}
