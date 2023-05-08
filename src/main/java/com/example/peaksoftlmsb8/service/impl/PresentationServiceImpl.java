package com.example.peaksoftlmsb8.service.impl;

import com.example.peaksoftlmsb8.db.entity.Lesson;
import com.example.peaksoftlmsb8.db.entity.Presentation;
import com.example.peaksoftlmsb8.db.exception.AlReadyExistException;
import com.example.peaksoftlmsb8.db.exception.NotFoundException;
import com.example.peaksoftlmsb8.dto.request.presentation.PresentationRequest;
import com.example.peaksoftlmsb8.dto.request.presentation.PresentationUpdateRequest;
import com.example.peaksoftlmsb8.dto.response.presentation.PresentationResponse;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.repository.LessonRepository;
import com.example.peaksoftlmsb8.repository.PresentationRepository;
import com.example.peaksoftlmsb8.service.PresentationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PresentationServiceImpl implements PresentationService {
    private final LessonRepository lessonRepository;
    private final PresentationRepository presentationRepository;

    @Override
    public SimpleResponse savePresentation(PresentationRequest presentationRequest) {
        Lesson lesson = lessonRepository.findById(presentationRequest.getLessonId()).orElseThrow(() ->
                new NotFoundException(String.format("Lesson with id: " + presentationRequest.getLessonId() + " not found")));
        if (presentationRepository.existsPresentationsByFormatPPT(presentationRequest.getFormatPPT())) {
            throw new AlReadyExistException("Presentation with formatPPT : " + presentationRequest.getFormatPPT() + " already exists");
        }
        Presentation presentation = new Presentation();
        presentation.setName(presentationRequest.getName());
        presentation.setDescription(presentationRequest.getDescription());
        presentation.setFormatPPT(presentationRequest.getFormatPPT());
        presentation.setLesson(lesson);
        presentationRepository.save(presentation);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Successfully saved").build();
    }

    @Override
    public PresentationResponse findByPresentationId(Long presentationId) {
        return presentationRepository.getPresentationById(presentationId).orElseThrow(() -> new NotFoundException(String.format("Presentation with id: " + presentationId + " not found")));
    }

    @Override
    public List<PresentationResponse> findAllPresentationsByLessonId(Long lessonId) {
        return presentationRepository.getAllPresentationsByLessonId(lessonId);
    }

    @Override
    @Transactional
    public SimpleResponse updatePresentation(PresentationUpdateRequest presentationUpdateRequest) {
        Presentation presentation = presentationRepository.findById(presentationUpdateRequest.getPresentationId()).orElseThrow(() ->
                new NotFoundException(String.format("Presentation with id : " + presentationUpdateRequest.getPresentationId() + " not found")));
        if (presentationRepository.existsPresentationsByFormatPPT(presentationUpdateRequest.getFormatPPT())) {
            throw new AlReadyExistException("Presentation with formatPPT : " + presentationUpdateRequest.getFormatPPT() + " already exists");
        }
        presentation.setId(presentationUpdateRequest.getPresentationId());
        presentation.setName(presentationUpdateRequest.getName());
        presentation.setDescription(presentationUpdateRequest.getDescription());
        presentation.setFormatPPT(presentationUpdateRequest.getFormatPPT());
        presentationRepository.save(presentation);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Successfully updated").build();
    }

    @Override
    public SimpleResponse deletePresentation(Long presentationId) {
        Presentation presentation = presentationRepository.findById(presentationId).orElseThrow(() ->
                new NotFoundException(String.format("Presentation with id : " + presentationId + " not found")));
        presentationRepository.delete(presentation);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Successfully deleted").build();
    }
}
