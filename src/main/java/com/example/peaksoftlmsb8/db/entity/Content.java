package com.example.peaksoftlmsb8.db.entity;

import com.example.peaksoftlmsb8.db.enums.ContentFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "contents")
@Getter
@Setter
@NoArgsConstructor
public class Content {
    @Id
    @SequenceGenerator(name = "content_gen",sequenceName = "content_seq", allocationSize = 1, initialValue = 8)
    @GeneratedValue(generator = "content_gen", strategy = GenerationType.SEQUENCE)
    private Long id;
    private String contentName;
    @Enumerated(EnumType.STRING)
    private ContentFormat contentFormat;
    private String contentValue;
    @ManyToOne(cascade = {
            MERGE,
            PERSIST,
            REFRESH,
            DETACH})
    private Task task;
}
