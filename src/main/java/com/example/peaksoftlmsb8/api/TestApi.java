package com.example.peaksoftlmsb8.api;

import com.example.peaksoftlmsb8.dto.request.TestRequest;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.service.TestService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tests")
@RequiredArgsConstructor
@Tag(name = "Tests")
public class TestApi {
    private final TestService testService;
    @PostMapping
    SimpleResponse save(@RequestBody TestRequest testRequest){
        return testService.saveTest(testRequest);
    }
}
