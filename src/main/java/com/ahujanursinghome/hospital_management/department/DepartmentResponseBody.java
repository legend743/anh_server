package com.ahujanursinghome.hospital_management.department;

import lombok.Getter;

@Getter
public class DepartmentResponseBody {
    private Long id;
    private String subdepartment;
    private String description;
    private String icon;

    public DepartmentResponseBody(DepartmentEntity entity) {
        this.id = entity.getId();
        this.subdepartment = entity.getTitle();
        this.description = entity.getDescription();
        this.icon = entity.getImageUrl();
    }
}
