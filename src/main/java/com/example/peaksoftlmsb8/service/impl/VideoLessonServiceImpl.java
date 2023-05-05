package com.example.peaksoftlmsb8.service.impl;

import com.example.peaksoftlmsb8.db.entity.Lesson;
import com.example.peaksoftlmsb8.db.entity.VideoLesson;
import com.example.peaksoftlmsb8.db.exception.BadRequestException;
import com.example.peaksoftlmsb8.db.exception.NotFoundException;
import com.example.peaksoftlmsb8.dto.request.VideoLessonRequest;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.dto.response.VideoLessonResponse;
import com.example.peaksoftlmsb8.repository.LessonRepository;
import com.example.peaksoftlmsb8.repository.VideoLessonRepository;
import com.example.peaksoftlmsb8.service.VideoLessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoLessonServiceImpl implements VideoLessonService {
    private final VideoLessonRepository videoLessonRepository;
    private final LessonRepository lessonRepository;

    @Override
    public SimpleResponse save(VideoLessonRequest videoLessonRequest, Long lessonId) {
        if (videoLessonRepository.existsByName(videoLessonRequest.getName())) {
            throw new BadRequestException("Video with Name: " + videoLessonRequest.getName() + " is already saved!");
        }
        if (videoLessonRepository.existsByLink(videoLessonRequest.getVideoLink())) {
            throw new BadRequestException("Video with Link: " + videoLessonRequest.getVideoLink() + " is already saved!");
        }
        VideoLesson videoLesson = new VideoLesson();
        videoLesson.setName(videoLessonRequest.getName());
        videoLesson.setDescription(videoLessonRequest.getDescription());
        videoLesson.setLink(videoLessonRequest.getVideoLink());
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(
                () -> new NotFoundException("Lesson with ID: " + lessonId + " is not found!"));
        videoLesson.setLesson(lesson);
        lesson.getVideoLessons().add(videoLesson);
        videoLessonRepository.save(videoLesson);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Lesson's Video with name: " + videoLessonRequest.getName() + " is successfully saved!")
                .build();
    }

    @Override
    public VideoLessonResponse getVideoLessonById(Long videoLessonId) {
        return videoLessonRepository.findVideoById(videoLessonId).orElseThrow(
                () -> new NotFoundException("Video with ID: " + videoLessonId + " is not found!"));
    }

    @Override
    public List<VideoLessonResponse> findAllVideos() {
        if (videoLessonRepository.findAllVideos().isEmpty()) {
            throw new NotFoundException("Videos not found...");
        }
        return videoLessonRepository.findAllVideos();
    }

    @Override
    public List<VideoLessonResponse> findByLessonId(Long lessonId) {
        if (!lessonRepository.existsById(lessonId)) {
            throw new NotFoundException("Not found Lesson with ID: " + lessonId);
        }
        if (videoLessonRepository.findByLessonId(lessonId).isEmpty()) {
            throw new NotFoundException("Videos not found with Lesson's ID:" + lessonId);
        }
        return videoLessonRepository.findByLessonId(lessonId);
    }

    @Override
    public SimpleResponse update(Long videoLessonId, VideoLessonRequest videoLessonRequest) {
        VideoLesson videoLesson = videoLessonRepository.findById(videoLessonId).orElseThrow(
                () -> new NotFoundException("Video with ID: " + videoLessonId + " is not found!"));
        if (!videoLessonRequest.getName().equals(videoLesson.getName())) {
            if (videoLessonRepository.existsByName(videoLessonRequest.getName())) {
                throw new BadRequestException("Video with Name: " + videoLessonRequest.getName() + " is already saved!");
            }
            videoLesson.setName(videoLessonRequest.getName());
        }
        if (!videoLessonRequest.getDescription().equals(videoLesson.getDescription())) {
            videoLesson.setDescription(videoLessonRequest.getDescription());
        }
        if (!videoLessonRequest.getVideoLink().equals(videoLesson.getLink())) {
            if (videoLessonRepository.existsByLink(videoLessonRequest.getVideoLink())) {
                throw new BadRequestException("Video with Link: " + videoLessonRequest.getVideoLink() + " is already saved!");
            }
            videoLesson.setLink(videoLessonRequest.getVideoLink());
        }
        videoLessonRepository.save(videoLesson);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Video with ID: " + videoLessonId + " is successfully updated!")
                .build();
    }

    @Override
    public SimpleResponse delete(Long videoLessonId) {
        if (!videoLessonRepository.existsById(videoLessonId)) {
            throw new NotFoundException("Video with ID: " + videoLessonId + " is not found!");
        }
        videoLessonRepository.deleteById(videoLessonId);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Video with ID: " + videoLessonId + " is successfully deleted!")
                .build();
    }
}
