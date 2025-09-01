package com.example.tossback.mypage.book.entity;

import com.example.tossback.menu.entity.Menu;
import com.example.tossback.common.entity.BaseEntity;
import com.example.tossback.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Book extends BaseEntity {

    @Id
    private String uuid = UUID.randomUUID().toString();

    private LocalDateTime bookDate;

    private int price;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;
}