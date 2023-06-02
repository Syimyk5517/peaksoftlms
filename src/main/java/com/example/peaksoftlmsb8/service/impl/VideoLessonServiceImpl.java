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
            logger.info("Video with Name: " + videoLessonRequest.getName() + " is already saved!");
            throw new BadRequestException("Video with Name: " + videoLessonRequest.getName() + " is already saved!");
        }
        if (videoLessonRepository.existsByLink(videoLessonRequest.getVideoLink())) {
            logger.info("Video with Link: " + videoLessonRequest.getVideoLink() + " is already saved!");
            throw new BadRequestException("Video with Link: " + videoLessonRequest.getVideoLink() + " is already saved!");
        }
        VideoLesson videoLesson = new VideoLesson();
        videoLesson.setName(videoLessonRequest.getName());
        videoLesson.setDescription(videoLessonRequest.getDescription());
        videoLesson.setLink(videoLessonRequest.getVideoLink());
        logger.info("Lesson with ID: " + lessonId + " is not found!");
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(
                () -> new NotFoundException("Lesson with ID: " + lessonId + " is not found!"));
        videoLesson.setLesson(lesson);
        lesson.getVideoLessons().add(videoLesson);
        logger.info("Lesson's Video with name: " + videoLessonRequest.getName() + " is successfully saved!");
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Lesson's Video with name: " + videoLessonRequest.getName() + " is successfully saved!")
                .build();
    }

    @Override
    public VideoLessonResponse getVideoLessonById(Long videoLessonId) {
        logger.info("Video with ID: " + videoLessonId + " is not found!");
        return videoLessonRepository.findVideoById(videoLessonId).orElseThrow(
                () -> new NotFoundException("Video with ID: " + videoLessonId + " is not found!"));
    }

    @Override
    public List<VideoLessonResponse> findAllVideos() {
        if (videoLessonRepository.findAllVideos().isEmpty()) {
            logger.info("Videos not found...");
            throw new NotFoundException("Videos not found...");
        }
        return videoLessonRepository.findAllVideos();
    }

    @Override
    public List<VideoLessonResponse> findByLessonId(Long lessonId) {
        if (!lessonRepository.existsById(lessonId)) {
            logger.info("Not found Lesson with ID: " + lessonId);
            throw new NotFoundException("Not found Lesson with ID: " + lessonId);
        }
        if (videoLessonRepository.findByLessonId(lessonId).isEmpty()) {
            logger.info("Videos not found with Lesson's ID:" + lessonId);
            throw new NotFoundException("Videos not found with Lesson's ID:" + lessonId);
        }
        return videoLessonRepository.findByLessonId(lessonId);
    }

    @Override
    public SimpleResponse update(Long videoLessonId, VideoLessonRequest videoLessonRequest) {
        logger.info("Video with ID: " + videoLessonId + " is not found!");
        VideoLesson videoLesson = videoLessonRepository.findById(videoLessonId).orElseThrow(
                () -> new NotFoundException("Video with ID: " + videoLessonId + " is not found!"));
        if (!videoLessonRequest.getName().equals(videoLesson.getName())) {
            if (videoLessonRepository.existsByName(videoLessonRequest.getName())) {
                logger.info("Video with Name: " + videoLessonRequest.getName() + " is already saved!");
                throw new BadRequestException("Video with Name: " + videoLessonRequest.getName() + " is already saved!");
            }
            videoLesson.setName(videoLessonRequest.getName());
        }
        if (!videoLessonRequest.getDescription().equals(videoLesson.getDescription())) {
            videoLesson.setDescription(videoLessonRequest.getDescription());
        }
        if (!videoLessonRequest.getVideoLink().equals(videoLesson.getLink())) {
            if (videoLessonRepository.existsByLink(videoLessonRequest.getVideoLink())) {
                logger.info("Video with Link: " + videoLessonRequest.getVideoLink() + " is already saved!");
                throw new BadRequestException("Video with Link: " + videoLessonRequest.getVideoLink() + " is already saved!");
            }
            videoLesson.setLink(videoLessonRequest.getVideoLink());
        }
        logger.info("Video with ID: " + videoLessonId + " is successfully updated!");
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Video with ID: " + videoLessonId + " is successfully updated!")
                .build();
    }

    @Override
    public SimpleResponse delete(Long videoLessonId) {
        if (!videoLessonRepository.existsById(videoLessonId)) {
            logger.info("Video with ID: " + videoLessonId + " is not found!");
            throw new NotFoundException("Video with ID: " + videoLessonId + " is not found!");
        }
        videoLessonRepository.deleteById(videoLessonId);
        logger.info("Video with ID: " + videoLessonId + " is successfully deleted!");
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Video with ID: " + videoLessonId + " is successfully deleted!")
                .build();
    }
}
