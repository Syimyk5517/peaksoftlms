package com.example.peaksoftlmsb8.service.impl;

import com.example.peaksoftlmsb8.config.JwtService;
import com.example.peaksoftlmsb8.db.entity.*;
import com.example.peaksoftlmsb8.db.enums.Role;
import com.example.peaksoftlmsb8.dto.request.AssignRequest;
import com.example.peaksoftlmsb8.dto.request.course.CourseRequest;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.dto.response.course.CoursePaginationResponse;
import com.example.peaksoftlmsb8.dto.response.course.CourseResponse;
import com.example.peaksoftlmsb8.exception.AlReadyExistException;
import com.example.peaksoftlmsb8.exception.NotFoundException;
import com.example.peaksoftlmsb8.repository.*;
import com.example.peaksoftlmsb8.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final InstructorRepository instructorRepository;
    private final TestRepository testRepository;
    private final LessonRepository lessonRepository;
    private final ResultOfTestRepository resultOfTestRepository;
    private final GroupRepository groupRepository;
    private final JwtService jwtService;

    private final CourseRepo courseRepo;

    private static final Logger logger = LogManager.getLogger(CourseServiceImpl.class);

    @Override
    public SimpleResponse assignInstructorToCourse(AssignRequest assignRequest) {
        Course course = courseRepository.findById(assignRequest.getCourseId()).orElseThrow(() -> {
            logger.error("Course with id : " + assignRequest.getCourseId() + " not found");
            throw new NotFoundException("Курс с идентификатором: " + assignRequest.getCourseId() + " не найден");
        });
        Instructor instructor = instructorRepository.findById(assignRequest.getInstructorId())
                .orElseThrow(() -> {
                    logger.error("this id = " + assignRequest.getInstructorId() + " not found !");
                    throw new NotFoundException("Этот идентификатор = " + assignRequest.getInstructorId() + " не найден!");
                });
        if (course.getInstructors().contains(instructor)) {
            throw new AlReadyExistException(String.format("Инструктор %s уже назначен", instructor.getUser().getFirstName()));
        } else {
            instructor.addCourse(course);
            course.addInstructor(instructor);
            courseRepository.save(course);
        }
        logger.info("Successfully assigned");
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Успешно назначено").build();
    }

    @Override
    public CoursePaginationResponse getAllCourse(int size, int page) {
        User user = jwtService.getAccountInToken();
        if (user == null) {
            throw new NotFoundException("Ползиватель не найден!");
        } else {
            if (user.getRole().equals(Role.STUDENT)) {
                return courseRepo.getAllCourses(user, size, page);
            } else {

                Pageable pageable = PageRequest.of(page - 1, size);
                Page<CourseResponse> coursePage = courseRepository.getAllCourses(pageable);
                List<CourseResponse> courseResponseList = new ArrayList<>(coursePage.getContent().stream()
                        .map(c -> new CourseResponse(
                                c.getId(),
                                c.getName(),
                                c.getImage(),
                                c.getDescription(),
                                c.getCreatedAt(),
                                c.getFinishDate()
                        )).toList());
                CoursePaginationResponse coursePaginationResponse = new CoursePaginationResponse();
                coursePaginationResponse.setCourseResponses(courseResponseList);
                coursePaginationResponse.setPageSize(coursePage.getNumber());
                coursePaginationResponse.setCurrentPage(coursePage.getSize());
                return coursePaginationResponse;
            }
        }
    }

    @Override
    public CourseResponse findByCourseId(Long courseId) {
        return courseRepository.findByCourseId(courseId).orElseThrow(() -> {
            logger.error("Course id: " + courseId + " not found");
            throw new NotFoundException("Идентификатор курса:  " + courseId + " не найден");
        });
    }

    @Override
    public SimpleResponse saveCourse(CourseRequest courseRequest) {
        logger.info("Course with name : " + courseRequest.getName() + " already exist");
        if (courseRepository.existsCourseByName(courseRequest.getName())) {
            return SimpleResponse.builder().httpStatus(HttpStatus.CONFLICT).message(String.format("Курс с названием : %s уже существует", courseRequest.getName())).build();
        }
        Course course = new Course();
        course.setName(courseRequest.getName());
        course.setImage(courseRequest.getImage());
        course.setDescription(courseRequest.getDescription());
        course.setCreatedAt(LocalDate.now());
        course.setFinishDate(courseRequest.getFinishDate());
        courseRepository.save(course);
        logger.info("Course with name" + courseRequest.getName() + "successfully saved!");
        return SimpleResponse.builder().httpStatus(HttpStatus.OK)
                .message("Курс с именем: " + courseRequest.getName() + " успешно сохранено!").build();
    }

    @Override
    public SimpleResponse updateCourse(Long courseId, CourseRequest courseUpdateRequest) {
        if (courseRepository.existsCourseByName(courseUpdateRequest.getName())) {
            logger.info("Method updateCourse return SimpleResponse builder");
            throw new AlReadyExistException(String.format("Course with name :%s already exist", courseUpdateRequest.getName()));
        }
        logger.info("Course with id: " + courseId + " not found");
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException(String.format("Course with id: " + courseId + " not found")));
        course.setName(courseUpdateRequest.getName());
        course.setImage(courseUpdateRequest.getImage());
        course.setDescription(courseUpdateRequest.getDescription());
        course.setFinishDate(courseUpdateRequest.getFinishDate());
        courseRepository.save(course);
        logger.info("Successfully updated!");
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Успешно обновлено!").build();
    }

    @Override
    @Transactional
    public SimpleResponse deleteCourse(Long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> {
            logger.error("Course with id: " + courseId + " not found");
            throw new NotFoundException("Курс с идентификатором: " + courseId + " не найден");
        });
        for (Lesson lesson : course.getLessons()) {
            Test test = lesson.getTest();
            if (test != null) {
                resultOfTestRepository.deleteByTest_Id(test.getId());
                testRepository.delete(test);
            }
            lessonRepository.delete(lesson);
        }
        for (Group group : course.getGroups()) {
            group.getCourses().remove(course);
            groupRepository.save(group);
        }
        courseRepository.delete(course);
        logger.info("Successfully deleted!");
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Успешно удалено!").build();
    }
}
