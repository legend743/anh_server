package com.ahujanursinghome.hospital_management.department;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "departmentcategory")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentCategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String department;  // e.g. Emergency, Pediatrics
}
