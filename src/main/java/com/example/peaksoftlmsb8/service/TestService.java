package com.example.peaksoftlmsb8.service;

import com.example.peaksoftlmsb8.dto.request.TestRequest;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.dto.response.TestResponse;

import java.util.List;

public interface TestService {
    List<TestResponse> findAll();
    SimpleResponse saveTest(TestRequest request);
    TestResponse findById(Long id);

}
