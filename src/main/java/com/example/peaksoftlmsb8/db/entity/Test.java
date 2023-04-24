package com.example.peaksoftlmsb8.db.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Getter
@Setter
@Entity
@Table(name = "tests")
@AllArgsConstructor
@NoArgsConstructor
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "test_gen")
    @SequenceGenerator(name = "test_gen", sequenceName = "test_seq",initialValue = 8,allocationSize = 1)
    private Long id;
    private String name;
    private LocalDate dateTest;
    @OneToOne(cascade = {PERSIST, MERGE, REFRESH, DETACH})
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;
    @OneToMany(mappedBy = "test", cascade = ALL)
    private List<Question> questions;

}