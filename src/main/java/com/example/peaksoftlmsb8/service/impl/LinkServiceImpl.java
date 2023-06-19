package com.example.peaksoftlmsb8.service.impl;

import com.example.peaksoftlmsb8.db.entity.Lesson;
import com.example.peaksoftlmsb8.dto.request.LinkRequest;
import com.example.peaksoftlmsb8.exception.NotFoundException;
import com.example.peaksoftlmsb8.dto.response.LinkResponse;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.repository.LessonRepository;
import com.example.peaksoftlmsb8.service.LinkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Log4j2
public class LinkServiceImpl implements LinkService {
    private final LessonRepository lessonRepository;
    private static final Logger logger = LogManager.getLogger(LinkServiceImpl.class);

    public SimpleResponse addLinkToLesson(LinkRequest linkRequest) {
        Lesson lesson = lessonRepository.findById(linkRequest.getLessonId()).orElseThrow(() -> {
            logger.error("Lesson with id: " + linkRequest.getLessonId() + " not found!");
            throw new NotFoundException("Урок с идентификатором: " + linkRequest.getLessonId() + " не найден");
        });
        lesson.getLink().put(linkRequest.getLink(), linkRequest.getDisplayText());
        lessonRepository.save(lesson);
        logger.info("Link successfully added");
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Ссылка успешно добавлена")
                .build();
    }

    public SimpleResponse removeLinkFromLesson(Long lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> {
            logger.error("Lesson with id: " + lessonId + " not found!");
            throw new NotFoundException("Урок с идентификатором: " + lessonId + " не найден");
        });
        lesson.setLink(null);
        lessonRepository.save(lesson);
        logger.info("Link successfully deleted");
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Ссылка успешно удалена")
                .build();
    }

    public SimpleResponse updateLinkInLesson(LinkRequest linkRequest) {
        Lesson lesson = lessonRepository.findById(linkRequest.getLessonId()).orElseThrow(() -> {
            logger.error("Lesson with id: " + linkRequest.getLessonId() + " not found!");
            throw new NotFoundException("Урок с идентификатором: " + linkRequest.getLink() + " не найден");
        });
        lesson.getLink().put(linkRequest.getLink(), linkRequest.getDisplayText());
        lessonRepository.save(lesson);
        logger.info("Link successfully update!");
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Ссылка успешно обновлена")
                .build();
    }

    public LinkResponse getLinkFromLesson(Long lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> {
            logger.error("Lesson with id: " + lessonId + " not found!");
            throw new NotFoundException("Урок с идентификатором: " + lessonId + " не найден");
        });
        if (lesson.getLink() == null) {
            throw new NotFoundException("В этом уроке не существует ссылка");
        }
        return LinkResponse.builder().
                lessonId(lesson.getId())
                .link(lesson.getLink()).build();
    }
}

