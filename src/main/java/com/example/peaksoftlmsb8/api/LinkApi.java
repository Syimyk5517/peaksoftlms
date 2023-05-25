package com.example.peaksoftlmsb8.api;

import com.example.peaksoftlmsb8.dto.response.LinkResponse;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.service.LinkService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Response;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/lessons/{lessonId}/links")
public class LinkApi {


    private final LinkService linkService;


    @PostMapping
    public SimpleResponse addLinkToLesson(@RequestParam Long lessonId, @RequestParam String key, @RequestParam String value) {
       return linkService.addLinkToLesson(lessonId, key, value);
    }

    @DeleteMapping("/{key}")
    public SimpleResponse removeLinkFromLesson(@RequestParam Long lessonId, @PathVariable String key,@PathVariable String value) {
        return linkService.removeLinkFromLesson(lessonId, key,value);
    }

    @PutMapping("/{key}")
    public SimpleResponse updateLinkInLesson(@RequestParam Long lessonId, @PathVariable String key, @RequestParam String value) {
        return linkService.updateLinkInLesson(lessonId, key, value);
    }

    @GetMapping("/{key}")
    public LinkResponse getLinkFromLesson(@RequestParam Long lessonId, String key) {
        return  linkService.getLinkFromLesson(lessonId,key);
    }
}
