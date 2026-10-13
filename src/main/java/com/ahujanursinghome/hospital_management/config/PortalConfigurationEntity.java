package com.ahujanursinghome.hospital_management.config;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "configuration")
public class PortalConfigurationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ✅ Corrected
    private int id;

    private String directorNote;

    private String directorImage;

    private String logoBytes;
    private String favIconBytes;
    private String banner;
    private String bannerTagline;
}
