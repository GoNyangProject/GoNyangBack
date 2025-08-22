package com.example.tossback.mypage.accountInfo.entity;

import com.example.tossback.common.entity.BaseEntity;
import com.example.tossback.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PetInfo extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String petImagePath;
    private String petName;
    private String catBreed;
    private Integer petAge;
    private Character petGender;
    @Column(columnDefinition = "TEXT")
    private String catNotes;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
