package com.ahujanursinghome.hospital_management.about;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AboutService  {
    List<AboutBannerImageEntity>getAllBannerImages();
    String uploadBannerImages(MultipartFile file) throws IOException;
    String uploadDirectorDetails(DirectorDetailsDTO directorDetailsDTO,MultipartFile file);
    AboutDirectorEntity getDirectorDetails();
    String uploadAboutJourney(AboutJourneyDTO aboutJourneyDTO,MultipartFile file) throws IOException;
    List<AboutJourneyEntity> getAllJourney();
    boolean deleteDiretctorById(Long id);
    boolean deleteJourneyById(Long id);
    boolean deleteBannerById(Long id);

}
