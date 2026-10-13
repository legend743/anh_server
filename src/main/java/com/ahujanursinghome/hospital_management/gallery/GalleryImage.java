package com.ahujanursinghome.hospital_management.gallery;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "galleryimages")
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GalleryImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imgUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private GalleryCategory gallery;


}
