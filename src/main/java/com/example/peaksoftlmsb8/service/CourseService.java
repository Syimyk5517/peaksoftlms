package com.example.peaksoftlmsb8.service;

import com.example.peaksoftlmsb8.dto.request.AssignRequest;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;


public interface CourseService {
    SimpleResponse assignInstructorToCourse(Boolean isAssigned, AssignRequest assignRequest);
}
