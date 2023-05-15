package com.example.peaksoftlmsb8.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.util.Map;

@Service
public class S3Service {

    private final S3Client s3;

    @Value("${aws.bucket.name}")
    private String bucketName;

    @Value("${aws.bucket.path}")
    private String bucketPath;

    @Autowired
    public S3Service(S3Client s3) {
        this.s3 = s3;
    }

    public Map<String, String> upload(MultipartFile file) throws IOException {

        String key = System.currentTimeMillis() + file.getOriginalFilename();

        PutObjectRequest por = PutObjectRequest
                .builder()
                .bucket(bucketName)
                .contentType("jpeg")
                .contentType("png")
                .contentType("ogg")
                .contentType("mp3")
                .contentType("mpeg")
                .contentType("ogg")
                .contentType("m4a")
                .contentType("oga")
                .contentLength(file.getSize())
                .key(key)
                .build();

        s3.putObject(por, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
        return Map.of(
                "link", bucketPath + key
        );
    }

    public Map<String, String> delete(String fileLink) {

        try {

            String key = fileLink.substring(bucketPath.length());

            s3.deleteObject(dor -> dor.bucket(bucketName).key(key).build());

        } catch (S3Exception e) {
            throw new IllegalStateException(e.awsErrorDetails().errorMessage());
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage());
        }

        return Map.of(
                "message", fileLink + " has been deleted"
        );
    }
}
