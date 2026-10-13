package com.ahujanursinghome.hospital_management.department;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupedDepartmentResponse {
    private Long departmentId;  // ID of the main department
    private String departmentTitle;  // Name of the main department (e.g., OPD)
    private List<DepartmentResponseBody> subDepartments;  // List of subdepartments
}
