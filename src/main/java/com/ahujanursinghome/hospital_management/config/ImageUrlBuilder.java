package com.ahujanursinghome.hospital_management.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ImageUrlBuilder {
        @Value("${image.base.url}")
        private String imageBaseUrl; // e.g., http://localhost:8080/uploads/

        public String buildUrl(String filename) {
            System.out.println(imageBaseUrl);
            return imageBaseUrl + filename;
        }
}
