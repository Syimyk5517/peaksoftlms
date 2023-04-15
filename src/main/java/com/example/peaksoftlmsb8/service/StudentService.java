package com.example.peaksoftlmsb8.service;

import com.example.peaksoftlmsb8.db.entity.Student;

import java.util.List;

public interface StudentService {
    void save(Student student);
    Student findById(Long studentId);
    List<Student> findAll();
    void deleteById(Long studentId);
    void update(Student newStudent,Long studentId);
}
