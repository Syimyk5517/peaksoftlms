package com.example.peaksoftlmsb8.service;

import com.example.peaksoftlmsb8.db.entity.Student;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;

import java.util.List;

public interface StudentService {
    SimpleResponse save(Student student);
    Student findById(Long studentId);
    List<Student> findAll();
    SimpleResponse deleteById(Long studentId);
    SimpleResponse update(Student newStudent,Long studentId);
}
