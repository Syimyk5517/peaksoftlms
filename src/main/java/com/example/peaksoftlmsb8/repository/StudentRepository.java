package com.example.peaksoftlmsb8.repository;

import com.example.peaksoftlmsb8.db.entity.Student;
import com.example.peaksoftlmsb8.dto.response.StudentResponse;
import com.example.peaksoftlmsb8.dto.response.StudentResponseForAdmin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("select exists (select 1 from Student s where s.user.email = :email) as user_exists")
    Boolean existsByEmail(String email);

    @Query("select exists (select 1 from Student s where s.user.phoneNumber = :phoneNumber) as user_exists")
    Boolean existsByPhoneNumber(String phoneNumber);

    @Query("select new com.example.peaksoftlmsb8.dto.response.StudentResponse(" +
            "s.id,concat(u.firstName,' ',u.lastName),u.phoneNumber,u.email,s.formLearning,s.group.name) " +
            "from Student s join User u on s.id=u.student.id where s.id=:studentId")
    Optional<StudentResponse> findStudentById(Long studentId);

    @Query("select new com.example.peaksoftlmsb8.dto.response.StudentResponse(" +
            "s.id,concat(u.firstName,' ',u.lastName),u.phoneNumber,u.email,s.formLearning,s.group.name) " +
            "from Student s join User u on s.id=u.student.id where u.firstName ilike concat('%', :search, '%')" +
            "or u.lastName ilike concat('%', :search, '%') or u.email ilike concat('%', :search, '%')" +
            "or u.phoneNumber ilike concat('%', :search, '%')")
    Page<StudentResponse> findAllStudents(Pageable pageable, String search);

    @Query("select new com.example.peaksoftlmsb8.dto.response.StudentResponseForAdmin(" +
            "s.id,concat(u.firstName,' ',u.lastName),u.phoneNumber,u.email,u.password,s.formLearning,s.group.name) " +
            "from Student s join User u on s.id=u.student.id")
    List<StudentResponseForAdmin> allStudents();
}

