package peaksoftlms.entity;

import peaksoftlms.entity.enums.FormLearning;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.CascadeType.*;

@Getter
@Setter
@Entity
@Table(name = "students")
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_gen")
    @SequenceGenerator(name = "student_gen", sequenceName = "student_seq")
    private Long id;
    @Enumerated(EnumType.STRING)
    private FormLearning formLearning;
    private Boolean isBlocked;
    private Long rating;
    @OneToOne(mappedBy = "student", cascade = {PERSIST, MERGE, REFRESH, DETACH})
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(cascade = {PERSIST, MERGE, REFRESH, DETACH})
    @JoinColumn(name = "group_id")
    private Group group;


}