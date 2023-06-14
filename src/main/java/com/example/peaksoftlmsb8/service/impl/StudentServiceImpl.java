package com.example.peaksoftlmsb8.service.impl;

import com.example.peaksoftlmsb8.db.entity.Group;
import com.example.peaksoftlmsb8.db.entity.Student;
import com.example.peaksoftlmsb8.db.entity.User;
import com.example.peaksoftlmsb8.db.enums.FormLearning;
import com.example.peaksoftlmsb8.db.enums.Role;
import com.example.peaksoftlmsb8.dto.request.student.StudentExcelRequest;
import com.example.peaksoftlmsb8.dto.request.student.StudentRequest;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.dto.response.student.StudentResponse;
import com.example.peaksoftlmsb8.exception.AlReadyExistException;
import com.example.peaksoftlmsb8.exception.BadRequestException;
import com.example.peaksoftlmsb8.exception.NotFoundException;
import com.example.peaksoftlmsb8.repository.GroupRepository;
import com.example.peaksoftlmsb8.repository.ResultOfTestRepository;
import com.example.peaksoftlmsb8.repository.StudentRepository;
import com.example.peaksoftlmsb8.repository.UserRepository;
import com.example.peaksoftlmsb8.service.EmailSenderService;
import com.example.peaksoftlmsb8.service.StudentService;
import com.poiji.bind.Poiji;
import com.poiji.exception.PoijiExcelType;
import com.poiji.option.PoijiOptions;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final EmailSenderService emailSenderService;
    private final ResultOfTestRepository resultOfTestRepository;
    private static final Logger logger = LogManager.getLogger(CourseServiceImpl.class);

    @Override
    public SimpleResponse importExcel(Long groupId, String link, MultipartFile multipartFile) throws IOException {
        Group group = groupRepository.findById(groupId).orElseThrow(
                () -> {
                    logger.error("Group with id " + groupId + " not found!");
                  throw    new NotFoundException("Группа с идентификатором:" + groupId + " не найдена!");
                }
        );
        if (!multipartFile.isEmpty()) {

            PoijiOptions poijiOptions = PoijiOptions.PoijiOptionsBuilder.settings().build();
            InputStream inputStream = multipartFile.getInputStream();
            @Valid
            List<StudentExcelRequest> excelRequests = Poiji.fromExcel(inputStream, PoijiExcelType.XLSX, StudentExcelRequest.class, poijiOptions);
            for (StudentExcelRequest excelRequest : excelRequests) {
                if (userRepository.existsByEmail(excelRequest.getEmail())) {
                    logger.info("Student with " + excelRequest.getEmail() + " exists!");
                    throw new AlReadyExistException("Студент с " + excelRequest.getEmail() + " существует!");
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
                emailSenderService.emailSender(excelRequest.getEmail(), link);
            }


        }
        logger.info("Students successfully uploaded");
        return SimpleResponse.builder().httpStatus(HttpStatus.OK)
                .message("Студенты успешно загружены").build();
    }


    @Override
    public SimpleResponse save(StudentRequest studentRequest) {
        if (studentRepository.existsByEmail(studentRequest.getEmail())) {
            logger.info("Student with Email: " + studentRequest.getEmail() + " is already saved!");
            throw new BadRequestException("Учащийся с электронной почтой: " + studentRequest.getEmail() + " уже сохранен!");
        }
        if (studentRepository.existsByPhoneNumber(studentRequest.getPhoneNumber())) {
            logger.info("Student with Phone number: " + studentRequest.getPhoneNumber() + " is already saved!");
            throw new BadRequestException("Студент с номером телефона: " + studentRequest.getPhoneNumber() + " уже сохранен!");
        }
        Group group = groupRepository.findById(studentRequest.getGroupId()).orElseThrow(
                () -> {
                    logger.error("Group with id : " + studentRequest.getGroupId() + "not found !");
               throw new NotFoundException("Группа с идентификатором: " + studentRequest.getGroupId() + " не найдена!");});
        User user = new User();
        user.setFirstName(studentRequest.getFirstName());
        user.setLastName(studentRequest.getLastName());
        user.setEmail(studentRequest.getEmail());
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
        emailSenderService.emailSender(studentRequest.getEmail(), studentRequest.getLink());
        logger.info("Student with ID: " + student.getId() + " is successfully saved!");
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Студент с идентификатором: " + student.getId() + " успешно сохранен!")
                .build();
    }

    @Override
    public StudentResponse findById(Long studentId) {
        return studentRepository.findStudentById(studentId).orElseThrow(
                () -> { logger.error("Student with id: "+studentId+" not found!");
                  throw   new NotFoundException("Студент с идентификатором: " + studentId + " не найден!");
                });
    }

    @Override
    public List<StudentResponse> findAllStudentsByCourse(Long courseId) {
        if (studentRepository.findAllStudentsByCourseId(courseId).isEmpty()) {
            logger.error("Student not found!");
            throw new NotFoundException("Студенты не найдены!");
        }
        return studentRepository.findAllStudentsByCourseId(courseId);
    }

    @Override
    public List<StudentResponse> findAllStudentsByCourseIdWithSort(String formatStudy) {
        if (formatStudy.equalsIgnoreCase("все")){
            return studentRepository.getAll();
        }else {
            FormLearning formLearning = FormLearning.valueOf(formatStudy);
            return studentRepository.findAllStudentsByCourseIdWithSort(formLearning);
        }
    }

    @Override
    public SimpleResponse deleteById(Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> { logger.error("Student with id: " +studentId+
                        " not found");
                    throw new NotFoundException("Студент с идентификатором: " + studentId + " не найден!");});
        resultOfTestRepository.deleteByStudentId(student.getId());
        studentRepository.delete(student);
        logger.info("Student with id: "+studentId+ " successfully deleted!");
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Студент с идентификатором: " + studentId + " успешно удален!")
                .build();
    }

    @Override
    public SimpleResponse update(StudentRequest newStudentRequest, Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> {
                    logger.error("Student with ID: " + studentId + " is not found!");
                  throw new NotFoundException("Студент с идентификатором: " + studentId + " не найден!");});
        Group group = groupRepository.findById(newStudentRequest.getGroupId()).orElseThrow(
                () ->{
                    logger.error("Group with id : " + newStudentRequest.getGroupId() + "not found !");
                throw new NotFoundException("Группа с идентификатором: " + newStudentRequest.getGroupId() + " не найдена!");});
        student.getUser().setFirstName(newStudentRequest.getFirstName());
        student.getUser().setLastName(newStudentRequest.getLastName());
        student.getUser().setEmail(newStudentRequest.getEmail());
        student.getUser().setPhoneNumber(newStudentRequest.getPhoneNumber());
        student.setFormLearning(newStudentRequest.getFormLearning());
        student.setGroup(group);
        studentRepository.save(student);
        logger.info("Student with ID: " + studentId + " is successfully updated!");
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Студент с идентификатором: " + studentId + " успешно обновлен!")
                .build();
    }
}

