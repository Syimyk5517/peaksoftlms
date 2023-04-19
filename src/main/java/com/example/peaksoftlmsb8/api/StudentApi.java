package com.example.peaksoftlmsb8.api;

import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentApi {
    private final StudentService studentService;
    @PostMapping()
    public SimpleResponse importExcel(@RequestParam("id")Long id,@RequestParam(name = "file") MultipartFile multipartFile) throws IOException {
        return studentService.importExcel(id,multipartFile);
    }
}

