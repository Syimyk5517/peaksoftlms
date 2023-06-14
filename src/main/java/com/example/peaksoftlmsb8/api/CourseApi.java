package com.example.peaksoftlmsb8.api;

import com.example.peaksoftlmsb8.dto.request.AssignRequest;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.dto.response.course.CourseResponse;
import com.example.peaksoftlmsb8.service.CourseService;

import io.swagger.v3.oas.annotations.Operation;
import com.example.peaksoftlmsb8.dto.request.course.CourseRequest;
import com.example.peaksoftlmsb8.dto.response.course.CoursePaginationResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/courses")
@Tag(name = "Courses")
public class CourseApi {
    private final CourseService courseService;

    @PostMapping("/assign")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "This method assign Instructor to Course",description = "You can assign Instructor to Course. Access to this method: ADMIN")
    public SimpleResponse assignInstructorToCourse(@RequestParam Boolean isAssigned, @RequestBody @Valid AssignRequest assignRequest) {
        return courseService.assignInstructorToCourse(isAssigned, assignRequest);
    }


    @PostMapping()
    @Operation(summary = "This method save Course", description = "You can save Course")
    public SimpleResponse saveCourse(@RequestBody @Valid CourseRequest courseRequest) {
        return courseService.saveCourse(courseRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR','STUDENT')")
    @GetMapping("/pagination")
    @Operation(summary = "This method get all Courses", description = "You can get all Courses")
    public CoursePaginationResponse getAllCourses(@RequestParam int size,
                                                  @RequestParam int page) {
        return courseService.getAllCourse(size, page);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR','STUDENT')")
    @GetMapping("/{courseId}")
    @Operation(summary = "This method find by Course with id", description = "You can find by Course with id")
    public CourseResponse findByCourseId(@PathVariable Long courseId) {
        return courseService.findByCourseId(courseId);
    }

    @PutMapping()
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @Operation(summary = "This method update Course",description = "you can update Course. Access to this method: ADMIN")
    public SimpleResponse updateCourse( @RequestParam Long courseId,@RequestBody @Valid CourseRequest courseUpdateRequest) {
        return courseService.updateCourse(courseId,courseUpdateRequest);
    }

    @DeleteMapping("/{courseId}")
    public SimpleResponse deleteCourse(@PathVariable Long courseId) {
        return courseService.deleteCourse(courseId);

    }
}
