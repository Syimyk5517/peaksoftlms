package com.example.peaksoftlmsb8.service.impl;

import com.example.peaksoftlmsb8.db.entity.Course;
import com.example.peaksoftlmsb8.db.entity.Lesson;
import com.example.peaksoftlmsb8.db.entity.ResultOfTest;
import com.example.peaksoftlmsb8.db.entity.Test;
import com.example.peaksoftlmsb8.exception.AlReadyExistException;
import com.example.peaksoftlmsb8.exception.NotFoundException;
import com.example.peaksoftlmsb8.dto.request.lesson.LessonRequest;
import com.example.peaksoftlmsb8.dto.response.*;
import com.example.peaksoftlmsb8.dto.response.lesson.LessonPaginationResponse;
import com.example.peaksoftlmsb8.dto.response.lesson.LessonResponse;
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
        Course course = courseRepository.findById(lessonRequest.getCourseId()).orElseThrow(() -> {
            logger.error("Course with id : " + lessonRequest.getCourseId() + " not found");
            throw new NotFoundException("Курс с идентификатором: " + lessonRequest.getCourseId() + " не найден");
        });
        if (lessonRepository.existsLessonByName(lessonRequest.getName())) {
            logger.error("Lesson with name : " + lessonRequest.getName() + " already exists");
            throw new AlReadyExistException("Урок с названием: " + lessonRequest.getName() + " уже существует");
        }
        Lesson lesson = new Lesson();
        lesson.setName(lessonRequest.getName());
        lesson.setCreatedAt(LocalDate.now());
        lesson.setCourse(course);
        lessonRepository.save(lesson);
        logger.info("Successfully saved");
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Успешно сохранено").build();
    }

    @Override
    public LessonPaginationResponse getAllLessonsByCourseId(Long courseId, int size, int page) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<LessonResponse> pageLesson = lessonRepository.getAllLessonsByCourseId(pageable, courseId);
        LessonPaginationResponse paginationResponse = new LessonPaginationResponse();
        paginationResponse.setLessonResponses(pageLesson.getContent());
        paginationResponse.setPageSize(pageLesson.getNumber());
        paginationResponse.setCurrentPage(pageLesson.getSize());
        return paginationResponse;
    }

    @Override
    public LessonResponse findByLessonId(Long lessonId) {
        return lessonRepository.getLessonById(lessonId).orElseThrow(() -> {
            logger.error("Lesson with id : " + lessonId + " not found");
            throw new NotFoundException("Урок с идентификатором: " + lessonId + " не найден");
        });
    }

    @Override
    @Transactional
    public SimpleResponse updateLesson(Long lessonId, LessonRequest lessonUpdateRequest) {
        logger.info("Lesson with id: " + lessonId + " not found");
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() ->
                new NotFoundException(String.format("Lesson with id: " + lessonId + " not found")));
        Course course = courseRepository.findById(lessonUpdateRequest.getCourseId()).orElseThrow(() ->
                new NotFoundException(String.format("Course with id : " + lessonUpdateRequest.getCourseId() + " not found")));
        if (!lesson.getCourse().equals(course)) {
            lesson.setCourse(course);
        }

        lesson.setName(lessonUpdateRequest.getName());
        lessonRepository.save(lesson);
        logger.info("Successfully updated");
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Успешно обновлено").build();
    }

    @Override
    public SimpleResponse deleteLesson(Long lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> {
            logger.error("Lesson with id: " + lessonId + " not found");
            throw new NotFoundException("Урок с идентификатором: " + lessonId + " не найден");
        });
        if (lesson.getTest() != null) {

            Test test = testRepository.findById(lesson.getTest().getId()).orElseThrow(() -> {
                logger.error("Test with id: " + lesson.getTest().getId() + " not found");
                throw new NotFoundException("Тест с идентификатором:" + lesson.getTest().getId() + " не найден");
            });


            ResultOfTest result = resultOfTestRepository.findResultOfTestById(test.getId())
                    .orElseThrow(() -> {
                        logger.error("lesson with id: " + test.getId() + " not found!");
                        throw new NotFoundException("Урок с идентификатором: " + test.getId() + " не найден");
                    });
            testRepository.delete(test);
            if (result != null) {
                resultOfTestRepository.delete(result);
            }
        }
        lessonRepository.delete(lesson);
        logger.info("Успешно удалено");
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Успешно удалено").build();
    }
}
