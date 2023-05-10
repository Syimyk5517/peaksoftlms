package com.example.peaksoftlmsb8.api;

import com.example.peaksoftlmsb8.db.entity.Lesson;
import com.example.peaksoftlmsb8.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/lessons/{lessonId}/links")
public class LinkApi {

    @Autowired
    private LinkService linkService;

    @PostMapping
    public ResponseEntity<Lesson> addLinkToLesson(@PathVariable Long lessonId, @RequestParam String key, @RequestParam String value) {
        Lesson lesson = linkService.addLinkToLesson(lessonId, key, value);
        return new ResponseEntity<>(lesson, HttpStatus.OK);
    }

    @DeleteMapping("/{key}")
    public ResponseEntity<Lesson> removeLinkFromLesson(@PathVariable Long lessonId, @PathVariable String key) {
        Lesson lesson = linkService.removeLinkFromLesson(lessonId, key);
        return new ResponseEntity<>(lesson, HttpStatus.OK);
    }

    @PutMapping("/{key}")
    public ResponseEntity<Lesson> updateLinkInLesson(@PathVariable Long lessonId, @PathVariable String key, @RequestParam String value) {
        Lesson lesson = linkService.updateLinkInLesson(lessonId, key, value);
        return new ResponseEntity<>(lesson, HttpStatus.OK);
    }

    @GetMapping("/{key}")
    public ResponseEntity<String> getLinkFromLesson(@PathVariable Long lessonId, @PathVariable String key) {
        String link = linkService.getLinkFromLesson(lessonId, key);
        return new ResponseEntity<>(link, HttpStatus.OK);
    }
}
