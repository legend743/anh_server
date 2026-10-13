package com.ahujanursinghome.hospital_management.about;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "aboutbannerimages")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AboutBannerImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imgurl;

}
