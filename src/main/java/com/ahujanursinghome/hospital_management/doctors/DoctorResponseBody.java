package com.ahujanursinghome.hospital_management.doctors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor


public class DoctorResponseBody {
    private String name; // e.g., Dr. Sarah Sharma

    private String qualifications; // e.g., MBBS, MD

    private String specialization; // e.g., Cardiologist

    private int experienceYears; // e.g., 12

    private String department; // Optional: If tied to a specific department

    private String bio; // Optional: Short summary or description

    private String contactNumber; // Optional

    private String email; // Optional
    private String url;
}
