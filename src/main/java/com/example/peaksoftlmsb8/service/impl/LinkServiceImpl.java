package com.example.peaksoftlmsb8.service.impl;

import com.example.peaksoftlmsb8.db.entity.Lesson;
import com.example.peaksoftlmsb8.db.exception.NotFoundException;
import com.example.peaksoftlmsb8.dto.response.LinkResponse;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.repository.LessonRepository;
import com.example.peaksoftlmsb8.service.LinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class LinkServiceImpl implements LinkService {
    private final LessonRepository lessonRepository;

    public SimpleResponse addLinkToLesson(Long lessonId, String key, String value) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> new NotFoundException("Урок с идентификатором: " + lessonId + " не найден"));
        lesson.getLink().put(key, value);
        lessonRepository.save(lesson);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Ссылка успешно добавлена")
                .build();
    }

    public SimpleResponse removeLinkFromLesson(Long lessonId, String key, String value) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> new NotFoundException("Урок с идентификатором: " + lessonId + " не найден"));
        lesson.getLink().put(key, value);
        lessonRepository.save(lesson);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Ссылка успешно удалена")
                .build();
    }

    public SimpleResponse updateLinkInLesson(Long lessonId, String key, String value) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> new NotFoundException("Урок с идентификатором: " + lessonId + " не найден"));
        lesson.getLink().put(key, value);
        lessonRepository.save(lesson);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Ссылка успешно обновлена")
                .build();
    }

    public LinkResponse getLinkFromLesson(Long lessonId, String key) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> new NotFoundException("Урок с идентификатором: " + lessonId + " не найден"));
        Map<String, String> link = lesson.getLink();
        return null;
    }
}

