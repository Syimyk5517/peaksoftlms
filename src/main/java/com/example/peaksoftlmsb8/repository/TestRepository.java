package com.example.peaksoftlmsb8.repository;

import com.example.peaksoftlmsb8.db.entity.Test;
import com.example.peaksoftlmsb8.dto.response.TestResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TestRepository extends JpaRepository<Test, Long> {

}