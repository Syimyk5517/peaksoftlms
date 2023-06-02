package com.example.peaksoftlmsb8.repository;

import com.example.peaksoftlmsb8.db.entity.User;
import com.example.peaksoftlmsb8.dto.response.course.CoursePaginationResponse;

public interface CourseRepo {
    CoursePaginationResponse getAllCourses(User user,int size,int page);
}
