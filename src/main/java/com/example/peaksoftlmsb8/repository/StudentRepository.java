package com.example.peaksoftlmsb8.repository;

import com.example.peaksoftlmsb8.db.entity.Group;
import com.example.peaksoftlmsb8.db.entity.Student;
import com.example.peaksoftlmsb8.dto.response.student.StudentResponse;
import com.example.peaksoftlmsb8.dto.response.student.StudentResponseForAdmin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
            "s.id,concat(u.firstName,' ',u.lastName),u.phoneNumber,u.email,s.formLearning,s.group.name) " +
            "from Course c join c.groups g join Student s on s.group.id=g.id join User u on u.student.id=s.id" +
            " where c.id=:courseId")
    List<StudentResponse> findAllStudentsByCourseId(Long courseId);
    @Query("select new com.example.peaksoftlmsb8.dto.response.student.StudentResponse(" +
            "s.id,concat(u.firstName,' ',u.lastName),u.phoneNumber,u.email,s.formLearning,s.group.name) " +
            "from Course c join c.groups g join Student s on s.group.id=g.id join User u on u.student.id=s.id " +
            "where c.id=:courseId " +
            "ORDER BY " +
            "CASE WHEN :formatStudy = 'online' THEN s.formLearning END ASC, " +
            "CASE WHEN :formatStudy = 'offline' THEN s.formLearning END DESC, " +
            "s.id ASC")
    List<StudentResponse> findAllStudentsByCourseIdWithSort(Long courseId,@Param("formatStudy") String formatStudy);
}

