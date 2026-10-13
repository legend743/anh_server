package com.ahujanursinghome.hospital_management.config;

import com.ahujanursinghome.hospital_management.miscellaneous.SaveImageHelperMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Service
public class PortalConfigServiceImpl implements PortalConfigService {
    @Autowired
    private portalConfigRepo portalConfigRepo;

    @Autowired
    private SaveImageHelperMethod saveImageHelperMethod;

    @Value("${image.upload.dir}")
    private  String upload;

    @Override
    public ResponseEntity<String> SaveConfigurationsData(String directorNote, MultipartFile directorImage, MultipartFile logo,
                                                         MultipartFile favicon,
                                                         MultipartFile banner, String bannerTagline) throws IOException {
        PortalConfigurationEntity portalConfig = new PortalConfigurationEntity();
        portalConfig.setDirectorNote(directorNote);
        portalConfig.setBannerTagline(bannerTagline);
        File directory = new File(upload);
        if (directorImage != null) {
//            String filename="director"+System.currentTimeMillis()+"_"+directorImage.getOriginalFilename();
//            String filepath=upload+File.separator+filename;
//            File destfile=new File(filepath);
//            directorImage.transferTo(destfile);
           String filename= saveImageHelperMethod.saveFile("director",directorImage);
            portalConfig.setDirectorImage(filename);

        }
        if (logo != null) {
//            String logofilename="logo"+System.currentTimeMillis()+"_"+logo.getOriginalFilename();
//            String filepath=upload+File.separator+logofilename;
//            File destfile=new File(filepath);
//            logo.transferTo(destfile);
            String logofilename=saveImageHelperMethod.saveFile("logo",logo);
            portalConfig.setLogoBytes(logofilename);
        }
        if (favicon != null) {
             String filename=saveImageHelperMethod.saveFile("favicon",favicon);
            portalConfig.setFavIconBytes(filename);
        }
        if (banner != null) {
            String filename=saveImageHelperMethod.saveFile("banner",banner);
            portalConfig.setBanner(filename);
        }

        portalConfigRepo.save(portalConfig);
        return ResponseEntity.ok("Data saved successfully!");

}

    @Override
    public ResponseEntity<Optional<PortalConfigurationEntity>> getConfigurationDataById(int Id) {
        Optional<PortalConfigurationEntity> portalConfigurationEntity=portalConfigRepo.findById(Id);
        return ResponseEntity.ok().body(portalConfigurationEntity);
    }

    @Override
    public PortalConfigurationEntity getLatestPortalConfig() {
        {
            return portalConfigRepo.findFirstByOrderByIdDesc();
        }
    }

    @Override
    public ResponseEntity<PortalConfigurationEntity> updateConfigurationData (PortalConfigDTO updateDTO) throws IOException {
        // Fetch the latest entry
        PortalConfigurationEntity config = portalConfigRepo.findFirstByOrderByIdDesc();

        // ✅ Null check: If no configuration exists, return 404 Not Found
        if (config == null) {
            return ResponseEntity.notFound().build();
        }

        // ✅ Update only non-null values
        if (updateDTO.getDirectorNote() != null) {
            config.setDirectorNote(updateDTO.getDirectorNote());
        }
        if (updateDTO.getDirectorImage() != null && !updateDTO.getDirectorImage().isEmpty()) {
            String filename=saveImageHelperMethod.saveFile("director",updateDTO.getDirectorImage());
            config.setDirectorImage(filename);
        }
        if (updateDTO.getLogoBytes() != null && !updateDTO.getLogoBytes().isEmpty()) {
            String filename=saveImageHelperMethod.saveFile("logo",updateDTO.getLogoBytes());
            config.setLogoBytes(filename);
        }
        if (updateDTO.getFavIconBytes() != null && !updateDTO.getFavIconBytes().isEmpty()) {
            String filename=saveImageHelperMethod.saveFile("favicon",updateDTO.getFavIconBytes());
            config.setFavIconBytes(filename);
        }
        if (updateDTO.getBanner() != null && !updateDTO.getBanner().isEmpty()) {
            String filename=saveImageHelperMethod.saveFile("banner",updateDTO.getBanner());
            config.setBanner(filename);
        }
        if (updateDTO.getBannerTagline() != null) {
            config.setBannerTagline(updateDTO.getBannerTagline());
        }

        // ✅ Save updated configuration
        portalConfigRepo.save(config);



        return ResponseEntity.ok(config);


    }
}