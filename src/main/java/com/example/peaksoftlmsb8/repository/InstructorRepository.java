package com.example.peaksoftlmsb8.repository;

import com.example.peaksoftlmsb8.db.entity.Instructor;
import com.example.peaksoftlmsb8.dto.response.InstructorResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * peaksoftlms-b8
 * 2023
 * macbook_pro
 **/
@Repository
public interface InstructorRepository extends JpaRepository<Instructor,Long> {
    @Query("select new com.example.peaksoftlmsb8.dto.response.InstructorResponse(" +
            "i.id, i.user.firstName, i.user.lastName, i.special, i.user.phoneNumber, i.user.email, i.user.password)" +
            " from Instructor i where i.user.firstName ilike concat('%' , :keyWord, '%') " +
            "or i.user.lastName ilike concat('%' , :keyWord, '%')"+
            "or i.special ilike concat('%' , :keyWord, '%')"+
            "or i.user.email ilike concat('%' , :keyWord, '%')"+
            "or i.user.phoneNumber ilike concat('%' , :keyWord, '%')")
    Page<InstructorResponse> getAll(Pageable pageable, String keyWord);
}