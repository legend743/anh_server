package com.ahujanursinghome.hospital_management.about;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/about-us")
public class AboutController {
    @Autowired
    private AboutServiceImpl aboutService;
    @PostMapping(value = "/upload-banner-image",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String>uploadBannerImage(@RequestParam MultipartFile file) throws IOException {
        if (file.isEmpty()){
            return ResponseEntity.badRequest().body("please upload valid image");
        }
        try {
      aboutService.uploadBannerImages(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok("uploaded");
    }

    @GetMapping("/get-all-banner")
    public ResponseEntity<List<AboutBannerImageEntity>>getAllBannerimage(){
        try{
            List<AboutBannerImageEntity>allBannerUrl=aboutService.getAllBannerImages();
            if (allBannerUrl.isEmpty()){
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(allBannerUrl);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping(value = "/upload-director",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String>getDirectorDetail(
            @RequestParam String directorname,
            @RequestParam String description,
            @RequestParam String designation,
            @RequestParam MultipartFile file


    ){
        if (description.isEmpty()||designation.isEmpty()||directorname.isEmpty() || file.isEmpty()){
            return ResponseEntity.badRequest().body("Either one or all field is empty");
        }
        DirectorDetailsDTO directorDetailsDTO=new DirectorDetailsDTO(directorname,designation,description);

        String aboutDirectorEntity= aboutService.uploadDirectorDetails(directorDetailsDTO,file);
        return ResponseEntity.ok(aboutDirectorEntity);

    }
    @GetMapping("get-director-details")
    public ResponseEntity<AboutDirectorEntity>getPresentDirectorDetails(){
        AboutDirectorEntity aboutDirectorEntity=aboutService.getDirectorDetails();
        if (aboutDirectorEntity==null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(aboutDirectorEntity);
    }
    @PostMapping(value = "/about-journey",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String>uploadJourneyDetails(
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam MultipartFile file
    ) throws IOException {

        if (title.isEmpty() || description.isEmpty() || file.isEmpty()){
            return ResponseEntity.badRequest().body("Either one or all field is empty");
        }
        AboutJourneyDTO aboutJourneyDTO=new AboutJourneyDTO(title,description);
        try {
            String result=aboutService.uploadAboutJourney(aboutJourneyDTO,file);
            return ResponseEntity.ok(result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @GetMapping("/get-journey-details")
    public ResponseEntity<List<AboutJourneyEntity>>getAllJourney(){
        List<AboutJourneyEntity>allJourneyDetails= aboutService.getAllJourney();
        if (allJourneyDetails.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(allJourneyDetails);
    }

    @DeleteMapping("/delete-director/{id}")
    public ResponseEntity<String>deletedirectorById(@PathVariable Long id){
        try{
            boolean result=aboutService.deleteDiretctorById(id);
            if (result){
                return ResponseEntity.ok("director deleted successfully!!");
            }
            else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Director with ID " + id + " not found.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while deleting the director details: " + e.getMessage());
        }
        }
        @DeleteMapping("/delete-banner-image/{id}")
     public ResponseEntity<String>deleteBannerImage(@PathVariable Long id){
        boolean result= aboutService.deleteBannerById(id);
        try{
            if (result){
                return ResponseEntity.ok("Banner deleted successfully!");
            }
            else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Banner with ID " + id + " not found.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while deleting the Banner: " + e.getMessage());
        }
        }
        @DeleteMapping("/delete-journey/{id}")
    public ResponseEntity<String>deleteJouneryById(@PathVariable Long id) {
            boolean result = aboutService.deleteJourneyById(id);
            try {


                if (result) {
                    return ResponseEntity.ok("Journey deleted successfully!!");
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("journey  with ID " + id + " not found.");
                }
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error occurred while deleting the journey : " + e.getMessage());
            }

        }
        }


