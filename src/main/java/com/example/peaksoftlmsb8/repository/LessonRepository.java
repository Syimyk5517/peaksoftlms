package com.example.peaksoftlmsb8.repository;

import com.example.peaksoftlmsb8.db.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
}