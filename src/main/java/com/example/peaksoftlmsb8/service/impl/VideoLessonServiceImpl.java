package com.example.peaksoftlmsb8.service.impl;

import com.example.peaksoftlmsb8.db.entity.Lesson;
import com.example.peaksoftlmsb8.db.entity.VideoLesson;
import com.example.peaksoftlmsb8.exception.BadRequestException;
import com.example.peaksoftlmsb8.exception.NotFoundException;
import com.example.peaksoftlmsb8.dto.request.videLesson.VideoLessonRequest;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.dto.response.videLesson.VideoLessonResponse;
import com.example.peaksoftlmsb8.repository.LessonRepository;
import com.example.peaksoftlmsb8.repository.VideoLessonRepository;
import com.example.peaksoftlmsb8.service.VideoLessonService;
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
public class VideoLessonServiceImpl implements VideoLessonService {
    private final VideoLessonRepository videoLessonRepository;
    private final LessonRepository lessonRepository;

    private static final Logger logger = LogManager.getLogger(VideoLessonServiceImpl.class);

    @Override
    public SimpleResponse save(VideoLessonRequest videoLessonRequest, Long lessonId) {
        if (videoLessonRepository.existsByName(videoLessonRequest.getName())) {
            logger.error("Video with Name: " + videoLessonRequest.getName() + " is already saved!");
            throw new BadRequestException("Видео с названием: " + videoLessonRequest.getName() + " уже сохранено!");
        }
        if (videoLessonRepository.existsByLink(videoLessonRequest.getVideoLink())) {
            logger.error("Video with Link: " + videoLessonRequest.getVideoLink() + " is already saved!");
            throw new BadRequestException("Видео со ссылкой: " + videoLessonRequest.getVideoLink() + " уже сохранено!");
        }
        VideoLesson videoLesson = new VideoLesson();
        videoLesson.setName(videoLessonRequest.getName());
        videoLesson.setDescription(videoLessonRequest.getDescription());
        videoLesson.setLink(videoLessonRequest.getVideoLink());
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> {
            logger.error("Lesson with ID: " + lessonId + " is not found!");
            throw new NotFoundException("Урок с идентификатором: " + lessonId + " не найден!");});
        videoLesson.setLesson(lesson);
        videoLessonRepository.save(videoLesson);
        logger.info("Lesson's Video with name: " + videoLessonRequest.getName() + " is successfully saved!");
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Видео урока с названием: " + videoLessonRequest.getName() + " успешно сохранено!")
                .build();
    }

    @Override
    public VideoLessonResponse getVideoLessonById(Long videoLessonId) {
        return videoLessonRepository.findVideoById(videoLessonId).orElseThrow(() ->{
            logger.error("Video with ID: " + videoLessonId + " is not found!");
            throw new NotFoundException("Видео с идентификатором: " + videoLessonId + " не найдено!");});
    }

    @Override
    public List<VideoLessonResponse> findAllVideos() {
        if (videoLessonRepository.findAllVideos().isEmpty()) {
            logger.error("Videos not found...");
            throw new NotFoundException("Видео не найдено...");
        }
        return videoLessonRepository.findAllVideos();
    }

    @Override
    public List<VideoLessonResponse> findByLessonId(Long lessonId) {
        if (!lessonRepository.existsById(lessonId)) {
            logger.error("Not found Lesson with ID: " + lessonId);
            throw new NotFoundException("Не найден урок с идентификатором: " + lessonId);
        }
        if (videoLessonRepository.findByLessonId(lessonId).isEmpty()) {
            logger.error("Videos not found with Lesson's ID:" + lessonId);
            throw new NotFoundException("Видео не найдено с идентификатором урока:" + lessonId);
        }
        return videoLessonRepository.findByLessonId(lessonId);
    }

    @Override
    public SimpleResponse update(Long videoLessonId, VideoLessonRequest videoLessonRequest) {
        VideoLesson videoLesson = videoLessonRepository.findById(videoLessonId).orElseThrow(() -> {
                    logger.error("Video with ID: " + videoLessonId + " is not found!");
                    throw new NotFoundException("Видео с идентификатором: " + videoLessonId + " не найдено!");});
        if (!videoLessonRequest.getName().equals(videoLesson.getName())) {
            if (videoLessonRepository.existsByName(videoLessonRequest.getName())) {
                logger.error("Video with Name: " + videoLessonRequest.getName() + " is already saved!");
                throw new BadRequestException("Видео с названием: " + videoLessonRequest.getName() + " уже сохранено!");
            }
            videoLesson.setName(videoLessonRequest.getName());
        }
        if (!videoLessonRequest.getDescription().equals(videoLesson.getDescription())) {
            videoLesson.setDescription(videoLessonRequest.getDescription());
        }
        if (!videoLessonRequest.getVideoLink().equals(videoLesson.getLink())) {
            if (videoLessonRepository.existsByLink(videoLessonRequest.getVideoLink())) {
                logger.error("Video with Link: " + videoLessonRequest.getVideoLink() + " is already saved!");
                throw new BadRequestException("Видео со ссылкой: " + videoLessonRequest.getVideoLink() + " уже сохранено!");
            }
            videoLesson.setLink(videoLessonRequest.getVideoLink());
        }
        logger.info("Video with ID: " + videoLessonId + " is successfully updated!");
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Видео с идентификатором: " + videoLessonId + " успешно обновлено!")
                .build();
    }

    @Override
    public SimpleResponse delete(Long videoLessonId) {
        if (!videoLessonRepository.existsById(videoLessonId)) {
            logger.error("Video with ID: " + videoLessonId + " is not found!");
            throw new NotFoundException("Видео с идентификатором: " + videoLessonId + " не найдено!");
        }
        videoLessonRepository.deleteById(videoLessonId);
        logger.info("Video with ID: " + videoLessonId + " is successfully deleted!");
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Видео с идентификатором: " + videoLessonId + " успешно удалено!")
                .build();
    }
}
