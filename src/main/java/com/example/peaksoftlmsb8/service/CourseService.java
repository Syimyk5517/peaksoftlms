package com.example.peaksoftlmsb8.service;

import com.example.peaksoftlmsb8.dto.request.AssignRequest;
import com.example.peaksoftlmsb8.dto.request.course.CourseRequest;
import com.example.peaksoftlmsb8.dto.response.course.CoursePaginationResponse;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.dto.response.course.CourseResponse;


public interface CourseService {

    SimpleResponse assignInstructorToCourse(Boolean isAssigned, AssignRequest assignRequest);

    CoursePaginationResponse getAllCourse(int size, int page);

    CourseResponse findByCourseId(Long courseId);

    SimpleResponse saveCourse(CourseRequest courseRequest);

    SimpleResponse updateCourse(CourseRequest courseRequest);

    SimpleResponse deleteCourse(Long courseId);

}
