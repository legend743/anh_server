package com.ahujanursinghome.hospital_management.appointments;

import lombok.*;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class AppointmentDTO {
    private String name;
    private  String number;
    private String department;
    private Date appointment_date;
    private String symptoms;
    private  String phone;
    private String email;
    private Character Gender;

}
