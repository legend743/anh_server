package com.ahujanursinghome.hospital_management.gallery;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface GalleryService {
    String uploadImage(Long CategoryId,MultipartFile file) throws IOException;
    List<String>getAllImagesUrls();
    List<GalleryCategory>getAllCategoriesWithImages();
    List<GalleryCategoryDto> getAllGalleryCategoriesWithImages();
    List<GalleryDtoWithoutImage> getAllCategories();
    boolean deleteGalleryImage(Long id);

}
