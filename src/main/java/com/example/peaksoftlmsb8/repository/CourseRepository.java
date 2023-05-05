package com.example.peaksoftlmsb8.repository;

import com.example.peaksoftlmsb8.db.entity.Course;
import com.example.peaksoftlmsb8.dto.response.CourseResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    //    @Query("select new com.example.peaksoftlmsb8.dto.response.CourseResponse(" +
//            "c.id,c.name,c.image,c.description,c.createdAt,c.finalDate)" +
//            " from Course c where c.name like concat('@',:search, '@')" +
//            " or c.image like concat('@', :search, '@')" +
//            "or c.description like concat('@', :search, '@') " +
//            "order by case when :sort ='id_asc' then c.id end asc case when :sort = 'id_desc' then u.id end desc")
//    Page<CourseResponse> getAllCourses(Pageable pageable, String search);
/*@Query("select new com.example.peaksoftlmsb8.dto.response.CourseResponse" +
        "(c.id,c.name,c.image,c.description,c.createdAt,c.finalDate) " +
        "from Course c where c.name like concat('@',:search, '@')" +
        "or c.image like concat('@', :search, '@')" +
        "or c.description like concat('@', :search, '@') " +
        "order by case when :sort ='id_asc' then c.id end asc," +
        "case when :sort = 'id_desc' then c.id end desc ")
Page<CourseResponse> findAllStudents(Pageable pageable, String search, @Param("sort") String sort);*/
    @Query("select new com.example.peaksoftlmsb8.dto.response.CoursePaginationResponse(" +
            "c.id,c.name,c.description,c.image,c.createdAt,c.finalDate) " +
            " from Course c where c.name ilike concat('%',:word, '%')" +
            " or c.image ilike concat('%', :word, '%')" +
            "or c.description ilike concat('%', :word, '%') " +
            "order by case when :sort = 'id_desc' then c.id end desc, " +
            "case when :sort = 'id_asc' then c.id end asc ")
    Page<CourseResponse> getAllCourses(Pageable pageable, String word, String sort);


    @Query(value = "select new com.example.peaksoftlmsb8.dto.response.CourseResponse(" +
            "c.id,c.name,c.image,c.description,c.createdAt,c.finalDate) from Course c where c.id = :courseId")
    Optional<CourseResponse> findByCourseId(Long courseId);

    boolean existsCourseByName(String name);
}