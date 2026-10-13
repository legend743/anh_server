package com.ahujanursinghome.hospital_management.gallery;

import com.ahujanursinghome.hospital_management.config.ImageUrlBuilder;
import com.ahujanursinghome.hospital_management.miscellaneous.SaveImageHelperMethod;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GalleryServiceImpl implements  GalleryService{

    @Value("${image.upload.dir}")
    private String uploadDir;
    @Autowired
    private  GalleryRepo galleryRepo;
    @Autowired
    private GalleryImagesRepo imagesRepo;
    @Autowired
    private SaveImageHelperMethod imageHelperMethod;
    @Autowired
    private ImageUrlBuilder imageUrlBuilder;
    @Override
    public String uploadImage(Long categoryId, MultipartFile file) throws IOException {
        GalleryCategory category = galleryRepo.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        String filepath=imageHelperMethod.saveFile("gallery",file);
        GalleryImage image = new GalleryImage();
        image.setImgUrl(filepath);      // ✅ Correct field
        image.setGallery(category);     // ✅ Correct field

        imagesRepo.save(image);

        return "Image uploaded successfully: " + filepath;
    }

    public List<GalleryCategory> getAllCategoriesWithImages() {
        System.out.println("this is all images");
        List<GalleryCategory> categories = galleryRepo.findAll();

        for (GalleryCategory category : categories) {
            category.getImages().forEach(image -> {
                String filename = image.getImgUrl();  // original filename
                String fullUrl = imageUrlBuilder.buildUrl(filename);
                image.setImgUrl(fullUrl);  // update to full URL
            });
        }

        return categories;
    }

    @Override
    public List<String> getAllImagesUrls() {
        return imagesRepo.findAll().stream()
                .map(image -> imageUrlBuilder.buildUrl(image.getImgUrl())) // Use image.getImgUrl() directly
                .collect(Collectors.toList());
    }


//    @Transactional
//    public List<GalleryCategoryDto> getAllGalleryCategoriesWithImages() {
////        return galleryRepo.findAll().stream().map(category -> {
////            List<GalleryImageDto> images = category.getImages().stream()
////                    .map(img -> {
////                        String filename = Paths.get(img.getImgUrl()).getFileName().toString();
////                        String fullUrl = imageUrlBuilder.buildUrl(filename);
////                        return new GalleryImageDto(img.getId(), fullUrl);
////                    })
////                    .collect(Collectors.toList());
////            return new GalleryCategoryDto(category.getId(), category.getCategory(), category.getDescription(), images);
////        }).collect(Collectors.toList());
//        List<GalleryCategory> galleryCategory= galleryRepo.findAll();
//        GalleryCategoryDto galleryCategoryDto=new GalleryCategoryDto();
//        return  galleryCategoryDto(galleryCategory.g)
//    }

@Transactional
public List<GalleryCategoryDto> getAllGalleryCategoriesWithImages() {
    List<GalleryCategory> galleryCategories = galleryRepo.findAll();

    return galleryCategories.stream().map(category -> {
        List<GalleryImageDto> images = category.getImages().stream()
                .map(img -> {
                    String filename = Paths.get(img.getImgUrl()).getFileName().toString(); // ✅ Only filename
                    return new GalleryImageDto(img.getId(), filename);
                })
                .collect(Collectors.toList());

        return new GalleryCategoryDto(
                category.getId(),
                category.getCategory(),
                category.getDescription(),
                images
        );
    }).collect(Collectors.toList());
}



    public List<GalleryDtoWithoutImage> getAllCategories() {
        List<GalleryCategory> categories = galleryRepo.findAll();
        return categories.stream().map(cat -> {
            GalleryDtoWithoutImage dto = new GalleryDtoWithoutImage();
            dto.setId(cat.getId());
            dto.setCategory(cat.getCategory());
            dto.setDescription(cat.getDescription());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public boolean deleteGalleryImage(Long id) {
        if (!imagesRepo.existsById(id)){
            return false;
        }
        try {
            imagesRepo.deleteById(id);
            return true;
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

}
