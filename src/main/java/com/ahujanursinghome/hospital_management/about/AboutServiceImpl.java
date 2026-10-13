package com.ahujanursinghome.hospital_management.about;

import com.ahujanursinghome.hospital_management.config.ImageUrlBuilder;
import com.ahujanursinghome.hospital_management.miscellaneous.SaveImageHelperMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@Service
public class AboutServiceImpl implements AboutService {

    @Autowired
    private SaveImageHelperMethod saveImageHelperMethod;
    @Autowired
    private AboutBannerRepo aboutBannerRepo;
    @Autowired
    private AboutDirectorRepo aboutDirectorRepo;
    @Autowired
    private AboutJourneyRepo aboutJourneyRepo;
    @Autowired
    private ImageUrlBuilder imageUrlBuilder;


    @Override
    public List<AboutBannerImageEntity> getAllBannerImages() {
        List<AboutBannerImageEntity> allBannerImageUrl = aboutBannerRepo.findAll();
//        try {
//            List<AboutBannerImageEntity> allBannerImageUrl = aboutBannerRepo.findAll();
//
//            for (AboutBannerImageEntity bannerImage : allBannerImageUrl) {
//                if (bannerImage.getImgurl() != null) {
//                    String filename = Paths.get(bannerImage.getImgurl()).getFileName().toString();
//                    bannerImage.setImgurl(imageUrlBuilder.buildUrl(filename));
//                }
//            }
//
//            return allBannerImageUrl;
//        } catch (RuntimeException e) {
//            throw new RuntimeException("Failed to fetch banner images", e);
//        }
        return allBannerImageUrl;
    }

    @Override
    public String uploadBannerImages(MultipartFile file) throws IOException {
        try{
        if (!file.isEmpty()) {
            String filepath = saveImageHelperMethod.saveFile("banner", file);
            AboutBannerImageEntity aboutBannerImageEntity=new AboutBannerImageEntity();
            aboutBannerImageEntity.setImgurl(filepath);
            aboutBannerRepo.save(aboutBannerImageEntity);
            return "banner image saved successfully!";

        }}catch (Exception e){
            e.printStackTrace();
        }
        return "unable to save banner image!!";
    }

    @Override
    public String uploadDirectorDetails(DirectorDetailsDTO directorDetailsDTO, MultipartFile file)  {
        AboutDirectorEntity aboutDirectorEntity=new AboutDirectorEntity();
try {
    aboutDirectorEntity.setDirectorname(directorDetailsDTO.getDirectorname());
    aboutDirectorEntity.setDesignation(directorDetailsDTO.getDesignation());
    aboutDirectorEntity.setDescription(directorDetailsDTO.getDescription());
    String filepath=saveImageHelperMethod.saveFile("director",file);
    aboutDirectorEntity.setImgurl(filepath);
    aboutDirectorRepo.save(aboutDirectorEntity);
    return "director details saved!!";
} catch (RuntimeException | IOException e) {
    throw new RuntimeException(e);
}


    }

    @Override
    public AboutDirectorEntity getDirectorDetails() {
        try {
            AboutDirectorEntity director = aboutDirectorRepo.findPresentDirectorDetails();
//
//            if (director != null && director.getImgurl() != null) {
//                String filename = Paths.get(director.getImgurl()).getFileName().toString();
//                director.setImgurl(imageUrlBuilder.buildUrl(filename));
//            }

            return director;
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch director details", e);
        }
    }

    @Override
    public String uploadAboutJourney(AboutJourneyDTO aboutJourneyDTO, MultipartFile file) throws IOException {
        AboutJourneyEntity aboutJourneyEntity=new AboutJourneyEntity();
        try{
            aboutJourneyEntity.setTitle(aboutJourneyDTO.getTitle());
            aboutJourneyEntity.setDescription(aboutJourneyDTO.getDescription());
           String filepath= saveImageHelperMethod.saveFile("jouney",file);
           aboutJourneyEntity.setImgurl(filepath);
            aboutJourneyRepo.save(aboutJourneyEntity);
            return "journey details uploaded";
                    }catch (IOException e){
            e.printStackTrace();
        }
        return "unable to upload jouney details";
    }

    @Override
    public List<AboutJourneyEntity> getAllJourney() {
        try {
            List<AboutJourneyEntity> journeys = aboutJourneyRepo.findAll();

//            for (AboutJourneyEntity journey : journeys) {
//                if (journey.getImgurl() != null) {
//                    String filename = Paths.get(journey.getImgurl()).getFileName().toString();
//                    journey.setImgurl(imageUrlBuilder.buildUrl(filename));
//                }
//            }

            return journeys;
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to fetch journey data", e);
        }
    }

    @Override
    public boolean deleteDiretctorById(Long id) {
        if (!aboutDirectorRepo.existsById(id)){

            return false;
        }
        try{
            aboutDirectorRepo.deleteById(id);
            return true;
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean deleteJourneyById(Long id) {
        if(!aboutJourneyRepo.existsById(id)){
            return false;
        }
        aboutJourneyRepo.deleteById(id);
        return true;
    }

    @Override
    public boolean deleteBannerById(Long id) {
        if (!aboutBannerRepo.existsById(id)){
            return false;
        }
        aboutBannerRepo.deleteById(id);
        return true;
    }
}
