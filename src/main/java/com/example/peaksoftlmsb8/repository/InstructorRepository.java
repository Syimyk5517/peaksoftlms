package com.example.peaksoftlmsb8.repository;

import com.example.peaksoftlmsb8.db.entity.Instructor;
import com.example.peaksoftlmsb8.dto.response.InstructorResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long> {
    @Query("SELECT new com.example.peaksoftlmsb8.dto.response.InstructorResponse(" +
            "i.id, CONCAT(u.firstName, ' ', u.lastName), i.special, u.phoneNumber, u.email) " +
            "FROM Instructor i " +
            "LEFT JOIN i.user u " +
            "WHERE (:search IS NULL " +
            "OR CONCAT(u.firstName, ' ', u.lastName) LIKE %:search%) " +
            "OR i.special LIKE %:search% " +
            "OR u.phoneNumber ILIKE %:search% " +
            "OR u.email ILIKE %:search% " +
            "ORDER BY " +
            "CASE WHEN :sort = 'name_asc' THEN u.firstName END ASC, " +
            "CASE WHEN :sort = 'name_desc' THEN u.firstName END DESC, " +
            "i.id ASC")
    Page<InstructorResponse> getAll(Pageable pageable, @Param("search") String search, @Param("sort") String sort);

    @Query("select new com.example.peaksoftlmsb8.dto.response.InstructorResponse(" +
            "i.id,concat(i.user.firstName,' ',i.user.lastName), i.special, i.user.phoneNumber, i.user.email)" +
            " from Instructor i where i.id = ?1")
    Optional<InstructorResponse> getByInstructorId(Long instructorId);

    @Query("select new com.example.peaksoftlmsb8.dto.response.InstructorResponse(" +
            "i.id,concat(u.firstName,' ',u.lastName),i.special,u.phoneNumber,u.email) " +
            "from User u join Instructor i")
    Page<InstructorResponse> getAllPage(Pageable pageable);
}