package com.example.peaksoftlmsb8.repository;

import com.example.peaksoftlmsb8.db.entity.User;
import com.example.peaksoftlmsb8.dto.response.InstructorResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    void deleteUserByStudentId(Long studentId);
}