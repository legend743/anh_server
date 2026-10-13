package com.ahujanursinghome.hospital_management.gallery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;



@RestController
@RequestMapping("/gallery")
public class GalleryImageController {
    @Value("${image.upload.dir}")
    private String UPLOAD_DIR;

    @Autowired
    private  GalleryService galleryService;
    @Autowired
    private GalleryRepo galleryRepo;

//    private static final String UPLOAD_DIR = "C:/uploads/";


    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadImage(
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("file") MultipartFile file
    ) {
        try {
            String result = galleryService.uploadImage(categoryId, file);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Upload failed: " + e.getMessage());
        }
    }
    @PostMapping("/add-image-category")
    public ResponseEntity<String>saveAllCategory(@RequestBody ImageCategoryDto galleryCategory){
        if (galleryCategory!=null){
            GalleryCategory galleryCategory1=new GalleryCategory();
            galleryCategory1.setCategory(galleryCategory.getCategory());
            galleryCategory1.setDescription(galleryCategory.getDescription());

            galleryRepo.save(galleryCategory1);
            return ResponseEntity.ok("categories saved successfully!!");

        }
        return ResponseEntity.badRequest().body("unable to save category");
    }
    @GetMapping("/all-gallery-images")
    public ResponseEntity<List<GalleryCategoryDto>> getAllCategoriesWithImages() {
        List<GalleryCategoryDto> categories = galleryService.getAllGalleryCategoriesWithImages();
        if (categories.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/all")
    public ResponseEntity<List<String>> getAllImages() {
        List<String> images = galleryService.getAllImagesUrls();
        return ResponseEntity.ok(images);
    }

    @GetMapping("/view/{filename}")
    public ResponseEntity<Resource> viewImage(@PathVariable String filename) {
        try {
            Path filePath = Paths.get(UPLOAD_DIR).resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }
            // ✅ Detect content type dynamically
            String contentType = "application/octet-stream";
            if (filename.toLowerCase().endsWith(".jpg") || filename.toLowerCase().endsWith(".jpeg")) {
                contentType = MediaType.IMAGE_JPEG_VALUE;
            } else if (filename.toLowerCase().endsWith(".png")) {
                contentType = MediaType.IMAGE_PNG_VALUE;
            }
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                    .body(resource);
        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/get-all-category")
    public ResponseEntity<List<GalleryDtoWithoutImage>>getAllCategory(){
        try {
           List<GalleryDtoWithoutImage>allGalleryCategory=galleryService.getAllCategories();

            return ResponseEntity.ok(allGalleryCategory);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    @DeleteMapping("/delete-gallery-image/{id}")
    ResponseEntity<String>deleteGalleryImage(@PathVariable Long id){
       boolean result= galleryService.deleteGalleryImage(id);
       try{


       if (result){
           return ResponseEntity.ok("Gallery image Deleted");
       }
       else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("gallery image with ID " + id + " not found.");
        }
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error occurred while deleting the gallery image: " + e.getMessage());
    }

    }
}
