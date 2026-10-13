package com.ahujanursinghome.hospital_management.appointments;

import jakarta.persistence.*;
import lombok.*;

@Entity

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "instantappointment")

public class InstantAppointmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;
    private  String name;
    private String mobileNumber;
    private String doctorname;
    private String speciality;
    private boolean entertainedByReception=false;
}
