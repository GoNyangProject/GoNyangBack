package com.example.tossback.board.dto;

import com.example.tossback.member.dto.MemberResponseDTO;
import com.example.tossback.board.entity.Board;
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
    private String userId;
    private MemberResponseDTO member;
    public static BoardResponseDTO fromEntity(Board board) {
        BoardResponseDTO dto = new BoardResponseDTO();
        dto.setId(board.getId());
        dto.setTitle(board.getTitle());
        dto.setUserId(board.getMember().getUserId());
        dto.setCreatedAt(board.getCreatedAt());
        dto.setViewCount(board.getViewCount());
        return dto;
    }
}
