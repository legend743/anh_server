package com.ahujanursinghome.hospital_management.appointments;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AppointmentService {
     ResponseEntity<String>bookAppointment(AppointmentEntity appointment);
     List<AppointmentDTO>getAllAppointments();
     ResponseEntity<AppointmentDTO>getAppointmentById(int patientId);

     ResponseEntity<String>getInstantAppointment(InstantAppointmentEntity instantAppointmentEntity);
     ResponseEntity<List<InstantAppointmentEntity>>getAllInstantAppointment();
}
