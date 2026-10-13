package com.ahujanursinghome.hospital_management.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class StaticResourceConfig implements WebMvcConfigurer {

    @Value("${image.upload.dir}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String normalizedPath = "file:///" + (uploadDir.endsWith("/") ? uploadDir : uploadDir + "/");
        System.out.println("Serving images from: " + normalizedPath); // Debug

        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(normalizedPath);
    }
}
