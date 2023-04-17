package com.example.peaksoftlmsb8.service.serviceImpl;

import com.example.peaksoftlmsb8.db.entity.Student;
import com.example.peaksoftlmsb8.db.exception.NotFoundException;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.repository.StudentRepository;
import com.example.peaksoftlmsb8.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    @Override
    public SimpleResponse save(Student student) {
        studentRepository.save(student);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Student with ID: "+student.getId()+" is successfully saved!")
                .build();
    }

    @Override
    public Student findById(Long studentId) {
        return studentRepository.findById(studentId).orElseThrow(
                () -> new NotFoundException("Student with ID: " + studentId + " is not found!"));
    }

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public SimpleResponse deleteById(Long studentId) {
        if (studentRepository.existsById(studentId)) {
            studentRepository.deleteById(studentId);
        }
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Student with ID: "+studentId+" is successfully deleted!")
                .build();
    }

    @Override
    public SimpleResponse update(Student newStudent, Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new NotFoundException("Student with ID: " + studentId + " is not found!"));
        studentRepository.save(newStudent);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Student with ID: "+studentId+" is successfully updated!")
                .build();
    }
}
