package com.ahujanursinghome.hospital_management.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/configuration")

public class PortalConfigController {
    @Autowired
    private PortalConfigService portalConfigService;

    @PostMapping(value = "/saveConfigurations", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> savePortalConfiguration(@RequestParam("directorNote") String directorNote,
                                                          @RequestParam(value = "directorImage", required = false) MultipartFile directorImage,
                                                          @RequestParam(value = "logo", required = false) MultipartFile logo,
                                                          @RequestParam(value = "favicon", required = false) MultipartFile favicon,
                                                          @RequestParam(value = "banner", required = false) MultipartFile banner,
                                                          @RequestParam("bannerTagline") String bannerTagline) throws IOException {
        return portalConfigService.SaveConfigurationsData(directorNote, directorImage, logo, favicon, banner, bannerTagline);
    }

    @GetMapping("/{Id}")
    public ResponseEntity<Optional<PortalConfigurationEntity>> getConfigurationData(@PathVariable Integer Id) {
        return portalConfigService.getConfigurationDataById(Id);
    }

    @GetMapping("/latest")
    public PortalConfigurationEntity getLatestConfigurationData() {
        return portalConfigService.getLatestPortalConfig();
    }

    @PatchMapping(value = "/update",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PortalConfigurationEntity> updatePortalConfig(
            @RequestParam(value = "directorNote", required = false) String directorNote,
            @RequestParam(value = "directorImage", required = false) MultipartFile directorImage,
            @RequestParam(value = "logoBytes", required = false) MultipartFile logoBytes,
            @RequestParam(value = "favIconBytes", required = false) MultipartFile favIconBytes,
            @RequestParam(value = "banner", required = false) MultipartFile banner,
            @RequestParam(value = "bannerTagline", required = false) String bannerTagline) throws Exception {

        PortalConfigDTO updateDTO = new PortalConfigDTO(directorNote, directorImage, logoBytes, favIconBytes, banner, bannerTagline);

        return portalConfigService.updateConfigurationData(updateDTO);
    }

}