package com.example.peaksoftlmsb8.service;

import com.example.peaksoftlmsb8.db.entity.Lesson;
import com.example.peaksoftlmsb8.dto.request.LinkRequest;
import com.example.peaksoftlmsb8.dto.response.LinkResponse;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;

import java.util.List;

public interface LinkService {
    SimpleResponse addLinkToLesson(Long lessonId, String key, String value);

    SimpleResponse removeLinkFromLesson(Long lessonId, String key,String value);

    SimpleResponse updateLinkInLesson(Long lessonId, String key, String value);

    LinkResponse getLinkFromLesson(Long lessonId,String key);
}


