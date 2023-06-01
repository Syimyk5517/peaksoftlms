package com.example.peaksoftlmsb8.service;

import com.example.peaksoftlmsb8.dto.request.resultOfTest.PassTestRequest;
import com.example.peaksoftlmsb8.dto.response.resultOfTest.ResultOfTestResponseForInstructor;
import com.example.peaksoftlmsb8.dto.response.resultOfTest.ResultOfTestResponseForStudent;

import java.util.List;

public interface ResultOfTestService {

    ResultOfTestResponseForStudent findResultOfTestByTestIdForStudent (Long testId);

    ResultOfTestResponseForStudent passTest(PassTestRequest passTestRequest);

    List<ResultOfTestResponseForInstructor> findAll(Long testId);

}
