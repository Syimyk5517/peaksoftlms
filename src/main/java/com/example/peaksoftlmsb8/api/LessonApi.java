package com.example.peaksoftlmsb8.api;

import com.example.peaksoftlmsb8.dto.request.LessonRequest;
import com.example.peaksoftlmsb8.dto.response.*;
import com.example.peaksoftlmsb8.service.LessonService;
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
    public SimpleResponse saveLesson(@RequestBody @Valid LessonRequest lessonRequest) {
        return lessonService.saveLessons(lessonRequest);
    }

    @GetMapping
    public LessonPaginationResponse getAllLessonsByCourseId(@RequestParam Long courseId,
                                                            @RequestParam int size,
                                                            @RequestParam int page) {
        return lessonService.getAllLessonsByCourseId(courseId, size, page);
    }

    @GetMapping("/{lessonId}")
    public LessonResponse getLessonById(@PathVariable Long lessonId) {
        return lessonService.findByLessonId(lessonId);
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    public SimpleResponse updateLesson(@RequestParam Long lessonId, @RequestBody @Valid LessonRequest lessonRequest) {
        return lessonService.updateLesson(lessonId, lessonRequest);
    }

    @DeleteMapping("/{lessonId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    public SimpleResponse deleteLesson(@PathVariable Long lessonId) {
        return lessonService.deleteLesson(lessonId);
    }
}
