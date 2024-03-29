package com.example.peaksoftlmsb8.db.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Getter
@Setter
@Entity
@Table(name = "instructors")
@NoArgsConstructor
@AllArgsConstructor
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "instructor_gen")
    @SequenceGenerator(name = "instructor_gen", sequenceName = "instructor_seq", initialValue = 8, allocationSize = 1)
    private Long id;
    private String special;
    @OneToOne(mappedBy = "instructor", cascade = {ALL})
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToMany(cascade = {DETACH, MERGE, PERSIST, REFRESH}, fetch = FetchType.LAZY)
    @JoinTable(name = "course_instructors",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "instructors_id"))
    private List<Course> courses;

    public void addCourse(Course course) {
        if (courses == null) {
            courses = new ArrayList<>();
        }
        courses.add(course);
    }
}