package com.ahujanursinghome.hospital_management.doctors;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="doctordetails")
public class DoctorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // e.g., Dr. Sarah Sharma

    private String qualifications; // e.g., MBBS, MD

    private String specialization; // e.g., Cardiologist

    private int experienceYears; // e.g., 12

    private String department; // Optional: If tied to a specific department
    @Column(length = 1000)
    private String bio; // Optional: Short summary or description

    private String contactNumber; // Optional

    private String email; // Optional

    private String imageUrl; // Optional: Profile picture
    private boolean activeDoctor=true;


}

