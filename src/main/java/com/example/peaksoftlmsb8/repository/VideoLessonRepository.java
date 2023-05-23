package com.example.peaksoftlmsb8.repository;

import com.example.peaksoftlmsb8.db.entity.VideoLesson;
import com.example.peaksoftlmsb8.dto.response.videLesson.VideoLessonResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VideoLessonRepository extends JpaRepository<VideoLesson, Long> {
    Boolean existsByName(String videoName);

    Boolean existsByLink(String videoLink);

    @Query("select new com.example.peaksoftlmsb8.dto.response.videLesson.VideoLessonResponse" +
            "(v.name,v.description,v.link) " +
            "from VideoLesson v join Lesson l on v.lesson.id=l.id where v.id=:videoId")
    Optional<VideoLessonResponse> findVideoById(Long videoId);

    @Query("select new com.example.peaksoftlmsb8.dto.response.videLesson.VideoLessonResponse" +
            "(v.name,v.description,v.link) " +
            "from VideoLesson v join Lesson l on v.lesson.id=l.id")
    List<VideoLessonResponse> findAllVideos();

    @Query("select new com.example.peaksoftlmsb8.dto.response.videLesson.VideoLessonResponse" +
            "(v.name,v.description,v.link) " +
            "from VideoLesson v join Lesson l on v.lesson.id=l.id where l.id=:lessonId")
    List<VideoLessonResponse> findByLessonId(Long lessonId);
}