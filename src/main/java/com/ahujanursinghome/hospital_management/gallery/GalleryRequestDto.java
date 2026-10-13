package com.ahujanursinghome.hospital_management.gallery;

import lombok.Data;

import java.util.List;

@Data
public class GalleryRequestDto {
    private String category;
    private String description;
    private List<ImagesDto> images;
}

