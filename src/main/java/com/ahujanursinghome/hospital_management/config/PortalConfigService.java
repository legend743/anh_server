package com.ahujanursinghome.hospital_management.config;

import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;


public interface PortalConfigService {
    public ResponseEntity<String>SaveConfigurationsData(String directorNote, MultipartFile directorImage, MultipartFile logo,
                                                        MultipartFile favicon, MultipartFile banner, String bannerTagline) throws IOException;
    public ResponseEntity<Optional<PortalConfigurationEntity>>getConfigurationDataById(int Id);

    public PortalConfigurationEntity getLatestPortalConfig();
    public ResponseEntity<PortalConfigurationEntity>updateConfigurationData(PortalConfigDTO portalConfigDTO) throws Exception;
}
