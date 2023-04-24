package com.example.peaksoftlmsb8.api;

import com.example.peaksoftlmsb8.dto.request.AssignRequest;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses")
public class CourseApi {
    private final CourseService courseService;

    @PostMapping("/assign")
    public SimpleResponse assignInstructorToCourse(@RequestParam Boolean isAssigned, @RequestBody AssignRequest assignRequest) {
        return courseService.assignInstructorToCourse(isAssigned, assignRequest);
    }
}
