package com.example.peaksoftlmsb8.db.entity;

import com.example.peaksoftlmsb8.db.enums.OptionType;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Getter
@Setter
@Entity
@Table(name = "questions")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "question_gen")
    @SequenceGenerator(name = "question_gen", sequenceName = "question_seq",initialValue = 8,allocationSize = 1)
    private Long id;
    private String questionName;
    @Enumerated(EnumType.STRING)
    private OptionType optionType;
    @ManyToOne(cascade = {PERSIST, MERGE, REFRESH, DETACH},fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id")
    private Test test;
    @OneToMany(mappedBy = "question", cascade = ALL)
    private List<Option> options = new ArrayList<>();

    public void addOption(Option option){
        options.add(option);
    }

}