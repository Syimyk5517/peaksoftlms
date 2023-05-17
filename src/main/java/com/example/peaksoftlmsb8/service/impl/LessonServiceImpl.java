package com.example.peaksoftlmsb8.service.impl;

import com.example.peaksoftlmsb8.db.entity.Course;
import com.example.peaksoftlmsb8.db.entity.Lesson;
import com.example.peaksoftlmsb8.db.entity.ResultOfTest;
import com.example.peaksoftlmsb8.db.entity.Test;
import com.example.peaksoftlmsb8.db.exception.AlReadyExistException;
import com.example.peaksoftlmsb8.db.exception.NotFoundException;
import com.example.peaksoftlmsb8.dto.request.LessonRequest;
import com.example.peaksoftlmsb8.dto.request.LessonUpdateRequest;
import com.example.peaksoftlmsb8.dto.response.*;
import com.example.peaksoftlmsb8.repository.CourseRepository;
import com.example.peaksoftlmsb8.repository.LessonRepository;
import com.example.peaksoftlmsb8.repository.ResultOfTestRepository;
import com.example.peaksoftlmsb8.repository.TestRepository;
import com.example.peaksoftlmsb8.service.LessonService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Log4j2
public class LessonServiceImpl implements LessonService {
    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;
    private final TestRepository testRepository;
    private final ResultOfTestRepository resultOfTestRepository;

    private static final Logger logger = LogManager.getLogger(LessonServiceImpl.class);

    @Override
    public SimpleResponse saveLessons(LessonRequest lessonRequest) {
        logger.info("Course with id : " + lessonRequest.getCourseId() + " not found");
        Course course = courseRepository.findById(lessonRequest.getCourseId()).orElseThrow(() ->
                new NotFoundException(String.format("Course with id : " + lessonRequest.getCourseId() + " not found")));
        if (lessonRepository.existsLessonByName(lessonRequest.getName())) {
            logger.info("Lesson with name : " + lessonRequest.getName() + " already exists");
            throw new AlReadyExistException("Lesson with name : " + lessonRequest.getName() + " already exists");
        }
        Lesson lesson = new Lesson();
        lesson.setName(lessonRequest.getName());
        lesson.setCreatedAt(LocalDate.now());
        lesson.setCourse(course);
        lessonRepository.save(lesson);
        logger.info("Successfully saved");
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
        logger.info("Lesson with id : " + lessonId + " not found");
        return lessonRepository.getLessonById(lessonId).orElseThrow(() ->
                new NotFoundException(String.format("Lesson with id : " + lessonId + " not found")));
    }

    @Override
    @Transactional
    public SimpleResponse updateLesson(LessonUpdateRequest lessonUpdateRequest) {
        logger.info("Lesson with id: " + lessonUpdateRequest.getLessonId() + " not found");
        Lesson lesson = lessonRepository.findById(lessonUpdateRequest.getLessonId()).orElseThrow(() ->
                new NotFoundException(String.format("Lesson with id: " + lessonUpdateRequest.getLessonId() + " not found")));
        if (lessonRepository.existsLessonByName(lessonUpdateRequest.getName())) {
            logger.info("Lesson with name: " + lessonUpdateRequest.getName() + " already exists");
            throw new AlReadyExistException("Lesson with name: " + lessonUpdateRequest.getName() + " already exists");
        }
        lesson.setId(lessonUpdateRequest.getLessonId());
        lesson.setName(lessonUpdateRequest.getName());
        lessonRepository.save(lesson);
        logger.info("Successfully updated");
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Successfully updated").build();
    }

    @Override
    public SimpleResponse deleteLesson(Long lessonId) {
        logger.info("Lesson with id: " + lessonId + " not found");
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() ->
                new NotFoundException(String.format("Lesson with id: " + lessonId + " not found")));
        logger.info("Test with id: " + lesson.getTest().getId() + " not found");
        Test test = testRepository.findById(lesson.getTest().getId()).orElseThrow(() ->
                new NotFoundException(String.format("Test with id: " + lesson.getTest().getId() + " not found")));
        ResultOfTest result = resultOfTestRepository.findResultOfTestById(test.getId()).orElseThrow();
        ResultOfTest result = resultOfTestRepository.findResultOfTestById(test.getId())
                .orElseThrow(()-> new NotFoundException(String.format("Lesson with id: " + test.getId() + " not found")));
        resultOfTestRepository.delete(result);
        testRepository.delete(test);
        lessonRepository.delete(lesson);
        logger.info("Successfully deleted");
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Successfully deleted").build();
    }
}
