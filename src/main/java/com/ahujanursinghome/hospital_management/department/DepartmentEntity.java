package com.ahujanursinghome.hospital_management.department;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "department")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    @Column(length = 1000)
    private String description;
    private boolean isActive = true;
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private DepartmentCategoryEntity department; // reference to category
}
