package com.example.peaksoftlmsb8.service;

import com.example.peaksoftlmsb8.dto.request.resultOfTest.PassTestRequest;
import com.example.peaksoftlmsb8.dto.response.resultOfTest.ResultOfTestResponse;

import java.util.List;

public interface ResultOfTestService {

    List<ResultOfTestResponse> getAll();
    ResultOfTestResponse passTest(PassTestRequest passTestRequest);


}
