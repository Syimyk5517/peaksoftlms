package com.example.peaksoftlmsb8.dto.request.videLesson;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoLessonRequest {
    @NotBlank(message = "Video name can't be empty!")
    @NotNull(message = "Video name should not be null")
    private String name;
    @NotBlank(message = "Description description can't be empty!")
    @NotNull(message = "Description should not be null")
    private String description;
    @NotBlank(message = "Video link can't be empty!")
    @Pattern(regexp = "https?:\\/\\/[\\w\\d-]+(\\.[\\w\\d-]+)+([\\w\\d.,@?^=%&:/~+#-]*[\\w\\d@?^=%&/~+#-])?",
            message = "Invalid URL")
    @NotNull(message = "Video link should not be null")
    private String videoLink;
}
