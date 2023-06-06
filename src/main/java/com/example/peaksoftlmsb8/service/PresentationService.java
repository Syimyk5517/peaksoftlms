package com.example.peaksoftlmsb8.service;

import com.example.peaksoftlmsb8.dto.request.presentation.PresentationRequest;
import com.example.peaksoftlmsb8.dto.response.presentation.PresentationResponse;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;

import java.util.List;

public interface PresentationService {
    SimpleResponse savePresentation(PresentationRequest presentationRequest);

    PresentationResponse findByPresentationId(Long presentationId);

    List<PresentationResponse> findAllPresentationsByLessonId(Long lessonId);

    SimpleResponse updatePresentation(Long presentationId, PresentationRequest presentationUpdateRequest);

    SimpleResponse deletePresentation(Long presentationId);

}
