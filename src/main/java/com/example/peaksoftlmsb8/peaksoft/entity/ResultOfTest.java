package com.example.peaksoftlmsb8.peaksoft.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name = "result_of_tests")
@AllArgsConstructor
@NoArgsConstructor
public class ResultOfTest {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "result_of_test_gen")
    @SequenceGenerator(name = "result_of_test_gen", sequenceName = "result_of_test_seq", allocationSize = 1)
    private Long id;
    private int countCorrect;
    private int countInCorrect;
    @OneToOne
    @JoinColumn(name = "student_id")
    private Student student;
    @OneToOne
    @JoinColumn(name = "test_id")
    private Test test;

}