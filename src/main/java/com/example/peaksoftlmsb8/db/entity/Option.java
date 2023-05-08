package com.example.peaksoftlmsb8.db.entity;

import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.CascadeType.*;

@Getter
@Setter
@Entity
@Table(name = "options")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "option_gen")
    @SequenceGenerator(name = "option_gen", sequenceName = "option_seq",initialValue = 8,allocationSize = 1)
    private Long id;
    private String text;
    private Boolean isTrue;
    @ManyToOne(cascade = {PERSIST, MERGE, REFRESH, DETACH})
    @JoinColumn(name = "question_id")
    private Question question;

}