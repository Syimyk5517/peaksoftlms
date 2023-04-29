package com.example.peaksoftlmsb8.service.impl;

import com.example.peaksoftlmsb8.db.entity.Group;
import com.example.peaksoftlmsb8.db.entity.Student;
import com.example.peaksoftlmsb8.db.entity.User;
import com.example.peaksoftlmsb8.db.enums.Role;
import com.example.peaksoftlmsb8.db.exception.AlReadyExistException;
import com.example.peaksoftlmsb8.db.exception.BadRequestException;
import com.example.peaksoftlmsb8.db.exception.NotFoundException;
import com.example.peaksoftlmsb8.dto.request.StudentExcelRequest;
import com.example.peaksoftlmsb8.dto.request.StudentRequest;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.dto.response.StudentPaginationResponse;
import com.example.peaksoftlmsb8.dto.response.StudentResponse;
import com.example.peaksoftlmsb8.dto.response.StudentResponseForAdmin;
import com.example.peaksoftlmsb8.repository.GroupRepository;
import com.example.peaksoftlmsb8.repository.StudentRepository;
import com.example.peaksoftlmsb8.repository.UserRepository;
import com.example.peaksoftlmsb8.service.StudentService;
import com.poiji.bind.Poiji;
import com.poiji.exception.PoijiExcelType;
import com.poiji.option.PoijiOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
     private final PasswordEncoder passwordEncoder;

    @Override
    public SimpleResponse importExcel(Long groupId, MultipartFile multipartFile) throws IOException {
        Group group = groupRepository.findById(groupId).orElseThrow(
                () -> new NotFoundException("Group with id " + groupId + " not found!")
        );
        if (!multipartFile.isEmpty()) {

            PoijiOptions poijiOptions = PoijiOptions.PoijiOptionsBuilder.settings().build();
            InputStream inputStream = multipartFile.getInputStream();
            List<StudentExcelRequest> excelRequests = Poiji.fromExcel(inputStream, PoijiExcelType.XLSX, StudentExcelRequest.class, poijiOptions);
            for (StudentExcelRequest excelRequest : excelRequests) {
                if (userRepository.existsByEmail(excelRequest.getEmail())) {
                    throw new AlReadyExistException("Student with " + excelRequest.getEmail() + " exists!");
                }


                User user = User.builder()
                        .firstName(excelRequest.getFirstNAme())
                        .lastName(excelRequest.getLastName())
                        .email(excelRequest.getEmail())
                        .phoneNumber(excelRequest.getPhoneNumber())
                        .role(Role.STUDENT)
                        .build();

                Student student = Student.builder()
                        .user(user)
                        .formLearning(excelRequest.getFormLearning())
                        .isBlocked(false)
                        .group(group)
                        .build();
                group.setStudents(List.of(student));
                user.setStudent(student);
                studentRepository.save(student);
            }


        }
        return SimpleResponse.builder().httpStatus(HttpStatus.OK)
                .message("Students successfully uploaded").build();
    }


    @Override
    public SimpleResponse save(StudentRequest studentRequest) {
        if (studentRepository.existsByEmail(studentRequest.getEmail())) {
            throw new BadRequestException("Student with Email: " + studentRequest.getEmail() + " is already saved!");
        }
        if (studentRepository.existsByPhoneNumber(studentRequest.getPhoneNumber())) {
            throw new BadRequestException("Student with Phone number: " + studentRequest.getPhoneNumber() + " is already saved!");
        }
        Group group = groupRepository.findById(studentRequest.getGroupId()).orElseThrow(
                () -> new NotFoundException("Group with id : " + studentRequest.getGroupId() + "not found !"));
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
        student.setGroup(group);
        group.setStudents(List.of(student));
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
        StudentPaginationResponse studentPaginationResponse = new StudentPaginationResponse();
        studentPaginationResponse.setStudentResponses(studentResponsePage.getContent());
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
        if (!studentRepository.existsById(studentId)) {
            throw new BadRequestException("Student with ID: " + studentId + " is not found!");
        }
        studentRepository.deleteById(studentId);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Student with ID: " + studentId + " is successfully deleted!")
                .build();
    }

    @Override
    public SimpleResponse update(StudentRequest newStudentRequest, Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new NotFoundException("Student with ID: " + studentId + " is not found!"));
        Group group = groupRepository.findById(newStudentRequest.getGroupId()).orElseThrow(
                () -> new NotFoundException("Group with id : " + newStudentRequest.getGroupId() + "not found !"));
        User user = student.getUser();
        if (!newStudentRequest.getFirstName().equals(user.getFirstName())) {
            user.setFirstName(newStudentRequest.getFirstName());
        }
        if (!newStudentRequest.getLastName().equals(user.getLastName())) {
            user.setLastName(newStudentRequest.getLastName());
        }
        if (!newStudentRequest.getEmail().equals(user.getEmail())) {
            if (studentRepository.existsByEmail(newStudentRequest.getEmail())) {
                throw new BadRequestException("Student with Email: " + newStudentRequest.getEmail() + " is already saved!");
            }
            user.setEmail(newStudentRequest.getEmail());
        }
        user.setPassword(passwordEncoder.encode(newStudentRequest.getPassword()));
        if (!newStudentRequest.getPhoneNumber().equals(user.getPhoneNumber())) {
            if (studentRepository.existsByPhoneNumber(newStudentRequest.getPhoneNumber())) {
                throw new BadRequestException("Student with Phone number: " + newStudentRequest.getPhoneNumber() + " is already saved!");
            }
            user.setPhoneNumber(newStudentRequest.getPhoneNumber());
        }
        if (!newStudentRequest.getFormLearning().equals(student.getFormLearning())) {
            student.setFormLearning(newStudentRequest.getFormLearning());
        }

            student.setGroup(group);
            group.setStudents(List.of(student));

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

