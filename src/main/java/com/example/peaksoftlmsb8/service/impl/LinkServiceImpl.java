package com.example.peaksoftlmsb8.service.impl;

import com.example.peaksoftlmsb8.db.entity.Lesson;
import com.example.peaksoftlmsb8.repository.LessonRepository;
import com.example.peaksoftlmsb8.service.LinkService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LinkServiceImpl implements LinkService {

    @Autowired
    private LessonRepository lessonRepository;

    public Lesson addLinkToLesson(Long lessonId, String key, String value) {
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
    }
}

