package com.example.peaksoftlmsb8.service;

import com.example.peaksoftlmsb8.db.entity.Course;
import com.example.peaksoftlmsb8.dto.request.CourseRequest;
import com.example.peaksoftlmsb8.dto.response.CoursePaginationResponse;
import com.example.peaksoftlmsb8.dto.response.CourseResponse;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;

import java.util.List;

public interface CourseService {
    CoursePaginationResponse getAllCourse(int size, int page, String word, String sort);
    CourseResponse findByCourseId(Long courseId);
    SimpleResponse saveCourse(CourseRequest courseRequest);
    SimpleResponse updateCourse(Long courseId, CourseRequest courseRequest);
    SimpleResponse deleteCourse(Long courseId);
}
