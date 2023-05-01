package com.example.peaksoftlmsb8.api;

import com.example.peaksoftlmsb8.dto.request.TestRequest;
import com.example.peaksoftlmsb8.dto.request.TestUpdateRequest;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.dto.response.TestResponse;
import com.example.peaksoftlmsb8.service.TestService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tests")
@RequiredArgsConstructor
@Tag(name = "Tests")
public class TestApi {
    private final TestService testService;

    @GetMapping
    public List<TestResponse> findAll() {
        return testService.findAll();
    }

    @PostMapping
    public SimpleResponse save(@RequestBody TestRequest testRequest) {
        return testService.saveTest(testRequest);
    }

    @GetMapping("/findById")
    public TestResponse findById(@RequestParam Long testId) {
        return testService.findById(testId);
    }

    @PutMapping
    public SimpleResponse updateTest(@RequestParam Long testId, @RequestBody TestUpdateRequest testUpdateRequest) {
        return testService.updateTest(testId, testUpdateRequest);
    }

    @DeleteMapping
    public SimpleResponse deleteById(@RequestParam Long testId) {
        return testService.deleteById(testId);
    }
}
