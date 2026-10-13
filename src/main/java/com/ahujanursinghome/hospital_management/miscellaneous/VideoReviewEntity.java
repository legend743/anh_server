package com.ahujanursinghome.hospital_management.miscellaneous;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "video review")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VideoReviewEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String img_url;
}
