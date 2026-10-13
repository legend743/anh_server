package com.ahujanursinghome.hospital_management.about;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "directordetails")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AboutDirectorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String directorname;
    private String designation;

    @Column(length = 1000)
    private String description;
    private String imgurl;

}
