package com.example.peaksoftlmsb8.api;

import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.service.LinkService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lessons/{lessonId}/links")
public class LinkApi {


    private LinkService linkService;

    public LinkApi(LinkService linkService) {
        this.linkService = linkService;
    }


    @PostMapping
    public SimpleResponse addLinkToLesson(@PathVariable Long lessonId, @RequestParam String key, @RequestParam String value) {
       return linkService.addLinkToLesson(lessonId, key, value);
    }

    @DeleteMapping("/{key}")
    public SimpleResponse removeLinkFromLesson(@PathVariable Long lessonId, @PathVariable String key) {
        return linkService.removeLinkFromLesson(lessonId, key);
    }

    @PutMapping("/{key}")
    public SimpleResponse updateLinkInLesson(@PathVariable Long lessonId, @PathVariable String key, @RequestParam String value) {
        return linkService.updateLinkInLesson(lessonId, key, value);
    }

    @GetMapping("/{key}")
    public SimpleResponse getLinkFromLesson(@PathVariable Long lessonId) {
        return linkService.getLinkFromLesson(lessonId);
    }
}
