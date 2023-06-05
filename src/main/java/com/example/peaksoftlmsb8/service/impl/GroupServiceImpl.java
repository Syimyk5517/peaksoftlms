package com.example.peaksoftlmsb8.service.impl;

import com.example.peaksoftlmsb8.db.entity.Course;
import com.example.peaksoftlmsb8.db.entity.Group;
import com.example.peaksoftlmsb8.db.entity.Student;
import com.example.peaksoftlmsb8.db.exception.NotFoundException;
import com.example.peaksoftlmsb8.dto.request.group.GroupRequest;
import com.example.peaksoftlmsb8.dto.request.group.GroupUpdateRequest;
import com.example.peaksoftlmsb8.dto.response.group.GroupPaginationResponse;
import com.example.peaksoftlmsb8.dto.response.group.GroupResponse;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.repository.CourseRepository;
import com.example.peaksoftlmsb8.repository.GroupRepository;
import com.example.peaksoftlmsb8.repository.ResultOfTestRepository;
import com.example.peaksoftlmsb8.repository.UserRepository;
import com.example.peaksoftlmsb8.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class GroupServiceImpl implements GroupService {
    private final CourseRepository courseRepository;
    private final GroupRepository groupRepository;
    private final ResultOfTestRepository resultOfTestRepository;
    private final UserRepository userRepository;
    private static final Logger logger = LogManager.getLogger(GroupServiceImpl.class);

    @Override
    public SimpleResponse saveGroup(GroupRequest groupRequest) {
        if (groupRepository.existsGroupByName(groupRequest.getName())) {
            logger.info("Group with name :" + groupRequest.getName() + " already exists");
            return SimpleResponse.builder().httpStatus(HttpStatus.CONFLICT).
                    message(String.format("Группа с именем: " + groupRequest.getName() + " уже существует")).build();
        }
        Group group = new Group();
        group.setName(groupRequest.getName());
        group.setCreatedAt(LocalDate.now());
        group.setDescription(groupRequest.getDescription());
        group.setImage(groupRequest.getImage());
        group.setFinishDate(groupRequest.getFinishDate());
        groupRepository.save(group);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Группа с именем: " + groupRequest.getName() + " успешно сохранена").build();
    }

    @Override
    public GroupPaginationResponse getAllGroups(int size, int page) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<GroupResponse> groupPage = groupRepository.getAllGroups(pageable);
        GroupPaginationResponse groupPaginationResponse = new GroupPaginationResponse();
        groupPaginationResponse.setGroupResponses(groupPage.getContent());
        groupPaginationResponse.setPageSize(groupPage.getNumber());
        groupPaginationResponse.setCurrentPage(groupPage.getSize());
        return groupPaginationResponse;
    }

    @Override
    public GroupResponse getGroupById(Long groupId) {
        return groupRepository.getGroupById(groupId).orElseThrow(() -> new NotFoundException(String.format("Идентификатор группы: " + groupId + " не найден")));
    }

    @Override
    @Transactional
    public SimpleResponse updateGroup(GroupUpdateRequest groupUpdateRequest) {
        logger.info("Group with id : " + groupUpdateRequest.getGroupId() + " not found");
        Group group = groupRepository.findById(groupUpdateRequest.getGroupId()).orElseThrow(() ->
                new NotFoundException(String.format("Группа с идентификатором: " + groupUpdateRequest.getGroupId() + " не найдена")));
        group.setId(groupUpdateRequest.getGroupId());
        group.setName(groupUpdateRequest.getName());
        group.setDescription(groupUpdateRequest.getDescription());
        group.setImage(groupUpdateRequest.getImage());
        group.setFinishDate(groupUpdateRequest.getFinishDate());
        groupRepository.save(group);
        logger.info("Successfully updated");
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Успешно обновлено").build();
    }

    @Override
    @Transactional
    public SimpleResponse deleteGroup(Long groupId) {
        logger.info("Group with id : " + groupId + " not found");
        Group group = groupRepository.findById(groupId).orElseThrow(() ->
                new NotFoundException(String.format("Группа с идентификатором: " + groupId + " не найдена")));
        for (Student student : group.getStudents()) {
            resultOfTestRepository.deleteByStudentId(student.getId());
            userRepository.deleteUserByStudentId(student.getId());
        }
        group.getCourses().forEach(course -> course.setGroups(null));
        groupRepository.delete(group);
        logger.info("Successfully deleted");
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Успешно удалено").build();
    }


    @Override
    public SimpleResponse assignGroupToCourse(Long groupId, Long courseId) {
        logger.info("Group with id : " + groupId + " not found");
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException("Группа с идентификатором: " + groupId + " не найдена"));
        logger.info("Course with id : " + courseId + " not found");
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException("Курс с идентификатором: " + courseId + " не найден"));
        group.addCourse(course);
        groupRepository.save(group);
        logger.info("Successfully saved!");
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Успешно сохранено!")
                .build();
    }
}
