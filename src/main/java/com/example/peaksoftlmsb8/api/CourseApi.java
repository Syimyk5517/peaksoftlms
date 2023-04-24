package com.example.peaksoftlmsb8.api;

import com.example.peaksoftlmsb8.dto.request.AssignRequest;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.service.CourseService;
import com.example.peaksoftlmsb8.dto.request.CourseRequest;
import com.example.peaksoftlmsb8.dto.response.CoursePaginationResponse;
import com.example.peaksoftlmsb8.dto.response.CourseResponse;
import jakarta.validation.Valid;
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


    @PostMapping("/save")
    public SimpleResponse saveCourse(@RequestBody CourseRequest courseRequest) {
        return courseService.saveCourse(courseRequest);
    }

    @GetMapping("/{sort}")
    public CoursePaginationResponse getAllCourses(@RequestParam int size,
                                                  @RequestParam int page,
                                                  @PathVariable String sort,
                                                  @RequestBody String word) {
        return courseService.getAllCourse(size, page, sort, word);
    }

    @GetMapping("/{courseId}")
    public CourseResponse findByCourseId(@PathVariable Long courseId) {
        return courseService.findByCourseId(courseId);
    }

    @PutMapping("/{courseId}")
    public SimpleResponse updateCourse(@PathVariable Long courseId, @RequestBody @Valid CourseRequest courseRequest) {
        return courseService.updateCourse(courseId, courseRequest);
    }

    @DeleteMapping("/{courseId}")
    public SimpleResponse deleteCourse(@PathVariable Long courseId) {
        return courseService.deleteCourse(courseId);

    }
}
