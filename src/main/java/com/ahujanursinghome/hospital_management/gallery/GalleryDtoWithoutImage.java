package com.ahujanursinghome.hospital_management.gallery;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GalleryDtoWithoutImage {
    private Long id;
    private String description;
    private String category;
}
