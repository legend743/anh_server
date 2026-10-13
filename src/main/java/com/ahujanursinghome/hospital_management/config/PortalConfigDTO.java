package com.ahujanursinghome.hospital_management.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
public class PortalConfigDTO {

    private String directorNote;
    private MultipartFile directorImage; // Allow updating image if needed
    private MultipartFile logoBytes;
    private MultipartFile favIconBytes;
    private MultipartFile banner;
    private String bannerTagline;
}
