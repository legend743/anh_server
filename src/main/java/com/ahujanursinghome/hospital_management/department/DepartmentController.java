package com.ahujanursinghome.hospital_management.department;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Encoding;
import jakarta.validation.Valid;

import java.io.IOException;
import java.util.List;


@RestController
@Validated 
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    /**
     * Upload department details along with image
     */
    @PostMapping(value = "/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
    content = @Content(
        mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
        encoding = {
            @Encoding(
                name = "departmentRequestBody",
                contentType = MediaType.APPLICATION_JSON_VALUE
            ),
            @Encoding(
                name = "file",
                contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE
            )
        }
    )
)
    public ResponseEntity<String> uploadDepartment(
            @Valid @RequestPart("departmentRequestBody") DepartmentRequestBody departmentRequestBody,
            @RequestParam("file") MultipartFile file) throws IOException {

        // DepartmentRequestBody departmentRequestBody = new DepartmentRequestBody(departmentRequestBody.getTitle(), departmentRequestBody.getDescription(), departmentRequestBody.getDepartmentCategoryId());

        String result = departmentService.uploadDepartmentDetails(departmentRequestBody, file);
        return ResponseEntity.ok(result);
    }

    /**
     * Get all active departments grouped by category name
     */
//    @GetMapping("/getAllDepartment")
//    public ResponseEntity<Map<String, List<DepartmentResponseBody>>> getDepartmentsGrouped() {
//        Map<String, List<DepartmentResponseBody>> response = departmentService.getAllDepartmentGrouped();
//        if (response.isEmpty()){
//            return ResponseEntity.noContent().build();
//        }
//        return ResponseEntity.ok(response);
//    }
    @GetMapping("/getAll")
    public ResponseEntity<List<GroupedDepartmentResponse>> getDepartmentsGrouped() {
        List<GroupedDepartmentResponse> response = departmentService.getAllDepartmentGrouped();
        if (response.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(response);
    }


    @GetMapping("/getAllDepartment")
    public ResponseEntity<List<DepartmentCategoryEntity>> getAllDepartmentCategory() {
        List<DepartmentCategoryEntity> allCategory = departmentService.getAllCategory();

        if (allCategory.isEmpty()) {
            return ResponseEntity.noContent().build(); // or return a custom message
        }

        return ResponseEntity.ok(allCategory);
    }

    /**
     * Soft delete or restore department by setting active status
     */
    @PutMapping("/{id}/status")
    public ResponseEntity<String> updateDepartmentStatus(@PathVariable @jakarta.validation.constraints.Positive  Long id, @RequestParam Boolean active) {
        String result = departmentService.removeDepartment(id, active);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<DepartmentCategoryEntity> createCategory(@Valid @RequestBody DepartmentCategoryRequestBody request) {
        DepartmentCategoryEntity createdCategory = departmentService.createCategory(request);
        return ResponseEntity.ok(createdCategory);
    }

    @DeleteMapping("/delete-subdepartment/{id}")
    public ResponseEntity<String> deleteDepartmentById(@PathVariable Long id) {
        boolean result = departmentService.deleteSubDepartment(id);
        try {
            if (result) {
                return ResponseEntity.ok("sub-Department Deleted Successfully!");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("sub-Department with ID " + id + " not found.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while deleting the sub-department : " + e.getMessage());
        }

    }

    @DeleteMapping("/delete-department/{id}")
    public ResponseEntity<String> deleteDepartment(@PathVariable Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().body("Please provide a valid department category ID.");
        }

        try {
            boolean result = departmentService.deleteDepartment(id);
            if (result) {
                return ResponseEntity.ok("Department category deleted successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Department category with ID " + id + " not found.");
            }
        } catch (RuntimeException ex) {
            // Custom message from service
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ex.getMessage());  // e.g., "Cannot delete department category as it has associated sub-departments."
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unexpected error occurred: " + e.getMessage());
        }
    }

}


