package com.example.peaksoftlmsb8.repository;

import com.example.peaksoftlmsb8.dto.response.resultOfTest.ResultOfTestResponseForInstructor;

import java.util.List;

public interface ResultRepository {
    List<ResultOfTestResponseForInstructor> resultTest(Long testId);
}
