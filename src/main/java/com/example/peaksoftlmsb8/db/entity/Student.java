package com.example.peaksoftlmsb8.db.entity;

import com.example.peaksoftlmsb8.db.enums.FormLearning;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;

import static jakarta.persistence.CascadeType.*;

@Getter
@Setter
@Entity
@Table(name = "students")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_gen")
    @SequenceGenerator(name = "student_gen", sequenceName = "student_seq", initialValue = 8, allocationSize = 1)
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