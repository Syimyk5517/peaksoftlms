package com.example.peaksoftlmsb8.dto.response;

import com.example.peaksoftlmsb8.db.enums.ContentFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ContentResponse {
    private Long id;
    private String contentName;
    private ContentFormat contentFormat;
    private String contentValue;
}
