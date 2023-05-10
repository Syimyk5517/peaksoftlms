package com.example.peaksoftlmsb8.service;

import com.example.peaksoftlmsb8.db.entity.Lesson;
import com.example.peaksoftlmsb8.dto.request.LinkRequest;
import com.example.peaksoftlmsb8.dto.response.LinkResponse;

import java.util.List;

    public interface LinkService {
        Lesson addLinkToLesson(Long lessonId, String key, String value);
        Lesson removeLinkFromLesson(Long lessonId, String key);
        Lesson updateLinkInLesson(Long lessonId, String key, String value);
        String getLinkFromLesson(Long lessonId, String key);
    }


