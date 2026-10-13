package com.ahujanursinghome.hospital_management.department;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<DepartmentEntity,Long> {
//    @Query(value ="Select * from department_entity where is_active=true",nativeQuery = true)
@Query("SELECT d FROM DepartmentEntity d WHERE d.isActive = true")
List<DepartmentEntity> findByIsActiveTrue();

}
