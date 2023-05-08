package com.example.peaksoftlmsb8.api;

import com.example.peaksoftlmsb8.dto.request.lesson.LessonRequest;
import com.example.peaksoftlmsb8.dto.request.lesson.LessonUpdateRequest;
import com.example.peaksoftlmsb8.dto.response.*;
import com.example.peaksoftlmsb8.dto.response.lesson.LessonPaginationResponse;
import com.example.peaksoftlmsb8.dto.response.lesson.LessonResponse;
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
    public SimpleResponse updateLesson(@RequestBody @Valid LessonUpdateRequest lessonUpdateRequest) {
        return lessonService.updateLesson(lessonUpdateRequest);
    }

    @DeleteMapping("/{lessonId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    @Operation(summary = "Delete a lesson by ID", description = "Deletes a lesson with the specified ID. This action requires admin or instructor authorization.")
    public SimpleResponse deleteLesson(@PathVariable Long lessonId) {
        return lessonService.deleteLesson(lessonId);
    }
}
