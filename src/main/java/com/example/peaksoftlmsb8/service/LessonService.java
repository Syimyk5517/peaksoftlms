package com.example.peaksoftlmsb8.service;

import com.example.peaksoftlmsb8.dto.request.lesson.LessonRequest;
import com.example.peaksoftlmsb8.dto.response.*;
import com.example.peaksoftlmsb8.dto.response.lesson.LessonPaginationResponse;
import com.example.peaksoftlmsb8.dto.response.lesson.LessonResponse;


public interface LessonService {
    SimpleResponse saveLessons(LessonRequest lessonRequest);

    LessonPaginationResponse getAllLessonsByCourseId(Long courseId, int size, int page);

    LessonResponse findByLessonId(Long lessonId);

    SimpleResponse updateLesson(Long lessonId, LessonRequest lessonUpdateRequest);

    SimpleResponse deleteLesson(Long lessonId);
}
