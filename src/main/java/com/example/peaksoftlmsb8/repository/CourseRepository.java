package com.example.peaksoftlmsb8.repository;

import com.example.peaksoftlmsb8.db.entity.Course;
import com.example.peaksoftlmsb8.dto.response.CourseResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query("select new com.example.peaksoftlmsb8.dto.response.CourseResponse(" +
            "c.id,c.name,c.image,c.description,c.createdAt,c.finalDate)" +
            " from Course c where c.name like concat('@',:word, '@')" +
            " or c.image like concat('@', :word, '@')" +
            "or c.description like concat('@', :word, '@') ")
    Page<CourseResponse> getAllCourses(Pageable pageable, String word);

    @Query(value = "select new com.example.peaksoftlmsb8.dto.response.CourseResponse(" +
            "c.id,c.name,c.image,c.description,c.createdAt,c.finalDate) from Course c where c.id = :courseId")
    Optional<CourseResponse> findByCourseId(Long courseId);

    boolean existsCourseByName(String name);
}