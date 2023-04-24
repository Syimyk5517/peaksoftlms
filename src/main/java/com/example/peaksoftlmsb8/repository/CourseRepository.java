package com.example.peaksoftlmsb8.repository;

import com.example.peaksoftlmsb8.db.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}