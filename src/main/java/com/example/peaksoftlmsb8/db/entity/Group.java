package com.example.peaksoftlmsb8.db.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Getter
@Setter
@Entity
@Table(name = "groups")
@AllArgsConstructor
@NoArgsConstructor
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "group_gen")
    @SequenceGenerator(name = "group_gen", sequenceName = "group_seq", initialValue = 8, allocationSize = 1)
    private Long id;
    private String name;
    private LocalDate createdAt;
    private String description;
    private String image;
    private LocalDate finalDate;
    @OneToMany(mappedBy = "group", cascade = ALL)
    private List<Student> students;
    @ManyToMany(mappedBy = "groups", cascade = {PERSIST, MERGE, REFRESH, DETACH})
    private List<Course> courses;

    public void assignCourse(Course course) {
        if (courses == null) {
            courses = new ArrayList<>();
        }
        courses.add(course);
    }
}