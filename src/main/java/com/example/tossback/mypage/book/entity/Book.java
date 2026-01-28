package com.example.tossback.mypage.book.entity;

import com.example.tossback.common.entity.BaseEntity;
import com.example.tossback.member.entity.Member;
import com.example.tossback.menu.entity.Menu;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Book extends BaseEntity {

    @Id
    private String orderId;
    private LocalDateTime bookDate;
    private int price;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;
    private String reservedPhone;
}