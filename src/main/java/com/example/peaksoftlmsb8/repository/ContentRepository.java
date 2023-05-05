package com.example.peaksoftlmsb8.repository;

import com.example.peaksoftlmsb8.db.entity.Content;
import com.example.peaksoftlmsb8.dto.response.ContentResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {
    List<ContentResponse> findAllByTaskId(Long taskId);
}