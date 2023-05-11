package com.example.peaksoftlmsb8.service.impl;

import com.example.peaksoftlmsb8.config.JwtService;
import com.example.peaksoftlmsb8.db.entity.*;
import com.example.peaksoftlmsb8.db.enums.Role;
import com.example.peaksoftlmsb8.db.exception.NotFoundException;
import com.example.peaksoftlmsb8.dto.request.AssignRequest;
import com.example.peaksoftlmsb8.dto.request.CourseRequest;
import com.example.peaksoftlmsb8.dto.response.CoursePaginationResponse;
import com.example.peaksoftlmsb8.dto.response.CourseResponse;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.repository.*;
import com.example.peaksoftlmsb8.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final InstructorRepository instructorRepository;
    private final TestRepository testRepository;
    private final LessonRepository lessonRepository;
    private final GroupRepository groupRepository;
    private final JwtService jwtService;

    private final JdbcTemplate jdbcTemplate;

    @Override
    public SimpleResponse assignInstructorToCourse(Boolean isAssigned, AssignRequest assignRequest) {
        Course course = courseRepository.findById(assignRequest.getCourseId()).orElseThrow(() ->
                new NotFoundException(String.format("Course with id : " + assignRequest.getCourseId() + " not found")));
        List<Instructor> instructors = instructorRepository.findAllById(assignRequest.getInstructorIds());
        if (isAssigned.equals(true)) {
            for (Instructor instructor : instructors) {
                course.addInstructor(instructor);
                instructor.addCourse(course);
            }
            courseRepository.save(course);
            return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Successfully assigned").build();
        } else {
            return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Not assigned ").build();
        }
    }

    @Override
    public CoursePaginationResponse getAllCourse(int size, int page) {
        User user = jwtService.getAccountInToken();
        if (user.getRole().equals(Role.STUDENT)) {
            int offset = size * page;
            Group group = user.getStudent().getGroup();
            String sql = """
                    select c.id as course_id,
                           c.name as course_name,
                           c.description as course_description,
                           c.created_at as course_created_at,
                           c.final_date as course_finish_date
                    from courses c join groups_courses gc on c.id = gc.courses_id where gc.group_id =:? limit ? offset ?;
                    """;
            List<CourseResponse> courseResponses = jdbcTemplate.query(sql, (resultSet, i) -> {
                CourseResponse courseResponse = new CourseResponse();
                courseResponse.setId(resultSet.getLong("course_id"));
                courseResponse.setName(resultSet.getString("course_name"));
                courseResponse.setDescription(resultSet.getString("course_description"));
                courseResponse.setCreatedAt(resultSet.getDate("course_create_date").toLocalDate());
                courseResponse.setFinalDate(resultSet.getDate("course_finish_date").toLocalDate());
                return courseResponse;

            }, group.getId(), size, offset);
            return CoursePaginationResponse.builder().
                    courseResponses(courseResponses)
                    .currentPage(page)
                    .pageSize(size).
                    build();

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
                            c.getFinalDate()
                    )).toList());
            CoursePaginationResponse coursePaginationResponse = new CoursePaginationResponse();
            coursePaginationResponse.setCourseResponses(courseResponseList);
            coursePaginationResponse.setPageSize(coursePage.getNumber());
            coursePaginationResponse.setCurrentPage(coursePage.getSize());
            return coursePaginationResponse;
        }
    }

    @Override
    public CourseResponse findByCourseId(Long courseId) {
        return courseRepository.findByCourseId(courseId).
                orElseThrow(() -> new NotFoundException("Course id: " + courseId + " not found"));
    }

    @Override
    public SimpleResponse saveCourse(CourseRequest courseRequest) {
        if (courseRepository.existsCourseByName(courseRequest.getName())) {
            return SimpleResponse.builder().httpStatus(HttpStatus.CONFLICT)
                    .message(String.format("Course with name :%s already exist", courseRequest.getName())).build();
        }
        Course course = new Course();
        course.setName(courseRequest.getName());
        course.setImage(courseRequest.getImage());
        course.setDescription(courseRequest.getDescription());
        course.setCreatedAt(LocalDate.now());
        course.setFinishDate(courseRequest.getFinishDate());
        courseRepository.save(course);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Course with name" +
                courseRequest.getName() + "successfully saved!").build();
    }

    @Override
    public SimpleResponse updateCourse(CourseRequest courseRequest) {
        if (courseRepository.existsCourseByName(courseRequest.getName())) {
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.CONFLICT)
                    .message(String.format("Course with name :%s already exist", courseRequest.getName())).build();
        }
        Course course = courseRepository.findById(courseRequest.getId())
                .orElseThrow(() -> new NotFoundException(String.format("Course with id: " + courseRequest.getId() + " not found")));
        course.setName(courseRequest.getName());
        course.setImage(courseRequest.getImage());
        course.setDescription(courseRequest.getDescription());
        course.setCreatedAt(courseRequest.getCreatedAt());
        course.setFinishDate(courseRequest.getFinishDate());
        courseRepository.save(course);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Successfully updated!").build();
    }

    @Override
    @Transactional
    public SimpleResponse deleteCourse(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException("Course with id: " + courseId + " not found"));
        for (Lesson lesson : course.getLessons()) {
            Test test = lesson.getTest();
            if (test != null) {
                testRepository.delete(test);
            }
            lessonRepository.delete(lesson);
        }
        for (Group group : course.getGroups()) {
            group.getCourses().remove(course);
            groupRepository.save(group);
        }
        courseRepository.delete(course);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully deleted!")
                .build();
    }
}
