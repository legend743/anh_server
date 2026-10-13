package com.ahujanursinghome.hospital_management.miscellaneous;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class SaveImageHelperMethod {

    @Value("${image.upload.dir}")
    private String uploadUrl;

    public String saveFile(String _prefix, MultipartFile uploaded_file) throws IOException {
        String filename = _prefix + System.currentTimeMillis() + "_" + uploaded_file.getOriginalFilename();
        Path directory = Paths.get(uploadUrl);
        if (!Files.exists(directory)) {
            Files.createDirectories(directory);
        }

        Path filepath = directory.resolve(filename);
        uploaded_file.transferTo(filepath.toFile());

        return filename; // ✅ Just the file name (store this in DB)
    }
}
