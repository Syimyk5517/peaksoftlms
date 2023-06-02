package com.example.peaksoftlmsb8.service.impl;

import com.example.peaksoftlmsb8.db.entity.Lesson;
import com.example.peaksoftlmsb8.db.entity.Presentation;
import com.example.peaksoftlmsb8.exception.AlReadyExistException;
import com.example.peaksoftlmsb8.exception.NotFoundException;
import com.example.peaksoftlmsb8.dto.request.presentation.PresentationRequest;
import com.example.peaksoftlmsb8.dto.request.presentation.PresentationUpdateRequest;
import com.example.peaksoftlmsb8.dto.response.presentation.PresentationResponse;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.repository.LessonRepository;
import com.example.peaksoftlmsb8.repository.PresentationRepository;
import com.example.peaksoftlmsb8.service.PresentationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class PresentationServiceImpl implements PresentationService {
    private final LessonRepository lessonRepository;
    private final PresentationRepository presentationRepository;

    private static final Logger logger = LogManager.getLogger(PresentationServiceImpl.class);

    @Override
    public SimpleResponse savePresentation(PresentationRequest presentationRequest) {
        logger.info("Lesson with id: " + presentationRequest.getLessonId() + " not found");
        Lesson lesson = lessonRepository.findById(presentationRequest.getLessonId()).orElseThrow(() ->
                new NotFoundException(String.format("Lesson with id: " + presentationRequest.getLessonId() + " not found")));
        logger.info("Presentation with formatPPT : " + presentationRequest.getFormatPPT() + " already exists");
        if (presentationRepository.existsPresentationsByFormatPPT(presentationRequest.getFormatPPT())) {
            throw new AlReadyExistException("Presentation with formatPPT : " + presentationRequest.getFormatPPT() + " already exists");
        }
        Presentation presentation = new Presentation();
        presentation.setName(presentationRequest.getName());
        presentation.setDescription(presentationRequest.getDescription());
        presentation.setFormatPPT(presentationRequest.getFormatPPT());
        presentation.setLesson(lesson);
        presentationRepository.save(presentation);
        logger.info("Successfully saved");
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Successfully saved").build();
    }

    @Override
    public PresentationResponse findByPresentationId(Long presentationId) {
        logger.info("Presentation with id: " + presentationId + " not found");
        return presentationRepository.getPresentationById(presentationId)
                .orElseThrow(() -> new NotFoundException(String.format("Presentation with id: " + presentationId + " not found")));
    }

    @Override
    public List<PresentationResponse> findAllPresentationsByLessonId(Long lessonId) {
        return presentationRepository.getAllPresentationsByLessonId(lessonId);
    }

    @Override
    @Transactional
    public SimpleResponse updatePresentation(PresentationUpdateRequest presentationUpdateRequest) {
        logger.info("Presentation with id : " + presentationUpdateRequest.getPresentationId() + " not found");
        Presentation presentation = presentationRepository.findById(presentationUpdateRequest.getPresentationId()).orElseThrow(() ->
                new NotFoundException(String.format("Presentation with id : " + presentationUpdateRequest.getPresentationId() + " not found")));
        logger.info("Presentation with formatPPT : " + presentationUpdateRequest.getFormatPPT() + " already exists");
        if (presentationRepository.existsPresentationsByFormatPPT(presentationUpdateRequest.getFormatPPT())) {
            throw new AlReadyExistException("Presentation with formatPPT : " + presentationUpdateRequest.getFormatPPT() + " already exists");
        }
        presentation.setId(presentationUpdateRequest.getPresentationId());
        presentation.setName(presentationUpdateRequest.getName());
        presentation.setDescription(presentationUpdateRequest.getDescription());
        presentation.setFormatPPT(presentationUpdateRequest.getFormatPPT());
        presentationRepository.save(presentation);
        logger.info("Successfully updated");
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Successfully updated").build();
    }

    @Override
    public SimpleResponse deletePresentation(Long presentationId) {
        logger.info("Presentation with id : " + presentationId + " not found");
        Presentation presentation = presentationRepository.findById(presentationId).orElseThrow(() ->
                new NotFoundException(String.format("Presentation with id : " + presentationId + " not found")));
        presentationRepository.delete(presentation);
        logger.info("Successfully deleted");
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Successfully deleted").build();
    }
}
