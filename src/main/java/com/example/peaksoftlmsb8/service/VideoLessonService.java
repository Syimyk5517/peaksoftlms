package com.example.peaksoftlmsb8.service;

import com.example.peaksoftlmsb8.dto.request.VideoLessonRequest;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.dto.response.VideoLessonResponse;

import java.util.List;

public interface VideoLessonService {
    SimpleResponse save(VideoLessonRequest videoLessonRequest,Long lessonId);

    VideoLessonResponse getVideoLessonById(Long videoLessonId);

    List<VideoLessonResponse> findAllVideos();
    List<VideoLessonResponse> findByLessonId(Long lessonId);

    SimpleResponse update(Long videoLessonId, VideoLessonRequest videoLessonRequest);

    SimpleResponse delete(Long videoLessonId);
}
