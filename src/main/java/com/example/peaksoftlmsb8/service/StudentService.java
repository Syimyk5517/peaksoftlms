package com.example.peaksoftlmsb8.service;

import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StudentService {
    SimpleResponse importExcel(Long groupId, MultipartFile multipartFile) throws IOException;

}
