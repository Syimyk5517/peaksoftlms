package com.example.peaksoftlmsb8.repository;

import com.example.peaksoftlmsb8.db.entity.Instructor;
import com.example.peaksoftlmsb8.dto.response.InstructorResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long> {
    @Query(" select new com.example.peaksoftlmsb8.dto.response.InstructorResponse(" +
            " u.id, concat(u.firstName,' ',u.lastName), i.special, u.phoneNumber, u.email)" +
            " from User u join Instructor i where u.firstName ilike concat('%' , :keyWord, '%') " +
            "or u.lastName ilike concat('%' , :keyWord, '%')" +
            "or i.special ilike concat('%' , :keyWord, '%')" +
            "or u.email ilike concat('%' , :keyWord, '%')" +
            "or u.phoneNumber ilike concat('%' , :keyWord, '%')")
    Page<InstructorResponse> getAll(Pageable pageable,String keyWord);
    @Query("select new com.example.peaksoftlmsb8.dto.response.InstructorResponse(" +
            " u.id, concat(u.firstName,' ',u.lastName), i.special, u.phoneNumber, u.email)" +
            " from User u join Instructor i")
    Page<InstructorResponse> getAllPage(Pageable pageable);

    @Query("select new com.example.peaksoftlmsb8.dto.response.InstructorResponse(" +
            "i.id,concat(i.user.firstName,' ',i.user.lastName), i.special, i.user.phoneNumber, i.user.email)" +
            " from Instructor i where i.id = ?1")
    Optional<InstructorResponse> getByInstructorId(Long instructorId);
}