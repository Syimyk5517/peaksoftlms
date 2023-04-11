package com.example.peaksoftlmsb8.peaksoft.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "video_lessons")
@NoArgsConstructor
@AllArgsConstructor
public class VideoLesson {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "video_lesson_seq")
    @SequenceGenerator(name = "video_lesson_seq", allocationSize = 1)
    private Long id;
    private String name;
    private String description;
    private String link;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

}