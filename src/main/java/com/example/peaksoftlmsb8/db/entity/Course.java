package com.example.peaksoftlmsb8.db.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
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
@Table(name = "courses")
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_gen")
    @SequenceGenerator(name = "course_gen", sequenceName = "course_seq", initialValue = 8, allocationSize = 1)
    private Long id;
    private String name;
    private String image;
    private String description;
    private LocalDate createdAt;
    private LocalDate finishDate;

    @ManyToMany(mappedBy = "courses", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Instructor> instructors;
    @ManyToMany(cascade = ALL)
    private List<Group> groups;

    @OneToMany(mappedBy = "course", cascade = ALL)
    private List<Lesson> lessons;

    public void assignCourse(Group group) {
        if (groups == null) {
            groups = new ArrayList<>();
        }
        groups.add(group);
    }

    public void addInstructor(Instructor instructor) {
        if (instructors == null) {
            instructors = new ArrayList<>();
        }
        instructors.add(instructor);
    }

}