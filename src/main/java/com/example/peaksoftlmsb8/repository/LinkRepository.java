package com.example.peaksoftlmsb8.repository;

import com.example.peaksoftlmsb8.db.entity.Lesson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository

public interface LinkRepository extends CrudRepository<Lesson, Long> {
}