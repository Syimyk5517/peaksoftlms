package com.example.peaksoftlmsb8.service.serviceImpl;

import com.example.peaksoftlmsb8.db.entity.Student;
import com.example.peaksoftlmsb8.db.exception.NotFoundException;
import com.example.peaksoftlmsb8.repository.StudentRepository;
import com.example.peaksoftlmsb8.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    @Override
    public void save(Student student) {
        studentRepository.save(student);
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
    public void deleteById(Long studentId) {
        if (studentRepository.existsById(studentId)) {
            studentRepository.deleteById(studentId);
        }
    }

    @Override
    public void update(Student newStudent, Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new NotFoundException("Student with ID: " + studentId + " is not found!"));
        studentRepository.save(newStudent);
    }
}
