package com.example.tossback.mypage.book.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MypageBookResponseDTO {
    private String orderId;
    private String username;
    private String menuName;
    private String content;
    private LocalDateTime bookDate;
    private int price;
}
