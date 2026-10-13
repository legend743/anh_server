package com.ahujanursinghome.hospital_management.department;

import com.ahujanursinghome.hospital_management.miscellaneous.SaveImageHelperMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Value("${image.upload.dir}")
    private String uploadDir;

    @Autowired
    private DepartmentRepository departmentRepository;


    @Autowired
    private DepartmentCategoryRepository categoryRepository;

    @Autowired
    private SaveImageHelperMethod imageHelperMethod;

    @Override
    public String uploadDepartmentDetails(DepartmentRequestBody departmentRequestBody, MultipartFile file) throws IOException {
        if (departmentRequestBody == null || file == null || file.isEmpty()) {
            return "Either file or department details are missing!";
        }

        try {
            String filePath=imageHelperMethod.saveFile("department",file);

            // Fetch category by ID
            Optional<DepartmentCategoryEntity> categoryOpt = categoryRepository.findById(departmentRequestBody.getDepartmentCategoryId());
            if (categoryOpt.isEmpty()) {
                return "Category with id " + departmentRequestBody.getDepartmentCategoryId() + " not found!";
            }

            DepartmentEntity departmentEntity = new DepartmentEntity();
            departmentEntity.setTitle(departmentRequestBody.getTitle());
            departmentEntity.setDescription(departmentRequestBody.getDescription());
            departmentEntity.setImageUrl(filePath);
            departmentEntity.setDepartment(categoryOpt.get());

            departmentRepository.save(departmentEntity);

            return "Department created successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Unable to create department: " + e.getMessage();
        }
    }

//    @Override
//    public Map<String, List<DepartmentResponseBody>> getAllDepartmentGrouped() {
//        List<DepartmentEntity> activeDepartments = departmentRepository.findByIsActiveTrue();
//
////        return activeDepartments.stream()
////                .filter(d -> d.getDepartment() != null && d.getDepartment().getDepartment() != null)
////                .collect(Collectors.groupingBy(
////                        d -> d.getDepartment().getDepartment(),
////                        Collectors.mapping(DepartmentResponseBody::new, Collectors.toList())
////                ));
//        DepartmentResponseBody departmentResponseBody=new DepartmentResponseBody(
//                activeDepartments.);
//
//        return
//    }

//@Override
//public Map<String, List<DepartmentResponseBody>> getAllDepartmentGrouped() {
//    List<DepartmentEntity> activeDepartments = departmentRepository.findByIsActiveTrue();
//
//    return activeDepartments.stream()
//            .filter(d -> d.getDepartment() != null && d.getDepartment().getDepartment() != null)
//            .collect(Collectors.groupingBy(
//                    d -> d.getDepartment().getDepartment(), // Group by main department name
//                    Collectors.mapping(DepartmentResponseBody::new, Collectors.toList())
//            ));
//}
@Override
public List<GroupedDepartmentResponse> getAllDepartmentGrouped() {
    List<DepartmentCategoryEntity> allCategories = categoryRepository.findAll(); // get all main departments
    List<DepartmentEntity> activeDepartments = departmentRepository.findByIsActiveTrue(); // get active sub-departments

    // Group active sub-departments by their category
    Map<Long, List<DepartmentEntity>> grouped = activeDepartments.stream()
            .filter(d -> d.getDepartment() != null)
            .collect(Collectors.groupingBy(d -> d.getDepartment().getId())); // Group by category ID

    // Build the structured response for all categories
    return allCategories.stream()
            .map(category -> {
                List<DepartmentEntity> children = grouped.getOrDefault(category.getId(), new ArrayList<>());

                List<DepartmentResponseBody> subDepartments = children.stream()
                        .map(DepartmentResponseBody::new)
                        .collect(Collectors.toList());

                GroupedDepartmentResponse response = new GroupedDepartmentResponse();
                response.setDepartmentId(category.getId());
                response.setDepartmentTitle(category.getDepartment());
                response.setSubDepartments(subDepartments);

                return response;
            })
            .collect(Collectors.toList());
}



    @Override
    public String removeDepartment(Long id, Boolean status) {
        Optional<DepartmentEntity> optionalDepartment = departmentRepository.findById(id);
        if (optionalDepartment.isPresent()) {
            DepartmentEntity dept = optionalDepartment.get();
            dept.setActive(status);
            departmentRepository.save(dept);
            return "Department status updated successfully!";
        } else {
            return "Department with id " + id + " not found";
        }
    }
    @Override
    public DepartmentCategoryEntity createCategory(DepartmentCategoryRequestBody request) {
        DepartmentCategoryEntity category = new DepartmentCategoryEntity();
        category.setDepartment(request.getDepartment());
        return categoryRepository.save(category);
    }

    @Override
    public boolean deleteSubDepartment(Long id) {
        if (!departmentRepository.existsById(id)){
            return false;
        }
        departmentRepository.deleteById(id);
        return true;
    }

    @Override
    public List<DepartmentCategoryEntity> getAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public boolean deleteDepartment(Long id) {
        boolean exists = categoryRepository.existsById(id);
        if (exists) {
            try {
                categoryRepository.deleteById(id);
                return true;
            } catch (DataIntegrityViolationException ex) {
                // Foreign key violation caught here
                throw new RuntimeException("Cannot delete department category as it has associated sub-departments.", ex);
            }
        }
        return false;
    }


}

