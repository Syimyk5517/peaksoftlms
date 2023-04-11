package com.example.peaksoftlmsb8.peaksoft.repository;

import com.example.peaksoftlmsb8.peaksoft.entity.VideoLesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoLessonRepository extends JpaRepository<VideoLesson, Long> {
}