package com.example.peaksoftlmsb8.service.impl;

import com.example.peaksoftlmsb8.db.entity.Course;
import com.example.peaksoftlmsb8.db.entity.Lesson;
import com.example.peaksoftlmsb8.db.exception.AlReadyExistException;
import com.example.peaksoftlmsb8.db.exception.NotFoundException;
import com.example.peaksoftlmsb8.dto.request.LessonRequest;
import com.example.peaksoftlmsb8.dto.response.*;
import com.example.peaksoftlmsb8.repository.CourseRepository;
import com.example.peaksoftlmsb8.repository.LessonRepository;
import com.example.peaksoftlmsb8.service.LessonService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {
    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;

    @Override
    public SimpleResponse saveLessons(LessonRequest lessonRequest) {
        Course course = courseRepository.findById(lessonRequest.getCourseId()).orElseThrow(() ->
                new NotFoundException(String.format("Course with id: " + lessonRequest.getCourseId() + " not found")));
        if (lessonRepository.existsLessonByName(lessonRequest.getName())) {
            throw new AlReadyExistException("Lesson with name: " + lessonRequest.getName() + " already exists");
        }
        Lesson lesson = new Lesson();
        lesson.setName(lessonRequest.getName());
        lesson.setCreatedAt(LocalDate.now());
        lesson.setCourse(course);
        lessonRepository.save(lesson);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Successfully saved").build();
    }

    @Override
    public LessonPaginationResponse getAllLessonsByCourseId(Long courseId, int size, int page) {
        Pageable pageable = PageRequest.of(page, size);
        Page<LessonResponse> pageLesson = lessonRepository.getAllLessonsByCourseId(pageable, courseId);
        LessonPaginationResponse paginationResponse = new LessonPaginationResponse();
        paginationResponse.setLessonResponses(pageLesson.getContent());
        paginationResponse.setPageSize(pageLesson.getNumber());
        paginationResponse.setCurrentPage(pageLesson.getSize());
        return paginationResponse;
    }

    @Override
    public LessonResponse findByLessonId(Long lessonId) {
        return lessonRepository.getLessonById(lessonId).orElseThrow(() ->
                new NotFoundException(String.format("Lesson with id: " + lessonId + " not found")));
    }

    @Override
    @Transactional
    public SimpleResponse updateLesson(Long lessonId, LessonRequest lessonRequest) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() ->
                new NotFoundException(String.format("Lesson with id: " + lessonId + " not found")));
        if (lessonRepository.existsLessonByName(lessonRequest.getName())) {
            throw new AlReadyExistException("Lesson with name: " + lessonRequest.getName() + " already exists");
        }
        lesson.setName(lessonRequest.getName());
        lessonRepository.save(lesson);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Successfully updated").build();
    }

    @Override
    public SimpleResponse deleteLesson(Long lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() ->
                new NotFoundException(String.format("Lesson with id: " + lessonId + " not found")));
        lessonRepository.delete(lesson);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Successfully deleted").build();
    }
}
