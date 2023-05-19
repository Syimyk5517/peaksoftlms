package com.example.peaksoftlmsb8.db.entity;

import com.example.peaksoftlmsb8.db.enums.TaskFormat;
import com.example.peaksoftlmsb8.db.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "task_answers")
@Getter
@Setter
@NoArgsConstructor
public class TaskAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_answer_gen")
    @SequenceGenerator(name = "task_answer_gen", sequenceName = "task_answer_seq", initialValue = 8, allocationSize = 1)
    private Long id;
    @Enumerated(EnumType.STRING)
    private TaskFormat contentFormat;
    private String contentValue;
    private Integer point;
    @OneToOne(cascade = {
            MERGE,
            PERSIST,
            REFRESH,
            DETACH})
    private Student student;
    @ManyToOne(cascade = {
            MERGE,
            PERSIST,
            REFRESH,
            DETACH})
    private Task task;
    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;


}
