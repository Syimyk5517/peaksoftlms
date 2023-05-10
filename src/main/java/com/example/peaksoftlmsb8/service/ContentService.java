package com.example.peaksoftlmsb8.service;

import com.example.peaksoftlmsb8.dto.request.ContentRequest;
import com.example.peaksoftlmsb8.dto.response.ContentResponse;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;

import java.util.List;

public interface ContentService {
    SimpleResponse sendTaskAnswer(Long taskId, ContentRequest contentRequest);

    SimpleResponse update(Long contentId, ContentRequest contentRequest);

    SimpleResponse delete(Long contentId);

    List<ContentResponse> findContentsByTaskId(Long taskId);
}
