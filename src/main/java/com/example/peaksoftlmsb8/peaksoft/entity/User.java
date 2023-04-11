package com.example.peaksoftlmsb8.peaksoft.entity;

import com.example.peaksoftlmsb8.peaksoft.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", allocationSize = 1)

    private Long id;
    private String lastName;
    private String firstName;
    private String email;
    private String password;
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Student student;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Instructor instructor;

}