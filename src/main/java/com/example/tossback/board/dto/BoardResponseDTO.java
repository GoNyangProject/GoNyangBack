package com.example.tossback.board.dto;

import com.example.tossback.member.dto.MemberResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BoardResponseDTO {

    private long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private int viewCount;
    private int likeCount;
    private String imgUrl;
    private MemberResponseDTO member;
}
