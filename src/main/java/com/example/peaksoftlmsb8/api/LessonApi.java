package com.example.peaksoftlmsb8.api;

import com.example.peaksoftlmsb8.dto.request.LessonRequest;
import com.example.peaksoftlmsb8.dto.response.*;
import com.example.peaksoftlmsb8.service.LessonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/lessons")
@RequiredArgsConstructor
@Tag(name = "Lessons")
public class LessonApi {
    private final LessonService lessonService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    @Operation(summary = "Save a new lesson", description = "Save a new lesson with the provided details")
    public SimpleResponse saveLesson(@RequestBody @Valid LessonRequest lessonRequest) {
        return lessonService.saveLessons(lessonRequest);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','STUDENT','INSTRUCTOR')")
    @Operation(summary = "Get all lessons by course ID", description = "Retrieve a paginated list of all lessons for a given course")
    public LessonPaginationResponse getAllLessonsByCourseId(@RequestParam Long courseId,
                                                            @RequestParam int size,
                                                            @RequestParam int page) {
        return lessonService.getAllLessonsByCourseId(courseId, size, page);
    }

    @GetMapping("/{lessonId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','STUDENT','INSTRUCTOR')")
    @Operation(summary = "Get lesson by ID", description = "Get the lesson details for the provided ID")
    public LessonResponse getLessonById(@PathVariable Long lessonId) {
        return lessonService.findByLessonId(lessonId);
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    @Operation(summary = "Update a lesson", description = "Update a lesson with the provided lessonId using the information provided in the request body.")
    public SimpleResponse updateLesson(@RequestParam Long lessonId, @RequestBody @Valid LessonRequest lessonRequest) {
        return lessonService.updateLesson(lessonId, lessonRequest);
    }

    @DeleteMapping("/{lessonId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    @Operation(summary = "Delete a lesson by ID", description = "Deletes a lesson with the specified ID. This action requires admin or instructor authorization.")
    public SimpleResponse deleteLesson(@PathVariable Long lessonId) {
        return lessonService.deleteLesson(lessonId);
    }
}
