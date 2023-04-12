package peaksoftlms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static jakarta.persistence.CascadeType.*;

@Getter
@Setter
@Entity
@Table(name = "lessons")
@NoArgsConstructor
@AllArgsConstructor
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lesson_gen")
    @SequenceGenerator(name = "lesson_gen", sequenceName = "lesson_seq")
    private Long id;
    private String name;
    private LocalDate createdAt;
    @ElementCollection
    private Map<String, String> link;
    @ManyToOne(cascade = {PERSIST, MERGE, REFRESH, DETACH})
    @JoinColumn(name = "course_id")
    private Course course;
    @OneToMany(mappedBy = "lesson", cascade = ALL)
    private List<Presentation> presentations;
    @OneToMany(mappedBy = "lesson", cascade = ALL)
    private List<VideoLesson> videoLessons;
    @OneToMany(mappedBy = "lesson", cascade = ALL)
    private List<Task> tasks;
    @OneToOne(mappedBy = "lesson", cascade = ALL)
    private Test test;

}