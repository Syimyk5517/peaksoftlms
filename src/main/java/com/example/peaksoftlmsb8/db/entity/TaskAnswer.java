package com.example.peaksoftlmsb8.db.entity;

import com.example.peaksoftlmsb8.db.enums.TaskAnswerFormat;
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
    @SequenceGenerator(name = "taskAnswer_gen",sequenceName = "taskAnswer_seq", allocationSize = 1, initialValue = 8)
    @GeneratedValue(generator = "taskAnswer_gen", strategy = GenerationType.SEQUENCE)
    private Long id;
    @Enumerated(EnumType.STRING)
    private TaskAnswerFormat taskAnswerFormat;
    private String taskValue;
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
