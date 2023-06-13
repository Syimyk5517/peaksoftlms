package com.example.peaksoftlmsb8.service.impl;

import com.example.peaksoftlmsb8.db.entity.Lesson;
import com.example.peaksoftlmsb8.db.entity.Presentation;
import com.example.peaksoftlmsb8.dto.request.presentation.PresentationRequest;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.dto.response.presentation.PresentationResponse;
import com.example.peaksoftlmsb8.exception.NotFoundException;
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
        Lesson lesson = lessonRepository.findById(presentationRequest.getLessonId()).orElseThrow(() -> {
            logger.error("Lesson with id: " + presentationRequest.getLessonId() + " not found");
            throw new NotFoundException("Урок с идентификатором: " + presentationRequest.getLessonId() + " не найден");});
        Presentation presentation = new Presentation();
        presentation.setName(presentationRequest.getName());
        presentation.setDescription(presentationRequest.getDescription());
        presentation.setFormatPPT(presentationRequest.getFormatPPT());
        presentation.setLesson(lesson);
        presentationRepository.save(presentation);
        logger.info("Successfully saved");
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Успешно сохранено").build();
    }

    @Override
    public PresentationResponse findByPresentationId(Long presentationId) {
        return presentationRepository.getPresentationById(presentationId)
                .orElseThrow(() ->  {
                    logger.error("Presentation with id: " + presentationId + " not found");
                    throw new NotFoundException("Презентация с идентификатором: " + presentationId + " не найдена");});
    }

    @Override
    public List<PresentationResponse> findAllPresentationsByLessonId(Long lessonId) {
        return presentationRepository.getAllPresentationsByLessonId(lessonId);
    }

    @Override
    @Transactional
    public SimpleResponse updatePresentation(Long presentationId, PresentationRequest presentationUpdateRequest) {
        logger.info("Presentation with id : " + presentationId + " not found");
        Presentation presentation = presentationRepository.findById(presentationId).orElseThrow(() ->
                new NotFoundException(String.format("Presentation with id : " + presentationId + " not found")));
        presentation.setName(presentationUpdateRequest.getName());
        presentation.setDescription(presentationUpdateRequest.getDescription());
        presentationRepository.save(presentation);
        logger.info("Successfully updated");
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Successfully updated").build();
    }

    @Override
    public SimpleResponse deletePresentation(Long presentationId) {
        Presentation presentation = presentationRepository.findById(presentationId).orElseThrow(() -> {
            logger.error("Presentation with id : " + presentationId + " not found");
            throw   new NotFoundException("Презентация с идентификатором: " + presentationId + " не найдена");});
        presentationRepository.delete(presentation);
        logger.info("Successfully deleted");
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Успешно удалено").build();
    }
}
