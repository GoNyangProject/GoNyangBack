package com.example.tossback.member.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true, nullable = false)
    private String userId;

    @Column(name = "username", columnDefinition = "VARCHAR(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci")
    private String username;
    private String password;
}
