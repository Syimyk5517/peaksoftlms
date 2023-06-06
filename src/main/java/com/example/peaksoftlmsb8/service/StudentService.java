package com.example.peaksoftlmsb8.service;

import com.example.peaksoftlmsb8.db.enums.FormLearning;
import com.example.peaksoftlmsb8.dto.request.StudentRequest;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.dto.response.student.StudentPaginationResponse;
import com.example.peaksoftlmsb8.dto.response.student.StudentResponse;
import org.springframework.data.repository.query.Param;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface StudentService {
    SimpleResponse save(StudentRequest studentRequest);

    SimpleResponse importExcel(Long groupId,String link, MultipartFile multipartFile) throws IOException;

    StudentResponse findById(Long studentId);
    List<StudentResponse> findAllStudentsByCourse(Long courseId);
    List<StudentResponse> findAllStudentsByCourseIdWithSort(String formatStudy);
    SimpleResponse deleteById(Long studentId);

    SimpleResponse update(StudentRequest newStudentRequest, Long studentId);

}
