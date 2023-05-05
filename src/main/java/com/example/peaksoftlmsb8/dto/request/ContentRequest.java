package com.example.peaksoftlmsb8.dto.request;

import com.example.peaksoftlmsb8.db.enums.ContentFormat;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContentRequest {
    private String contentName;
    private ContentFormat contentFormat;
    private String contentValue;
}
