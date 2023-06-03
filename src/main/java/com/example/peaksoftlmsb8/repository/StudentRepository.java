package com.example.peaksoftlmsb8.repository;

import com.example.peaksoftlmsb8.db.entity.Student;
import com.example.peaksoftlmsb8.db.enums.FormLearning;
import com.example.peaksoftlmsb8.dto.response.student.StudentResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("select exists (select 1 from Student s where s.user.email = :email) as user_exists")
    Boolean existsByEmail(String email);

    @Query("select exists (select 1 from Student s where s.user.phoneNumber = :phoneNumber) as user_exists")
    Boolean existsByPhoneNumber(String phoneNumber);

    @Query("select new com.example.peaksoftlmsb8.dto.response.student.StudentResponse(" +
            "s.id,concat(u.firstName,' ',u.lastName),u.phoneNumber,u.email,s.formLearning,s.group.name) " +
            "from Student s join User u on s.id=u.student.id where s.id=:studentId")
    Optional<StudentResponse> findStudentById(Long studentId);
    @Query("select new com.example.peaksoftlmsb8.dto.response.student.StudentResponse(" +
            "s.id,concat(s.user.firstName,' ',s.user.lastName),s.user.phoneNumber,s.user.email,s.formLearning,s.group.name) " +
            "from Student s  JOIN s.group g JOIN g.courses c WHERE c.id = :courseId")
    List<StudentResponse> findAllStudentsByCourseId(Long courseId);
    @Query("SELECT new com.example.peaksoftlmsb8.dto.response.student.StudentResponse(" +
            "s.id, CONCAT(s.user.firstName, ' ', s.user.lastName), s.user.phoneNumber, s.user.email, s.formLearning, g.name) " +
            "FROM Student s join s.group g " +
            "WHERE s.formLearning = :formatStudy " +
            "order by s.id desc ")
    List<StudentResponse> findAllStudentsByCourseIdWithSort(@Param("formatStudy") FormLearning formatStudy);



}

