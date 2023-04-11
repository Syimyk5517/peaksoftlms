package com.example.peaksoftlmsb8.peaksoft.repository;

import com.example.peaksoftlmsb8.peaksoft.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
}