package com.example.peaksoftlmsb8.service.impl;

import com.example.peaksoftlmsb8.db.entity.Course;
import com.example.peaksoftlmsb8.db.exception.NotFoundException;
import com.example.peaksoftlmsb8.dto.request.CourseRequest;
import com.example.peaksoftlmsb8.dto.response.CoursePaginationResponse;
import com.example.peaksoftlmsb8.dto.response.CourseResponse;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.repository.CourseRepository;
import com.example.peaksoftlmsb8.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;

    @Override
    public CoursePaginationResponse getAllCourse(int size, int page, String word, String sort) {
        Pageable pageable = PageRequest.of(page -1,size , Sort.by(sort));
        Page<CourseResponse> coursePage = courseRepository.getAllCourses(pageable,word);
        List<CourseResponse> courseResponseList = new ArrayList<>(coursePage.getContent().stream()
                .map(c->new CourseResponse(
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

    @Override
    public CourseResponse findByCourseId(Long courseId) {
        return courseRepository.findByCourseId(courseId).
                orElseThrow(()-> new NotFoundException("Course id: " + courseId + "not found"));
    }

    @Override
    public SimpleResponse saveCourse(CourseRequest courseRequest) {
        if (courseRepository.existsCourseByName(courseRequest.getName())){
            return SimpleResponse.builder().httpStatus(HttpStatus.CONFLICT)
                    .message(String.format("Course with name :%s already exist",courseRequest.getName())).build();
        }
        Course course = new Course();
        course.setName(courseRequest.getName());
        course.setImage(courseRequest.getImage());
        course.setDescription(courseRequest.getDescription());
        course.setCreatedAt(courseRequest.getCreatedAt());
        course.setFinalDate(courseRequest.getFinalDate());
        courseRepository.save(course);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Course with name" +
                courseRequest.getName()+"successfully saved!").build();
    }

    @Override
    public SimpleResponse updateCourse(Long courseId, CourseRequest courseRequest) {
        if (courseRepository.existsCourseByName(courseRequest.getName())){
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.CONFLICT)
                    .message(String.format("Course with name :%s already exist", courseRequest.getName())).build();
        }
        Course course = courseRepository.findById(courseId)
                .orElseThrow(()-> new NotFoundException(String.format("Course with id: "+ courseId+ "not found")));
        course.setName(courseRequest.getName());
        course.setImage(courseRequest.getImage());
        course.setDescription(courseRequest.getDescription());
        course.setCreatedAt(courseRequest.getCreatedAt());
        course.setFinalDate(courseRequest.getFinalDate());
        courseRepository.save(course);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Successfully updated!").build();
    }

    @Override
    public SimpleResponse deleteCourse(Long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(
                ()-> new NotFoundException(String.format("Course with id: "+courseId+ "not found")));
        courseRepository.delete(course);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully delete!")
                .build();
    }
}
