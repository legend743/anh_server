package com.ahujanursinghome.hospital_management.Appointment;

import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.ahujanursinghome.hospital_management.appointments.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class AppointmentServiceTest {

    @Mock
    private AppointmentRepo appointmentRepo;
    @Mock
    private InstantAppointmentRepo instantAppointmentRepo;
    @InjectMocks
    private AppointmentServiceImpl appointmentService;

    @Test
    void bookAppointmentTest() {
        LocalDate localDate = LocalDate.of(2026, 10, 27);
        Date date = Date.from(
                localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        AppointmentEntity aEntity = new AppointmentEntity();

        aEntity.setAppointmentDate(date);
        aEntity.setDepartment("Pediatrician");
        aEntity.setEmail("fmohd743@gmail.com");
        aEntity.setGender('M');
        aEntity.setName("Ravi raj");
        aEntity.setPhone("02929302923");
        ResponseEntity<String> result = appointmentService.bookAppointment(aEntity);
        assertEquals(201, result.getStatusCode().value());
        System.out.println("test pass");

    }

    @Test
    void getAllInstantAppointmentTest() {
        InstantAppointmentEntity iAE = new InstantAppointmentEntity();
        iAE.setDoctorname("Anamta Deny");
        iAE.setEntertainedByReception(false);
        iAE.setName("Juhi Signh");
        iAE.setMobileNumber("9309380092");
        iAE.setSpeciality("Pediatrician");
        ResponseEntity<String> book = appointmentService.getInstantAppointment(iAE);
        assertEquals(200, book.getStatusCode().value());
        assertEquals("Appointment saved successfully!", book.getBody());
    }

    @Test
    void getAllAppointmentsTest() {
        AppointmentDTO dto = AppointmentDTO.builder().name("Anamta Deny").department("pediatrician")
                .email("fmohd743@gmail.com").build();
        AppointmentEntity iAE = AppointmentEntity
                .builder().name("Anamta Deny").department("pediatrician").email("fmohd743@gmail.com").build();
        when(appointmentRepo.findAll()).thenReturn(List.of(iAE));
        List<AppointmentDTO> response = appointmentService.getAllAppointments();

        assertEquals(List.of(dto), response, "Test pass with grace");
    }

    @Test
    void getAppointmentByIdTest() {
        AppointmentDTO acutalResult = AppointmentDTO.builder().name("Faisal").Gender('M').department("Neuro")
                .email("fmohd743@gmail.com").phone("8909802233").symptoms("jhun-jhuni").build();

        AppointmentEntity appointmentEntity = AppointmentEntity.builder().name("Faisal").Gender('M').department("Neuro")
                .email("fmohd743@gmail.com").phone("8909802233").symptoms("jhun-jhuni").build();
        when(appointmentRepo.findById(1)).thenReturn(Optional.of(appointmentEntity));
        ResponseEntity<AppointmentDTO> expectedResult = appointmentService.getAppointmentById(1);
        assertEquals(acutalResult, expectedResult.getBody());
        assertEquals(HttpStatus.OK, expectedResult.getStatusCode());
    }

}
