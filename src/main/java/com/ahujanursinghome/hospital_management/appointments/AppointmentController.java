package com.ahujanursinghome.hospital_management.appointments;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {
    @Autowired
    private AppointmentServiceImpl appointmentService;

    @Autowired
    private InstantAppointmentRepo appointmentRepo;

    @PostMapping("/bookAppointment")
    public ResponseEntity<String>bookAppointment(@RequestBody AppointmentEntity appointmentEntity){
        try {
           if (appointmentEntity==null){
               return ResponseEntity.noContent().build();
           }
            appointmentService.bookAppointment(appointmentEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body("Appointment booked succesfully!!");

        }catch (Exception e){
            e.printStackTrace();
        }
return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to created");
    }

    @PatchMapping("/isEntertained/{Id}")
    public ResponseEntity<String>entertainedByReception(@PathVariable Long Id){
       Optional<InstantAppointmentEntity> appointmentOptional= appointmentRepo.findById(Id);

        if (appointmentOptional.isPresent()) {
            InstantAppointmentEntity appointment = appointmentOptional.get();
            appointment.setEntertainedByReception(true); // ✅ update field
            appointmentRepo.save(appointment);       // ✅ save updated entity

            return ResponseEntity.ok("Appointment updated successfully.");
        } else {
            return ResponseEntity.status(404).body("Appointment not found.");
        }

    }
@GetMapping("/getAllAppointment")
    public ResponseEntity<List<AppointmentDTO>>getAllAppointmentDetails(){
    List<AppointmentDTO> appointmentDTO=appointmentService.getAllAppointments();
    if (appointmentDTO.isEmpty()){
        return ResponseEntity.noContent().build();
    }
        return ResponseEntity.ok(appointmentDTO);
}
@GetMapping("/getAppointment/{ID}")
    public ResponseEntity<AppointmentDTO>getAppointmentById(@PathVariable Integer ID){

    return  appointmentService.getAppointmentById(ID);

}
@PostMapping("/instant-appointment")
    public ResponseEntity<String>bookInstandAppointment(@RequestBody InstantAppointmentEntity instantAppointmentEntity){
       return appointmentService.getInstantAppointment(instantAppointmentEntity);
}
@GetMapping("/getAllInstant-appointment")
    public ResponseEntity<List<InstantAppointmentEntity>>getAllInstatntAppointment(){
//        List<InstantAppointmentEntity>allInstantAppointment=appointmentService.getAllInstantAppointment();
    return appointmentService.getAllInstantAppointment();
}

}
