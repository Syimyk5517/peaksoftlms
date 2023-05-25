package com.example.peaksoftlmsb8.service;

import com.example.peaksoftlmsb8.dto.request.TaskAnswerRequest;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.dto.response.TaskAnswerResponse;

import java.util.List;

public interface TaskAnswerService {
    SimpleResponse sendTaskAnswer(Long taskId, TaskAnswerRequest taskAnswerRequest);

    SimpleResponse update(Long taskAnswerId, TaskAnswerRequest taskAnswerRequest);

    SimpleResponse delete(Long taskAnswerId);

    List<TaskAnswerResponse> findTaskAnswerByTaskId(Long taskId,String taskAnswerStatus);
}