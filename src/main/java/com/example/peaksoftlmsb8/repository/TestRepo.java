package com.example.peaksoftlmsb8.repository;

import com.example.peaksoftlmsb8.dto.response.test.TestResponse;
import com.example.peaksoftlmsb8.dto.response.test.TestResponseForInstructor;

import java.util.List;

public interface TestRepo {
    List<TestResponseForInstructor> findAll();
    TestResponse findById(Long testId);
}
