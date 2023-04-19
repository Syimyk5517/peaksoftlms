package com.example.peaksoftlmsb8.service.impl;

import com.example.peaksoftlmsb8.db.entity.Group;
import com.example.peaksoftlmsb8.db.entity.Student;
import com.example.peaksoftlmsb8.db.entity.User;
import com.example.peaksoftlmsb8.db.enums.Role;
import com.example.peaksoftlmsb8.db.exception.AlReadyExistException;
import com.example.peaksoftlmsb8.db.exception.NotFoundException;
import com.example.peaksoftlmsb8.dto.request.StudentExcelRequest;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.repository.GroupRepository;
import com.example.peaksoftlmsb8.repository.StudentRepository;
import com.example.peaksoftlmsb8.repository.UserRepository;
import com.example.peaksoftlmsb8.service.StudentService;
import com.poiji.bind.Poiji;
import com.poiji.exception.PoijiExcelType;
import com.poiji.option.PoijiOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @Override
    public SimpleResponse importExcel(Long groupId, MultipartFile multipartFile) throws IOException {
        Group group = groupRepository.findById(groupId).orElseThrow(
                () -> new NotFoundException("Group with id" + groupId + "not found!")
        );
        if (!multipartFile.isEmpty()) {

            PoijiOptions poijiOptions = PoijiOptions.PoijiOptionsBuilder.settings().build();
            InputStream inputStream = multipartFile.getInputStream();
            List<StudentExcelRequest> excelRequests = Poiji.fromExcel(inputStream, PoijiExcelType.XLSX, StudentExcelRequest.class, poijiOptions);
            for (StudentExcelRequest excelRequest : excelRequests) {
                boolean exists = userRepository.existsByEmail(excelRequest.getEmail());
                if (exists) {
                    throw new AlReadyExistException("Student with " + excelRequest.getEmail() + "exists!");
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

}