package com.example.peaksoftlmsb8.service;

import com.example.peaksoftlmsb8.dto.request.TestRequest;
import com.example.peaksoftlmsb8.dto.request.TestUpdateRequest;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.dto.response.test.TestResponse;
import com.example.peaksoftlmsb8.dto.response.test.TestResponseForStudent;

import java.util.List;

public interface TestService {
    List<TestResponse> findAll();
    SimpleResponse saveTest(TestRequest request);
    TestResponse findById(Long id);
    SimpleResponse updateTest(Long testId , TestUpdateRequest testUpdateRequest);
    SimpleResponse deleteById(Long testId);
    TestResponseForStudent findByTestById(Long testId);
}
