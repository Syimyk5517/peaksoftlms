package com.example.peaksoftlmsb8.service;

import com.example.peaksoftlmsb8.dto.request.test.TestRequest;
import com.example.peaksoftlmsb8.dto.request.test.TestUpdateRequest;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.dto.response.test.TestResponse;
import com.example.peaksoftlmsb8.dto.response.test.TestResponseForInstructor;

import java.util.List;

public interface TestService {
    List<TestResponseForInstructor> findAll();
    SimpleResponse saveTest(TestRequest request);
    SimpleResponse updateTest(TestUpdateRequest testUpdateRequest);
    SimpleResponse deleteById(Long testId);
    TestResponse findById(Long testId);
}
