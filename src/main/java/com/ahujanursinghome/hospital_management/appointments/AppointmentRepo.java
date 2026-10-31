package com.ahujanursinghome.hospital_management.appointments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository 

public interface AppointmentRepo extends JpaRepository<AppointmentEntity, Integer> {

    @Query("""
            SELECT COUNT(a) FROM AppointmentEntity a WHERE a.status=false """)
    public int countPendinAppointment();
}
