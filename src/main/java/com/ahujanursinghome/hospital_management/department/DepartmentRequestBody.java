package com.ahujanursinghome.hospital_management.department;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentRequestBody {

    @jakarta.validation.constraints.NotBlank(message = "Title must not be blank")
    private String title;
    @jakarta.validation.constraints.NotBlank(message = "Description must not be blank")
    private String description;

    // Instead of passing full DepartmentCategoryEntity,
    // client sends only the category id.
    @jakarta.validation.constraints.Positive(message = "Department category ID must be a positive number")
    private Long departmentCategoryId;

    // MultipartFile is handled separately in Controller method
}
