package com.example.peaksoftlmsb8.repository;

import com.example.peaksoftlmsb8.db.entity.Lesson;
import com.example.peaksoftlmsb8.dto.response.lesson.LessonResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    boolean existsById(Long lessonId);

    @Query("select new com.example.peaksoftlmsb8.dto.response.lesson.LessonResponse(" +
            "l.id,l.name,l.course.id) " +
            "from Lesson l where l.course.id=:courseId order by l.id desc ")
    Page<LessonResponse> getAllLessonsByCourseId(Pageable pageable, Long courseId);

    Boolean existsLessonByName(String name);

    @Query("select new com.example.peaksoftlmsb8.dto.response.lesson.LessonResponse(l.id,l.name,l.course.id) from Lesson l where l.id=:lessonId")
    Optional<LessonResponse> getLessonById(Long lessonId);
}