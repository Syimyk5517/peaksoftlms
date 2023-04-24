package com.example.peaksoftlmsb8.service;

import com.example.peaksoftlmsb8.dto.request.StudentRequest;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.dto.response.StudentPaginationResponse;
import com.example.peaksoftlmsb8.dto.response.StudentResponse;
import com.example.peaksoftlmsb8.dto.response.StudentResponseForAdmin;

import java.util.List;

public interface StudentService {
    SimpleResponse save(StudentRequest studentRequest);

    StudentResponse findById(Long studentId);

    StudentPaginationResponse findAllPagination(int size, int page, String word, String sort);

    List<StudentResponseForAdmin> allStudents();

    SimpleResponse deleteById(Long studentId);

    SimpleResponse update(StudentRequest newStudentRequest, Long studentId);
}
