package com.example.tossback.board.entity;

import com.example.tossback.common.entity.BaseEntity;
import com.example.tossback.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Setter
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardTypeId")
    private BoardType boardType;

    private String title;

    private String content;

    private int likeCount;

    @ColumnDefault("0")
    private int viewCount = 0;

    private String imgUrl;

    public void incrementViewCount() {
        this.viewCount++;
    }

}
