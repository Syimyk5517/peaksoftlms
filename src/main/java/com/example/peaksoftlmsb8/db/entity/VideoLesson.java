package com.example.peaksoftlmsb8.db.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.CascadeType.*;

@Getter
@Setter
@Entity
@Table(name = "video_lessons")
@NoArgsConstructor
@AllArgsConstructor
public class VideoLesson {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "video_lesson_gen")
    @SequenceGenerator(name = "video_lesson_gen", sequenceName = "video_lesson_seq")
    private Long id;
    private String name;
    private String description;
    private String link;
    @ManyToOne(cascade = {PERSIST, MERGE, REFRESH, DETACH})
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

}