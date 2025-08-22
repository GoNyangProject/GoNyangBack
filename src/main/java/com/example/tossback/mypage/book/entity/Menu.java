package com.example.tossback.mypage.book.entity;

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

    private String name;

    private int price;

    private String content;

    @Column(precision = 3, scale = 2)  // 최대 3자리 중 소수점 2자리
    private BigDecimal score;

    private int bookCount;

    @OneToMany(mappedBy = "menu", fetch = FetchType.LAZY)
    List<Book> books = new ArrayList<>();

}
