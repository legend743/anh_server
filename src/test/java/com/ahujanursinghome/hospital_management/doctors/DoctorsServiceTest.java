package com.ahujanursinghome.hospital_management.doctors;

import com.ahujanursinghome.hospital_management.config.ImageUrlBuilder;
import com.ahujanursinghome.hospital_management.miscellaneous.SaveImageHelperMethod;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.StackWalker.Option;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
public class DoctorsServiceTest {
        @Mock
        private DoctorRepo doctorRepo;
        @Mock
        private ImageUrlBuilder imageUrlBuilder;
        @Mock
        private SaveImageHelperMethod saveImage;

        @InjectMocks
        private DoctorServiceImpl doctorServiceImpl;
        @TempDir
        Path TempDir;

        @Test
        void uploadDoctorsDetailsTest() throws Exception {
                ReflectionTestUtils.setField(doctorServiceImpl, "upload", TempDir.resolve("uploads").toString());
                MockMultipartFile file = new MockMultipartFile(
                                "file", // Parameter name
                                "doctor.jpg", // Original filename
                                "image/jpeg", // Content type
                                "dummy image content".getBytes()); // File content);
                DoctorRequestBody doctorRequestBody = DoctorRequestBody.builder().name("Jakir Ali")
                                .qualifications("Medical").specialization("MD").bio("Best in his filed")
                                .contactNumber("8980090990")
                                .department("OT").email("jakir@umail.com").experienceYears(10).build();
                // when(doctorRepo.findById()).thenReturn(doctorEntity);
                ResponseEntity<String> res = doctorServiceImpl.uploadDoctorsDetails(doctorRequestBody, file);
                assertEquals(200, res.getStatusCode().value());
        }

        @Test
        void removeDoctorDetailsTest() {
                DoctorEntity doctorEntity = DoctorEntity.builder().name("Jakir").qualifications("Medical")
                                .experienceYears(25).specialization("Ops").contactNumber("90000000000")
                                .email("jakir@gmail.com").activeDoctor(true).imageUrl("/upload/abcdotcor90239.jped")
                                .build();
                when(doctorRepo.findById(1L)).thenReturn(Optional.of(doctorEntity));
                when(doctorRepo.save(any(DoctorEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));
                // when(doctorRepo.findAll())
                //                 .thenReturn(List.of());
                when(doctorRepo.findActiveDoctors()).thenReturn(List.of(doctorEntity));
                List<DoctorEntity> doc = doctorServiceImpl.removeDoctorDetails(1L);
                assertEquals(List.of(doctorEntity), doc);
                verify(doctorRepo).save(doctorEntity);

        }
}
