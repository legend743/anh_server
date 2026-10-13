package com.ahujanursinghome.hospital_management.gallery;


import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor

public class GalleryCategoryDto {
    private Long id;
    private String category;
    private String description;
    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<GalleryImageDto> images;

    public GalleryCategoryDto(Long id, String category, String description, List<GalleryImageDto> images) {
        this.id = id;
        this.category = category;
        this.description = description;
        this.images = images;
    }

}
