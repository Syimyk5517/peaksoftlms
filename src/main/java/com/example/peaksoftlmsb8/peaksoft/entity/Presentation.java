package com.example.peaksoftlmsb8.peaksoft.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "presentations")
@AllArgsConstructor
@NoArgsConstructor
public class Presentation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "presentation_seq")
    @SequenceGenerator(name = "presentation_seq", allocationSize = 1)
    private Long id;
    private String name;
    private String description;
    private String formatPPT;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

}