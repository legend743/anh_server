package com.ahujanursinghome.hospital_management.about;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "anhjourney")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AboutJourneyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(length = 1000)
    private String description;
    private String imgurl;
}
