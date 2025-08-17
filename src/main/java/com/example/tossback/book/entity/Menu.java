package com.example.tossback.book.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", columnDefinition = "VARCHAR(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci")
    private String name;

    private int price;

    @Column(name = "content", columnDefinition = "VARCHAR(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci")
    private String content;

    @Column(precision = 3, scale = 2)  // 최대 3자리 중 소수점 2자리
    private BigDecimal score;

    private int bookCount;

    @OneToMany(mappedBy = "menu")
    List<Book> books = new ArrayList<>();

}
