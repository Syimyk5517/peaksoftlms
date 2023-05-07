package com.example.peaksoftlmsb8.service;

import com.example.peaksoftlmsb8.dto.request.LessonRequest;
import com.example.peaksoftlmsb8.dto.response.*;


public interface LessonService {
    SimpleResponse saveLessons(LessonRequest lessonRequest);

    LessonPaginationResponse getAllLessonsByCourseId(Long courseId, int size, int page);

    LessonResponse findByLessonId(Long lessonId);

    SimpleResponse updateLesson(Long lessonId, LessonRequest lessonRequest);

    SimpleResponse deleteLesson(Long lessonId);
}
