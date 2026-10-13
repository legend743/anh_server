package com.ahujanursinghome.hospital_management.department;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface DepartmentService {
    String uploadDepartmentDetails(DepartmentRequestBody departmentRequestBody, MultipartFile file) throws IOException;

//    Map<String, List<DepartmentResponseBody>> getAllDepartmentGrouped();
//List<GroupedDepartmentResponse> getAllDepartmentGrouped();
    List<GroupedDepartmentResponse> getAllDepartmentGrouped();
    String removeDepartment(Long id, Boolean status);
    DepartmentCategoryEntity createCategory(DepartmentCategoryRequestBody request);
    boolean deleteSubDepartment(Long id);
    List<DepartmentCategoryEntity>getAllCategory();
//    boolean deleteSubdepartment(Long id);
//    boolean deleteDepartment(Long id);

    boolean deleteDepartment(Long id);
}
