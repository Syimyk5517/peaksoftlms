package com.example.peaksoftlmsb8.api;

import com.example.peaksoftlmsb8.dto.request.StudentRequest;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.dto.response.student.StudentResponse;
import com.example.peaksoftlmsb8.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/students")
@RequiredArgsConstructor
@Tag(name = "Students")
public class StudentApi {
    private final StudentService studentService;
    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            path = "/import")
    @Operation(summary = "This method import excel file to database",
            description = "Add students from excel file by administrator")
    public SimpleResponse importExcel(@RequestParam Long id, @RequestParam(name = "file") MultipartFile multipartFile) throws IOException {
        return studentService.importExcel(id, multipartFile);
    }

    @Operation(summary = "This method can save Students",
            description = "You can save Students in Database")
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN,INSTRUCTOR')")
    public SimpleResponse saveStudent(@RequestBody @Valid StudentRequest studentRequest) {
        return studentService.save(studentRequest);
    }


    @Operation(summary = "This method can get Student with ID",
            description = "You can get Student with ID")
    @GetMapping("/getById")
    @PreAuthorize("hasAnyAuthority('ADMIN,INSTRUCTOR')")
    public StudentResponse findById(@RequestParam Long studentId) {
        return studentService.findById(studentId);
    }
    @Operation(summary = "This method can get Student with Group ID",
            description = "You can get Student with ID")
    @GetMapping()
    @PreAuthorize("hasAnyAuthority('ADMIN,INSTRUCTOR')")
    public List<StudentResponse> findAllStudentsByGroupId(@RequestParam Long courseId) {
        return studentService.findAllStudentsByCourse(courseId);
    }
    @Operation(summary = "This method can get Students",
            description = "You can get Students")
    @GetMapping("/getAllForAdmin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<StudentResponse> findAllStudentsByGroupIdAdmin(
                                                               @RequestParam(required = false) String formStudy) {
        return studentService.findAllStudentsByCourseIdWithSort(formStudy);
    }

    @Operation(summary = "This method can update Student with ID",
            description = "You can update Student with ID")
    @PutMapping
    @PreAuthorize("hasAnyAuthority('ADMIN,INSTRUCTOR')")
    public SimpleResponse update(@RequestParam Long studentId,
                                 @RequestBody StudentRequest newStudentRequest) {
        return studentService.update(newStudentRequest, studentId);
    }

    @Operation(summary = "This method can delete Student with ID",
            description = "You can delete Student with ID")
    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('ADMIN,INSTRUCTOR')")
    public SimpleResponse delete(@RequestParam Long studentId) {
        return studentService.deleteById(studentId);
    }
}
