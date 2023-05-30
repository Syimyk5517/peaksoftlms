package com.example.peaksoftlmsb8.db.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.CascadeType.DETACH;

@Getter
@Setter
@Entity
@Table(name = "result_of_tests")
@AllArgsConstructor
@NoArgsConstructor
public class ResultOfTest {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "result_of_test_gen")
    @SequenceGenerator(name = "result_of_test_gen", sequenceName = "result_of_test_seq",initialValue = 8,allocationSize = 1)
    private Long id;
    private int countCorrect;
    private int countInCorrect;
    @OneToOne (cascade = {PERSIST, MERGE, REFRESH, DETACH})
    @JoinColumn(name = "student_id")
    private Student student;
    @ManyToOne(cascade = {PERSIST, MERGE, REFRESH, DETACH})
    @JoinColumn(name = "test_id")
    private Test test;
    @ElementCollection
    private List<Long> studentAnswers;



}