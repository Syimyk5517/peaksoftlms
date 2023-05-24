package com.example.peaksoftlmsb8.service.impl;

import com.example.peaksoftlmsb8.db.entity.Lesson;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.repository.LessonRepository;
import com.example.peaksoftlmsb8.service.LinkService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class LinkServiceImpl implements LinkService {


    private LessonRepository lessonRepository;

    public LinkServiceImpl(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    public SimpleResponse addLinkToLesson(Long lessonId, String key, String value) {
        Lesson lesson = findLessonById(lessonId);
        lesson.getLink().put(key, value);
        saveLesson(lesson);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Link added successfully")
                .build();
    }

    public SimpleResponse removeLinkFromLesson(Long lessonId, String key) {
        Lesson lesson = findLessonById(lessonId);
        lesson.getLink().remove(key);
        saveLesson(lesson);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Link removed successfully")
                .build();
    }

    public SimpleResponse updateLinkInLesson(Long lessonId, String key, String value) {
        Lesson lesson = findLessonById(lessonId);
        lesson.getLink().put(key, value);
        saveLesson(lesson);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Link updated successfully")
                .build();
    }

    public SimpleResponse getLinkFromLesson(Long lessonId) {
        Lesson lesson = findLessonById(lessonId);
        Map<String, String> link = lesson.getLink();
        if (!link.isEmpty()) {
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.OK)
                    .message("Link found: " + link)
                    .build();
        } else {
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .message("Link not found")
                    .build();
        }
    }

    private Lesson findLessonById(Long lessonId) {
        return lessonRepository.findById(lessonId)
                .orElseThrow(() -> new EntityNotFoundException("Lesson not found"));
    }

    private void saveLesson(Lesson lesson) {
        lessonRepository.save(lesson);
    }

  /*  public Lesson addLinkToLesson(Long lessonId, String key, String value) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> new EntityNotFoundException("Lesson not found"));
        lesson.getLink().put(key, value);
        return lessonRepository.save(lesson);
    }

    public Lesson removeLinkFromLesson(Long lessonId, String key) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> new EntityNotFoundException("Lesson not found"));
        lesson.getLink().remove(key);
        return lessonRepository.save(lesson);
    }

    public Lesson updateLinkInLesson(Long lessonId, String key, String value) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> new EntityNotFoundException("Lesson not found"));
        lesson.getLink().put(key, value);
        return lessonRepository.save(lesson);
    }

    public String getLinkFromLesson(Long lessonId, String key) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> new EntityNotFoundException("Lesson not found"));
        return lesson.getLink().get(key);
    }*/
}

