package com.example.peaksoftlmsb8.service.impl;

import com.example.peaksoftlmsb8.db.entity.Student;
import com.example.peaksoftlmsb8.db.entity.User;
import com.example.peaksoftlmsb8.db.enums.Role;
import com.example.peaksoftlmsb8.db.exception.NotFoundException;
import com.example.peaksoftlmsb8.dto.request.StudentRequest;
import com.example.peaksoftlmsb8.dto.response.*;
import com.example.peaksoftlmsb8.repository.StudentRepository;
import com.example.peaksoftlmsb8.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public SimpleResponse save(StudentRequest studentRequest) {
        if (studentRepository.existsByEmail(studentRequest.getEmail())) {
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.CONFLICT)
                    .message("Student with Email: " + studentRequest.getEmail() + " is already saved!")
                    .build();
        }
        if (studentRepository.existsByPhoneNumber(studentRequest.getPhoneNumber())) {
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.CONFLICT)
                    .message("Student with Phone number: " + studentRequest.getPhoneNumber() + " is already saved!")
                    .build();
        }
        User user = new User();
        user.setFirstName(studentRequest.getFirstName());
        user.setLastName(studentRequest.getLastName());
        user.setEmail(studentRequest.getEmail());
        user.setPassword(passwordEncoder.encode(studentRequest.getPassword()));
        user.setPhoneNumber(studentRequest.getPhoneNumber());
        user.setRole(Role.STUDENT);
        Student student = new Student();
        student.setFormLearning(studentRequest.getFormLearning());
        student.setIsBlocked(false);
        student.setGroup(studentRequest.getGroup());
        student.setUser(user);
        user.setStudent(student);
        studentRepository.save(student);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Student with ID: " + student.getId() + " is successfully saved!")
                .build();
    }

    @Override
    public StudentResponse findById(Long studentId) {
        return studentRepository.findStudentById(studentId).orElseThrow(
                () -> new NotFoundException("Student with ID: " + studentId + " is not found!"));
    }

    @Override
    public StudentPaginationResponse findAllPagination(int size, int page, String search, String sort) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(sort));
        Page<StudentResponse> studentResponsePage = studentRepository.findAllStudents(pageable, search);
        List<StudentResponse> studentResponseList = new ArrayList<>(studentResponsePage
                .getContent()
                .stream()
                .map(studentResponse -> new StudentResponse(
                        studentResponse.getId(),
                        studentResponse.getFullName(),
                        studentResponse.getPhoneNumber(),
                        studentResponse.getEmail(),
                        studentResponse.getFormLearning(),
                        studentResponse.getGroupName()))
                .toList());
        StudentPaginationResponse studentPaginationResponse = new StudentPaginationResponse();
        studentPaginationResponse.setStudentResponses(studentResponseList);
        studentPaginationResponse.setPageSize(studentResponsePage.getNumber());
        studentPaginationResponse.setCurrentPage(studentResponsePage.getSize());
        return studentPaginationResponse;
    }

    @Override
    public List<StudentResponseForAdmin> allStudents() {
        if (!studentRepository.allStudents().isEmpty()) {
            return studentRepository.allStudents();
        }
        return null;
    }

    @Override
    public SimpleResponse deleteById(Long studentId) {
        if (studentRepository.existsById(studentId)) {
            studentRepository.deleteById(studentId);
        }
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Student with ID: " + studentId + " is successfully deleted!")
                .build();
    }

    @Override
    public SimpleResponse update(StudentRequest newStudentRequest, Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new NotFoundException("Student with ID: " + studentId + " is not found!"));
        User user = student.getUser();
        if (!newStudentRequest.getFirstName().equals(user.getFirstName())) {
            user.setFirstName(newStudentRequest.getFirstName());
        }
        if (!newStudentRequest.getLastName().equals(user.getLastName())) {
            user.setLastName(newStudentRequest.getLastName());
        }
        if (!newStudentRequest.getEmail().equals(user.getEmail())) {
            if (studentRepository.existsByEmail(newStudentRequest.getEmail())) {
                return SimpleResponse.builder()
                        .httpStatus(HttpStatus.CONFLICT)
                        .message("Student with Email: " + newStudentRequest.getEmail() + " is already saved!")
                        .build();
            }
            user.setEmail(newStudentRequest.getEmail());
        }
        user.setPassword(passwordEncoder.encode(newStudentRequest.getPassword()));
        if (!newStudentRequest.getPhoneNumber().equals(user.getPhoneNumber())) {
            if (studentRepository.existsByPhoneNumber(newStudentRequest.getPhoneNumber())) {
                return SimpleResponse.builder()
                        .httpStatus(HttpStatus.CONFLICT)
                        .message("Student with Phone number: " + newStudentRequest.getPhoneNumber() + " is already saved!")
                        .build();
            }
            user.setPhoneNumber(newStudentRequest.getPhoneNumber());
        }
        if (!newStudentRequest.getFormLearning().equals(student.getFormLearning())) {
            student.setFormLearning(newStudentRequest.getFormLearning());
        }
        if (!newStudentRequest.getGroup().equals(student.getGroup())) {
            student.setGroup(newStudentRequest.getGroup());
        }
        if (!student.getUser().equals(user)) {
            student.setUser(user);
        }
        if (!student.equals(user.getStudent())) {
            user.setStudent(student);
        }
        studentRepository.save(student);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Student with ID: " + studentId + " is successfully updated!")
                .build();
    }
}
