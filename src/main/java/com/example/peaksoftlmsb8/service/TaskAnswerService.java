package com.example.peaksoftlmsb8.service;

import com.example.peaksoftlmsb8.dto.request.TaskPointRequest;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;

public interface TaskAnswerService {
    SimpleResponse saveTaskPoint(TaskPointRequest taskPointRequest);

    SimpleResponse updateTaskPoint(TaskPointRequest taskPointRequest);

    SimpleResponse deleteTaskPoint(Long taskAnswerId);
}
