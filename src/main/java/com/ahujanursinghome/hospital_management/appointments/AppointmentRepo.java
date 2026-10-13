package com.ahujanursinghome.hospital_management.appointments;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepo extends JpaRepository<AppointmentEntity,Integer> {
}
