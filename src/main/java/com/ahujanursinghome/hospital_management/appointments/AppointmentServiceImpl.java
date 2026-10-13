package com.ahujanursinghome.hospital_management.appointments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepo appointmentRepo;
    @Autowired
    private InstantAppointmentRepo instantAppointmentRepo;
    @Override
    public ResponseEntity<String> bookAppointment(AppointmentEntity appointment) {
        if(appointment==null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data is null");
        }
        try{
            appointmentRepo.save(appointment);
            return ResponseEntity.status(HttpStatus.CREATED).body("appointment booked successfully!");

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to book appointment: " + e.getMessage());
        }
    }

    @Override
    public List<AppointmentDTO> getAllAppointments() {


            return appointmentRepo.findAll()
                    .stream()
                    .map(this::ConvertAppointmentToDto)
                    .toList();


    }

    @Override
    public ResponseEntity<AppointmentDTO> getAppointmentById(int patientId) {
        Optional<AppointmentEntity> patientAppointment = appointmentRepo.findById(patientId);

        if (patientAppointment.isPresent()) {
            AppointmentDTO dto = ConvertAppointmentToDto(patientAppointment.get());
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override

    public ResponseEntity<String> getInstantAppointment(InstantAppointmentEntity instantAppointmentEntity) {
        if (instantAppointmentEntity == null) {
            return ResponseEntity.badRequest().body("Invalid appointment data");
        }

        try {
            instantAppointmentRepo.save(instantAppointmentEntity);
            return ResponseEntity.ok("Appointment saved successfully!");
        } catch (Exception e) {
            // Log the error if you want (for now, simple return)
            return ResponseEntity.status(500).body("Failed to save appointment: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<List<InstantAppointmentEntity>> getAllInstantAppointment() {
        List<InstantAppointmentEntity>allAppointment=instantAppointmentRepo.findAll();
        if (allAppointment.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(allAppointment);
    }


    private AppointmentDTO ConvertAppointmentToDto(AppointmentEntity appointmententity){
        return  AppointmentDTO.builder()
                .name(appointmententity.getName())
                .phone(appointmententity.getPhone())
                .appointment_date(appointmententity.getAppointmentDate())
                .email(appointmententity.getEmail())
                .department(appointmententity.getDepartment())
                .Gender(appointmententity.getGender())
                .symptoms(appointmententity.getSymptoms())
                .build();
    }
        }


