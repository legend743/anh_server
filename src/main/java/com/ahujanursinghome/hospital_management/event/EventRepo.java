package com.ahujanursinghome.hospital_management.event;


import com.ahujanursinghome.hospital_management.department.DepartmentEntity;
import com.ahujanursinghome.hospital_management.doctors.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepo extends JpaRepository<EventEntity,Integer> {
    @Query(value = "SELECT * FROM event WHERE active_event = true", nativeQuery = true)
    List<EventEntity> findActiveEvents();

}
