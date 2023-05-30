package com.example.peaksoftlmsb8.service;

import com.example.peaksoftlmsb8.dto.request.resultOfTest.PassTestRequest;
import com.example.peaksoftlmsb8.dto.response.resultOfTest.ResultOfTestResponse;

import java.util.List;

public interface ResultOfTestService {
    ResultOfTestResponse passTest(PassTestRequest passTestRequest);

    List<ResultOfTestResponse> findAllTests();
}
