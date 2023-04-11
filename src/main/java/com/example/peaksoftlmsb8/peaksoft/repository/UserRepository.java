package com.example.peaksoftlmsb8.peaksoft.repository;

import com.example.peaksoftlmsb8.peaksoft.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}