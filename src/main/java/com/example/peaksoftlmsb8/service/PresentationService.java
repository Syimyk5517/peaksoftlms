package com.example.peaksoftlmsb8.service;

import com.example.peaksoftlmsb8.dto.request.PresentationRequest;
import com.example.peaksoftlmsb8.dto.request.PresentationUpdateRequest;
import com.example.peaksoftlmsb8.dto.response.PresentationResponse;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;

import java.util.List;

public interface PresentationService {
    SimpleResponse savePresentation(PresentationRequest presentationRequest);

    PresentationResponse findByPresentationId(Long presentationId);

    List<PresentationResponse> findAllPresentationsByLessonId(Long lessonId);

    SimpleResponse updatePresentation(PresentationUpdateRequest presentationUpdateRequest);

    SimpleResponse deletePresentation(Long presentationId);

}